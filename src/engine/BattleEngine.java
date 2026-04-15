package engine;

import actions.Action;
import actions.ActionResult;
import actions.BasicAttack;
import combatants.Combatant;
import combatants.Enemy;
import combatants.Player;
import java.util.ArrayList;
import java.util.List;
import ui.BattleUI;

public class BattleEngine {

    private final Player player;
    private final List<Combatant> combatants = new ArrayList<>();
    private final TurnOrderStrategy turnOrder = new SpeedBasedOrder();
    private final Level level;
    private final BattleUI ui;

    private int round = 0;
    private boolean over = false;
    private boolean playerWon = false;

    public BattleEngine(Player player, Level level, BattleUI ui) {
        this.player = player;
        this.level = level;
        this.ui = ui;
        combatants.add(player);
        combatants.addAll(level.getInitialWave());
    }


    public void run() {
        ui.onBattleStart(player, getAliveEnemies(), level.getDifficulty());
        while (!over) {
            round++;
            runRound();
        }
    }
    public boolean isPlayerWon() {
        return playerWon; 
    }
    public int getRound() { 
        return round; 
    }
    public Player getPlayer() { 
        return player; 
    }


    private void runRound() {
        ui.onRoundStart(round);

        checkBackup(); 

        ui.onStatusDisplay(combatants); 

        List<Combatant> order = turnOrder.getOrder(combatants); 

        
        for (Combatant current : order) { 

            if (!current.isAlive()) continue;
            if (over) break;

            current.tickTurnEffects();

            if (!current.isAlive()) continue;

            if (current.isStunned()) {
                ui.onSkipTurn(current);
                continue;
            }

            if (current instanceof Player) {
                doPlayerTurn((Player) current);
            } else if (current instanceof Enemy) {
                doEnemyTurn((Enemy) current);
            }

            if (checkEnd()) break;
        }

        for (Combatant c : combatants) {
            if (c.isAlive()) c.tickRoundEndEffects();
        }

        if (!over) { 
            ui.onRoundEnd(round, combatants, player);
        }
    }


    private void doPlayerTurn(Player player) {
        player.decrementCooldown();

        List<Combatant> enemies = getAliveEnemies();
        Action action = ui.promptAction(player, enemies);

        ui.onActionChosen(player, action);
        ActionResult result = action.execute(player, enemies);
        ui.onActionResult(result);
        ui.onPostAction(combatants);
    }

    private void doEnemyTurn(Enemy enemy) {
        List<Combatant> targets = new ArrayList<>();
        targets.add(player);

        ui.onEnemyAction(enemy, player);
        ActionResult result = new BasicAttack().execute(enemy, targets);
        ui.onActionResult(result);
        ui.onPostAction(combatants);
    }


    private void checkBackup() {
        if (!level.hasBackup()) return;

        boolean allDead = combatants.stream()
                .filter(c -> c instanceof Enemy)
                .allMatch(c -> !c.isAlive());

        if (allDead) {
            List<Enemy> backup = level.triggerBackup();
            combatants.addAll(backup);
            ui.onBackupSpawn(backup);
        }
    }


    private boolean checkEnd() {
        if (!player.isAlive()) {
            over = true;
            playerWon = false;
            ui.onDefeat(round, aliveEnemyCount());
            return true;
        }

        boolean allDead = combatants.stream()
                .filter(c -> c instanceof Enemy)
                .allMatch(c -> !c.isAlive());

        if (allDead && !level.hasBackup()) {
            over = true;
            playerWon = true;
            ui.onVictory(round, player);
            return true;
        }

        return false;
    }


    private List<Combatant> getAliveEnemies() {
        List<Combatant> list = new ArrayList<>();
        for (Combatant c : combatants) {
            if (c instanceof Enemy && c.isAlive()) {
                list.add(c);
            }
        }
        return list;
    }

    private int aliveEnemyCount() {
        return (int) combatants.stream()
                .filter(c -> c instanceof Enemy && c.isAlive())
                .count();
    }
}

