package item;

import combatant.Combatant;
import core.BattleContext;

/**
 * All items implement this.
 */
public interface Item {
    String getName();
    void use(Combatant user, BattleContext context);
}