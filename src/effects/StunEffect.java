package effects;

import combatants.Combatant;

/**
 * STUN:
 * - Affected entity cannot act for the current turn and the next turn
 * - Duration: 2 turns (including the turn it was applied)
 * Fixed timing: Stun persists for the remainder of current turn + next full turn.
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
        if (!applied) return;

        // Don't decrement on the first turn (the turn it was applied)
        // We only decrement at the start of subsequent turns
        if (turnsRemaining == 2) {
            turnsRemaining--;  // Mark that we've processed the current turn
        } else {
            turnsRemaining--;
            if (turnsRemaining <= 0) {
                target.setStunned(false);
            }
        }
    }

    public void onTurnEnd(Combatant target) {
        turnsRemaining--;
    }

    public void onExpire(Combatant target) {
        target.setStunned(false);
    }

    public boolean isExpired() {
        return turnsRemaining <= 0;
    }
}