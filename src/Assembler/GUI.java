package Assembler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static Assembler.Assembler.SC;
import static Assembler.Assembler.counter;
import static Assembler.Elements.*;
import static Assembler.Memory.*;
import static Assembler.Operations.runOperations;

public class GUI {
    private Elements elements;

    private static JTextArea inputArea = new JTextArea();
    static JTextArea outputArea = new JTextArea();

    private JTextField textField = new JTextField();
    private JTextField memoryField = new JTextField();
    private JButton stepButton = new JButton();
    private JButton runButton = new JButton();

    private JLabel flagSLabel = new JLabel();
    private JLabel flagZLabel = new JLabel();
    private JLabel flagPLabel = new JLabel();
    private JLabel flagCLabel = new JLabel();

    private JLabel registerALabel = new JLabel();
    private JLabel registerFLabel = new JLabel();
    private JLabel registerBLabel = new JLabel();
    private JLabel registerCLabel = new JLabel();
    private JLabel registerDLabel = new JLabel();
    private JLabel registerELabel = new JLabel();
    private JLabel registerHLabel = new JLabel();
    private JLabel registerLLabel = new JLabel();
    private JLabel registerMLabel = new JLabel();
    private JLabel registerPairPC1Label = new JLabel();
    private JLabel registerPairPC2Label = new JLabel();
    private JLabel registerPairSP1Label = new JLabel();
    private JLabel registerPairSP2Label = new JLabel();

    private GUI() {
        JFrame frame = new JFrame("Emulator");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        elements = new Elements(frame);

        setElements();

        frame.setSize(800, 800);
        frame.setVisible(true);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    memoryField.setText(Memory.addresses[Integer.parseInt(textField.getText())]);
                } catch (Exception ignored) {
                }
            }
        });

        inputArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    new Assembler().gui();
                }
            }
        });

        stepButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                runOperations(String.valueOf(counter+1));
                changeFlags();
                changeRegisters();
            }
        });

        runButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

            }
        });

    }

    private void setElements() {
        int x = 300;
        int y = 20;
        int s = 40;
        int a = 20;

        createTextArea(inputArea, 20, 20, 50, 200);
        createTextArea(outputArea, 100, 20, 50, 200);

        createLabel("S", x, y);
        createLabel(flagSLabel, x, y + s + a);
        createLabel("Z", x + s + a, y);
        createLabel(flagZLabel, x + s + a, y + s + a);
        createLabel("P", x + (s + a) * 2, y);
        createLabel(flagPLabel, x + (s + a) * 2, y + s + a);
        createLabel("C", x + (s + a) * 3, y);
        createLabel(flagCLabel, x + (s + a) * 3, y + s + a);

        y += 100 + 40;

        createLabel("A", x, y);
        createLabel(registerALabel, x, y + s);
        createLabel("F", x, y + 2 * s + a);
        createLabel(registerFLabel, x, y + 3 * s + a);
        createLabel("B", x + s + a, y);
        createLabel(registerBLabel, x + s + a, y + s);
        createLabel("C", x + 2 * s + a, y);
        createLabel(registerCLabel, x + 2 * s + a, y + s);
        createLabel("D", x + s + a, y + 2 * s + a);
        createLabel(registerDLabel, x + s + a, y + 3 * s + a);
        createLabel("E", x + 2 * s + a, y + 2 * s + a);
        createLabel(registerELabel, x + 2 * s + a, y + 3 * s + a);
        createLabel("H", x + 3 * s + 2 * a, y);
        createLabel(registerHLabel, x + 3 * s + 2 * a, y + s);
        createLabel("L", x + 4 * s + 2 * a, y);
        createLabel(registerLLabel, x + 4 * s + 2 * a, y + s);
        createLabel("M", (int) (x + 3.5 * s + 2 * a), y + 2 * s + a);
        createLabel(registerMLabel, (int) (x + 3.5 * s + 2 * a), y + 3 * s + a);
        createLabel("P", x + 5 * s + 3 * a, y);
        createLabel(registerPairPC1Label, x + 5 * s + 3 * a, y + s);
        createLabel("C", x + 6 * s + 3 * a, y);
        createLabel(registerPairPC2Label, x + 6 * s + 3 * a, y + s);
        createLabel("S", x + 5 * s + 3 * a, y + 2 * s + a);
        createLabel(registerPairSP1Label, x + 5 * s + 3 * a, y + 3 * s + a);
        createLabel("P", x + 6 * s + 3 * a, y + 2 * s + a);
        createLabel(registerPairSP2Label, x + 6 * s + 3 * a, y + 3 * s + a);

        y += 180 + 40;

        createTextField(textField, x, y, 80, 30);
        createTextField(memoryField, x + 100, y, 80, 30);

        createButton(stepButton, "STEP", x, y + 40, 80, 30);
        createButton(runButton, "RUN", x + 100, y + 40, 80, 30);
    }

    private void changeFlags() {
        if (flagS) {
            flagSLabel.setBackground(Color.RED);
        }
        if (flagZ) {
            flagZLabel.setBackground(Color.RED);
        }
        if (flagP) {
            flagPLabel.setBackground(Color.RED);
        }
        if (flagC) {
            flagCLabel.setBackground(Color.RED);
        }
    }

    private void changeRegisters() {
        registerALabel.setText(regA.getValue());
        registerFLabel.setText(regF.getValue());
        registerBLabel.setText(regB.getValue());
        registerCLabel.setText(regC.getValue());
        registerDLabel.setText(regD.getValue());
        registerELabel.setText(regE.getValue());
        registerHLabel.setText(regH.getValue());
        registerLLabel.setText(regL.getValue());
        registerMLabel.setText(regM.getValue());

        registerPairPC1Label.setText(String.valueOf(counter / 100));
        registerPairPC2Label.setText(String.valueOf(counter % 100));
        registerPairSP1Label.setText(String.valueOf(SC / 100));
        registerPairSP2Label.setText(String.valueOf(SC % 100));
    }

    static String[] getText() {
        return inputArea.getText().split("\n");
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
