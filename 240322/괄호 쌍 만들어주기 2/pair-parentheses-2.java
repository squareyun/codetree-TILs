import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        
        int answer = 0;
        int sz = s.length();

        for (int i=0; i<sz-1; i++) {
            for (int j=i+1; j<sz-1; j++) {
                if (s.charAt(i) == '(' && s.charAt(i+1) == '(' && s.charAt(j) == ')' && s.charAt(j+1) == ')')
                    answer++;
            }
        }

        System.out.println(answer);
    }
}