package actions;

import combatants.Combatant;
import java.util.List;

public class BasicAttack implements Action {

    public ActionResult execute(Combatant user, List<Combatant> targets) {
        ActionResult result = new ActionResult();

        if (targets == null || targets.isEmpty()) {
            result.setMessage("No valid target.");
            return result;
        }

        Combatant target = targets.get(0);
        int damage = Math.max(0, user.getAttack() - target.getDefense());
        int hpBefore = target.getHp();
        target.takeRawDamage(damage);
        int actualDamage = hpBefore - target.getHp();

        result.setDamageDealt(actualDamage);
        result.setMessage(user.getName() + " attacks " + target.getName() +
                " for " + actualDamage + " damage!");
        return result;
    }

    public String getName() { return "Basic Attack"; }
}