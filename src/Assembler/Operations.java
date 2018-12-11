package Assembler;

import java.util.Arrays;

import static Assembler.Assembler.tmpCP;
import static Assembler.Memory.*;
import static Assembler.Memory.toString;

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

    static int getReg(String s) {
        byte B = 0b000;
        byte C = 0b001;
        byte D = 0b010;
        byte E = 0b011;
        byte H = 0b100;
        byte L = 0b101;
        byte M = 0b110;
        byte A = 0b111;
        switch (s) {
            case "B":
                return B;
            case "C":
                return C;
            case "D":
                return D;
            case "E":
                return E;
            case "H":
                return H;
            case "L":
                return L;
            case "M":
                return M;
            case "A":
                return A;
        }
        return -1;
    }

    static int getRegPair(String s) {
        byte BC = 0b00;
        byte DE = 0b01;
        byte HL = 0b10;
        byte SP = 0b11;
        byte PSW = 0b11;
        switch (s) {
            case "B":
                return BC;
            case "D":
                return DE;
            case "H":
                return HL;
            case "SP":
                return SP;
            case "PSW":
                return PSW;
        }
        return -1;
    }

    static String[] result = new String[1000];
    static int counter = 0;

 /*   static void step(String s) {
        System.out.println(s);
        String[] tmp = s.split(" ");
        System.out.println("qq"+Arrays.toString(tmp));
        System.out.println(tmp.length);
        for (int i = 0; i < tmp.length; i++) {
            program[counter]=tmp[i];
            counter++;
            System.out.println(tmp[i]);
        }
    }*/

    static String[] exchange(String[] program) {
        Double tact = 0d;
        //int counter = 0;
        int tmp;
        for (int i = 0; i < program.length; i++) {
            if(program[i]==null){break;}
            String[] arguments = program[i].toUpperCase().split(" ");
            switch (arguments[0]) {
                case "MOV"://1b
                    tmp = 0b01 << 6;
                    tmp |= getReg(arguments[1]) << 3;
                    tmp |= getReg(arguments[2]);

                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    // step(Integer.toHexString(tmp));
                    tact += 5;
                    break;
                case "MVI"://2b
                    tmp = 0;
                    tmp |= getReg(arguments[1]) << 3;
                    tmp |= getReg("M");

                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    result[counter] = arguments[2];
                    counter++;
                    tact += 7;
                    break;
                case "LXI"://3b
                    tmp = 0;
                    tmp |= getRegPair(arguments[1]) << 4;
                    tmp |= 0b0001;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    result[counter] = arguments[2].substring(2, 4);
                    counter++;
                    result[counter] = arguments[2].substring(0, 2);
                    counter++;
                    // step(Integer.toHexString(tmp) + " " + arguments[2].substring(2, 4) + " "+ arguments[2].substring(0, 2));
                    tact += 10;
                    break;
                case "LDAX"://1b
                    tmp = 0;
                    tmp |= getRegPair(arguments[1]) << 4;
                    tmp |= 0b1010;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 7;
                    break;
                case "STAX"://1b
                    tmp = 0;
                    tmp |= getRegPair(arguments[1]) << 4;
                    tmp |= 0b0010;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 7;
                    break;
                case "LDA"://3b
                    tmp = 0b00111010;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    result[counter] = arguments[2].substring(2, 4);
                    counter++;
                    result[counter] = arguments[2].substring(0, 2);
                    counter++;
                    tact += 13;
                    break;
                case "STA"://3b
                    tmp = 0b00110010;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    result[counter] = arguments[2].substring(2, 4);
                    counter++;
                    result[counter] = arguments[2].substring(0, 2);
                    counter++;
                    tact += 13;
                    break;
                case "LHLD"://3b
                    tmp = 0b00101010;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    result[counter] = arguments[2].substring(2, 4);
                    counter++;
                    result[counter] = arguments[2].substring(0, 2);
                    counter++;
                    tact += 16;
                    break;
                case "SHLD"://3b
                    tmp = 0b00100010;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    result[counter] = arguments[2].substring(2, 4);
                    counter++;
                    result[counter] = arguments[2].substring(0, 2);
                    counter++;
                    tact += 16;
                    break;
                case "XCHG"://1b
                    tmp = 0b11101011;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 4;
                    break;
                case "XTHL"://1b
                    tmp = 0b11100011;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 18;
                    break;
                case "SPHL"://1b
                    tmp = 0b11111001;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 5;
                    break;
                case "PUSH"://1b
                    tmp = 0b11 << 6;
                    tmp |= getRegPair(arguments[1]) << 4;
                    tmp |= 0b0101;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 11;
                    break;
                case "POP"://1b
                    tmp = 0b11 << 6;
                    tmp |= getRegPair(arguments[1]) << 4;
                    tmp |= 0b0001;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 10;
                    break;
                case "ADD"://1b
                    tmp = 0b10000 << 5;
                    tmp |= getReg(arguments[1]);
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 4;
                    break;
                case "ADC"://1b
                    tmp = 0b10001 << 5;
                    tmp |= getReg(arguments[1]);
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 4;
                    break;
                case "ADI"://2b
                    tmp = 0b11000110;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    result[counter] = arguments[1];
                    counter++;
                    tact += 7;
                    break;
                case "ACI"://2b
                    tmp = 0b11001110;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    result[counter] = arguments[1];
                    counter++;
                    tact += 7;
                    break;
                case "DAD"://1b
                    tmp = 0;
                    tmp |= getRegPair(arguments[1]) << 4;
                    tmp |= 0b1010;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 10;
                    break;
                case "SUB"://1b
                    tmp = 0b10010 << 5;
                    tmp |= getReg(arguments[1]);
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 4;
                    break;
                case "SBB"://1b
                    tmp = 0b10011 << 5;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 4;
                    break;
                case "SUI"://2b
                    tmp = 0b11010110;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    result[counter] = arguments[1];
                    counter++;
                    tact += 7;
                    break;
                case "SBI"://2b
                    tmp = 0b11011110;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    result[counter] = arguments[1];
                    counter++;
                    tact += 7;
                    break;
                case "INR"://1b
                    tmp = 0;
                    tmp |= getReg(arguments[1]) << 3;
                    tmp |= 0b100;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 5;
                    break;
                case "DCR"://1b
                    tmp = 0;
                    tmp |= getReg(arguments[1]) << 3;
                    tmp |= 0b101;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 5;
                    break;
                case "INX"://1b
                    tmp = 0;
                    tmp |= getRegPair(arguments[1]) << 4;
                    tmp |= 0b0011;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 5;
                    break;
                case "DCX"://1b
                    tmp = 0;
                    tmp |= getRegPair(arguments[1]) << 4;
                    tmp |= 0b0011;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 5;
                    break;
                case "ANA"://1b
                    tmp = 0b10100 << 5;
                    tmp |= getReg(arguments[1]);
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 7;
                    break;
                case "ANI"://2b
                    tmp = 0b11100110;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    result[counter] = arguments[1];
                    counter++;
                    tact += 7;
                    break;
                case "XRA"://1b
                    tmp = 0b10101 << 5;
                    tmp |= getReg(arguments[1]);
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 4;
                    break;
                case "XRI"://2b
                    tmp = 0b11101110;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    result[counter] = arguments[1];
                    counter++;
                    tact += 7;
                    break;
                case "ORA"://1b
                    tmp = 0b10110 << 5;
                    tmp |= getReg(arguments[1]);
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    tact += 4;
                    break;
                case "ORI"://2b
                    tmp = 0b10110110;
                    result[counter] = Integer.toHexString(tmp);
                    counter++;
                    result[counter] = arguments[1];
                    counter++;
                    tact += 7;
                    break;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        String[] qq=new String[100];
        qq[0]="LXI D 9011";
        qq[1]="MOV A A";
        System.out.println(Arrays.toString(exchange(qq)));
    }

    static void runOperations(String program) {
        String[] arguments = program.split(" ");

        switch (arguments[0]) {
            case "CALL":
                pushStack(regPC);
                //tmpCP = regPC.getValue();
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
                popStack(regPC);
                //regPC.setValue(tmpCP);
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
                    flagP = false;
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
