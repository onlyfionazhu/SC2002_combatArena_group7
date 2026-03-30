package combatants;

/**
 * Wolf:
 * - HP: 40, Attack: 45, Defense: 5, Speed: 35
 */
public class Wolf extends Enemy {
    public Wolf(String label) {
        super("Wolf " + label, 40, 45, 5, 35);
    }
}