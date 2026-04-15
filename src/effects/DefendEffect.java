package effects;

import combatants.Combatant;

public class DefendEffect implements StatusEffect {
    private static final int DEFENSE_BONUS = 10;
    private int turnsRemaining = 2;
    private boolean applied = false;

    public String getName() {
        return "Defend";
    }

    public void onApply(Combatant target) {
        if (!applied ) {
            target.modifyDefense(DEFENSE_BONUS);
            applied = true;
        }
    }

    public void onTurnStart(Combatant target) {
    }

    public void onTurnEnd(Combatant target) {
        if (!applied) return;

        turnsRemaining--;
        if (turnsRemaining <= 0) {
            target.modifyDefense(-DEFENSE_BONUS);
            applied = false;
        }
    }

    public void onExpire(Combatant target) {
        if (applied) {
            target.modifyDefense(-DEFENSE_BONUS);
            applied = false;
        }
    }

    public boolean isExpired() {
        return turnsRemaining <= 0;
    }
}
