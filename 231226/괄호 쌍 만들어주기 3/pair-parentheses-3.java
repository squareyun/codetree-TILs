import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        
        int answer = 0;
        for (int i=0; i<s.length()-1; i++) {
            if (s.charAt(i) != '(') continue;

            for (int j=i+1; j<s.length(); j++) {
                if (s.charAt(j) == ')')
                    answer++;
            }
        }

        System.out.println(answer);
    }
}