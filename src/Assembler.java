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

    public static void main(String[] args) throws IOException {
        String[] program = readProgram();
        for (String aProgram : program) {
            System.out.println(aProgram);
        }
        System.out.println("qq");
    }
}
