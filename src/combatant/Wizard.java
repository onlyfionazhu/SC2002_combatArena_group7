package combatant;

import effect.ArcaneBlast;

public class Wizard extends Player {
    private final ArcaneBlast passiveArcaneBlast;

    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
        this.passiveArcaneBlast = new ArcaneBlast();
        addEffect(passiveArcaneBlast);
    }

    @Override
    public String getSpecialSkillName() {
        return "Arcane Blast";
    }

    public void useArcaneBlast(Combatant[] enemies) {
        if (enemies == null) {
            return;
        }

        for (Combatant enemy : enemies) {
            if (enemy != null && enemy.isAlive()) {
                int hpBefore = enemy.getHp();
                enemy.takeRawDamage(this.attack);

                if (hpBefore > 0 && !enemy.isAlive()) {
                    passiveArcaneBlast.addStack(this);
                }
            }
        }
    }
}