package combatant;

import effect.StatusEffect;
import java.util.List;

public interface Combatant {
    String getName();

    int getHp();
    int getMaxHp();
    int getAttack();
    int getDefense();
    int getSpeed();

    boolean isAlive();
    boolean canAct();

    void takeRawDamage(int rawDamage);
    void heal(int amount);

    void addEffect(StatusEffect effect);
    void removeExpiredEffects();

    void onTurnStart();
    void onTurnEnd();

    List<StatusEffect> getEffects();
}