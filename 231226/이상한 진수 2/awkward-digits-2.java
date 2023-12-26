import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        int answer = 0;
        for (int i=1; i<s.length(); i++) {
            StringBuilder sb = new StringBuilder(s);
            sb.setCharAt(i, (sb.charAt(i) == '0' ? '1' : '0'));
            answer = Math.max(answer, Integer.parseInt(sb.toString(), 2));
        }

        System.out.println(answer);
    }
}