package Assembler;

public class Instruction {
    private String mnemonics;
    private String code;
    private int length;
    private Double tact;

    Instruction(String mnemonics, String code, int length, Double tact) {
        this.mnemonics = mnemonics;
        this.code = code;
        this.length = length;
        this.tact = tact;
    }

    public String getMnemonics() {
        return mnemonics;
    }

    public void setMnemonics(String mnemonics) {
        this.mnemonics = mnemonics;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Double getTact() {
        return tact;
    }

    public void setTact(Double tact) {
        this.tact = tact;
    }
}
