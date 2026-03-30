package items;

import actions.ActionResult;
import combatants.Combatant;
import combatants.Player;
import java.util.List;

/**
 * Power Stone:
 * - Trigger the special skill effect once
 * - Does NOT start or change the cooldown timer
 * - Free extra use of the skill
 */

public class PowerStone implements Item {

    @Override
    public String getName() { return "Power Stone"; }

    @Override
    public String getDescription() {
        return "Trigger special skill once. Does NOT change cooldown.";
    }

    @Override
    public ActionResult use(Combatant user, List<Combatant> targets) {
        ActionResult result = new ActionResult();

        if (!(user instanceof Player)) {
            result.setMessage("Only players can use Power Stone.");
            return result;
        }

        Player player = (Player) user;
        int savedCooldown = player.getSpecialCooldown();

        // Use special skill without affecting cooldown
        ActionResult skillResult = player.useSpecialSkill(targets);

        // Restore original cooldown
        player.setSpecialCooldown(savedCooldown);

        result.setMessage(player.getName() + " uses Power Stone! " +
                skillResult.getMessage() + "\n(Cooldown unchanged: " + savedCooldown + " turns)");

        return result;
    }
}