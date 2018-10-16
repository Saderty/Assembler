import Support.ArrayOperations;

import java.io.File;
import java.io.IOException;

import static Memory.Memory.*;
import static Support.ArrayOperations.TrimArray;
import static Support.FileOperations.ReadFile;

//TODO : Add Flags
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
        //
        for (int i = 0; i < addresses.length; i++) {
            addresses[i]="00";
        }
        //
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

                        if (regA.getValue().length() > 2) {
                            flagC = true;
                            addRegister(regA, "-100");
                        }
                    } else {
                        addRegister(regA, addresses
                                [Integer.parseInt(getRegisterPairValue(regH))]);
                        incRegister(regA);
                        flagC = false;

                        if (regA.getValue().length() > 2) {
                            flagC = true;
                            addRegister(regA, "-100");
                        }
                    }
                }
                counter++;
                break;

            case "CPI":
                int a = Integer.parseInt(regA.getValue(), 16);
                int b = Integer.parseInt(arguments[1], 16);
                flagC = a < b;
                counter++;
                break;

            case "SUB":
                getRegister(arguments[1]).setValue("00");
                counter++;
                break;

            case "DCR":
                if (Integer.parseInt(getRegister(arguments[1]).getValue(), 16) == 0) {
                    flagZ = true;
                } else {
                    decRegister(getRegister(arguments[1]));
                }

                counter++;
                break;

            case "MOV":
                if (!arguments[2].equals("M")) {
                    getRegister(arguments[1]).setValue(getRegister(arguments[2]).getValue());
                } else {
                    regA.setValue(addresses[Integer.parseInt(getRegisterPairValue(regH))]);
                }
                counter++;
                break;

            case "LDA"://d16 -> a16 -> RegA
                regA.setValue(addresses[Integer.parseInt(arguments[1])]);
                counter++;
                break;

            case "LDAX"://RP -> a16 -> RegA
                if (addresses[Integer.parseInt(getRegisterPairValue(getRegister(arguments[1])))] != null) {
                    regA.setValue(addresses
                            [Integer.parseInt(getRegisterPairValue(getRegister(arguments[1])))]);
                }
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

            case "JC":
                if (flagC) {
                    counter = Integer.parseInt(arguments[1]);
                    flagC = false;
                } else {
                    counter++;
                }
                break;

            case "JNC":
                if (!flagC) {
                    counter = Integer.parseInt(arguments[1]);
                    //flagC = false;
                } else {
                    counter++;
                }
                break;

            case "JNZ":
                if (!flagZ) {
                    counter = Integer.parseInt(arguments[1]);
                    //flagZ = false;
                } else {
                    counter++;
                }
                break;

            case "JZ":
                if (flagZ) {
                    counter = Integer.parseInt(arguments[1]);
                    flagZ = false;
                } else {
                    counter++;
                }
                break;

            case "STAX"://RegA -> RP -> a16
                addresses[Integer.parseInt(getRegisterPairValue(getRegister(arguments[1])))] =
                        regA.getValue();
                regA.setValue("00");
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
            displayFlags();
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

    private void displayFlags() {
        String flags = "";
        flags += "C : " + flagC + " | ";
        flags += "Z : " + flagZ + " | ";

        System.out.println(flags);
    }

    public static void main(String[] args) throws IOException {
        new Assembler().runProgram();
    }
}
