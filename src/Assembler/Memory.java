package Assembler;

import static Assembler.Registers.Register;
import static Assembler.Registers.RegisterPair;

class Memory {
    private static final int bit = 16;
    private static final String doubleByte = "100";
    private static final String sDoubleByte = "-100";

    static String[] addresses = new String[20000];

    static boolean flagS;
    static boolean flagZ;
    static boolean flagP;
    static boolean flagC;

    static Register regA = new Register();
    static Register regB = new Register();
    static Register regC = new Register();
    static Register regD = new Register();
    static Register regE = new Register();
    static Register regH = new Register();
    static Register regL = new Register();

    static Register regM = new Register();
    static Register regF = new Register();

    static Register regPC0 = new Register();
    static Register regPC1 = new Register();
    static Register regSP0 = new Register("10");
    static Register regSP1 = new Register();

    static RegisterPair regPSW = new RegisterPair(regA);
    static RegisterPair regBC = new RegisterPair(regB);
    static RegisterPair regDE = new RegisterPair(regD);
    static RegisterPair regHL = new RegisterPair(regH);

    static RegisterPair regPC = new RegisterPair(regPC0);
    static RegisterPair regSP = new RegisterPair(regSP0);

    static Register getRegister(String s) {
        switch (s.toUpperCase()) {
            case "A":
                return regA;
            case "B":
                return regB;
            case "C":
                return regC;
            case "D":
                return regD;
            case "E":
                return regE;
            case "H":
                return regH;
            case "L":
                return regL;

            case "M":
                regM.setValue(getMemory(regHL));
                return regM;
            case "F":
                return regF;
        }
        return null;
    }

    static RegisterPair getRegisterPair(String s) {
        switch (s.toUpperCase()) {
            case "PSW":
                return regPSW;
            case "B":
                return regBC;
            case "D":
                return regDE;
            case "H":
                return regHL;
            case "PC":
                return regPC;
            case "SP":
                return regSP;
        }
        return null;
    }

    static String addHex(String arg0, String arg1) {
        return Integer.toHexString(Integer.parseInt(arg0, bit) + Integer.parseInt(arg1, bit));
    }

    static String toString(int dec) {
        return decToHex(dec);
    }

    private static String decToHex(int dec) {
        return Integer.toHexString(dec);
    }

    static int toInt(String hex) {
        return hexToDec(hex);
    }

    private static int hexToDec(String hex) {
        return Integer.parseInt(hex, bit);
    }

    private static String normalise(String s) {
        return normalise(s, 2);
    }

    static String normalise(String s, int bits) {
        if (s.length() > bits) {
            if (bits == 2) {
                flagC = true;
                s = addHex(s, sDoubleByte);
            }
        } else {
            while (s.length() < bits) {
                s = "0" + s;
            }
        }
        return s;
    }

    static void addRegister(Register reg0, Register reg1) {
        addRegister(reg0, reg1.getValue());
    }

    static void subRegister(Register reg0, Register reg1) {
        addRegister(reg0, "-" + reg1.getValue());
    }

    static void subRegister(Register reg0, String reg1) {
        addRegister(reg0, "-" + reg1);
    }

    static void incRegister(Register register) {
        addRegister(register, "1");
    }

    static void decRegister(Register register) {
        addRegister(register, "-1");
    }

    //АЛУ?
    static void addRegister(Register reg0, String reg1) {
        flagS = false;
        flagP = false;
        flagC = false;
        flagZ = false;

        String a = reg0.getValue();
        String c = addHex(a, reg1);
        c = normalise(c);
        if (reg0 == regA) {
            if (hexToDec(c) % 2 == 0) {
                flagP = true;
            }
            if (hexToDec(c) < 0) {
                flagS = true;
            }
            if (hexToDec(c) == 0) {
                flagZ = true;
            }
        }
        reg0.setValue(c);
    }

    static void andShift(Register register) {
        andShift(register.getValue());
    }

    static void andShift(String s) {
        int a = toInt(regA.getValue());
        int b;

        if (s.toUpperCase().equals("M")) {
            b = toInt(getMemory(regHL));
        } else {
            b = toInt(s);
        }
        String c = toString(a & b);

        regA.setValue(c);
    }

    static void orShift(String s) {
        int a = Integer.parseInt(regA.getValue(), bit);
        int b;

        if (s.toUpperCase().equals("M")) {
            b = Integer.parseInt(getMemory(regHL));
        } else {
            b = Integer.parseInt(getRegister(s).getValue(), bit);
        }
        String c = Integer.toHexString(a | b);

        regA.setValue(c);
    }

    static void xraShift(Register register) {
        xraShift(register.getValue());
    }

    static void xraShift(String s) {
        int a = toInt(regA.getValue());
        int b;

        if (s.toUpperCase().equals("M")) {
            b = Integer.parseInt(getMemory(regHL));
        } else {
            b = Integer.parseInt(getRegister(s).getValue(), bit);
        }
        String c = Integer.toHexString(a ^ b);

        regA.setValue(c);
    }

    static void cycleShift(boolean s) {
        int a = Integer.parseInt(regA.getValue(), 16);
        if (s) {
            a >>= 1;
        } else {
            a <<= 1;
        }
        String aa = Integer.toHexString(a);
        aa = normalise(aa);

        if (Integer.parseInt(aa, bit) % 2 == 0) {
            flagP = true;
        }

        regA.setValue(aa);
    }

    static void setMemory(RegisterPair registerPair, Register reg1) {
        setMemory(registerPair.getValue(), reg1.getValue());
    }

    static void setMemory(String address, String value) {
        setMemory(toInt(address), value);
    }

    static void setMemory(int address, String s) {
        addresses[address] = s;
    }

    static String getMemory(String address) {
        return getMemory(toInt(address));
    }

    static String getMemory(int address) {
        return addresses[address];
    }

    static String getMemory(RegisterPair registerPair) {
        return getMemory(registerPair.getValue());
    }

    static void pushStack(RegisterPair registerPair) {
        regSP.dec();
        setMemory(regSP, registerPair.getLowRegister());
        regSP.dec();
        setMemory(regSP, registerPair.getHighRegister());
    }

    static void popStack(RegisterPair registerPair) {
        registerPair.setHighByte(getMemory(regSP));
        regSP.inc();
        registerPair.setLowByte(getMemory(regSP));
        regSP.inc();
    }
}