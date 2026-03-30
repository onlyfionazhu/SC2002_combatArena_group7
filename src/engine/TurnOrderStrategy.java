package engine;

import combatants.Combatant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// TURN ORDER STRATEGY
// Explanation: The order and sequence of actions depend on the speed stat of the entity. Higher speed goes first

public interface TurnOrderStrategy {
    List<Combatant> getOrder(List<Combatant> all);
}

class SpeedBasedOrder implements TurnOrderStrategy {
    @Override
    public List<Combatant> getOrder(List<Combatant> all) {
        List<Combatant> alive = new ArrayList<>();
        for (Combatant c : all) if (c.isAlive()) alive.add(c);
        alive.sort(Comparator.comparingInt(Combatant::getSpeed).reversed());
        return alive;
    }
}

/* DESIGN PRINCIPLES:
 - DIP: BattleEngine depends on this interface, not SpeedBasedOrder directly.
 - OCP: alternative ordering strategies can be added without changing BattleEngine.
 */
