package items;

import actions.ActionResult;
import combatants.Combatant;
import java.util.List;

/**
 * Potion:
 * - Heal 100 HP
 * - New HP = min(Current HP + Heal Amount, Max HP)
 */

public class Potion implements Item {
    private static final int HEAL_AMOUNT = 100;

    @Override
    public String getName() { return "Potion"; }

    @Override
    public String getDescription() { return "Heal 100 HP (capped at max HP)."; }

    @Override
    public ActionResult use(Combatant user, List<Combatant> targets) {
        int oldHp = user.getHp();
        user.heal(HEAL_AMOUNT);
        int healed = user.getHp() - oldHp;

        ActionResult result = new ActionResult();
        result.setMessage(user.getName() + " uses Potion and heals " + healed + " HP.");
        return result;
    }
}