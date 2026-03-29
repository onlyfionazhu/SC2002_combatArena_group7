package action;

import combatant.Combatant;
import combatant.Warrior;
import core.BattleContext;
import core.ActionResult;

public class ShieldBashAction extends SpecialSkillAction {
    public ShieldBashAction() {
        this.cooldownDuration = 3;
    }

    @Override
    protected ActionResult doExecute(Combatant user, BattleContext context) {
        if (!(user instanceof Warrior)) {
            throw new IllegalStateException("Only Warrior can use Shield Bash");
        }
        Combatant target = context.getCurrentTarget();
        if (target == null || !target.isAlive()) {
            ActionResult result = new ActionResult();
            result.setMessage("No valid target for Shield Bash.");
            return result;
        }

        int hpBefore = target.getHp();
        /*
         Use the existing Warrior method (if available) or do manually
         To avoid modifying Warrior, we replicate the logic:
         */
        target.takeRawDamage(user.getAttack());
        int damageDealt = hpBefore - target.getHp();
        target.addEffect(new effect.Stun());  // Stun lasts 2 turns

        ActionResult result = new ActionResult();
        result.setDamageDealt(damageDealt);
        result.setStunned(true);
        result.setMessage(user.getName() + " performs Shield Bash on " + target.getName() +
                " for " + damageDealt + " damage and stuns them!");
        return result;
    }
}