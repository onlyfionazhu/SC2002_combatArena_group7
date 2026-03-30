package combatants;

import actions.ActionResult;
import effects.StatusEffect;
import items.Item;
import java.util.ArrayList;
import java.util.List;

public abstract class Player extends AbstractCombatant {
    protected final List<Item> items = new ArrayList<>();
    protected int specialCooldown = 0;

    protected Player(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
    }

    // Inventory management
    public void addItem(Item item) { items.add(item); }
    public void removeItem(Item item) { items.remove(item); }
    public List<Item> getItems() { return new ArrayList<>(items); }
    public boolean hasItems() { return !items.isEmpty(); }

    // Special skill cooldown
    public boolean isSpecialReady() { return specialCooldown == 0; }
    public int getSpecialCooldown() { return specialCooldown; }
    public void setSpecialCooldown(int cooldown) { this.specialCooldown = cooldown; }
    public void decrementCooldown() {
        if (specialCooldown > 0) specialCooldown--;
    }

    // Abstract methods for special skills
    public abstract ActionResult useSpecialSkill(List<Combatant> targets);
    public abstract String getSpecialSkillName();
    public abstract String getSpecialSkillDescription();

    protected List<StatusEffect> effects = new ArrayList<>();

    public List<StatusEffect> getEffects() {
        return effects;
    }

    public void addEffect(StatusEffect effect) {
        effects.add(effect);
    }
}