import Support.ArrayOperations;

import java.io.File;
import java.io.IOException;

import static Memory.Memory.*;
import static Support.ArrayOperations.TrimArray;
import static Support.FileOperations.ReadFile;

public class Assembler {
    private File programFile = new File("Program.txt");
    private String[] program;

    final private String commentary = "#";
    private int counter = 0;
    private int counterStack;
    private String[] labels;

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

        //Find goto
        labels = new String[program.length];
        for (int i = 0; i < program.length; i++) {
            if (program[i].contains(":")) {
                labels[i] = program[i].toUpperCase().replaceAll(":", "") + " " + i;
            }
        }
    }

    private void toGoto(String s) {
        for (String aGoto : labels) {
            if (aGoto != null) {
                if (s.equals(aGoto.split(" ")[0])) {
                    counter = Integer.parseInt(aGoto.split(" ")[1]) + 1;
                }
            }
        }
    }

    private void runOperations(String program) {
        String[] arguments = program.split(" ");

        switch (arguments[0]) {
            case "CALL":
                counterStack = counter;
                toGoto(arguments[1]);
                break;

            case "RET":
                counter = counterStack + 1;
                break;

            case "PUSH":
                pushStack(getRegister(arguments[1]));
                counter++;
                break;

            case "POP":
                popStack(getRegister(arguments[1]));
                counter++;
                break;

            case "INR":
                incRegister(getRegister(arguments[1]));
                counter++;
                break;

            case "RAR":
                cycleShift(true);
                counter++;
                break;

            case "RAL":
                cycleShift(false);
                counter++;
                break;

            case "ANA":
                andShift(arguments[1]);
                counter++;
                break;

            case "ORA":
                orShift(arguments[1]);
                counter++;
                break;

            case "XRA":
                xraShift(arguments[1]);
                counter++;
                break;

            case "MVI":
             /*   if (arguments[1].equals("M")) {
                    getRegister(arguments[1]).setValue(getRegisterPairAddressValue(regH));
                    getRegister(arguments[1]).setValue(getRegister(arguments[2]));
                } else {
                    getRegister(arguments[1]).setValue(arguments[2]);
                }*/
                getRegister(arguments[1]).setValue(arguments[2]);
                counter++;
                break;

            case "ADD":
                /*if (arguments[1].equals("M")) {
                    addRegister(regA, getRegisterPairAddressValue(regH));
                } else {
                    addRegister(regA, getRegister(arguments[1]));
                }*/
                addRegister(regA, getRegister(arguments[1]));
                counter++;
                break;

            case "ADC":
                //  if (arguments[1].equals("M")) {
                if (!flagC) {
                    //addRegister(regA, getRegisterPairAddressValue(regH));
                    addRegister(regA, getRegister(arguments[1]));
                } else {
                    // addRegister(regA, getRegisterPairAddressValue(regH));
                    addRegister(regA, getRegister(arguments[1]));
                    incRegister(regA);
                    flagC = false;
                }
                // }
                counter++;
                break;

            case "CPI":
                int a2 = Integer.parseInt(regA.getValue(), 16);
                int b2 = Integer.parseInt(arguments[1], 16);
                flagC = a2 < b2;
                counter++;
                break;

            case "CMC":
                flagC = !flagC;
                counter++;
                break;

            case "SUB":
                subRegister(regA, getRegister(arguments[1]));
                counter++;
                break;

            case "DCR":
                if (getRegister(arguments[1]).getValue().equals("00")) {
                    flagZ = true;
                } else {
                    decRegister(getRegister(arguments[1]));
                }
                counter++;
                break;

            case "MOV"://Reg -> Reg
            /*    if (arguments[2].equals("M")) {
                    regA.setValue(getRegisterPairAddressValue(regH));
                } else {
                    getRegister(arguments[1]).setValue(getRegister(arguments[2]).getValue());
                }*/
                getRegister(arguments[1]).setValue(getRegister(arguments[2]).getValue());
                counter++;
                break;

            case "LDA"://d16 -> a16 -> RegA
                regA.setValue(addresses[Integer.parseInt(arguments[1])]);
                counter++;
                break;

            case "LDAX"://RP -> a16 -> RegA
                regA.setValue(getRegisterPairAddressValue(getRegister(arguments[1])));
                counter++;
                break;

            case "LXI"://d16 -> RP
                for (Register[] aRegPair : regPair) {
                    if (getRegister(arguments[1]) == aRegPair[0]) {
                        aRegPair[0].setValue(arguments[2].substring(0, 2));
                        aRegPair[1].setValue(arguments[2].substring(2, 4));
                    }
                }
                counter++;
                break;

            case "INX":
                incRegisterPair(getRegister(arguments[1]));
                counter++;
                break;

            case "JMP":
                toGoto(arguments[1]);
                break;

            case "JC":
                if (flagC) {
                    toGoto(arguments[1]);
                    flagC = false;
                } else {
                    counter++;
                }
                break;

            case "JNC":
                if (!flagC) {
                    toGoto(arguments[1]);
                } else {
                    flagC = false;
                    counter++;
                }
                break;

            case "JZ":
                if (flagZ) {
                    toGoto(arguments[1]);
                    flagZ = false;
                } else {
                    counter++;
                }
                break;

            case "JNZ":
                if (!flagZ) {
                    toGoto(arguments[1]);
                } else {
                    flagZ = false;
                    counter++;
                }
                break;

            case "JPE":
                if (flagP) {
                    toGoto(arguments[1]);
                    flagP = false;
                } else {
                    counter++;
                }
                break;

            case "JPO":
                if (!flagP) {
                    toGoto(arguments[1]);
                } else {
                    counter++;
                }
                break;

            case "STAX"://RegA -> RP -> a16
                //addresses[Integer.parseInt(getRegisterPairValue(getRegister(arguments[1])))] =
                //        regA.getValue();
                setAddress(getRegister(arguments[1]), regA);
                regA.setValue("00");
                counter++;
                break;

            case "SET":
                //addresses[Integer.parseInt(arguments[1])] = arguments[2];
                setAddress(Integer.parseInt(arguments[1]), arguments[2]);
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
        //goto
        if (arguments[0].contains(":")) {
            counter++;
        }
    }

    private void runProgram() throws IOException {
        loadToAddresses();
        while (!addresses[counter].equals("END")) {
            System.out.println("Counter : " + counter + "     " + program[counter]);
            runOperations(addresses[counter]);
            displayRegisters();
            displayFlags();
            displayRegistersStack();
            System.out.println();
        }
        counter++;
        while (addresses[counter].contains("GET")) {
            System.out.println("Counter : " + counter);
            runOperations(addresses[counter]);
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

        System.out.println(flags);
    }

    public static void main(String[] args) throws IOException {
        new Assembler().runProgram();
    }
}
