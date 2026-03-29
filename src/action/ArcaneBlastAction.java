package action;

import combatant.Combatant;
import combatant.Wizard;
import core.BattleContext;
import core.ActionResult;
import effect.ArcaneBlast;
import java.util.List;

public class ArcaneBlastAction extends SpecialSkillAction {
    public ArcaneBlastAction() {
        this.cooldownDuration = 3;
    }

    @Override
    protected ActionResult doExecute(Combatant user, BattleContext context) {
        if (!(user instanceof Wizard)) {
            throw new IllegalStateException("Only Wizard can use Arcane Blast");
        }
        List<Combatant> enemies = context.getEnemies();
        int kills = 0;
        StringBuilder sb = new StringBuilder(user.getName() + " unleashes Arcane Blast!\n");

        for (Combatant enemy : enemies) {
            if (enemy != null && enemy.isAlive()) {
                int hpBefore = enemy.getHp();
                enemy.takeRawDamage(user.getAttack());
                int damage = hpBefore - enemy.getHp();
                sb.append("  Hits ").append(enemy.getName()).append(" for ").append(damage).append(" damage.\n");
                if (hpBefore > 0 && !enemy.isAlive()) {
                    kills++;
                }
            }
        }

        // Increase attack via the ArcaneBlast passive effect that should already be on Wizard
        if (kills > 0) {
            // The ArcaneBlast effect on the Wizard will add stacks
            // We need to find the ArcaneBlast effect and addStack
            for (effect.StatusEffect e : user.getEffects()) {
                if (e instanceof ArcaneBlast) {
                    ((ArcaneBlast) e).addStack(user);
                    break;
                }
            }
            sb.append("Arcane Blast killed ").append(kills).append(" enemy(ies)! Attack increased by ").append(kills * 10).append(".");
        }

        ActionResult result = new ActionResult();
        result.setMessage(sb.toString());
        return result;
    }
}