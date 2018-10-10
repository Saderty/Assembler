package Support;

import java.io.*;
import java.util.Arrays;

public class FileOperations {
    private String ReadFile(File file) throws IOException {
        String splitter = "#";

        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String tmp;
        while ((tmp = bufferedReader.readLine()) != null) {
            stringBuilder.append(tmp);
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();//.split(splitter);
    }

    public static void main(String[] args) throws IOException {
        File file = new File("F:\\SYMLINK\\Users\\Downloads\\version.json");
        System.out.println(new FileOperations().ReadFile(file));
    }
}
