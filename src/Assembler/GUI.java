package Assembler;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.SeparatorUI;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class GUI {
    static JTextArea textArea;
    static JTextArea outputArea;

    void GUI1() {
        JFrame frame = new JFrame("Emulator");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new Panel();
        frame.setContentPane(panel);

        textArea = new JTextArea(50, 30);
        textArea.setFont(new Font("Dialog", Font.PLAIN, 14));
        textArea.setTabSize(10);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(false);

        //frame.getContentPane().add(textArea);
        panel.add(textArea);
        textArea.setBounds(10, 10, 110, 110);
        textArea.setSize(100, 100);

        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        new Assembler().runProgram();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    GUI() {
        JFrame frame = new JFrame("Emulator");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();

        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        //JPanel panel = new Panel();
        //frame.setContentPane(panel);

        textArea = new JTextArea(30, 30);
        textArea.setFont(new Font("Dialog", Font.PLAIN, 14));
        textArea.setTabSize(10);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(false);
        //panel.add(textArea);
        contentPane.add(textArea);

        outputArea = new JTextArea(30, 30);
        outputArea.setFont(new Font("Dialog", Font.PLAIN, 14));
        outputArea.setTabSize(10);
        outputArea.setLineWrap(true);

        JScrollPane scrollPane=new JScrollPane(outputArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        contentPane.add(scrollPane);

        layout.putConstraint(SpringLayout.NORTH, textArea, 20,
                SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, textArea, 20,
                SpringLayout.WEST, contentPane);

        layout.putConstraint(SpringLayout.NORTH, scrollPane, 20,
                SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 350+20,
                SpringLayout.WEST, contentPane);

        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        new Assembler().runProgram();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        //frame.pack();
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    class Panel extends JPanel {
        Panel() {
            setPreferredSize(new Dimension(500, 500));
            setBackground(new java.awt.Color(255, 255, 255));
            setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            repaint();

        }
    }

    static String[] getText() {
        return textArea.getText().split("\n");
    }

    public static void main(String... args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}
