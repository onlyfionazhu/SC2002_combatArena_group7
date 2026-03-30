package combatants;

/**
 * Goblin:
 * - HP: 55, Attack: 35, Defense: 15, Speed: 25
 */

public class Goblin extends Enemy {
    public Goblin(String label) {
        super("Goblin " + label, 55, 35, 15, 25);
    }
}
