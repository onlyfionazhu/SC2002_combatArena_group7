package combatants;

import effects.StatusEffect;
import java.util.List;

public interface Combatant {
    String getName();

    int getHp();
    int getMaxHp();
    int getAttack();
    int getDefense();
    int getSpeed();

    boolean isAlive();
    boolean isStunned();
    boolean isDamageImmune();

    void takeRawDamage(int rawDamage);
    void heal(int amount);

    void addEffect(StatusEffect effect);
    void removeExpiredEffects();
    List<StatusEffect> getEffects();

    void tickTurnEffects();
    void tickRoundEndEffects();

    void setStunned(boolean stunned);
    void setDamageImmune(boolean Immune);
    void modifyAttack(int delta);
    void modifyDefense(int delta);

    String getStatusLine();

}