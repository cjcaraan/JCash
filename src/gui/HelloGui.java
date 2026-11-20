package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class HelloGui {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new JLabel("It works", SwingConstants.CENTER));
            frame.setSize(1920, 1080);
            frame.setLocationRelativeTo(null);   // centers the window
            frame.setVisible(true);
        });
    }

    
}