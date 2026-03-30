package combatants;

import effects.StatusEffect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractCombatant implements Combatant {
    private final String name;
    private final int maxHp;

    protected int hp;
    protected int attack;
    protected int defense;
    protected final int speed;

    protected boolean alive = true;
    protected boolean stunned = false;
    protected boolean damageImmune = false;

    protected final List<StatusEffect> statusEffects = new ArrayList<>();

    protected AbstractCombatant(String name, int maxHp, int attack, int defense, int speed) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
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
        return alive && hp > 0;
    }

    public boolean isStunned() { return stunned; }

    public boolean isDamageImmune() { return damageImmune; }

    public void takeRawDamage(int rawDamage) {
        if (!isAlive()) return;
        if (damageImmune) rawDamage = 0;
        int actualDamage = Math.max(0, rawDamage);
        hp = Math.max(0, hp - actualDamage);
        if (hp == 0) {
            alive = false;
        }
    }

    public void heal(int amount) {
        if (!isAlive()) return;
        hp = Math.min(maxHp, hp + Math.max(0, amount));
        if (hp == 0) {
            alive = false;
        }
    }

    public void addEffect(StatusEffect effect) {
        if (effect == null) return;
        statusEffects.add(effect);
        effect.onApply(this);
    }

    public void removeExpiredEffects() {
        Iterator<StatusEffect> iterator = statusEffects.iterator();
        while (iterator.hasNext()) {
            StatusEffect effect = iterator.next();
            if (effect.isExpired()) {
                effect.onExpire(this);
                iterator.remove();
            }
        }
    }


    public void tickTurnEffects() {
        Iterator<StatusEffect> it = statusEffects.iterator();
        while (it.hasNext()) {
            StatusEffect e = it.next();
            e.onTurnStart(this);
            if (e.isExpired()) {
                e.onExpire(this);
                it.remove();
            }
        }
    }

    public void tickRoundEndEffects() {
        Iterator<StatusEffect> it = statusEffects.iterator();
        while (it.hasNext()) {
            StatusEffect e = it.next();
            e.onTurnEnd(this);
            if (e.isExpired()) {
                e.onExpire(this);
                it.remove();
            }
        }
    }

    public void setStunned(boolean stunned) {
        this.stunned = stunned;
    }

    public void setDamageImmune(boolean immune) {
        this.damageImmune = immune;
    }

    public void modifyAttack(int delta) {
        this.attack += delta;
    }

    public void modifyDefense(int delta) {
        this.defense += delta;
    }

    public String getStatusLine() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("  %-16s HP:%3d/%-3d", name, hp, maxHp));
        if (!isAlive()) sb.append("  [ELIMINATED]");
        if (stunned) sb.append("  [STUNNED]");
        if (damageImmune) sb.append("  [IMMUNE]");
        for (StatusEffect e : statusEffects) {
            sb.append("  [").append(e.getName()).append("]");
        }
        return sb.toString();
    }
}