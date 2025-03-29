package Brick_Breaker_Game;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
public class Mapgenerator {
    public int map[][];
    public int brickWidth;
    public int brickHeight;
    public Mapgenerator(int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1; // Initialize bricks as present
            }
        }
        brickWidth = 540 / col; // Width of each brick
        brickHeight = 150 / row; // Height of each brick
    }
    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) { // Check if the brick is present
                    // Draw the brick
                    g.setColor(Color.gray);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    // Draw the border
                    g.setStroke(new BasicStroke(2)); // Set the border thickness
                    g.setColor(Color.BLACK); // Border color
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight); // Draw border
                }
            }
        }
    }
    public void setbrickvalue(int value, int row, int col) {
        map[row][col] = value; // Set the value of the brick
    }
} 


