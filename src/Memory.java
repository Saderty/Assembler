class Memory {
    Address[] addresses=new Address[1000];
    Register[] registers=new Register[100];

    class Address {
        private int address;
        private String value;

        Address(int address) {
            this.address = address;
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
        }

        public void setRegister(String register) {
            this.register = register;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getRegister() {
            return register;
        }

        public String getValue() {
            return value;
        }
    }
}