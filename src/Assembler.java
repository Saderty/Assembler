import Support.ArrayOperations;

import java.io.File;
import java.io.IOException;

import static Support.ArrayOperations.TrimArray;
import static Support.FileOperations.ReadFile;

public class Assembler {
    private File programFile = new File("Program.txt");
    final private String commentary = "#";

    private String[] readProgram() throws IOException {
        String[] program = ReadFile(programFile);

        for (int i = 0; i < program.length; i++) {
            if (program[i].split("", 2)[0].equals(commentary)) {
                program[i] = null;
            }
        }
        return TrimArray(program, ArrayOperations.SPACE);
    }

    private Memory memory = new Memory();

    private void runOperations(String program) {
        String[] arguments = program.split(" ");
        switch (arguments[0]) {
            case "MVI":
                if (arguments[1].equals("A")) {
                    memory.regA.setValue(arguments[2]);
                }
                if (arguments[1].equals("B")) {
                    memory.regB.setValue(arguments[2]);
                }
                break;

            case "ADD":
                if (arguments[1].equals("B")) {
                    int a = Integer.parseInt(memory.regA.getValue(), 16);
                    int b = Integer.parseInt(memory.regB.getValue(), 16);

                    memory.regA.setValue(Integer.toHexString(a + b));
                }
                break;

            case "SET":
                memory.addresses[Integer.parseInt(arguments[1])] = arguments[2];
                break;

            case "GET":
                if (arguments[1].length() == 1) {
                    if (arguments[1].equals("A")) {
                        System.out.println("Register " + arguments[1] + " = " + memory.regA.getValue());
                    }
                }
                break;

            default:
                break;
        }
        //System.out.println(memory.regA.getValue());
        //System.out.println(memory.regB.getValue());
        //System.out.println();
    }

    private void runProgram() throws IOException {
        String[] program = readProgram();

        for (String aProgram : program) {
            runOperations(aProgram.toUpperCase());
        }
    }


    public static void main(String[] args) throws IOException {
        new Assembler().runProgram();
    }
}
