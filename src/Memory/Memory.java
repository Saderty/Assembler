package Memory;

public class Memory {
    public static String[] addresses = new String[2000];

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

    public static void addRegister(Register reg0, Register reg1) {
        String a = reg0.getValue();
        String b = reg1.getValue();

        int aa = Integer.parseInt(a, 16);
        int bb = Integer.parseInt(b, 16);

        int cc = aa + bb;

        String c = Integer.toHexString(cc);

        if (c.length() > 2) {
            flagC = true;
        } else {
            while (c.length() < 2) {
                c = "0" + c;
            }
        }

        regA.setValue(c);
    }

    public static void addRegister(Register reg0, String reg1) {
        String a = reg0.getValue();

        int aa = Integer.parseInt(a, 16);
        int bb = Integer.parseInt(reg1, 16);

        int cc = aa + bb;
        String c = Integer.toHexString(cc);

        if (c.length() > 2) {
            flagC = true;
        } else {
            while (c.length() < 2) {
                c = "0" + c;
            }
        }

        reg0.setValue(c);
    }

    public static void incRegister(Register register) {
        addRegister(register, "1");
    }

    public static void decRegister(Register register) {
        addRegister(register, "-1");
    }

    public static String getRegisterPairValue(Register register) {
        if (register == regB) {
            return regB.getValue() + regC.getValue();
        }
        if (register == regD) {
            return regD.getValue() + regE.getValue();
        }
        if (register == regH) {
            return regH.getValue() + regL.getValue();
        }
        return null;
    }

    public static void incRegisterPair(Register register) {
        if (register.getValue().length() > 2) {
            flagC = true;
        } else {
            if (register == regB) {
                incRegister(regC);
            }
            if (register == regD) {
                incRegister(regE);
            }
            if (register == regH) {
                incRegister(regL);
            }
        }
    }

}