import Support.ArrayOperations;

import java.io.File;
import java.io.IOException;

import static Support.ArrayOperations.TrimArray;
import static Support.FileOperations.ReadFile;

public class Assembler {
    private File programFile = new File("Program.txt");

    final private String commentary = "#";
    private int counter = 0;

    private Memory memory = new Memory();

    private Memory.Register regA = memory.regA;
    private Memory.Register regB = memory.regB;
    private Memory.Register regC = memory.regC;
    private Memory.Register regD = memory.regD;
    private Memory.Register regE = memory.regE;
    private Memory.Register regH = memory.regH;
    private Memory.Register regL = memory.regL;

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
                memory.getRegister(arguments[1]).setValue(arguments[2]);
                counter++;
                break;

            case "ADD":
                if (!arguments[1].equals("M")) {
                    memory.addRegister(regA, memory.getRegister(arguments[1]));
                } else {
                    memory.addRegister(regA, memory.addresses[Integer.parseInt(memory.getRegisterPairValue(regH))]);
                }

                counter++;
                break;

            case "ADC":
                if (arguments[1].equals("M")) {
                    if (!memory.flagC) {
                        memory.addRegister(regA, memory.addresses
                                [Integer.parseInt(memory.getRegisterPairValue(regH))]);
                    } else {
                        memory.addRegister(regA, memory.addresses
                                [Integer.parseInt(memory.getRegisterPairValue(regH))]);
                        memory.incRegister(regA);
                        memory.flagC = false;
                    }
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
                //memory.decRegister(memory.getRegister(arguments[1]));

                int a = Integer.parseInt(memory.getRegister(arguments[1]).getValue(), 16) - 1;
                String aa = Integer.toHexString(a);
                memory.getRegister(arguments[1]).setValue(aa);

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

            case "LDAX":
                regA.setValue(memory.addresses
                        [Integer.parseInt(memory.getRegisterPairValue(memory.getRegister(arguments[1])))]);
                counter++;
                break;

            case "LXI":
                if (memory.getRegister(arguments[1]) == regB) {
                    regB.setValue(arguments[2].substring(0, 2));
                    regC.setValue(arguments[2].substring(2, 4));
                }
                if (memory.getRegister(arguments[1]) == regD) {
                    regD.setValue(arguments[2].substring(0, 2));
                    regE.setValue(arguments[2].substring(2, 4));
                }
                if (memory.getRegister(arguments[1]) == regH) {
                    regH.setValue(arguments[2].substring(0, 2));
                    regL.setValue(arguments[2].substring(2, 4));
                }

                counter++;
                break;

            case "INX":
                memory.incRegisterPair(memory.getRegister(arguments[1]));
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

            case "STAX":
                memory.addresses[Integer.parseInt(memory.getRegisterPairValue(memory.getRegister(arguments[1])))] =
                        regA.getValue();
                counter++;
                break;

            case "SET":
                memory.addresses[Integer.parseInt(arguments[1])] = arguments[2];
                counter++;
                break;

            case "GET":
                if (arguments[1].length() == 1) {
                    System.out.println("Register " + arguments[1] + " = " + memory.getRegister(arguments[1]).getValue());
                }
                if (arguments[1].length() == 4) {
                    System.out.println("Address " + arguments[1] + " = " + memory.addresses[Integer.parseInt(arguments[1])]);
                }
                counter++;
                break;

            default:
                break;
        }
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
