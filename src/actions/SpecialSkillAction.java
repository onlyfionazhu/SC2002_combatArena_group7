package actions;

import combatants.Combatant;
import combatants.Player;
import combatants.Warrior;
import combatants.Wizard;
import java.util.List;

public class SpecialSkillAction implements Action {
    private final Player player;

    public SpecialSkillAction(Player player) {
        this.player = player;
    }

    public ActionResult execute(Combatant user, List<Combatant> targets) {
        if (!player.isSpecialReady()) {
            System.out.println(user.getName() + " cannot use special skill: on cooldown (" +
                    player.getSpecialCooldown() + " turns left).");
            return null;
        }

        // Call the player's existing special skill method
        ActionResult result = player.useSpecialSkill(targets);

        //Print result message
        if(result.getMessage() != null && !result.getMessage().isEmpty()) {
            System.out.println(result.getMessage());
        }

        player.setSpecialCooldown(3);

        return result;
    }

    public String getName() {
        return player.getSpecialSkillName();
    }
}