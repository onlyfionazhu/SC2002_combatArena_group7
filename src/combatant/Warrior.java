package combatant;

import effect.Stun;

public class Warrior extends Player {
    public Warrior() {
        super("Warrior", 260, 40, 20, 30);
    }

    public String getSpecialSkillName() {
        return "Shield Bash";
    }

    public void useShieldBash(Combatant target) {
        if (target == null || !target.isAlive()) {
            return;
        }
        target.takeRawDamage(this.attack);
        target.addEffect(new Stun());
    }
}