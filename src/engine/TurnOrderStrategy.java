package engine;

import combatants.Combatant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public interface TurnOrderStrategy {
    List<Combatant> getOrder(List<Combatant> all);
}

class SpeedBasedOrder implements TurnOrderStrategy {
    public List<Combatant> getOrder(List<Combatant> all) {
        List<Combatant> alive = new ArrayList<>();
        for (Combatant c : all) if (c.isAlive()) alive.add(c);
        alive.sort(Comparator.comparingInt(Combatant::getSpeed).reversed());
        return alive;
    }
}
