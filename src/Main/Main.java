package Main;

import engine.BattleEngine;
import engine.Level;
import ui.GameCLI;
import ui.GameSession;

/**
 * Entry point for the Turn-Based Combat Arena.
 *
 * Compilation (from src/ folder):
 *   Mac/Linux: find . -name "*.java" | xargs javac -d ../out
 *   Windows:   dir /s /B *.java > sources.txt && javac -d ../out @sources.txt
 *
 * Execution: java -cp ../out Main
 */
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