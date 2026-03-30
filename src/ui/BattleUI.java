package ui;

import actions.Action;
import actions.ActionResult;
import combatants.Combatant;
import combatants.Enemy;
import combatants.Player;
import engine.Difficulty;
import java.util.List;

/**
 * BattleUI — I/O contract between BattleEngine and presentation layer.
 * SRP: BattleEngine never touches System.in/out.
 * DIP: BattleEngine depends on this abstraction, not GameCLI directly.
 */
public interface BattleUI {
    void onBattleStart(Player player, List<Combatant> enemies, Difficulty diff);
    void onRoundStart(int round);
    void onStatusDisplay(List<Combatant> all);
    void onSkipTurn(Combatant c);
    void onActionResult(ActionResult result);
    void onActionChosen(Combatant actor, Action action);
    void onEnemyAction(Enemy enemy, Combatant target);
    void onPostAction(List<Combatant> all);
    void onRoundEnd(int round, List<Combatant> all, Player player);
    void onBackupSpawn(List<Enemy> backup);
    void onVictory(int rounds, Player player);
    void onDefeat(int rounds, int enemiesLeft);

    Action promptAction(Player player, List<Combatant> aliveEnemies);
    int promptPostGame();
}