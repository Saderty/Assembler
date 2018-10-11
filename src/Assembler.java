import java.io.File;
import java.io.IOException;

import static Support.FileOperations.ReadFile;

public class Assembler {
    private static File programFile = new File("\\Program.txt");
    static final private char commentary = '#';

    private static String[] readProgram() throws IOException {
        String[] program = ReadFile(programFile);

        for (int i = 0; i < program.length; i++) {
            if (program[i].charAt(0) == commentary) {
                program[i] = null;
            }
        }
        //array trim
        return program;
    }

    public static void main(String[] args) throws IOException {
        String[] program = readProgram();
        for (String aProgram : program) {
            System.out.println(aProgram);
        }
    }
}
