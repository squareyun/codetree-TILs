import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();
        int x = sc.nextInt();
        int y = sc.nextInt();

        // A-B
        int answer = Math.abs(A-B);

        // caseA : A-x-y-B
        int caseA = Math.abs(A-x) + Math.abs(B-y);
        
        // caseB : A-y-x-B
        int caseB = Math.abs(A-y) + Math.abs(B-x);

        answer = Math.min(answer, Math.min(caseA, caseB));
        System.out.println(answer);
    }
}