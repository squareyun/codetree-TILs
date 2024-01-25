import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        StringTokenizer st = null;
        Pos[] data = new Pos[N];
        int x, y;
        int total = 0;
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());
            data[i] = new Pos(x, y);

            if (i!=0) {
                total += Math.abs(data[i-1].x - x) + Math.abs(data[i-1].y - y);
            }
        }

        int answer = Integer.MAX_VALUE;
        for (int i=1; i<N-1; i++) {
            int temp = total;
            temp -= Math.abs(data[i-1].x - data[i].x) + Math.abs(data[i-1].y - data[i].y);
            temp -= Math.abs(data[i+1].x - data[i].x) + Math.abs(data[i+1].y - data[i].y);
            temp += Math.abs(data[i-1].x - data[i+1].x) + Math.abs(data[i-1].y - data[i+1].y);
            answer = Math.min(answer, temp);
        }

        System.out.println(answer);
    }

    static class Pos {
        int x, y;
        public Pos(int x, int y) {
            this.x=x;
            this.y=y;
        }
    }
}