package items;

import actions.ActionResult;
import combatants.Combatant;
import effects.SmokeBombEffect;
import java.util.List;

public class SmokeBomb implements Item {

    public String getName() { return "Smoke Bomb"; }

    public String getDescription() {
        return "Enemy attacks deal 0 damage this round + next round.";
    }

    public ActionResult use(Combatant user, List<Combatant> targets) {
        user.addEffect(new SmokeBombEffect());

        ActionResult result = new ActionResult();
        result.setMessage(user.getName() + " uses Smoke Bomb! Enemy attacks deal 0 damage for this and next turn.");
        return result;
    }
}