import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n+n];
        for (int i=0; i<n; i++) {
            arr[i] = sc.nextInt();
        }
        for (int i=n; i<n+n; i++) {
            arr[i] = arr[i-n];
        }

        int answer = Integer.MAX_VALUE;
        for (int i=0; i<n; i++) {
            int temp = 0;
            for (int j=0; j<n; j++) {
                temp += arr[i+j] * j;
            }
            answer = Math.min(answer, temp);
        }

        System.out.println(answer);
    }
}