package Main;

import engine.BattleEngine;
import engine.Level;
import ui.GameCLI;
import ui.GameSession;

public class Main {
    public static void main(String[] args) {
        GameCLI cli = new GameCLI();
        GameSession session = null;

        while (true) {
            if (session == null) {
                session = cli.showLoadingScreen();
            }

            Level level = new Level(session.difficulty);
            BattleEngine engine = new BattleEngine(session.player, level, cli);
            engine.run();

            int choice = cli.promptPostGame();
            if (choice == 1) {
                session = cli.rebuildSession(session);
            } else {
                session = null;
            }
        }
    }
}
