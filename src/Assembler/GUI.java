package Assembler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Assembler.Elements.*;
import static Assembler.Memory.*;
import static Assembler.Operations.runOperations;

class GUI {
    private static JTextArea inputArea = new JTextArea();
    private static JTextArea outputArea = new JTextArea();

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

    GUI() {
        JFrame frame = new JFrame("Emulator");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        new Elements(frame);

        setElements();

        JLabel label = new JLabel("Directed by Bulat Shagidullin #Saderty");
        frame.getContentPane().add(label);
        setElement(label, 0, 0);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    memoryField.setText(getMemory(textField.getText()));
                } catch (Exception ignored) {
                }
            }
        });

        inputArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    new Assembler().gui();
                    regPC.setValue("0000");
                    regSP.setValue("1000");

                    regPSW.setValue("0000");
                    regBC.setValue("0000");
                    regDE.setValue("0000");
                    regHL.setValue("0000");
                }
            }
        });

        stepButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println(getMemory(regPC));
                System.out.println(regPC.getValue());
                runOperations(getMemory(regPC));
                System.out.println();
                changeFlags();
                changeRegisters();

                try {
                    memoryField.setText(getMemory(textField.getText()));
                } catch (Exception ignored) {
                }
            }
        });

        runButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                while (!getMemory(regPC).equals("END")) {
                    System.out.println(getMemory(regPC));
                    System.out.println(regPC.getValue());
                    runOperations(getMemory(regPC));
                    System.out.println();
                    changeFlags();
                    changeRegisters();

                    try {
                        memoryField.setText(getMemory(textField.getText()));
                    } catch (Exception ignored) {
                    }
                }
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                System.out.println(button.getText());
                inputArea.setText(button.getText());

                if (e.getSource().equals(runButton)) {
                    System.out.println("qqqqq");
                }
            }
        });

        frame.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(((JButton) e.getSource()).getText());
            }
        });

        frame.setSize(1600, 1000);
        frame.setVisible(true);

        JFrame.setDefaultLookAndFeelDecorated(true);
    }

    private void setElements() {
        int s = 40;
        int a = 20;

        int x = 20;
        int y = 20;

        createTextArea(inputArea, x, y, 200, 890);
        //createTextArea(outputArea, 240, 20, 300, 800);

        x = 240;
        y = 20;

        createLabel("S", x, y);
        createLabel(flagSLabel, x, y + s + a);
        createLabel("Z", x + s + a, y);
        createLabel(flagZLabel, x + s + a, y + s + a);
        createLabel("P", x + (s + a) * 2, y);
        createLabel(flagPLabel, x + (s + a) * 2, y + s + a);
        createLabel("C", x + (s + a) * 3, y);
        createLabel(flagCLabel, x + (s + a) * 3, y + s + a);

        x = 240;
        y = 140;

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

        x = 240;
        y = 340;

        createTextField(textField, x, y, 80, 30);
        createTextField(memoryField, x + 100, y, 80, 30);

        createButton(stepButton, "STEP", x, y + 40, 80, 30);
        createButton(runButton, "RUN", x + 100, y + 40, 80, 30);

        createCodeTable(x, y + 90);
    }

    private void changeFlags() {
        if (flagS) {
            flagSLabel.setOpaque(true);
            flagSLabel.setBackground(Color.RED);
        }
        if (flagZ) {
            flagZLabel.setOpaque(true);
            flagZLabel.setBackground(Color.RED);
        }
        if (flagP) {
            flagPLabel.setOpaque(true);
            flagPLabel.setBackground(Color.RED);
        }
        if (flagC) {
            flagCLabel.setOpaque(true);
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
        registerMLabel.setText(getMemory(regHL));

        registerPairPC1Label.setText(regPC.getLowByte());
        registerPairPC2Label.setText(regPC.getHighByte());
        registerPairSP1Label.setText(regSP.getLowByte());
        registerPairSP2Label.setText(regSP.getHighByte());
    }

    static String[] getText() {
        return inputArea.getText().split("\n");
    }

    private static void createCodeTable(int x, int y) {
        String[][] codes = new String[16][16];

        codes[0][0] = "NOP";
        codes[0][4] = "MOV B B";
        codes[0][5] = "MOV D B";
        codes[0][6] = "MOV H B";
        codes[0][7] = "MOV M B";
        codes[0][8] = "ADD B";
        codes[0][9] = "SUB B";
        codes[0][10] = "ANA B";
        codes[0][11] = "ORA B";
        codes[0][12] = "RNZ";
        codes[0][13] = "RNC";
        codes[0][14] = "RPO";
        codes[0][15] = "RP";

        codes[1][0] = "LXI B";
        codes[1][1] = "LXI D";
        codes[1][2] = "LXI H";
        codes[1][3] = "LXI SP";
        codes[1][4] = "MOV B C";
        codes[1][5] = "MOV D C";
        codes[1][6] = "MOV H C";
        codes[1][7] = "MOV M C";
        codes[1][8] = "ADD C";
        codes[1][9] = "SUB C";
        codes[1][10] = "ANA C";
        codes[1][11] = "ORA C";
        codes[1][12] = "POP B";
        codes[1][13] = "POP D";
        codes[1][14] = "POP H";
        codes[1][15] = "POP PSW";

        codes[2][0] = "STAX B";
        codes[2][1] = "STAX D";
        codes[2][2] = "SHLD";
        codes[2][3] = "STA";
        codes[2][4] = "MOV B D";
        codes[2][5] = "MOV D D";
        codes[2][6] = "MOV H D";
        codes[2][7] = "MOV M D";
        codes[2][8] = "ADD D";
        codes[2][9] = "SUB D";
        codes[2][10] = "ANA D";
        codes[2][11] = "ORA D";
        codes[2][12] = "JNZ";
        codes[2][13] = "JNC";
        codes[2][14] = "JPO";
        codes[2][15] = "JP";

        codes[3][0] = "INX B";
        codes[3][1] = "INX D";
        codes[3][2] = "INX H";
        codes[3][3] = "INX SP";
        codes[3][4] = "MOV B E";
        codes[3][5] = "MOV D E";
        codes[3][6] = "MOV H E";
        codes[3][7] = "MOV M E";
        codes[3][8] = "ADD E";
        codes[3][9] = "SUB E";
        codes[3][10] = "ANA E";
        codes[3][11] = "ORA E";
        codes[3][12] = "JMP";
        codes[3][13] = "";
        codes[3][14] = "XTHL";
        codes[3][15] = "";

        codes[4][0] = "INR B";
        codes[4][1] = "INR D";
        codes[4][2] = "INR H";
        codes[4][3] = "INR M";
        codes[4][4] = "MOV B H";
        codes[4][5] = "MOV D H";
        codes[4][6] = "MOV H H";
        codes[4][7] = "MOV M H";
        codes[4][8] = "ADD H";
        codes[4][9] = "SUB H";
        codes[4][10] = "ANA H";
        codes[4][11] = "ORA H";
        codes[4][12] = "CNZ";
        codes[4][13] = "CNC";
        codes[4][14] = "CPO";
        codes[4][15] = "CP";

        codes[5][0] = "DCR B";
        codes[5][1] = "DCR D";
        codes[5][2] = "DCR H";
        codes[5][3] = "DCR M";
        codes[5][4] = "MOV B L";
        codes[5][5] = "MOV D L";
        codes[5][6] = "MOV H L";
        codes[5][7] = "MOV M L";
        codes[5][8] = "ADD L";
        codes[5][9] = "SUB L";
        codes[5][10] = "ANA L";
        codes[5][11] = "ORA L";
        codes[5][12] = "PUSH B";
        codes[5][13] = "PUSH D";
        codes[5][14] = "PUSH H";
        codes[5][15] = "PUSH PSW";

        codes[6][0] = "MVI B";
        codes[6][1] = "MVI D";
        codes[6][2] = "MVI H";
        codes[6][3] = "MVI M";
        codes[6][4] = "MOV B M";
        codes[6][5] = "MOV D M";
        codes[6][6] = "MOV H M";
        codes[6][7] = "";
        codes[6][8] = "ADD M";
        codes[6][9] = "SUB M";
        codes[6][10] = "ANA M";
        codes[6][11] = "ORA M";
        codes[6][12] = "ADI";
        codes[6][13] = "SUI";
        codes[6][14] = "ANI";
        codes[6][15] = "ORI";

        codes[7][0] = "RLC";
        codes[7][1] = "RAL";
        codes[7][2] = "";
        codes[7][3] = "STC";
        codes[7][4] = "MOV B A";
        codes[7][5] = "MOV D A";
        codes[7][6] = "MOV H A";
        codes[7][7] = "MOV M A";
        codes[7][8] = "ADD A";
        codes[7][9] = "SUB A";
        codes[7][10] = "ANA A";
        codes[7][11] = "ORA A";
        codes[7][12] = "RST 0";
        codes[7][13] = "RST 2";
        codes[7][14] = "RST 4";
        codes[7][15] = "RST 6";

        codes[8][0] = "";
        codes[8][1] = "";
        codes[8][2] = "";
        codes[8][3] = "";
        codes[8][4] = "MOV C B";
        codes[8][5] = "MOV E B";
        codes[8][6] = "MOV L B";
        codes[8][7] = "MOV A B";
        codes[8][8] = "ADC B";
        codes[8][9] = "SBB B";
        codes[8][10] = "XRA B";
        codes[8][11] = "CMP B";
        codes[8][12] = "RZ";
        codes[8][13] = "RC";
        codes[8][14] = "RPE";
        codes[8][15] = "RM";

        codes[9][0] = "DAD B";
        codes[9][1] = "DAD D";
        codes[9][2] = "DAD H";
        codes[9][3] = "DAD SP";
        codes[9][4] = "MOV C C";
        codes[9][5] = "MOV E C";
        codes[9][6] = "MOV L C";
        codes[9][7] = "MOV A C";
        codes[9][8] = "ADC C";
        codes[9][9] = "SBB C";
        codes[9][10] = "XRA C";
        codes[9][11] = "CMP C";
        codes[9][12] = "RET";
        codes[9][13] = "";
        codes[9][14] = "PCHL";
        codes[9][15] = "SPHL";

        codes[10][0] = "LDAX B";
        codes[10][1] = "LDAX D";
        codes[10][2] = "LHLD";
        codes[10][3] = "LDA";
        codes[10][4] = "MOV C D";
        codes[10][5] = "MOV E D";
        codes[10][6] = "MOV L D";
        codes[10][7] = "MOV A D";
        codes[10][8] = "ADC D";
        codes[10][9] = "SBB D";
        codes[10][10] = "XRA D";
        codes[10][11] = "CMP D";
        codes[10][12] = "JZ";
        codes[10][13] = "JC";
        codes[10][14] = "JPE";
        codes[10][15] = "JM";

        codes[11][0] = "DCX B";
        codes[11][1] = "DCX D";
        codes[11][2] = "DCX H";
        codes[11][3] = "DCX SP";
        codes[11][4] = "MOV C E";
        codes[11][5] = "MOV E E";
        codes[11][6] = "MOV L E";
        codes[11][7] = "MOV A E";
        codes[11][8] = "ADC E";
        codes[11][9] = "SBB E";
        codes[11][10] = "XRA E";
        codes[11][11] = "CMP E";
        codes[11][12] = "";
        codes[11][13] = "";
        codes[11][14] = "XCHG";
        codes[11][15] = "";

        codes[12][0] = "INR C";
        codes[12][1] = "INR E";
        codes[12][2] = "INR L";
        codes[12][3] = "INR A";
        codes[12][4] = "MOV C H";
        codes[12][5] = "MOV E H";
        codes[12][6] = "MOV L H";
        codes[12][7] = "MOV A H";
        codes[12][8] = "ADC H";
        codes[12][9] = "SBB H";
        codes[12][10] = "XRA H";
        codes[12][11] = "CMP H";
        codes[12][12] = "CZ";
        codes[12][13] = "CC";
        codes[12][14] = "CPE";
        codes[12][15] = "CM";

        codes[13][0] = "DCR C";
        codes[13][1] = "DCR E";
        codes[13][2] = "DCR L";
        codes[13][3] = "DCR A";
        codes[13][4] = "MOV C L";
        codes[13][5] = "MOV E L";
        codes[13][6] = "MOV L L";
        codes[13][7] = "MOV A L";
        codes[13][8] = "ADC L";
        codes[13][9] = "SBB L";
        codes[13][10] = "XRA L";
        codes[13][11] = "CMP L";
        codes[13][12] = "CALL";
        codes[13][13] = "";
        codes[13][14] = "";
        codes[13][15] = "";

        codes[14][0] = "MVI C";
        codes[14][1] = "MVI E";
        codes[14][2] = "MVI L";
        codes[14][3] = "MVI AP";
        codes[14][4] = "MOV E M";
        codes[14][5] = "MOV E M";
        codes[14][6] = "MOV L M";
        codes[14][7] = "MOV A M";
        codes[14][8] = "ADC M";
        codes[14][9] = "SBB M";
        codes[14][10] = "XRA M";
        codes[14][11] = "CMP M";
        codes[14][12] = "ACI";
        codes[14][13] = "SBI";
        codes[14][14] = "XRI";
        codes[14][15] = "ORI";

        codes[15][0] = "RRC";
        codes[15][1] = "RAR";
        codes[15][2] = "CMA";
        codes[15][3] = "CMC";
        codes[15][4] = "MOV C A";
        codes[15][5] = "MOV E A";
        codes[15][6] = "MOV L A";
        codes[15][7] = "MOV A A";
        codes[15][8] = "ADC A";
        codes[15][9] = "SBB A";
        codes[15][10] = "XRA A";
        codes[15][11] = "CMP A";
        codes[15][12] = "RST 1";
        codes[15][13] = "RST 3";
        codes[15][14] = "RST 5";
        codes[15][15] = "RST 7";

        //List<JButton> listOfButtons = new ArrayList<>(15*15);

        for (int i = 0; i < codes.length; i++) {
            for (int j = 0; j < codes[0].length; j++) {
                JButton button = new JButton();
                createButton(button, codes[j][i], x + i * 80, y + j * 30, 80, 30);
                // listOfButtons.add(button);
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //System.out.println(((JButton) e.getSource()).getText());
                        String tmp=inputArea.getText();
                        tmp+=((JButton) e.getSource()).getText().toLowerCase()+"\n";
                        inputArea.setText(tmp);
                    }
                });
            }
        }
    }
}
