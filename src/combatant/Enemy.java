package combatant;

public abstract class Enemy extends AbstractCombatant {
    protected Enemy(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
    }
}