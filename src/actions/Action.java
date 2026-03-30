package actions;

import combatants.Combatant;
import java.util.List;

/**
 * OCP: New action types plug in by implementing this interface.
 * DIP: BattleEngine depends on this abstraction, not concrete actions.
 *
 * All actions return an ActionResult containing what happened
 * (damage, status effects applied, message).
 */
public interface Action {
    ActionResult execute(Combatant user, List<Combatant> targets);
    String getName();
}