package ui;

import combatants.Player;
import engine.Difficulty;
import items.Item;
import java.util.List;

/**
 * Holds setup choices for one game session.
 * Used for replay functionality.
 */
public class GameSession {
    public final int playerChoice;   // 1=Warrior, 2=Wizard
    public final int item1Choice;    // 1=Potion, 2=PowerStone, 3=SmokeBomb
    public final int item2Choice;
    public final Difficulty difficulty;
    public final Player player;
    public final List<Item> items;

    public GameSession(int playerChoice, int item1Choice, int item2Choice,
                       Difficulty difficulty, Player player, List<Item> items) {
        this.playerChoice = playerChoice;
        this.item1Choice = item1Choice;
        this.item2Choice = item2Choice;
        this.difficulty = difficulty;
        this.player = player;
        this.items = items;
    }
}