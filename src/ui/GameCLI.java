package ui;

import actions.*;
import combatants.*;
import effects.StatusEffect;
import engine.Difficulty;
import engine.Level;
import items.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class GameCLI implements BattleUI {

    private static final String THICK = "═".repeat(60);
    private static final String THIN = "─".repeat(60);

    private final Scanner sc = new Scanner(System.in);
    private String lastActionResult = "";


    public GameSession showLoadingScreen() {
        cls();
        println();
        println();
        println("    TURN-BASED COMBAT ARENA   ·   SC2002");
        println();

        // Players
        println("  PLAYERS");
        println(THIN);
        println("  [1] Warrior");
        println("      HP: 260  |  Attack: 40  |  Defense: 20  |  Speed: 30");
        println("      Special: Shield Bash - BasicAttack dmg + Stun (2 turns)");
        println();
        println("  [2] Wizard");
        println("      HP: 200  |  Attack: 50  |  Defense: 10  |  Speed: 20");
        println("      Special: Arcane Blast - Damage ALL enemies, +10 ATK per kill");
        println();

        // Enemies
        println("  ENEMIES");
        println(THIN);
        println("  Goblin   HP: 55  |  Attack: 35  |  Defense: 15  |  Speed: 25");
        println("  Wolf     HP: 40  |  Attack: 45  |  Defense:  5  |  Speed: 35");
        println();

        // Items
        println("  ITEMS  (choose 2; duplicates allowed; single-use)");
        println(THIN);
        println("  [1] Potion      - Heal 100 HP");
        println("  [2] Power Stone - Free special skill use (no cooldown change)");
        println("  [3] Smoke Bomb  - Enemy attacks deal 0 damage this round + next");
        println();

        // Difficulty
        println("  DIFFICULTY");
        println(THIN);
        println("  [1] Easy    Initial: 3 Goblins    Backup: None");
        println("  [2] Medium  Initial: 1 Goblin + 1 Wolf    Backup: 2 Wolves");
        println("  [3] Hard    Initial: 2 Goblins    Backup: 1 Goblin + 2 Wolves");
        println();
        println(THICK);
        println();

        int pc = promptInt("  Choose character [1-2]: ", 1, 2);
        int i1 = promptInt("  Choose item 1 [1-3]: ", 1, 3);
        int i2 = promptInt("  Choose item 2 [1-3]: ", 1, 3);
        int dc = promptInt("  Choose difficulty [1-3]: ", 1, 3);

        Difficulty diff = Difficulty.values()[dc - 1];
        Player player = buildPlayer(pc);
        List<Item> items = new ArrayList<>();
        items.add(buildItem(i1));
        items.add(buildItem(i2));
        for (Item item : items) {
            player.addItem(item);
        }

        println();
        println(THIN);
        println("  Ready!");
        println("  Character  : " + player.getName());
        println("  Items      : " + items.get(0).getName() + " + " + items.get(1).getName());
        println("  Difficulty : " + diff.name());
        println(THIN);
        pressEnter("  Press ENTER to start...");

        return new GameSession(pc, i1, i2, diff, player, items);
    }

    public GameSession rebuildSession(GameSession prev) {
        Player player = buildPlayer(prev.playerChoice);
        List<Item> items = new ArrayList<>();
        items.add(buildItem(prev.item1Choice));
        items.add(buildItem(prev.item2Choice));
        for (Item item : items) {
            player.addItem(item);
        }
        return new GameSession(prev.playerChoice, prev.item1Choice, prev.item2Choice,
                prev.difficulty, player, items);
    }


    public void onBattleStart(Player player, List<Combatant> enemies, Difficulty diff) {
        println();
        println(THICK);
        println("  BATTLE START  -  " + diff.name());
        print("  Enemies: ");
        for (Combatant e : enemies) {
            print(e.getName() + "  ");
        }
        println();
        println("  Turn order: Highest Speed acts first.");
        println(THICK);
    }

    private static final int BOX_WIDTH = 60;

    public void onRoundStart(int round) {
        String title = " ROUND " + round + " ";
        int innerWidth = BOX_WIDTH - 2;
        int left = (innerWidth - title.length()) / 2;
        int right = innerWidth - title.length() - left;

        println();
        println("┌" + "─".repeat(left) + title + "─".repeat(right) + "┐");
    }

    public void onStatusDisplay(List<Combatant> all) {
        int contentWidth = BOX_WIDTH - 4;
        for (Combatant c : all) {
            String text = c.getStatusLine();
            String padded = padRight(text, contentWidth);
            println("│ " + padded + " │");
        }
        println("└" + "─".repeat(BOX_WIDTH - 2) + "┘");
    }

    private String padRight(String s, int width) {
        if (s.length() >= width) {
            return s.substring(0, width);
        }
        return s + " ".repeat(width - s.length());
    }

    public void onSkipTurn(Combatant c) {
        println("     " + c.getName() + " is STUNNED - turn skipped.");
    }

    public void onActionResult(ActionResult result) {
        if (result.getMessage() != null && !result.getMessage().isEmpty()) {
            println("     " + result.getMessage());
        }
        lastActionResult = result.getMessage();
    }

    public void onActionChosen(Combatant actor, Action action) {
        println("     " + actor.getName() + " --> " + action.getName());
    }

    public void onEnemyAction(Enemy enemy, Combatant target) {
        println("     " + enemy.getName() + " --> Basic Attack --> " + target.getName());
    }

    public void onPostAction(List<Combatant> all) {
        for (Combatant c : all) {
            if (!c.isAlive()) {
                println("       " + c.getName() + "  HP: 0  [ELIMINATED]");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("       ").append(c.getName())
                        .append("  HP: ").append(c.getHp()).append("/").append(c.getMaxHp());
                if (c.isStunned()) sb.append("  [STUNNED]");
                if (c.isDamageImmune()) sb.append("  [IMMUNE]");
                for (StatusEffect e : c.getEffects()) {
                    sb.append("  [").append(e.getName()).append("]");
                }
                println(sb.toString());
            }
        }
    }

    public void onRoundEnd(int round, List<Combatant> all, Player player) {
        println();
        println("  End of Round " + round + ":");
        for (Combatant c : all) {
            if (c.isAlive()) {
                println("  " + c.getStatusLine());
            } else {
                println("    [✗] " + c.getName());
            }
        }

        if (player.getItems().isEmpty()) {
            println("    Items: none remaining");
        } else {
            StringBuilder sb = new StringBuilder("    Items: ");
            for (Item item : player.getItems()) {
                sb.append(item.getName()).append("  ");
            }
            println(sb.toString());
        }

        if (player.isSpecialReady()) {
            println("    Special cooldown: READY");
        } else {
            println("    Special cooldown: " + player.getSpecialCooldown() + " round(s)");
        }
    }

    public void onBackupSpawn(List<Enemy> backup) {
        println();
        println("  !! BACKUP SPAWN !!");
        print("     Entering: ");
        for (Enemy e : backup) {
            print(e.getName() + "  ");
        }
        println();
    }

    public void onVictory(int rounds, Player player) {
        println();
        println(THICK);
        println("  PLAYER VICTORY");
        println(THICK);
        println("  Congratulations, you have defeated all your enemies.");
        println();
        println("  Statistics:");
        println("    Remaining HP    : " + player.getHp() + " / " + player.getMaxHp());
        println("    Total Rounds    : " + rounds);
        if (!player.getItems().isEmpty()) {
            StringBuilder sb = new StringBuilder("    Items remaining: ");
            for (Item item : player.getItems()) {
                sb.append(item.getName()).append("  ");
            }
            println(sb.toString());
        } else {
            println("    Items remaining: none");
        }
        println(THICK);
    }

    public void onDefeat(int rounds, int enemiesLeft) {
        println();
        println(THICK);
        println("  DEFEATED");
        println(THICK);
        println("  Defeated. Don't give up, try again!");
        println();
        println("  Statistics:");
        println("    Enemies remaining   : " + enemiesLeft);
        println("    Total Rounds Survived: " + rounds);
        println(THICK);
    }

    public Action promptAction(Player player, List<Combatant> enemies) {
        println();
        println("  YOUR TURN  ──  " + player.getName()
                + "   HP: " + player.getHp() + "/" + player.getMaxHp()
                + "   ATK: " + player.getAttack() + "   DEF: " + player.getDefense());
        println("  " + THIN.substring(0, 44));

        List<Action> menu = new ArrayList<>();
        int idx = 1;

        println("  [" + idx + "] Basic Attack");
        menu.add(new BasicAttack());
        idx++;

        println("  [" + idx + "] Defend  (+10 DEF for 2 turns)");
        menu.add(new DefendAction());
        idx++;

        for (Item item : player.getItems()) {
            println("  [" + idx + "] Use " + item.getName() + "  - " + item.getDescription());
            menu.add(new ItemAction(item));
            idx++;
        }

        if (player.isSpecialReady()) {
            println("  [" + idx + "] " + player.getSpecialSkillName()
                    + "  - " + player.getSpecialSkillDescription());
            menu.add(new SpecialSkillAction(player));
            idx++;
        } else {
            println("  [ ] " + player.getSpecialSkillName()
                    + "  [COOLDOWN: " + player.getSpecialCooldown() + " turns]");
        }

        println();
        int choice = promptInt("  Choose action [1-" + (idx - 1) + "]: ", 1, idx - 1);
        Action chosen = menu.get(choice - 1);

        boolean needsTarget = (chosen instanceof BasicAttack) ||
                (chosen instanceof SpecialSkillAction && player instanceof Warrior) ||
                (chosen instanceof ItemAction && ((ItemAction) chosen).getItem() instanceof PowerStone && player instanceof Warrior);

        if (needsTarget && enemies.size() > 1) {
            Combatant target = pickTarget(enemies);
            final Action base = chosen;
            final Combatant tgt = target;
            return new Action() {
                public ActionResult execute(Combatant user, List<Combatant> targets) {
                    List<Combatant> single = new ArrayList<>();
                    single.add(tgt);
                    return base.execute(user, single);
                }
                public String getName() {
                    return base.getName() + " --> " + tgt.getName();
                }
            };
        }

        return chosen;
    }

    public int promptPostGame() {
        println();
        println("  [1] Replay with same settings");
        println("  [2] New game (return to home screen)");
        println("  [3] Exit");
        int choice = promptInt("  Choose: ", 1, 3);
        if (choice == 3) {
            println("  Goodbye!");
            System.exit(0);
        }
        return choice;
    }

    private Player buildPlayer(int choice) {
        return choice == 1 ? new Warrior() : new Wizard();
    }

    private Item buildItem(int choice) {
        switch (choice) {
            case 1: return new Potion();
            case 2: return new PowerStone();
            default: return new SmokeBomb();
        }
    }

    private Combatant pickTarget(List<Combatant> enemies) {
        println("  Select target:");
        for (int i = 0; i < enemies.size(); i++) {
            Combatant e = enemies.get(i);
            String stunned = e.isStunned() ? "  [STUNNED]" : "";
            println("    [" + (i + 1) + "] " + e.getName()
                    + "  HP: " + e.getHp() + "/" + e.getMaxHp() + stunned);
        }
        int choice = promptInt("  Target [1-" + enemies.size() + "]: ", 1, enemies.size());
        return enemies.get(choice - 1);
    }

    private int promptInt(String prompt, int min, int max) {
        while (true) {
            print(prompt);
            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                if (value >= min && value <= max) return value;
                println("  Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                println("  Invalid input. Please enter a number.");
            }
        }
    }

    private void pressEnter(String prompt) {
        print(prompt);
        sc.nextLine();
    }

    private void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void println() { System.out.println(); }
    private void println(String s) { System.out.println(s); }
    private void print(String s) { System.out.print(s); }
}
