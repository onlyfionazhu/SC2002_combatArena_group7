package item;

import combatant.Combatant;
import core.BattleContext;

public class Potion implements Item {
    private static final int HEAL_AMOUNT = 100;

    @Override
    public String getName() { return "Potion"; }

    @Override
    public void use(Combatant user, BattleContext context) {
        int oldHp = user.getHp();
        user.heal(HEAL_AMOUNT);
        int healed = user.getHp() - oldHp;
        System.out.println(user.getName() + " uses Potion and heals " + healed + " HP.");
    }
}