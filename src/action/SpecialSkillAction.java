package action;

import combatant.Combatant;
import core.BattleContext;
import core.ActionResult;

/**
 * Abstract base for special skills with cooldown.
 * Concrete subclasses implement the actual effect.
 */
public abstract class SpecialSkillAction implements Action {
    protected int cooldownDuration = 3; // turns until reuse
    protected int remainingCooldown = 0;

    public boolean isAvailable(Combatant user) {
        return remainingCooldown == 0;
    }

    @Override
    public ActionResult execute(Combatant user, BattleContext context) {
        if (!isAvailable(user)) {
            ActionResult result = new ActionResult();
            result.setMessage(user.getName() + " cannot use special skill: on cooldown (" + remainingCooldown + " turns left).");
            return result;
        }
        ActionResult result = doExecute(user, context);
        remainingCooldown = cooldownDuration;
        return result;
    }

    // Execute without affecting cooldown (for Power Stone item).

    public ActionResult executeWithoutCooldown(Combatant user, BattleContext context) {
        return doExecute(user, context);
    }

    protected abstract ActionResult doExecute(Combatant user, BattleContext context);

    // Called by battle engine at end of turn
    public void decrementCooldown() {
        if (remainingCooldown > 0) remainingCooldown--;
    }

    public int getRemainingCooldown() { return remainingCooldown; }
}