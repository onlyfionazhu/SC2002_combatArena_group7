package effects;

import combatants.Combatant;

public class SmokeBombEffect implements StatusEffect {
    private int turnsRemaining = 2;
    private boolean applied = false;

    public String getName() {
        return "SMOKE";
    }

    public void onApply(Combatant target) {
        target.setDamageImmune(true);
        applied = true;
    }
    
    public void onTurnStart(Combatant target) {
    }

    public void onTurnEnd(Combatant target) {
        if (!applied) return;

        turnsRemaining--;
        if (turnsRemaining <= 0) {
            target.setDamageImmune(false);
            applied = false;
        }
    }

    public void onExpire(Combatant target) {
        if (applied) {
            target.setDamageImmune(false);
            applied = false;
        }
    }

    public boolean isExpired() {
        return turnsRemaining <= 0;
    }
}