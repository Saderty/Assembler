package Support;

public class ArrayOperations {
    String[] TrimArray(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            while (strings[i] == null) {
                for (int j = i; j < strings.length-i; j++) {
                    strings [j]=strings[j++];
                }
            }

        }
        return strings;
    }
}
