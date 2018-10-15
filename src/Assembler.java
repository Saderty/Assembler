import Support.ArrayOperations;

import java.io.File;
import java.io.IOException;
import static Memory.Memory.*;
import static Support.ArrayOperations.TrimArray;
import static Support.FileOperations.ReadFile;

public class Assembler {
    private File programFile = new File("Program.txt");

    final private String commentary = "#";
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
            addresses[i] = program[i].toUpperCase();
        }
    }

    private void runOperations(String program) {
        String[] arguments = program.split(" ");
        switch (arguments[0]) {
            case "MVI":
                getRegister(arguments[1]).setValue(arguments[2]);
                counter++;
                break;

            case "ADD":
                if (!arguments[1].equals("M")) {
                    addRegister(regA, getRegister(arguments[1]));
                } else {
                    addRegister(regA, addresses[Integer.parseInt(getRegisterPairValue(regH))]);
                }

                counter++;
                break;

            case "ADC":
                if (arguments[1].equals("M")) {
                    if (!flagC) {
                        addRegister(regA, addresses
                                [Integer.parseInt(getRegisterPairValue(regH))]);
                    } else {
                        addRegister(regA, addresses
                                [Integer.parseInt(getRegisterPairValue(regH))]);
                        incRegister(regA);
                        flagC = false;
                    }
                }
                counter++;
                break;

            case "SUB":
                if (arguments[1].equals("A")) {
                    regA.setValue(Integer.toHexString(0));
                }
                counter++;
                break;

            case "DCR":
                decRegister(getRegister(arguments[1]));

               // int a = Integer.parseInt(memory.getRegister(arguments[1]).getValue(), 16) - 1;
               // String aa = Integer.toHexString(a);
               // memory.getRegister(arguments[1]).setValue(aa);

                counter++;
                break;

            case "MOV":
                if (arguments[1].equals("B")) {
                    if (arguments[2].equals("A")) {
                        regB.setValue(regA.getValue());
                    }
                }
                counter++;
                break;

            case "LDA":
                regA.setValue(addresses[Integer.parseInt(arguments[1])]);
                counter++;
                break;

            case "LDAX":
                regA.setValue(addresses
                        [Integer.parseInt(getRegisterPairValue(getRegister(arguments[1])))]);
                counter++;
                break;

            case "LXI":
                if (getRegister(arguments[1]) == regB) {
                    regB.setValue(arguments[2].substring(0, 2));
                    regC.setValue(arguments[2].substring(2, 4));
                }
                if (getRegister(arguments[1]) == regD) {
                    regD.setValue(arguments[2].substring(0, 2));
                    regE.setValue(arguments[2].substring(2, 4));
                }
                if (getRegister(arguments[1]) == regH) {
                    regH.setValue(arguments[2].substring(0, 2));
                    regL.setValue(arguments[2].substring(2, 4));
                }

                counter++;
                break;

            case "INX":
                incRegisterPair(getRegister(arguments[1]));
                counter++;
                break;

            case "JMP":
                counter = Integer.parseInt(arguments[1]);
                break;

            case "JZ":
                if (Integer.parseInt(regB.getValue(), 16) == 0) {
                    counter = Integer.parseInt(arguments[1]);
                } else {
                    counter++;
                }
                break;

            case "STAX":
                addresses[Integer.parseInt(getRegisterPairValue(getRegister(arguments[1])))] =
                        regA.getValue();
                counter++;
                break;

            case "SET":
                addresses[Integer.parseInt(arguments[1])] = arguments[2];
                counter++;
                break;

            case "GET":
                if (arguments[1].length() == 1) {
                    System.out.println("Register " + arguments[1] + " = " + getRegister(arguments[1]).getValue());
                }
                if (arguments[1].length() == 4) {
                    System.out.println("Address " + arguments[1] + " = " + addresses[Integer.parseInt(arguments[1])]);
                }
                counter++;
                break;

            default:
                break;
        }
    }

    private void runProgram() throws IOException {
        loadToAddresses();
        while (!addresses[counter].equals("END")) {
            System.out.println("Counter : " + counter);
            runOperations(addresses[counter]);
            displayRegisters();
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
        registers += "H : ";
        registers += regH.getValue();
        registers += " | ";
        registers += "L : ";
        registers += regL.getValue();
        registers += " | ";

        System.out.println(registers);
    }

    public static void main(String[] args) throws IOException {
        new Assembler().runProgram();
    }
}
