package effect;

import combatant.Combatant;

public class Stun implements StatusEffect {
    private int turnsRemaining = 2;

    public String getName() {
        return "Stun";
    }

    public void onApply(Combatant target) {
    }

    public void onTurnStart(Combatant target) {
    }

    public void onTurnEnd(Combatant target) {
        turnsRemaining--;
    }

    public void onExpire(Combatant target) {
    }

    public boolean preventsAction() {
        return turnsRemaining > 0;
    }

    public boolean isExpired() {
        return turnsRemaining <= 0;
    }
}