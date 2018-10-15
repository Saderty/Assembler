import Support.ArrayOperations;

import java.io.File;
import java.io.IOException;

import static Support.ArrayOperations.TrimArray;
import static Support.FileOperations.ReadFile;

public class Assembler {
    private File programFile = new File("Program.txt");
    final private String commentary = "#";

    private Memory memory = new Memory();
    private int counter = 0;

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
        String[] program = readProgram();

        for (int i = 0; i < program.length; i++) {
            memory.addresses[i] = program[i].toUpperCase();
        }
    }

    private void runOperations(String program) {
        String[] arguments = program.split(" ");
        switch (arguments[0]) {
            case "MVI":
                if (arguments[1].equals("A")) {
                    memory.regA.setValue(arguments[2]);
                }
                if (arguments[1].equals("B")) {
                    memory.regB.setValue(arguments[2]);
                }
                counter++;
                break;

            case "ADD":
               /* if (arguments[1].equals("B")) {
                    int a = Integer.parseInt(memory.regA.getValue(), 16);
                    int b = Integer.parseInt(memory.regB.getValue(), 16);

                    memory.regA.setValue(Integer.toHexString(a + b));
                }
                if (arguments[1].equals("M")) {
                    int a = Integer.parseInt(memory.regA.getValue(), 16);
                    int b = Integer.parseInt(memory.addresses[Integer.parseInt(memory.regH.getValue() + memory.regL.getValue())]);
                    memory.regA.setValue(String.valueOf(a+b));
                }*/
                if (!arguments[1].equals("M")) {
                    memory.addRegister("a", arguments[1]);
                } else {
                    String a = memory.regA.getValue();
                    String hl = memory.regH.getValue() + memory.regL.getValue();

                    int intA = Integer.parseInt(a, 16);
                    int intHl = Integer.parseInt(memory.addresses[Integer.parseInt(hl)], 16);

                    int intRes = intA + intHl;

                    String c = Integer.toHexString(intRes);

                    if (c.length() > 2) {
                    } else {
                        while (c.length() < 2) {
                            c = "0" + c;
                        }
                    }

                    memory.regA.setValue(c);
                }

                counter++;
                break;

            case "SUB":
                if (arguments[1].equals("A")) {
                    memory.regA.setValue(Integer.toHexString(0));
                }
                counter++;
                break;

            case "DCR":
                if (arguments[1].equals("B")) {
                    int a = Integer.parseInt(memory.regB.getValue(), 16);
                    memory.regB.setValue(String.valueOf(a - 1));
                }
                counter++;
                break;

            case "MOV":
                if (arguments[1].equals("B")) {
                    if (arguments[2].equals("A")) {
                        memory.regB.setValue(memory.regA.getValue());
                    }
                }
                counter++;
                break;

            case "LDA":
                memory.regA.setValue(memory.addresses[Integer.parseInt(arguments[1])]);
                counter++;
                break;

            case "LXI":
                if (arguments[1].equals("H")) {
                    memory.regH.setValue(arguments[2].substring(0, 2));
                    memory.regL.setValue(arguments[2].substring(2, 4));
                }
                counter++;
                break;

            case "INX":
                if (arguments[1].equals("H")) {
                    memory.incRegisterPair(memory.regH);
                }
                counter++;
                break;

            case "JMP":
                counter = Integer.parseInt(arguments[1]);
                break;

            case "JZ":
                if (Integer.parseInt(memory.regB.getValue(), 16) == 0) {
                    counter = Integer.parseInt(arguments[1]);
                } else {
                    counter++;
                }
                break;

            case "SET":
                memory.addresses[Integer.parseInt(arguments[1])] = arguments[2];
                counter++;
                break;

            case "GET":
                if (arguments[1].length() == 1) {
                    if (arguments[1].equals("A")) {
                        System.out.println("Register " + arguments[1] + " = " + memory.regA.getValue());
                    }
                }
                counter++;
                break;

            default:
                break;
        }
        //System.out.println(memory.regA.getValue());
        //System.out.println(memory.regB.getValue());
        //System.out.println();
    }

    private void runProgram() throws IOException {
        loadToAddresses();
        while (!memory.addresses[counter].equals("END")) {
            System.out.println("Counter : " + counter);
            runOperations(memory.addresses[counter]);
            displayRegisters();
        }
    }

    private void displayRegisters() {
        String registers = "";
        registers += "A : ";
        registers += memory.regA.getValue();
        registers += " | ";
        registers += "B : ";
        registers += memory.regB.getValue();
        registers += " | ";
        registers += "H : ";
        registers += memory.regH.getValue();
        registers += " | ";
        registers += "L : ";
        registers += memory.regL.getValue();
        registers += " | ";

        System.out.println(registers);
    }

    public static void main(String[] args) throws IOException {
        new Assembler().runProgram();
    }
}
