package actions;

import combatants.Combatant;
import java.util.List;

public interface Action {
    ActionResult execute(Combatant user, List<Combatant> targets);
    String getName();
}