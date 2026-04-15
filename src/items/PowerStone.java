package items;

import actions.ActionResult;
import combatants.Combatant;
import combatants.Player;
import java.util.List;

public class PowerStone implements Item {

    public String getName() { return "Power Stone"; }

    public String getDescription() {
        return "Trigger special skill once. Does NOT change cooldown.";
    }

    public ActionResult use(Combatant user, List<Combatant> targets) {
        ActionResult result = new ActionResult();

        if (!(user instanceof Player)) {
            result.setMessage("Only players can use Power Stone.");
            return result;
        }

        Player player = (Player) user;
        int savedCooldown = player.getSpecialCooldown();

        ActionResult skillResult = player.useSpecialSkill(targets);

        player.setSpecialCooldown(savedCooldown);

        result.setMessage(player.getName() + " uses Power Stone! " +
                skillResult.getMessage() + "\n(Cooldown unchanged: " + savedCooldown + " turns)");

        return result;
    }
}