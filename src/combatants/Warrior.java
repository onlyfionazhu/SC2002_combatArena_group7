package combatants;

import actions.ActionResult;
import effects.StunEffect;
import java.util.List;

/**
 * Warrior:
 * - HP: 260, Attack: 40, Defense: 20, Speed: 30
 * - Special Skill: Shield Bash
 *   Effect: Deal BasicAttack damage to selected enemy.
 *           Selected enemy is unable to take actions for the current turn and the next turn.
 */

public class Warrior extends Player {

    public Warrior(String name) {
        super(name, 260, 40, 20, 30);
    }

    public ActionResult useSpecialSkill(List<Combatant> targets) {
        ActionResult result = new ActionResult();

        if (targets == null || targets.isEmpty()) {
            result.setMessage("No valid target for Shield Bash.");
            return result;
        }

        Combatant target = targets.get(0);
        int damage = Math.max(0, this.attack - target.getDefense());
        int hpBefore = target.getHp();
        target.takeRawDamage(damage);
        int actualDamage = hpBefore - target.getHp();

        if (target.isAlive()) {
            target.addEffect(new StunEffect());
            result.setStunned(true);
        }

        result.setDamageDealt(actualDamage);
        result.setMessage(getName() + " performs Shield Bash on " + target.getName() +
                " for " + actualDamage + " damage and stuns them!");
        return result;
    }

    public String getSpecialSkillName() {
        return "Shield Bash";
    }

    public String getSpecialSkillDescription() {
        return "BasicAttack damage + Stun target for current + next turn. CD:3";
    }
}