package effects;

import combatants.Combatant;

/**
 * ARCANE BLAST BUFF:
 * - Each enemy defeated by Arcane Blast adds 10 to Wizard's Attack
 * - Lasts until end of level (never expires)
 */

public class ArcaneBlastEffect implements StatusEffect {
    private static final int PER_KILL = 10;
    private final int kills;
    private boolean applied = false;

    public ArcaneBlastEffect(int kills) {
        this.kills = kills;
    }

    public String getName() {
        return "ARCANE_ATK(+" + (kills * PER_KILL) + ")";
    }

    public void onApply(Combatant target) {
        if (!applied) {
            target.modifyAttack(kills * PER_KILL);
            applied = true;
        }
    }

    public void onTurnStart(Combatant target) {
    }

    public void onTurnEnd(Combatant target) {
    }

    public void onExpire(Combatant target) {
    }

    public boolean isExpired() {
        return false; // Lasts until end of level
    }
}