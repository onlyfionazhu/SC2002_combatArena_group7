package item;

import combatant.Combatant;
import core.BattleContext;
import effect.SmokeBombInvulnerability;

public class SmokeBomb implements Item {
    @Override
    public String getName() { return "Smoke Bomb"; }

    @Override
    public void use(Combatant user, BattleContext context) {
        // Apply SmokeBombInvulnerability to the user (player)
        user.addEffect(new SmokeBombInvulnerability());
        System.out.println(user.getName() + " uses Smoke Bomb! Enemy attacks deal 0 damage for this and next turn.");
    }
}