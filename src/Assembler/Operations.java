package Assembler;

import static Assembler.Assembler.tmpCP;
import static Assembler.Memory.*;

class Operations {
    private static void toGoto(String s) {
        for (String aGoto : Assembler.labels) {
            if (aGoto != null) {
                if (s.equals(aGoto.split(" ")[0])) {
                    regPC.setValue(aGoto.split(" ")[1]);
                    regPC.inc();
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

            case "CZ":
                if (flagZ) {
                    toGoto(arguments[1]);
                    flagZ = false;
                } else {
                    regPC.inc();
                }
                break;

            case "CNZ":
                if (!flagZ) {
                    toGoto(arguments[1]);
                } else {
                    flagZ = false;
                    regPC.inc();
                }
                break;

            case "CP":
                if (!flagS) {
                    toGoto(arguments[1]);
                    flagS = false;
                } else {
                    regPC.inc();
                }
                break;

            case "CM":
                if (flagS) {
                    toGoto(arguments[1]);
                } else {
                    flagS = false;
                    regPC.inc();
                }
                break;

            case "CC":
                if (flagC) {
                    toGoto(arguments[1]);
                    flagC = false;
                } else {
                    regPC.inc();
                }
                break;

            case "CNC":
                if (!flagC) {
                    toGoto(arguments[1]);
                } else {
                    flagC = false;
                    regPC.inc();
                }
                break;

            case "CPE":
                if (flagP) {
                    toGoto(arguments[1]);
                    flagP = false;
                } else {
                    regPC.inc();
                }
                break;

            case "CPO":
                if (!flagP) {
                    toGoto(arguments[1]);
                } else {
                    flagP = false;
                    regPC.inc();
                }
                break;

            case "RET":
                regPC.setValue(tmpCP);
                regPC.inc();
                break;

            case "RZ":
                if (!flagZ) {
                    regPC.setValue(tmpCP);
                    regPC.inc();
                } else {
                    flagZ = false;
                    regPC.inc();
                }
                break;

            case "RNZ":
                if (flagZ) {
                    regPC.setValue(tmpCP);
                    regPC.inc();
                } else {
                    regPC.inc();
                }
                break;

            case "RP":
                if (flagS) {
                    regPC.setValue(tmpCP);
                    regPC.inc();
                } else {
                    regPC.inc();
                }
                break;

            case "RM":
                if (!flagS) {
                    regPC.setValue(tmpCP);
                    regPC.inc();
                } else {
                    flagS = false;
                    regPC.inc();
                }
                break;

            case "RC":
                if (flagC) {
                    regPC.setValue(tmpCP);
                    regPC.inc();
                } else {
                    regPC.inc();
                }
                break;

            case "RNC":
                if (!flagC) {
                    regPC.setValue(tmpCP);
                    regPC.inc();
                } else {
                    flagC = false;
                    regPC.inc();
                }
                break;

            case "RPE":
                if (flagP) {
                    regPC.setValue(tmpCP);
                    regPC.inc();
                } else {
                    regPC.inc();
                }
                break;

            case "RPO":
                if (!flagP) {
                    regPC.setValue(tmpCP);
                    regPC.inc();
                } else {
                    flagP=false;
                    regPC.inc();
                }
                break;

            case "SPHL":
                regSP.setValue(regHL.getValue());
                break;

            case "SHLD":
                setMemory(arguments[1], regHL.getValue());
                break;

            case "STA":
                setMemory(arguments[1], regA.getValue());
                break;

            case "PUSH":
                pushStack(getRegisterPair(arguments[1]));
                regPC.inc();
                break;

            case "POP":
                popStack(getRegisterPair(arguments[1]));
                regPC.inc();
                break;

            case "XCHG":
                String de = regDE.getValue();
                String hl = regHL.getValue();
                regHL.setValue(de);
                regDE.setValue(hl);
                regPC.inc();
                break;

            case "XTHL":
                String sp = regSP.getValue();
                String hl1 = regHL.getValue();
                regHL.setValue(sp);
                regSP.setValue(hl1);
                regPC.inc();
                break;

            case "CMA":
                regA.setValue(String.valueOf(~toInt(regA.getValue())));
                regPC.inc();
                break;

            case "INR":
                incRegister(getRegister(arguments[1]));
                regPC.inc();
                break;

            case "RRC":
                cycleShift(true);
                regPC.inc();
                break;

            case "RAR":
                cycleShift(true);
                regPC.inc();
                break;

            case "RAL":
                cycleShift(false);
                regPC.inc();
                break;

            case "ANA":
                andShift(arguments[1]);
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
                orShift(getRegister(arguments[1]).getValue());
                regPC.inc();
                break;

            case "ORI":
                orShift(arguments[1]);
                regPC.inc();
                break;

            case "PCHL":
                regPC.setValue(regHL.getValue());
                break;

            case "XRA":
                xraShift(getRegister(arguments[1]));
                regPC.inc();
                break;

            case "XRI":
                xraShift(arguments[1]);
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
                addRegister(regA, getRegister(arguments[1]));
                if (flagC) {
                    incRegister(regA);
                    flagC = false;
                }
                regPC.inc();
                break;

            case "ACI":
                addRegister(regA, arguments[1]);
                if (flagC) {
                    incRegister(regA);
                    flagC = false;
                }
                regPC.inc();
                break;

            case "CPI":
                flagC = toInt(regA.getValue()) < toInt(arguments[1]);
                regPC.inc();
                break;

            case "DAD":
                regHL.setValue(addHex(getMemory(regHL), getMemory(getRegisterPair(arguments[1]))));
                regPC.inc();
                break;

            case "CMC":
                flagC = !flagC;
                regPC.inc();
                break;

            case "CMP":
                flagC = toInt(regA.getValue()) - toInt(getRegister(arguments[1]).getValue()) <= 0;
                break;

            case "SUB":
                subRegister(regA, getRegister(arguments[1]));
                //  counter++;
                regPC.inc();
                break;

            case "SUI":
                subRegister(regA, arguments[1]);
                regPC.inc();
                break;

            case "SBB":
                subRegister(regA, getRegister(arguments[1]));
                if (flagC) {
                    decRegister(regA);
                    flagC = false;
                }
                regPC.inc();
                break;

            case "SBI":
                subRegister(regA, arguments[1]);
                if (flagC) {
                    decRegister(regA);
                    flagC = false;
                }
                regPC.inc();
                break;

            case "DCR":
                if (getRegister(arguments[1]).getValue().equals("00")) {
                    flagZ = true;
                } else {
                    decRegister(getRegister(arguments[1]));
                }
                regPC.inc();
                break;

            case "DCX":
                getRegisterPair(arguments[1]).dec();
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

            case "LHLD":
                regHL.setValue(getMemory(arguments[1]));
                regPC.inc();
                break;

            case "LXI"://d16 -> RP
                getRegisterPair(arguments[1]).setValue(arguments[2]);
                //   counter++;
                regPC.inc();
                break;

            case "INX":
                getRegisterPair(arguments[1]).inc();
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

            case "JP":
                if (!flagS) {
                    toGoto(arguments[1]);
                    flagS = false;
                } else {
                    regPC.inc();
                }
                break;

            case "JM":
                if (flagS) {
                    toGoto(arguments[1]);
                } else {
                    flagS = false;
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

            case "STC":
                flagC = true;
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
