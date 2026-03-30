package engine;

import combatants.Enemy;
import combatants.Goblin;
import combatants.Wolf;
import java.util.ArrayList;
import java.util.List;

/**
 * Level configuration (spec 3.5 + 3.6):
 *
 * EASY:   Initial: Goblin A, Goblin B, Goblin C     Backup: none
 * MEDIUM: Initial: Goblin A, Wolf A                 Backup: Wolf B, Wolf C
 * HARD:   Initial: Goblin A, Goblin B               Backup: Goblin C, Wolf A, Wolf B
 */
public class Level {

    private final Difficulty difficulty;
    private final List<Enemy> initialWave;
    private final List<Enemy> backupWave;
    private boolean backupSpawned = false;

    public Level(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.initialWave = buildInitial(difficulty);
        this.backupWave = buildBackup(difficulty);
    }

    private static List<Enemy> buildInitial(Difficulty d) {
        List<Enemy> wave = new ArrayList<>();
        switch (d) {
            case EASY:
                wave.add(new Goblin("A"));
                wave.add(new Goblin("B"));
                wave.add(new Goblin("C"));
                break;
            case MEDIUM:
                wave.add(new Goblin("A"));
                wave.add(new Wolf("A"));
                break;
            case HARD:
                wave.add(new Goblin("A"));
                wave.add(new Goblin("B"));
                break;
        }
        return wave;
    }

    private static List<Enemy> buildBackup(Difficulty d) {
        List<Enemy> wave = new ArrayList<>();
        switch (d) {
            case EASY:
                break;
            case MEDIUM:
                wave.add(new Wolf("B"));
                wave.add(new Wolf("C"));
                break;
            case HARD:
                wave.add(new Goblin("C"));
                wave.add(new Wolf("A"));
                wave.add(new Wolf("B"));
                break;
        }
        return wave;
    }

    public boolean hasBackup() {
        return !backupWave.isEmpty() && !backupSpawned;
    }

    public List<Enemy> getInitialWave() {
        return new ArrayList<>(initialWave);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public List<Enemy> triggerBackup() {
        backupSpawned = true;
        return new ArrayList<>(backupWave);
    }

    public String getWaveDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Initial: ");
        for (Enemy e : initialWave) {
            sb.append(e.getName()).append("  ");
        }
        if (!backupWave.isEmpty()) {
            sb.append("\n    Backup: ");
            for (Enemy e : backupWave) {
                sb.append(e.getName()).append("  ");
            }
        }
        return sb.toString();
    }
}