package actions;

import combatants.Combatant;
import effects.DefendEffect;
import java.util.List;

/**
 * Defend (spec 3.2):
 * - Increases defense by 10 for the current round and the next round
 */
public class DefendAction implements Action {

    @Override
    public ActionResult execute(Combatant user, List<Combatant> targets) {
        user.addEffect(new DefendEffect());

        ActionResult result = new ActionResult();
        result.setMessage(user.getName() + " defends, increasing defense by 10 for 2 turns.");
        return result;
    }

    @Override
    public String getName() { return "Defend"; }
}