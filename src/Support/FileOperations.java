package Support;

import java.io.*;

 public class FileOperations {
     public static String[] ReadFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String tmp;
        while ((tmp = bufferedReader.readLine()) != null) {
            stringBuilder.append(tmp);
            stringBuilder.append("\n");
        }

        return stringBuilder.toString().split("\n");
    }
}
