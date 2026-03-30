package engine;

import combatants.Enemy;
import combatants.Goblin;
import combatants.Wolf;
import java.util.ArrayList;
import java.util.List;

// LEVEL

public class Level {
    private final Difficulty difficulty;
    private final List<Enemy> initialWave;
    private final List<Enemy> backupWave;
    private boolean backupSpawned = false;

    public Level(Difficulty d) {
        this.difficulty = d;
        this.initialWave = buildInitial(d);
        this.backupWave = buildBackup(d);
    }

    private static List<Enemy> buildInitial(Difficulty d) {
        List<Enemy> w = new ArrayList<>();
        switch (d) {
            case EASY:
                w.add(new Goblin("A")); 
                w.add(new Goblin("B")); 
                w.add(new Goblin("C"));
                break;
            case MEDIUM:
                w.add(new Goblin("A")); 
                w.add(new Wolf("A"));
                break;
            case HARD:
                w.add(new Goblin("A")); 
                w.add(new Goblin("B"));
                break;
        }
        return w;
    }

    private static List<Enemy> buildBackup(Difficulty d) {
        List<Enemy> w = new ArrayList<>();
        switch (d) {
            case EASY:
                break;
            case MEDIUM:
                w.add(new Wolf("B")); 
                w.add(new Wolf("C")); 
                break;
            case HARD:
                w.add(new Goblin("C")); 
                w.add(new Wolf("A")); 
                w.add(new Wolf("B")); 
                break;
        }
        return w;
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

    public List<Enemy> triggerBackup() { //Returns backup enemies and permanently marks wave as spawned
        backupSpawned = true;
        return new ArrayList<>(backupWave);
    }
        /* Backup spawn rule: 
            Backup Spawns after the initial wave is completely defeated. 
            All entities of a Backup Spawn enter simultaneously.
            → Checked at START of each round before turn order is built.
        */


    public String waveDescription() { //For loading screen display.
        StringBuilder sb = new StringBuilder();
        sb.append("Initial : ");
        for (Enemy e : initialWave) sb.append(e.getName()).append("  ");
        if (!backupWave.isEmpty()) {
            sb.append("\n    Backup  : ");
            for (Enemy e : backupWave) sb.append(e.getName()).append("  ");
        }
        return sb.toString();
    }
}
