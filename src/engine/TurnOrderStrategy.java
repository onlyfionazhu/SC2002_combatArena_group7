package engine;

import combatants.Combatant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * TurnOrderStrategy (spec Turn Order Strategy Flow):
 * - The order and sequence of actions depend on the speed stat
 * - Higher speed goes first
 *
 * DIP: BattleEngine depends on this interface.
 * OCP: New ordering strategies can be added without modifying BattleEngine.
 */
public interface TurnOrderStrategy {
    List<Combatant> getOrder(List<Combatant> all);
}

class SpeedBasedOrder implements TurnOrderStrategy {
    @Override
    public List<Combatant> getOrder(List<Combatant> all) {
        List<Combatant> alive = new ArrayList<>();
        for (Combatant c : all) {
            if (c.isAlive()) alive.add(c);
        }
        alive.sort(Comparator.comparingInt(Combatant::getSpeed).reversed());
        return alive;
    }
}