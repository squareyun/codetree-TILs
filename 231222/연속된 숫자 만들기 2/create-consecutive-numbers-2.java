import java.util.*;

public class Main {
    static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();

        dfs(a, b, c, 0);
        System.out.println(answer);
    }

    static void dfs(int a, int b, int c, int cnt) {
        if (a == b || b == c) {
            return;
        }

        if ((b-a) == 1 && (c-b) == 1) {
            answer = Math.min(answer, cnt);
            return;
        }

        // a를 이동
        dfs(b, c-1, c, cnt + 1);
        // c를 이동
        dfs(a, a+1, b, cnt + 1);
    }
}