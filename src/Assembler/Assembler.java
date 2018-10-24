package Assembler;

import Support.ArrayOperations;

import java.io.File;
import java.io.IOException;

import static Assembler.Memory.*;
import static Support.ArrayOperations.TrimArray;
import static Support.FileOperations.ReadFile;

//TODO : INX command, register dec to hex
public class Assembler {
    private File programFile = new File("Program2.4.1.txt");
    private String[] program;

    final private String commentary = "#";
    static int counter = 0;
    static int counterStack;
    static int SC=5000;
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

    private void runProgram() throws IOException {
        loadToAddresses();
        while (!addresses[counter].equals("END")) {
            System.out.println("Counter : " + counter + "     " + program[counter]);
            Operations.runOperations(addresses[counter]);
            displayRegisters();
            displayFlags();
            //displayRegistersStack();
            System.out.println();
        }
        counter++;
        while (addresses[counter].contains("GET")) {
            System.out.println("Counter : " + counter);
            Operations.runOperations(addresses[counter]);
            System.out.println();
        }
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

    private void displayRegistersStack() {
        String registers = "";
        registers += "A : ";
        registers += "--";
        registers += " | ";
        registers += "B : ";
        registers += regBStack.getValue();
        registers += " | ";
        registers += "C : ";
        registers += regCStack.getValue();
        registers += " | ";
        registers += "D : ";
        registers += regDStack.getValue();
        registers += " | ";
        registers += "E : ";
        registers += regEStack.getValue();
        registers += " | ";
        registers += "H : ";
        registers += regHStack.getValue();
        registers += " | ";
        registers += "L : ";
        registers += regLStack.getValue();
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
