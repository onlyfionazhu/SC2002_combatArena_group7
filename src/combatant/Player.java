package combatant;

public abstract class Player extends AbstractCombatant {
    protected Player(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
    }

    public abstract String getSpecialSkillName();
}