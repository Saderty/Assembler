package Assembler;

import static Assembler.Assembler.tmpCP;
import static Assembler.Memory.*;

class Operations {
    private static void toGoto(String s) {
        for (String aGoto : Assembler.labels) {
            if (aGoto != null) {
                if (s.equals(aGoto.split(" ")[0])) {
                    regPC.setValue(aGoto.split(" ")[1]);
                    incRegisterPair(regPC);
                }
            }
        }
    }

    static void runOperations(String program) {
        String[] arguments = program.split(" ");

        switch (arguments[0]) {
            case "CALL":
                tmpCP = regPC.getValue();
                toGoto(arguments[1]);
                break;

            case "RET":
                regPC.setValue(tmpCP);
                incRegisterPair(regPC);
                break;

            case "PUSH":
                pushStack(getRegisterPair(arguments[1]));
                //counter++;
                regPC.inc();
                break;

            case "POP":
                popStack(getRegisterPair(arguments[1]));
                regPC.inc();
                // counter++;
                break;

            case "XCHG":
                String de = regDE.getValue();
                String hl = regHL.getValue();
                regHL.setValue(de);
                regDE.setValue(hl);
                //counter++;
                regPC.inc();
                break;

            case "CMA":
                regA.setValue(String.valueOf(~Integer.parseInt(regA.getValue(), 16)));
                //counter++;
                regPC.inc();
                break;

            case "INR":
                incRegister(getRegister(arguments[1]));
                // counter++;
                regPC.inc();
                break;

            case "RRC":
                cycleShift(true);
                //counter++;
                regPC.inc();
                break;

            case "RAR":
                cycleShift(true);
                //      counter++;
                regPC.inc();
                break;

            case "RAL":
                cycleShift(false);
                //      counter++;
                regPC.inc();
                break;

            case "ANA":
                andShift(arguments[1]);
                //       counter++;
                regPC.inc();
                break;

            case "ANI":
                andShift(arguments[1]);
                //       counter++;
                regPC.inc();
                break;

            case "ADI":
                addRegister(regA, arguments[1]);
                //      counter++;
                regPC.inc();
                break;

            case "ORA":
                orShift(arguments[1]);
                //      counter++;
                regPC.inc();
                break;

            case "XRA":
                xraShift(arguments[1]);
                //     counter++;
                regPC.inc();
                break;

            case "MVI":
                getRegister(arguments[1]).setValue(arguments[2]);
                //     counter++;
                regPC.inc();
                break;

            case "ADD":
                addRegister(regA, getRegister(arguments[1]));
                //     counter++;
                regPC.inc();
                break;

            case "ADC":
                if (!flagC) {
                    addRegister(regA, getRegister(arguments[1]));
                } else {
                    addRegister(regA, getRegister(arguments[1]));
                    incRegister(regA);
                    flagC = false;
                }
                //  counter++;
                regPC.inc();
                break;

            case "CPI":
                flagC = toInt(regA.getValue()) < toInt(getRegister(arguments[1]).getValue());
                regPC.inc();
                break;

            case "CMC":
                flagC = !flagC;
                //   counter++;
                regPC.inc();
                break;

            case "SUB":
                subRegister(regA, getRegister(arguments[1]));
                //  counter++;
                regPC.inc();
                break;

            case "DCR":
                if (getRegister(arguments[1]).getValue().equals("00")) {
                    flagZ = true;
                } else {
                    decRegister(getRegister(arguments[1]));
                }
                //  counter++;
                regPC.inc();
                break;

            case "MOV"://Reg -> Reg
                getRegister(arguments[1]).setValue(getRegister(arguments[2]).getValue());
                regPC.inc();
                break;

            case "LDA"://d16 -> a16 -> RegA
                regA.setValue(getMemory(arguments[1]));
                regPC.inc();
                break;

            case "LDAX"://RP -> a16 -> RegA
                regA.setValue(getMemory(getRegisterPair(arguments[1])));
                //  counter++;
                regPC.inc();
                break;

            case "LXI"://d16 -> RP
                getRegisterPair(arguments[1]).setValue(arguments[2]);
                //   counter++;
                regPC.inc();
                break;

            case "INX":
                incRegisterPair(getRegisterPair(arguments[1]));
                //   counter++;
                regPC.inc();
                break;

            case "JMP":
                toGoto(arguments[1]);
                break;

            case "JC":
                if (flagC) {
                    toGoto(arguments[1]);
                    flagC = false;
                } else {
                    //     counter++;
                    regPC.inc();
                }
                break;

            case "JNC":
                if (!flagC) {
                    toGoto(arguments[1]);
                } else {
                    flagC = false;
                    //    counter++;
                    regPC.inc();
                }
                break;

            case "JZ":
                if (flagZ) {
                    toGoto(arguments[1]);
                    flagZ = false;
                } else {
                    //   counter++;
                    regPC.inc();
                }
                break;

            case "JNZ":
                if (!flagZ) {
                    toGoto(arguments[1]);
                } else {
                    flagZ = false;
                    //  counter++;
                    regPC.inc();
                }
                break;

            case "JPE":
                if (flagP) {
                    toGoto(arguments[1]);
                    flagP = false;
                } else {
                    //   counter++;
                    regPC.inc();
                }
                break;

            case "JPO":
                if (!flagP) {
                    toGoto(arguments[1]);
                } else {
                    //   counter++;
                    regPC.inc();
                }
                break;

            case "STAX"://RegA -> RP -> a16
                setMemory(getRegisterPair(arguments[1]), regA);
                regA.setValue("00");
                // counter++;
                regPC.inc();
                break;

            case "SET":
                setMemory(arguments[1], arguments[2]);
                regPC.inc();
                break;

          /*  case "GET":
                if (arguments[1].length() == 1) {
                    System.out.println("Register " + arguments[1] + " = " + getRegister(arguments[1]).getValue());
                }
                if (arguments[1].length() == 4) {
                    System.out.println("Address " + arguments[1] + " = " + addresses[Integer.parseInt(arguments[1])]);
                }
                regPC.inc();
                break;
*/

            default:
                break;
        }

        if (arguments[0].contains(":")) {
            regPC.inc();
        }
    }
}
