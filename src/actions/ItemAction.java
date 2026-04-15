package actions;

import combatants.Combatant;
import combatants.Player;
import items.Item;
import java.util.List;

public class ItemAction implements Action {
    private final Item item;

    public ItemAction(Item item) { this.item = item; }

    public ActionResult execute(Combatant user, List<Combatant> targets) {
        ActionResult result = item.use(user, targets);

        if (user instanceof Player) {
            ((Player) user).removeItem(item);
        }

        return result;
    }

    public String getName() { return "Use " + item.getName(); }

    public Item getItem() { return item; }
}