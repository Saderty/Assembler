package Assembler;

import javax.swing.*;
import java.awt.*;

public class GUI {
    private static void createGUI() {
        JFrame frame = new JFrame("Frame");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Label");
        frame.getContentPane().add(label);

        frame.setPreferredSize(new Dimension(500, 500));

        Graphics g=frame.getGraphics();
        g.setColor(Color.RED);
        g.drawLine(0,0,800,800);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String... args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUI();
            }
        });
    }
}
