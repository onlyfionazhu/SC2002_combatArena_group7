package combatant;

import effect.StatusEffect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractCombatant implements Combatant {
    private final String name;
    private final int maxHp;

    protected int hp;
    protected int attack;
    protected int defense;
    protected int speed;

    protected final List<StatusEffect> effects;

    protected AbstractCombatant(String name, int maxHp, int attack, int defense, int speed) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.effects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public boolean canAct() {
        if (!isAlive()) {
            return false;
        }

        for (StatusEffect effect : effects) {
            if (!effect.isExpired() && effect.preventsAction()) {
                return false;
            }
        }
        return true;
    }

    public void takeRawDamage(int rawDamage) {
        if (!isAlive()) {
            return;
        }

        if (hasEffect("Smoke Bomb Invulnerability")) {
            return;
        }

        int damage = Math.max(0, rawDamage - defense);
        hp = Math.max(0, hp - damage);
    }

    public void heal(int amount) {
        if (!isAlive()) {
            return;
        }
        hp = Math.min(maxHp, hp + Math.max(0, amount));
    }

    public void addEffect(StatusEffect effect) {
        if (effect == null) {
            return;
        }
        effects.add(effect);
        effect.onApply(this);
    }

    public void removeExpiredEffects() {
        Iterator<StatusEffect> iterator = effects.iterator();
        while (iterator.hasNext()) {
            StatusEffect effect = iterator.next();
            if (effect.isExpired()) {
                effect.onExpire(this);
                iterator.remove();
            }
        }
    }

    public void onTurnStart() {
        for (StatusEffect effect : new ArrayList<>(effects)) {
            effect.onTurnStart(this);
        }
        removeExpiredEffects();
    }

    public void onTurnEnd() {
        for (StatusEffect effect : new ArrayList<>(effects)) {
            effect.onTurnEnd(this);
        }
        removeExpiredEffects();
    }

    public List<StatusEffect> getEffects() {
        return new ArrayList<>(effects);
    }

    public void increaseAttack(int amount) {
        this.attack += amount;
    }

    public void increaseDefense(int amount) {
        this.defense += amount;
    }

    protected boolean hasEffect(String effectName) {
        for (StatusEffect effect : effects) {
            if (!effect.isExpired() && effect.getName().equalsIgnoreCase(effectName)) {
                return true;
            }
        }
        return false;
    }
}