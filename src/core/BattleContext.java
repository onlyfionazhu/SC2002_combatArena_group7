package core;

import combatant.Combatant;
import java.util.List;

/**
 * provides the battle state needed for actions/items.
 */
public class BattleContext {
    private final List<Combatant> allies;
    private final List<Combatant> enemies;
    private Combatant currentTarget;  // set by UI before action execution

    public BattleContext(List<Combatant> allies, List<Combatant> enemies) {
        this.allies = allies;
        this.enemies = enemies;
    }

    public List<Combatant> getAllies() { return allies; }
    public List<Combatant> getEnemies() { return enemies; }
    public Combatant getCurrentTarget() { return currentTarget; }
    public void setCurrentTarget(Combatant target) { this.currentTarget = target; }
}