package Assembler;

import static Assembler.Assembler.SC;

public class Memory {
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

    static RegisterPair regBC = new RegisterPair(regB);
    static RegisterPair regDE = new RegisterPair(regD);
    static RegisterPair regHL = new RegisterPair(regH);

    static Register regBStack = new Register();
    static Register regCStack = new Register();
    static Register regDStack = new Register();
    static Register regEStack = new Register();
    static Register regHStack = new Register();
    static Register regLStack = new Register();

    static Register[][] regPair = {
            {regB, regC},
            {regD, regE},
            {regH, regL}};

    static class Register {
        private String value;

        Register() {
            value = "00";
        }

        void setValue(String value) {
            if (value.toUpperCase().equals("M")) {
                this.value = getRegister(value).getValue();
            } else {
                this.value = value;
            }
        }

        String getValue() {
            return value;
        }
    }

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
                regM.setValue(getRegisterPairAddressValue(regH));
                return regM;
        }
        return null;
    }

    static RegisterPair getRegisterPair(String s) {
        switch (s.toUpperCase()) {
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

    static String getRegisterPairValue(Register register) {
        for (Register[] aRegPair : regPair) {
            if (register == aRegPair[0]) {
                return aRegPair[0].getValue() + aRegPair[1].getValue();
            }
        }
        return null;
    }

    public static void setRegisterPairValue(Register register, String s) {
        for (Register[] aRegPair : regPair) {
            if (register == aRegPair[0]) {
                String tmp = s;
                while (tmp.length() < 4) {
                    tmp = "0" + tmp;
                }
                aRegPair[0].setValue(tmp.substring(0, 2));
                aRegPair[1].setValue(tmp.substring(2, 4));
            }
        }
    }

    static void incRegisterPair(Register register) {
        for (Register[] aRegPair : regPair) {
            if (register == aRegPair[0]) {
                String tmp = getRegisterPairValue(aRegPair[0]);
                tmp = addHex(tmp, "1");
                while (tmp.length() < 4) {
                    tmp = "0" + tmp;
                }
                aRegPair[0].setValue(tmp.substring(0, 2));
                aRegPair[1].setValue(tmp.substring(2, 4));
            }
        }
    }

    static String getRegisterPairAddressValue(Register register) {
        return addresses[Integer.parseInt(getRegisterPairValue(register))];
    }

    static void andShift(String s) {
        int a = Integer.parseInt(regA.getValue(), bit);
        int b;

        if (s.toUpperCase().equals("M")) {
            b = Integer.parseInt(getRegisterPairAddressValue(regH));
        } else {
            b = Integer.parseInt(getRegister(s).getValue(), bit);
        }
        String c = Integer.toHexString(a & b);

        regA.setValue(c);
    }

    static void orShift(String s) {
        int a = Integer.parseInt(regA.getValue(), bit);
        int b;

        if (s.toUpperCase().equals("M")) {
            b = Integer.parseInt(getRegisterPairAddressValue(regH));
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
            b = Integer.parseInt(getRegisterPairAddressValue(regH));
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

    static void setAddress(Register reg0, Register reg1) {
        setAddress(Integer.parseInt(getRegisterPairValue(reg0)), reg1.getValue());
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