package item;

import action.ArcaneBlastAction;
import action.ShieldBashAction;
import action.SpecialSkillAction;
import combatant.Combatant;
import core.BattleContext;

public class PowerStone implements Item {
    @Override
    public String getName() { return "Power Stone"; }

    @Override
    public void use(Combatant user, BattleContext context) {
        // We need to get the user's special skill action.
        // In a full integration, the user (Player) would have a method getSpecialAction().
        // For now, we assume the battle engine passes the appropriate action.
        // This is a simplified version: we look up the action based on class.
        SpecialSkillAction special = getSpecialActionFor(user);
        if (special != null) {
            special.executeWithoutCooldown(user, context);
            System.out.println(user.getName() + " uses Power Stone and gains a free special skill use!");
        } else {
            System.err.println("Power Stone cannot be used: no special skill found.");
        }
    }

    private SpecialSkillAction getSpecialActionFor(Combatant user) {
        if (user instanceof combatant.Warrior) {
            return new ShieldBashAction();
        } else if (user instanceof combatant.Wizard) {
            return new ArcaneBlastAction();
        }
        return null;
    }
}