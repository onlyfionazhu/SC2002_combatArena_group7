package combatants;

import actions.Action;
import actions.BasicAttack;
import java.util.List;

public abstract class Enemy extends AbstractCombatant {

    protected Enemy(String name, int hp, int attack, int defense, int speed) {
        super(name, hp, attack, defense, speed);
    }
}