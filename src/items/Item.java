package items;

import actions.ActionResult;
import combatants.Combatant;
import java.util.List;

/**
 * OCP: New item types plug in by implementing this interface.
 * Items return ActionResult to provide feedback to the battle engine.
 */

public interface Item {
    String getName();
    String getDescription();
    ActionResult use(Combatant user, List<Combatant> targets);
}