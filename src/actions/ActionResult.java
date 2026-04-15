package actions;

import effects.StatusEffect;
import java.util.ArrayList;
import java.util.List;

public class ActionResult {
    private int damageDealt = 0;
    private boolean stunned = false;
    private List<StatusEffect> appliedEffects = new ArrayList<>();
    private String message = "";
    private boolean actionConsumed = true; // For Power Stone free actions

    public int getDamageDealt() { return damageDealt; }
    public void setDamageDealt(int damageDealt) { this.damageDealt = damageDealt; }

    public boolean isStunned() { return stunned; }
    public void setStunned(boolean stunned) { this.stunned = stunned; }

    public List<StatusEffect> getAppliedEffects() { return appliedEffects; }
    public void addAppliedEffect(StatusEffect effect) { appliedEffects.add(effect); }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isActionConsumed() { return actionConsumed; }
    public void setActionConsumed(boolean actionConsumed) { this.actionConsumed = actionConsumed; }
}