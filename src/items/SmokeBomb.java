package items;

import actions.ActionResult;
import combatants.Combatant;
import effects.SmokeBombEffect;
import java.util.List;

/**
 * Smoke Bomb:
 * - When used, Enemy attacks do 0 damage in the current turn and the next turn
 * - Applies SmokeBombInvulnerability effect to the user
 */

public class SmokeBomb implements Item {

    @Override
    public String getName() { return "Smoke Bomb"; }

    @Override
    public String getDescription() {
        return "Enemy attacks deal 0 damage this round + next round.";
    }

    @Override
    public ActionResult use(Combatant user, List<Combatant> targets) {
        user.addEffect(new SmokeBombEffect());

        ActionResult result = new ActionResult();
        result.setMessage(user.getName() + " uses Smoke Bomb! Enemy attacks deal 0 damage for this and next turn.");
        return result;
    }
}