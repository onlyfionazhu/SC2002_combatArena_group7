package Main;

import combatant.Combatant;
import combatant.Goblin;
import combatant.Warrior;
import combatant.Wizard;
import combatant.Wolf;

public class Main {
    public static void main(String[] args) {
        Warrior warrior = new Warrior();
        Wizard wizard = new Wizard();
        Goblin goblin = new Goblin();
        Wolf wolf = new Wolf();

        warrior.useShieldBash(goblin);
        wizard.useArcaneBlast(new Combatant[]{goblin, wolf});

        System.out.println("Warrior HP: " + warrior.getHp());
        System.out.println("Wizard HP: " + wizard.getHp());
        System.out.println("Goblin HP: " + goblin.getHp());
        System.out.println("Wolf HP: " + wolf.getHp());
    }
}