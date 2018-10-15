class Memory {
    String[] addresses = new String[2000];

    boolean flagS;
    boolean flagZ;
    boolean flagP;
    boolean flagC;

    Register regA = new Register("A");
    Register regB = new Register("B");
    Register regC = new Register("C");
    Register regD = new Register("D");
    Register regE = new Register("E");
    Register regH = new Register("H");
    Register regL = new Register("L");

    class Address {
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

    class Register {
        private String register;
        private String value;

        Register(String register) {
            this.register = register;
            value = "00";
        }

        // public void setRegister(String register) {
        //     this.register = register;
        // }

        void setValue(String value) {
            this.value = value;
        }

        //  public String getRegisterValue() {
        //      return register;
        //  }

        String getValue() {
            return value;
        }
    }

    Register getRegister(String a) {
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

    String getRegisterValue(String a) {
        return getRegister(a).getValue();
       /* switch (a.toUpperCase()) {
            case "A":
                return regA.getValue();
            case "B":
                return regB.getValue();
            case "C":
                return regC.getValue();
            case "D":
                return regD.getValue();
            case "E":
                return regE.getValue();
            case "H":
                return regH.getValue();
            case "L":
                return regL.getValue();
        }
        return null;*/
    }

    void addRegister(Register reg0, Register reg1) {
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

    void addRegister(Register reg0, String reg1) {
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

    void incRegister(Register register) {
        addRegister(register, "1");
    }

    void decRegister(Register register) {
        addRegister(register, "-1");
    }

    String getRegisterPairValue(Register register) {
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

    void incRegisterPair(Register register) {
        if (register == regH) {
            if (!regL.getValue().equals("FF")) {
                incRegister(regL);
            }
        }
    }

}