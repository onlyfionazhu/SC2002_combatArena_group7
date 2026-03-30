package effects;

import combatants.Combatant;

/**
 * STUN:
 * - Affected entity cannot act for the current turn and the next turn
 * - Duration: 2 turns total (including the turn it was applied)
 * - Decrements at the START of each of the stunned combatant's turns,
 *   so tickTurnEffects() (called for every combatant) handles the countdown.
 */
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
        //Countdown at turn start so all combatants tick consistently.
        if (turnsRemaining > 0) {
            turnsRemaining--;
        }
    }

    public void onTurnEnd(Combatant target) {
        //Expire only at round end so the target still skips this turn when counter reaches zero.
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
