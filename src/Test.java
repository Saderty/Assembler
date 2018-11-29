public class Test {
    public static void main(String[] args) {
        int a = Integer.parseInt("71", 16);
        int b = a >> 1;
        String c = Integer.toHexString(b);
        System.out.println(c);
    }
}
