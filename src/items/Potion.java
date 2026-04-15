package items;

import actions.ActionResult;
import combatants.Combatant;
import java.util.List;

public class Potion implements Item {
    private static final int HEAL_AMOUNT = 100;

    public String getName() { return "Potion"; }

    public String getDescription() { return "Heal 100 HP (capped at max HP)."; }

    public ActionResult use(Combatant user, List<Combatant> targets) {
        int oldHp = user.getHp();
        user.heal(HEAL_AMOUNT);
        int healed = user.getHp() - oldHp;

        ActionResult result = new ActionResult();
        result.setMessage(user.getName() + " uses Potion and heals " + healed + " HP.");
        return result;
    }
}