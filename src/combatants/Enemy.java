package combatants;

import actions.Action;
import actions.BasicAttack;
import java.util.List;

/**
 * Abstract enemy. Always uses BasicAttack.
 */

public abstract class Enemy extends AbstractCombatant {

    protected Enemy(String name, int hp, int attack, int defense, int speed) {
        super(name, hp, attack, defense, speed);
    }

    public void performAction(List<Combatant> targets) {
        Action action = new BasicAttack();
        action.execute(this, targets);
    }

    protected Action chooseAction() {
        return new BasicAttack();
    }
}