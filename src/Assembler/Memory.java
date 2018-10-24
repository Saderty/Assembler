package Assembler;

import static Assembler.Assembler.SC;
import static Assembler.Registers.*;

class Memory {
    private static final int bit = 16;
    private static final String doubleByte = "100";
    private static final String sDoubleByte = "-100";

    static String[] addresses = new String[9000];

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

    static RegisterPair regPSW = new RegisterPair(regA);
    static RegisterPair regBC = new RegisterPair(regB);
    static RegisterPair regDE = new RegisterPair(regD);
    static RegisterPair regHL = new RegisterPair(regH);

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
                regM.setValue(getRegisterPairAddressValue(regHL));
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
        }
        return null;
    }

    private static String addHex(String arg0, String arg1) {
        return Integer.toHexString(Integer.parseInt(arg0, bit) + Integer.parseInt(arg1, bit));
    }

    private static String normalise(String s) {
        if (s.length() > 2) {
            flagC = true;
            s = addHex(s, sDoubleByte);
        } else {
            while (s.length() < 2) {
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

    static void incRegister(Register register) {
        addRegister(register, "1");
    }

    static void decRegister(Register register) {
        addRegister(register, "-1");
    }

    static void addRegister(Register reg0, String reg1) {
        flagS = false;
        flagP = false;
        flagC = false;
        flagZ = false;

        String a = reg0.getValue();
        String c = addHex(a, reg1);
        c = normalise(c);
        if (reg0 == regA) {
            if (Integer.parseInt(c, bit) % 2 == 0) {
                flagP = true;
            }
            if (Integer.parseInt(c, bit) < 0) {
                flagS = true;
            }
            if (Integer.parseInt(c, bit) == 0) {
                flagZ = true;
            }
        }
        reg0.setValue(c);
    }

    static void incRegisterPair(RegisterPair registerPair) {
        String tmp = registerPair.getValue();
        tmp = addHex(tmp, "1");
        while (tmp.length() < 4) {
            tmp = "0" + tmp;
        }
        registerPair.setValue(tmp);
    }

    static String getRegisterPairAddressValue(RegisterPair registerPair) {
        return addresses[Integer.parseInt(registerPair.getValue())];
    }

    static void andShift(Register register) {
        andShift(register.getValue());
    }

    static void andShift(String s) {
        int a = Integer.parseInt(regA.getValue(), bit);
        int b;

        if (s.toUpperCase().equals("M")) {
            b = Integer.parseInt(getRegisterPairAddressValue(regHL));
        } else {
            b = Integer.parseInt(s, bit);
        }
        String c = Integer.toHexString(a & b);

        regA.setValue(c);
    }

    static void orShift(String s) {
        int a = Integer.parseInt(regA.getValue(), bit);
        int b;

        if (s.toUpperCase().equals("M")) {
            b = Integer.parseInt(getRegisterPairAddressValue(regHL));
        } else {
            b = Integer.parseInt(getRegister(s).getValue(), bit);
        }
        String c = Integer.toHexString(a | b);

        regA.setValue(c);
    }

    static void xraShift(String s) {
        int a = Integer.parseInt(regA.getValue(), bit);
        int b;

        if (s.toUpperCase().equals("M")) {
            b = Integer.parseInt(getRegisterPairAddressValue(regHL));
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

    static void setAddress(RegisterPair registerPair, Register reg1) {
        setAddress(Integer.parseInt(registerPair.getValue()), reg1.getValue());
    }

    static void setAddress(int address, String s) {
        addresses[address] = s;
    }

    static void pushStack(RegisterPair registerPair) {
        SC--;
        addresses[SC] = registerPair.getLowByte();
        SC--;
        addresses[SC] = registerPair.getHighByte();
    }

    static void popStack(RegisterPair registerPair) {
        registerPair.setHighByte(addresses[SC]);
        SC++;
        registerPair.setLowByte(addresses[SC]);
        SC++;
    }
}