package combatants;

import actions.ActionResult;
import effects.ArcaneBlastEffect;
import java.util.List;

public class Wizard extends Player {

    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
    }

    public ActionResult useSpecialSkill(List<Combatant> targets) {
        ActionResult result = new ActionResult();
        StringBuilder message = new StringBuilder(getName() + " unleashes Arcane Blast!\n");
        int kills = 0;

        if (targets == null || targets.isEmpty()) {
            result.setMessage("No enemies to target.");
            return result;
        }

        for (Combatant target : targets) {
            if (!target.isAlive()) continue;

            int damage = Math.max(0, this.attack - target.getDefense());
            int hpBefore = target.getHp();
            target.takeRawDamage(damage);
            int actualDamage = hpBefore - target.getHp();

            message.append("  Hits ").append(target.getName()).append(" for ").append(actualDamage).append(" damage.\n");

            if (hpBefore > 0 && !target.isAlive()) {
                kills++;
                this.addEffect(new ArcaneBlastEffect(1));
                message.append("    ").append(target.getName()).append(" eliminated! Attack +10.\n");
            }
        }

        if (kills > 0) {
            message.append("Arcane Blast killed ").append(kills).append(" enemy(ies)! Total Attack is now ").append(this.attack);
        }

        result.setMessage(message.toString());
        result.setDamageDealt(0);
        return result;
    }

    @Override
    public String getSpecialSkillName() {
        return "Arcane Blast";
    }

    @Override
    public String getSpecialSkillDescription() {
        return "Damage ALL enemies. +10 ATK per kill (stacks until level end). CD:3";
    }
}