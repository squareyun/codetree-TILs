import java.util.*;

public class Main {
    static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int[] num = new int[3];
        num[0] = sc.nextInt();
        num[1] = sc.nextInt();
        num[2] = sc.nextInt();

        // 정렬
        Arrays.sort(num);
        
        int answer = 0;
        if ((num[1] - num[0]) == 1 && (num[2] - num[1]) == 1) {
            answer = 0;
        } else if ((num[1] - num[0]) == 2 || (num[2] - num[1]) == 2) {
            answer = 1;
        } else {
            answer = 2;
        }

        System.out.println(answer);
    }
}