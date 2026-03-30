package effects;

import combatants.Combatant;

/**
 * STUN:
 * - Affected entity cannot act for the current turn and the next turn
 * - Duration: 2 turns total (including the turn it was applied)
 * - We decrement ONLY at turn end
 */
public class StunEffect implements StatusEffect {
    private int turnsRemaining = 2;
    private boolean applied = false;

    public String getName() {
        return "Stun";
    }

    public void onApply(Combatant target) {
        target.setStunned(true);
        applied = true;
    }

    public void onTurnStart(Combatant target) {
        // Do nothing
        // Stun should already block action through target.isStunned()
    }

    public void onTurnEnd(Combatant target) {
        if (!applied) return;

        turnsRemaining--;

        if (turnsRemaining <= 0) {
            target.setStunned(false);
            applied = false;
        }
    }

    public void onExpire(Combatant target) {
        target.setStunned(false);
        applied = false;
    }

    public boolean isExpired() {
        return turnsRemaining <= 0;
    }
}