import java.io.IOException;

class Memory {
    String[] addresses = new String[2000];
    //Address[] addresses=new Address[1000];

    boolean flagS;
    boolean flagZ;
    boolean flagP;
    boolean flagC;

    Register regA = new Register("A");
    Register regB = new Register("B");
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

        //  public String getRegister() {
        //      return register;
        //  }

        String getValue() {
            return value;
        }
    }

    String getRegister(String a) {
        switch (a.toUpperCase()) {
            case "A":
                return regA.getValue();
            case "B":
                return regB.getValue();
            case "H":
                return regH.getValue();
            case "L":
                return regL.getValue();
        }
        return null;
    }

    void addRegister(String a, String b) {
        a = getRegister(a);
        b = getRegister(b);

        int aa = Integer.parseInt(a, 16);
        int bb = Integer.parseInt(b, 16);

        int cc = aa + bb;

        String c = Integer.toHexString(cc);

        if (c.length() > 2) {
            //TODO
            flagC = true;
        } else {
            while (c.length() < 2) {
                c = "0" + c;
            }
        }

        regA.setValue(c);
    }

    void incRegister(Register register) {
        if (register == regL) {
            if (!regL.getValue().equals("FF")) {
                int a = Integer.parseInt(regL.getValue(), 16) + 1;
                String aa = Integer.toHexString(a);

                if (aa.length() < 2) {
                    while (aa.length() < 2) {
                        aa = "0" + aa;
                    }
                }

                regL.setValue(aa);
            }
        }
    }

    void incRegisterPair(Register register) {
        if (register == regH) {
            if (!regL.getValue().equals("FF")) {
                incRegister(regL);
            }
        }
    }
}