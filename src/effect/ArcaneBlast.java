package effect;

import combatant.AbstractCombatant;
import combatant.Combatant;

public class ArcaneBlast implements StatusEffect {
    private int stacks = 0;

    public String getName() {
        return "Arcane Blast";
    }

    public void onApply(Combatant target) {
    }

    public void onTurnStart(Combatant target) {
    }

    public void onTurnEnd(Combatant target) {
    }

    public void onExpire(Combatant target) {
    }

    public boolean preventsAction() {
        return false;
    }

    public boolean isExpired() {
        return false;
    }

    public void addStack(Combatant target) {
        stacks++;
        if (target instanceof AbstractCombatant combatant) {
            combatant.increaseAttack(10);
        }
    }

    public int getStacks() {
        return stacks;
    }
}