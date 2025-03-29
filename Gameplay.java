package Brick_Breaker_Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = true;
    private int score = 0;
    private int total_bricks;
    private Timer timer;
    private int delay = 5; //speed

    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;

    private int balldirX = -2;
    private int balldirY = -4;

    private Mapgenerator map;
    private int currentLevel = -1;
    private Level levelManager;
    private BufferedImage backgroundImage;

    public Gameplay() {
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\laksh\\Brick_Breaker_Game\\resources\\bg.jpg"));
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception
        }
        
        map = new Mapgenerator(3, 7);
        levelManager = new Level(); // Ensure to initialize levelManager here
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }
    
    private void loadLevel(int levelIndex) {
        int[] levelConfig = levelManager.getLevel(levelIndex);
        map = new Mapgenerator(levelConfig[0], levelConfig[1]);
        total_bricks = levelConfig[0] * levelConfig[1];
        score = 0; 
        play = true; 
        delay = Math.max(1, delay - 1); //delay does not go below 1
        timer.setDelay(delay);
    }
    

    public void paint(Graphics g) {
        // Background
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // Draw bricks
        map.draw((Graphics2D) g);
    
        // Borders
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
    
        // Paddle
        g.setColor(Color.GRAY);
        g.fillRect(playerX, 550, 100, 8);
        
        // Paddle Border
        g.setColor(Color.BLACK); // Border color
        g.drawRect(playerX, 550, 100, 8); // Draw paddle border
    
        // Ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballposX, ballposY, 20, 20);
        
        // Ball Border
        g.setColor(Color.BLACK); // Border color
        g.drawOval(ballposX, ballposY, 20, 20); // Draw ball border
    
        // Score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 590, 30);
    
        // Game Over message
        if (!play) {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over!", 250, 300); // Centered message
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + score, 270, 350); // Display the score
            g.drawString("Press Enter to Restart", 220, 380); // Prompt to restart
        }

        g.dispose();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveright();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveleft();
            }
        }
        // Restart the game when Enter is pressed
        if (e.getKeyCode() == KeyEvent.VK_ENTER && !play) {
            restartGame();
        }
    }
    public void moveright() {
        play = true;
        playerX += 20;
    }
    public void moveleft() {
        play = true;
        playerX -= 20;
    }
    private void restartGame() {
        play = true;
        ballposX = 120;
        ballposY = 350;
        balldirX = -2;
        balldirY = -4;
        total_bricks = 21;
        score = 0;
        map = new Mapgenerator(3, 7); // Reset the map
        repaint(); // Refresh the game display
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            // Check for paddle collision
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                balldirY = -balldirY;
            }

            // Check for brick collisions
            A: for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;
                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                    
                        if (ballRect.intersects(rect)) {
                            map.setbrickvalue(0, i, j);
                            total_bricks--;
                            score += 5;
                            if (ballposX + 19 <= rect.x || ballposX + 1 >= rect.x + rect.width) {
                                balldirX = -balldirX;
                            } else {
                                balldirY = -balldirY;
                            }
                            break A;
                        }
                    }
                }
            }
            // Check if all bricks are cleared
            if (total_bricks <= 0) {
                currentLevel++;
                if (currentLevel < levelManager.getTotalLevels()) {
                    loadLevel(currentLevel);
                } else {
                    play = false; // Stop the game
                    System.out.println("Congratulations! You completed all levels!");
                }
            }
            // Ball movement
            ballposX += balldirX;
            ballposY += balldirY;
            // Game lost
            if (ballposY > 570) {
                play = false; // End the game
                repaint(); // Refresh to show the game over screen
            }
            // Ball boundary conditions
            if (ballposX < 0) {
                balldirX = -balldirX;
            }
            if (ballposY < 0) {
                balldirY = -balldirY;
            }
            if (ballposX > 670) {
                balldirX = -balldirX;
            }
        }
        repaint(); // Calls paint() and draws everything again
    }
    private void resetGame(int levelIndex) {
        ballposX = 120; 
        ballposY = 350; 
        balldirX = -2;  
        balldirY = -4;  
        score += 10; // Reward for level completion
        map = new Mapgenerator(levelManager.getLevel(levelIndex)[0], levelManager.getLevel(levelIndex)[1]);
        total_bricks = levelManager.getLevel(levelIndex)[0] * levelManager.getLevel(levelIndex)[1];
        play = true; // Resume the game
    }
}