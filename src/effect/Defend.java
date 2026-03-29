package effect;

import combatant.AbstractCombatant;
import combatant.Combatant;

public class Defend implements StatusEffect {
    private static final int DEFENSE_BONUS = 10;
    private int turnsRemaining = 2;
    private boolean applied = false;

    public String getName() {
        return "Defend";
    }

    public void onApply(Combatant target) {
        if (!applied && target instanceof AbstractCombatant combatant) {
            combatant.increaseDefense(DEFENSE_BONUS);
            applied = true;
        }
    }

    public void onTurnStart(Combatant target) {
    }

    public void onTurnEnd(Combatant target) {
        turnsRemaining--;
    }

    public void onExpire(Combatant target) {
        if (applied && target instanceof AbstractCombatant combatant) {
            combatant.increaseDefense(-DEFENSE_BONUS);
            applied = false;
        }
    }

    public boolean preventsAction() {
        return false;
    }

    public boolean isExpired() {
        return turnsRemaining <= 0;
    }
}
