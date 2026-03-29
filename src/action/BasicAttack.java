package action;

import combatant.Combatant;
import core.BattleContext;
import core.ActionResult;

public class BasicAttack implements Action {
    @Override
    public ActionResult execute(Combatant user, BattleContext context) {
        Combatant target = context.getCurrentTarget();
        if (target == null || !target.isAlive()) {
            ActionResult result = new ActionResult();
            result.setMessage("No valid target.");
            return result;
        }

        int rawDamage = user.getAttack();
        int hpBefore = target.getHp();
        target.takeRawDamage(rawDamage);
        int damageDealt = hpBefore - target.getHp();

        ActionResult result = new ActionResult();
        result.setDamageDealt(damageDealt);
        result.setMessage(user.getName() + " attacks " + target.getName() +
                " for " + damageDealt + " damage!");
        return result;
    }
}