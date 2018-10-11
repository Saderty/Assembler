import Support.ArrayOperations;

import java.io.File;
import java.io.IOException;

import static Support.ArrayOperations.TrimArray;
import static Support.FileOperations.ReadFile;

public class Assembler {
    private static File programFile = new File("Program.txt");
    static final private String commentary = "#";

    private static String[] readProgram() throws IOException {
        String[] program = ReadFile(programFile);

        for (int i = 0; i < program.length; i++) {
            if (program[i].split("", 2)[0].equals(commentary)) {
                program[i] = null;
            }
        }
        return TrimArray(program, ArrayOperations.SPACE);
    }

    static Memory memory = new Memory();

    private static void runOperations(String program) {
        String[] arguments=program.split(" ");
        switch (arguments[0].toUpperCase()) {
            case "MVI B":
                    memory.regB.setValue(arguments[2]);
                break;
            case "MVI A":
                memory.regA.setValue(arguments[2]);
                break;
                case "ADD B"
            default:
                break;
        }
    }

    private static void runProgram() throws IOException {
        String[] program = readProgram();




        for (int i = 0; i < program.length; i++) {
            runOperations(program[i]);
        }
    }


    public static void main(String[] args) throws IOException {
        String[] program = readProgram();
        /*for (String aProgram : program) {
            System.out.println(aProgram);
        }
        System.out.println("qq");*/
        new Memory();
    }
}
