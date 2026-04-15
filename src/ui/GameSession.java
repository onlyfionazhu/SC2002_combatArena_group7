package ui;

import combatants.Player;
import engine.Difficulty;
import items.Item;
import java.util.List;

public class GameSession {
    public final int playerChoice;
    public final int item1Choice;
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