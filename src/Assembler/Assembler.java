package Assembler;

import javax.swing.*;

import static Assembler.GUI.getText;
import static Assembler.Memory.addresses;
import static Assembler.Memory.normalise;

public class Assembler {
    static String[] labels;

    static String tmpCP;

    void gui() {
        String[] program = getText();

        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = "00";
        }

        for (int i = 0; i < program.length; i++) {
            addresses[i] = program[i].toUpperCase();
        }

        labels = new String[program.length];
        for (int i = 0; i < program.length; i++) {
            if (program[i].contains(":")) {
                labels[i] = program[i].toUpperCase().replaceAll(":", "") + " " + normalise(Integer.toHexString(i), 4);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI());

        /*String[] nums = {"11","22","23","09","1e"};
        int acc = 0;
        for (int i = 0; i < nums.length; i++) {
            acc += Integer.parseInt(nums[i], 16);
        }
        System.out.println(acc);
        System.out.println(Integer.toHexString(acc));*/
    }
}
