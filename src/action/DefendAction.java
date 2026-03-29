package action;

import combatant.Combatant;
import core.BattleContext;
import core.ActionResult;
import effect.Defend;

public class DefendAction implements Action {
    @Override
    public ActionResult execute(Combatant user, BattleContext context) {
        // apply the Defend status effect (lasts 2 turns)
        user.addEffect(new Defend());
        ActionResult result = new ActionResult();
        result.setMessage(user.getName() + " defends, increasing defense by 10 for 2 turns.");
        return result;
    }
}