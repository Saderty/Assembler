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

    final private String commentary = "#";
    static int counter = 0;
    static int counterStack;
    static int SC = 5000;
    static String[] labels;

    private String[] readProgram() throws IOException {
        String[] program = ReadFile(programFile);

        for (int i = 0; i < program.length; i++) {
            if (program[i].split("", 2)[0].equals(commentary)) {
                program[i] = null;
            }
        }
        return TrimArray(program, ArrayOperations.SPACE);
    }

    private void loadToAddresses(boolean gui) throws IOException {
        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = "00";
        }
        if (!gui) {
            program = readProgram();
        } else {
            program = getText();
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

    public void runProgram() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        loadToAddresses(true);
        while (!addresses[counter].equals("END")) {
            System.out.println("Counter : " + counter + "     " + program[counter]);
            stringBuilder.append("Counter : ").append(counter).append("     ").append(program[counter]).append("\n");
            Operations.runOperations(addresses[counter]);
            displayRegisters();
            displayFlags();
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

    private void displayRegisters() {
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

        System.out.println(registers);
    }

    private void displayFlags() {
        String flags = "";
        flags += "C : " + flagC + " | ";
        flags += "Z : " + flagZ + " | ";
        flags += "P : " + flagP + " | ";
        flags += "S : " + flagS + " | ";

        System.out.println(flags);
    }

    public static void main(String[] args) throws IOException {
        new Assembler().runProgram();
    }
}
