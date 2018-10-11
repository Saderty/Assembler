package Support;

public class ArrayOperations {
    public static final int NULL = 0;
    public static final int SPACE = 1;

    public static String[] TrimArray(String[] strings, int arg) {
        if (arg == NULL) {
            int n = 0;
            for (int i = 0; i < strings.length; i++) {
                if (strings[i] == null) {
                    strings=MoveArray(strings, i, 1);
                    i--;
                    n++;
                    if (strings.length < i+n) {
                        break;
                    }
                }
            }
            return CopyArray(strings,0,strings.length-n);
        }

        if (arg == SPACE) {
            int n = 0;
            for (int i = 0; i < strings.length; i++) {
                if (strings[i] == null|| strings[i].equals("")) {
                    strings=MoveArray(strings, i, 1);
                    i--;
                    n++;
                    if (strings.length < i+n) {
                        break;
                    }
                }
            }
            return CopyArray(strings,0,strings.length-n);
        }

        return strings;
    }

    public static String[] MoveArray(String[] strings, int start, int value) {
        for (int i = start; i < strings.length - 1; i++) {
            strings[i] = strings[i + value];
        }
        return strings;
    }

    public static String[] CopyArray(String[] strings,int start,int length){
        String[] tmp=new String[length];
        for (int i = 0; i < length-start; i++) {
            tmp[i]=strings[i+start];
        }
        return tmp;
    }
}
