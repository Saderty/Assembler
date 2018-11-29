package Assembler;

import static Assembler.GUI.getText;
import static Assembler.Memory.addresses;

public class Assembler {
    private static String[] program;

    static String[] labels;

    static String tmpCP;

    void gui() {
        program = getText();

        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = "00";
        }

        for (int i = 0; i < program.length; i++) {
            addresses[i] = program[i].toUpperCase();
        }

        labels = new String[program.length];
        for (int i = 0; i < program.length; i++) {
            if (program[i].contains(":")) {
                labels[i] = program[i].toUpperCase().replaceAll(":", "") + " " + i;
            }
        }
    }
}
