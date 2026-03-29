package action;

import combatant.Combatant;
import core.BattleContext;
import core.ActionResult;

/**
 * all actions (basic attack, defend, special skill) implement this.
 */
public interface Action {
    ActionResult execute(Combatant user, BattleContext context);
}