package Assembler;

import static Assembler.Memory.*;
import static Assembler.Memory.regE;
import static Assembler.Memory.regL;

class Registers {
    static class Register {
        private String value;

        Register() {
            value = "00";
        }

        Register(String value) {
            this.value = value;
        }

        void setValue(String value) {
            if (value.toUpperCase().equals("M")) {
                this.value = getMemory(regHL);
            } else {
                this.value = value;
            }
        }

        String getValue() {
            return value;
        }
    }

    static class RegisterPair {
        final String REG_PSW = "PSW";
        final String REG_BC = "BC";
        final String REG_DE = "DE";
        final String REG_HL = "HL";

        final String REG_PC = "PC";
        final String REG_SP = "SP";

        private String name;

        RegisterPair(Register reg0) {
            if (reg0 == regA) {
                name = REG_PSW;
            }
            if (reg0 == regB) {
                name = REG_BC;
            }
            if (reg0 == regD) {
                name = REG_DE;
            }
            if (reg0 == regH) {
                name = REG_HL;
            }
            if (reg0 == regPC0) {
                name = REG_PC;
            }
            if (reg0 == regSP0) {
                name = REG_SP;
            }
        }

        RegisterPair getRegister() {
            if (name.equals(REG_PSW)) {
                return regPSW;
            }
            if (name.equals(REG_BC)) {
                return regBC;
            }
            if (name.equals(REG_DE)) {
                return regDE;
            }
            if (name.equals(REG_HL)) {
                return regHL;
            }
            if (name.equals(REG_PC)) {
                return regPC;
            }
            if (name.equals(REG_SP)) {
                return regSP;
            }
            return null;
        }

        Register getLowRegister() {
            if (name.equals(REG_PSW)) {
                return regA;
            }
            if (name.equals(REG_BC)) {
                return regB;
            }
            if (name.equals(REG_DE)) {
                return regD;
            }
            if (name.equals(REG_HL)) {
                return regH;
            }
            if (name.equals(REG_PC)) {
                return regPC0;
            }
            if (name.equals(REG_SP)) {
                return regSP0;
            }
            return null;
        }

        Register getHighRegister() {
            if (name.equals(REG_PSW)) {
                return regF;
            }
            if (name.equals(REG_BC)) {
                return regC;
            }
            if (name.equals(REG_DE)) {
                return regE;
            }
            if (name.equals(REG_HL)) {
                return regL;
            }
            if (name.equals(REG_PC)) {
                return regPC1;
            }
            if (name.equals(REG_SP)) {
                return regSP1;
            }
            return null;
        }

        void setValue(String value) {
            getRegister().setLowByte(value.substring(0, 2));
            getRegister().setHighByte(value.substring(2, 4));
        }

        String getValue() {
            return getRegister().getLowByte() + getRegister().getHighByte();
        }

        void setLowByte(String value) {
            getLowRegister().setValue(value);
        }

        String getLowByte() {
            return getLowRegister().getValue();
        }

        void setHighByte(String value) {
            getHighRegister().setValue(value);
        }

        String getHighByte() {
            return getHighRegister().getValue();
        }

        void inc() {
            String tmp = getRegister().getValue();
            tmp = addHex(tmp, "1");
            tmp = normalise(tmp, 4);
            getRegister().setValue(tmp);
        }

        void dec() {
            String tmp = getRegister().getValue();
            tmp = addHex(tmp, "-1");
            tmp = normalise(tmp, 4);
            getRegister().setValue(tmp);
        }
    }
}
