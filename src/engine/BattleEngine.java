package engine;

import actions.Action;
import actions.ActionResult;
import actions.BasicAttack;
import actions.ItemAction;
import actions.SpecialSkillAction;
import combatants.Combatant;
import combatants.Enemy;
import combatants.Player;
import items.PowerStone;
import ui.BattleUI;
import java.util.ArrayList;
import java.util.List;

/**
 * BattleEngine — central game-loop controller.
 *
 * DIP: Depends on TurnOrderStrategy, Action, BattleUI abstractions.
 * SRP: BattleEngine never does I/O — delegates entirely to BattleUI.
 * OCP: New actions/items added without modifying this class.
 *
 * Game flow per spec (Appendix A):
 *   1. Backup spawn check
 *   2. Display status
 *   3. Build turn order
 *   4. For each combatant: tick effects → if stunned skip → execute action
 *   5. End-of-round effects
 */
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

    public boolean isPlayerWon() { return playerWon; }
    public int getRound() { return round; }
    public Player getPlayer() { return player; }

    private void runRound() {
        ui.onRoundStart(round);

        // 1. Backup spawn check
        checkBackup();

        // 2. Status display
        ui.onStatusDisplay(combatants);

        // 3. Turn order
        List<Combatant> order = turnOrder.getOrder(combatants);

        // 4. Each combatant's turn
        for (Combatant current : order) {
            if (!current.isAlive()) continue;
            if (over) break;

            // Tick turn-based effects at START of this combatant's turn
            current.tickTurnEffects();

            if (!current.isAlive()) continue;

            // Check stun AFTER ticking (stun may expire this turn)
            if (current.isStunned()) {
                ui.onSkipTurn(current);
                continue;
            }

            // Execute turn
            if (current instanceof Player) {
                doPlayerTurn((Player) current);
                ((Player) current).decrementCooldown();
            } else if (current instanceof Enemy) {
                doEnemyTurn((Enemy) current);
            }

            // Check end condition
            if (checkEnd()) break;
        }

        // 5. Tick round-end effects on player (SmokeBomb)
        player.tickRoundEndEffects();

        // 6. End-of-round summary
        if (!over) {
            ui.onRoundEnd(round, combatants, player);
        }
    }

    private void doPlayerTurn(Player player) {
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