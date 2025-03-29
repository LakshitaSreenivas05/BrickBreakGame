package Brick_Breaker_Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainMenu extends JPanel implements ActionListener {
    private JButton startButton;
    public MainMenu() {
        setBackground(new Color(34, 139, 34)); //dark green
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title Label
        JLabel titleLabel = new JLabel("Brick Breaker", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(new Color(255, 204, 0));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        
        // Add spacing above title
        add(Box.createVerticalGlue()); // Pushes title to the center vertically
        add(titleLabel);

        // Start Button
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center button in BoxLayout
        startButton.addActionListener(this);
        startButton.setFocusable(false);

        // Add button with spacing below
        add(Box.createVerticalStrut(20)); // Space between title and button
        add(startButton);
        add(Box.createVerticalGlue()); // Pushes button upwards
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            // Switch to gameplay
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.remove(this);
            Gameplay gameplay = new Gameplay();
            frame.add(gameplay);
            frame.revalidate();
            frame.repaint();
            gameplay.requestFocus(); // Request focus for gameplay
        }
    }
}
