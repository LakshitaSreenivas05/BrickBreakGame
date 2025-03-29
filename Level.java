package Brick_Breaker_Game;

public class Level {
    private int[][] levels;
    public Level() {
        levels = new int[][] {
            {3, 7},  // Level 1
            {4, 8},  // Level 2
            {5, 9},  // Level 3
            {6, 10}, // Level 4
            {7, 10}, // Level 5
            {6, 12}, // Level 6
            {5, 14}, // Level 7
            {4, 15}, // Level 8
            {5, 15}, // Level 9
            {6, 15}  // Level 10
        };
    }
    public int[] getLevel(int levelIndex) {
        return levels[levelIndex];
    }
    public int getTotalLevels() {
        return levels.length;
    }
}
