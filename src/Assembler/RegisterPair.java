package Assembler;

import static Assembler.Memory.*;

class RegisterPair {
    final String REG_A = "regA";
    final String REG_B = "regB";
    final String REG_C = "regC";
    final String REG_D = "regD";
    final String REG_E = "regE";
    final String REG_H = "regH";
    final String REG_L = "regL";

    private String name;

    RegisterPair(Register reg0) {
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

    void setValue(String value) {
        if (name.equals(REG_B)) {
            regB.setValue(value.substring(0, 2));
            regC.setValue(value.substring(2, 4));
        }
        if (name.equals(REG_D)) {
            regD.setValue(value.substring(0, 2));
            regE.setValue(value.substring(2, 4));
        }
        if (name.equals(REG_H)) {
            regH.setValue(value.substring(0, 2));
            regL.setValue(value.substring(2, 4));
        }
    }

    String getValue() {
        if (name.equals(REG_B)) {
            return regB.getValue() + regC.getValue();
        }
        if (name.equals(REG_D)) {
            return regD.getValue() + regE.getValue();
        }
        if (name.equals(REG_H)) {
            return regH.getValue() + regL.getValue();
        }
        return null;
    }

    void setLowByte(String value) {
        if (name.equals(REG_B)) {
            regB.setValue(value);
        }
        if (name.equals(REG_D)) {
            regD.setValue(value);
        }
        if (name.equals(REG_H)) {
            regH.setValue(value);
        }
    }

    String getLowByte() {
        if (name.equals(REG_B)) {
            return regB.getValue();
        }
        if (name.equals(REG_D)) {
            return regD.getValue();
        }
        if (name.equals(REG_H)) {
            return regH.getValue();
        }
        return null;
    }

    void setHighByte(String value) {
        if (name.equals(REG_B)) {
            regC.setValue(value);
        }
        if (name.equals(REG_D)) {
            regE.setValue(value);
        }
        if (name.equals(REG_H)) {
            regL.setValue(value);
        }
    }

    String getHighByte() {
        if (name.equals(REG_B)) {
            return regC.getValue();
        }
        if (name.equals(REG_D)) {
            return regE.getValue();
        }
        if (name.equals(REG_H)) {
            return regL.getValue();
        }
        return null;
    }
}
