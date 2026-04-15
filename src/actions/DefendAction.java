package actions;

import combatants.Combatant;
import effects.DefendEffect;
import java.util.List;

public class DefendAction implements Action {

    public ActionResult execute(Combatant user, List<Combatant> targets) {
        user.addEffect(new DefendEffect());

        ActionResult result = new ActionResult();
        result.setMessage(user.getName() + " defends, increasing defense by 10 for 2 turns.");
        return result;
    }

    public String getName() { return "Defend"; }
}