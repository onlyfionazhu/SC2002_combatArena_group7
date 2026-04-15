package engine;


public enum Difficulty {
    EASY, MEDIUM, HARD;

    public String label() {
        switch (this) {
            case EASY:   
                return "Easy   - Initial: 3 Goblins. No backup.";
            case MEDIUM: 
                return "Medium - Initial: 1 Goblin + 1 Wolf. Backup: 2 Wolves.";
            case HARD:   
                return "Hard   - Initial: 2 Goblins. Backup: 1 Goblin + 2 Wolves.";
            default:     
                return name();
        }
    }
}
