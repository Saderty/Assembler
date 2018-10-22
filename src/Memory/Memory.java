package Memory;

public class Memory {
    private static final int bit = 16;
    private static final String doubleByte = "100";
    private static final String sDoubleByte = "-100";

    public static String[] addresses = new String[9000];

    public static boolean flagS;
    public static boolean flagZ;
    public static boolean flagP;
    public static boolean flagC;

    public static Register regA = new Register();
    public static Register regB = new Register();
    public static Register regC = new Register();
    public static Register regD = new Register();
    public static Register regE = new Register();
    public static Register regH = new Register();
    public static Register regL = new Register();

    public static Register regBStack = new Register();
    public static Register regCStack = new Register();
    public static Register regDStack = new Register();
    public static Register regEStack = new Register();
    public static Register regHStack = new Register();
    public static Register regLStack = new Register();

    public static Register[][] regPair = {
            {regB, regC},
            {regD, regE},
            {regH, regL}};

    public static Register[][] regPairStack = {
            {regBStack, regCStack},
            {regDStack, regEStack},
            {regHStack, regLStack}};
    /*
        static  class Address {
            private int address;
            private String value;
    
            Address(int address) {
                this.address = address;
                value = "00";
            }
    
            public void setAddress(int address) {
                this.address = address;
            }
    
            public void setValue(String value) {
                this.value = value;
            }
    
            public int getAddress() {
                return address;
            }
    
            public String getValue() {
                return value;
            }
        }
    */

    public static class Register {
        private String value;

        Register() {
            value = "00";
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static Register getRegister(String a) {
        switch (a.toUpperCase()) {
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

    public static void addRegister(Register reg0, Register reg1) {
        addRegister(reg0, reg1.getValue());
    }

    public static void subRegister(Register reg0, Register reg1) {
        addRegister(reg0, "-" + reg1.getValue());
    }

    public static void addRegister(Register reg0, String reg1) {
        String a = reg0.getValue();

        String c = addHex(a, reg1);

        c = normalise(c);

        reg0.setValue(c);
    }

    public static void incRegister(Register register) {
        addRegister(register, "1");
    }

    public static void decRegister(Register register) {
        addRegister(register, "-1");
    }

    public static String getRegisterPairValue(Register register) {
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

    public static void incRegisterPair(Register register) {
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

    public static String getRegisterPairAddressValue(Register register) {
        return addresses[Integer.parseInt(getRegisterPairValue(register))];
    }

    public static void cycleShift(boolean s) {
        int a = Integer.parseInt(regA.getValue(), 16);
        if (s) {
            a >>= 1;
        } else {
            a <<= 1;
        }
        String aa = Integer.toHexString(a);
        aa = normalise(aa);
        regA.setValue(aa);
    }

    public static void pushStack(Register register) {
        for (int i = 0; i < regPair.length; i++) {
            if (regPair[i][0] == register) {
                regPairStack[i][0] = regPair[i][0];
                regPairStack[i][1] = regPair[i][1];
            }
        }
    }

    public static void popStack(Register register){
        for (int i = 0; i < regPair.length; i++) {
            if (regPair[i][0] == register) {
                regPair[i][0] = regPairStack[i][0];
                regPair[i][1] = regPairStack[i][1];
            }
        }
    }
}