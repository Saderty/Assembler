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

        void setValue(String value) {
            if (value.toUpperCase().equals("M")) {
                //    this.value = getRegister(value).getValue();
                this.value = getRegisterPairAddressValue(regHL);
            } else {
                this.value = value;
            }
        }

        String getValue() {
            return value;
        }
    }

    static class RegisterPair {
        final String REG_A = "regA";
        final String REG_B = "regB";
        final String REG_C = "regC";
        final String REG_D = "regD";
        final String REG_E = "regE";
        final String REG_H = "regH";
        final String REG_L = "regL";

        private String name;

        RegisterPair(Register reg0) {
            if (reg0 == regA) {
                name = REG_A;
            }
            if (reg0 == regB) {
                name = REG_B;
            }
            if (reg0 == regD) {
                name = REG_D;
            }
            if (reg0 == regH) {
                name = REG_H;
            }
        }

        RegisterPair getRegister() {
            if (name.equals(REG_A)) {
                return regPSW;
            }
            if (name.equals(REG_B)) {
                return regBC;
            }
            if (name.equals(REG_D)) {
                return regDE;
            }
            if (name.equals(REG_H)) {
                return regHL;
            }
            return null;
        }

        Register getLowRegister(RegisterPair registerPair) {
            if (name.equals(REG_A)) {
                return regA;
            }
            if (name.equals(REG_B)) {
                return regB;
            }
            if (name.equals(REG_D)) {
                return regD;
            }
            if (name.equals(REG_H)) {
                return regH;
            }
            return null;
        }

        Register getHighRegister(RegisterPair registerPair) {
            if (name.equals(REG_A)) {
                return regF;
            }
            if (name.equals(REG_B)) {
                return regC;
            }
            if (name.equals(REG_D)) {
                return regE;
            }
            if (name.equals(REG_H)) {
                return regL;
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
            getLowRegister(getRegister()).setValue(value);
        }

        String getLowByte() {
            return getLowRegister(getRegister()).getValue();
        }

        void setHighByte(String value) {
            getHighRegister(getRegister()).setValue(value);
        }

        String getHighByte() {
            return getHighRegister(getRegister()).getValue();
        }
    }
}
