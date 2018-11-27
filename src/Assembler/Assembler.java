package Assembler;

import Support.ArrayOperations;

import java.io.File;
import java.io.IOException;

import static Assembler.GUI.getText;
import static Assembler.GUI.outputArea;
import static Assembler.Memory.*;
import static Support.ArrayOperations.TrimArray;
import static Support.FileOperations.ReadFile;

//TODO : INX command, register dec to hex
public class Assembler {
    private File programFile = new File("Program.txt");
    private String[] program;

    static int counter = 0;
    static int counterStack;
    static int SC = 5000;
    static String[] labels;

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

        while (!addresses[counter].equals("END")) {
            Operations.runOperations(addresses[counter]);
        }
        counter++;
        while (addresses[counter].contains("GET")) {
            Operations.runOperations(addresses[counter]);
        }

        System.out.println(program);
    }

    private String[] readProgram() throws IOException {
        String[] program = ReadFile(programFile);

        for (int i = 0; i < program.length; i++) {
            String commentary = "#";
            if (program[i].split("", 2)[0].equals(commentary)) {
                program[i] = null;
            }
        }
        return TrimArray(program, ArrayOperations.SPACE);
    }

    private void loadToAddresses() throws IOException {
        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = "00";
        }

        program = readProgram();

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

    void runProgram() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        loadToAddresses();

        while (!addresses[counter].equals("END")) {
            System.out.println("Counter : " + counter + "     " + program[counter]);
            stringBuilder.append("Counter : ").append(counter).append("     ").append(program[counter]).append("\n");
            Operations.runOperations(addresses[counter]);
            displayRegisters();
            stringBuilder.append(getDisplayRegisters()).append("\n");
            displayFlags();
            stringBuilder.append(getDisplayFlags()).append("\n");
            System.out.println();
        }
        counter++;
        while (addresses[counter].contains("GET")) {
            System.out.println("Counter : " + counter);
            stringBuilder.append("Counter : ").append(counter).append("\n");
            Operations.runOperations(addresses[counter]);
            System.out.println();
        }

        outputArea.setText(stringBuilder.toString());
    }

    String getDisplayRegisters() {
        String registers = "";
        registers += "A : ";
        registers += regA.getValue();
        registers += " | ";
        registers += "B : ";
        registers += regB.getValue();
        registers += " | ";
        registers += "C : ";
        registers += regC.getValue();
        registers += " | ";
        registers += "D : ";
        registers += regD.getValue();
        registers += " | ";
        registers += "E : ";
        registers += regE.getValue();
        registers += " | ";
        registers += "H : ";
        registers += regH.getValue();
        registers += " | ";
        registers += "L : ";
        registers += regL.getValue();
        registers += " | ";

        return registers;
    }

    private void displayRegisters() {
        System.out.println(getDisplayRegisters());
    }

    String getDisplayFlags() {
        String flags = "";
        flags += "C : " + flagC + " | ";
        flags += "Z : " + flagZ + " | ";
        flags += "P : " + flagP + " | ";
        flags += "S : " + flagS + " | ";

        return flags;
    }

    private void displayFlags() {
        System.out.println(getDisplayFlags());
    }

    public static void main(String[] args) throws IOException {
        new Assembler().runProgram();
    }
}
