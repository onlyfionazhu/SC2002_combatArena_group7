package effects;

import combatants.Combatant;

public class StunEffect implements StatusEffect {
    private int turnsRemaining = 2;
    private boolean expired = false;

    public String getName() {
        return "Stun";
    }

    public void onApply(Combatant target) {
        target.setStunned(true);
    }

    public void onTurnStart(Combatant target) {
        if (turnsRemaining > 0) {
            turnsRemaining--;
        }
    }

    public void onTurnEnd(Combatant target) {
        if (!expired && turnsRemaining <= 0) {
            target.setStunned(false);
            expired = true;
        }
    }

    public void onExpire(Combatant target) {
        target.setStunned(false);
    }

    public boolean isExpired() {
        return expired;
    }
}
