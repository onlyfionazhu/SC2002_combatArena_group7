package effect;

import combatant.Combatant;

public interface StatusEffect {
    String getName();

    void onApply(Combatant target);
    void onTurnStart(Combatant target);
    void onTurnEnd(Combatant target);
    void onExpire(Combatant target);

    boolean preventsAction();
    boolean isExpired();
}
