import java.util.*;
import java.io.*;

public class Main {

    static final int MAX_L = 41;
    static final int MAX_N = 31;

    static int L, N, Q;
    static int[][] board = new int[MAX_L][MAX_L];
    static int[] r = new int[MAX_N], c = new int[MAX_N], h = new int[MAX_N], w = new int[MAX_N], k = new int[MAX_N];
    static int[] nr = new int[MAX_N], nc = new int[MAX_N];
    static int[] k_origin = new int[MAX_N];
    static int[] dmg = new int[MAX_N];
    static boolean[] v = new boolean[MAX_N];

    static final int[] dx = {-1, 0, 1, 0}; //위쪽, 오른쪽, 아래쪽, 왼쪽
    static final int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        L = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        for (int i=1; i<=L; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=1; j<=L; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i=1; i<=N; i++) {
            st = new StringTokenizer(br.readLine());
            r[i] = Integer.parseInt(st.nextToken());
            c[i] = Integer.parseInt(st.nextToken());
            h[i] = Integer.parseInt(st.nextToken());
            w[i] = Integer.parseInt(st.nextToken());
            k[i] = Integer.parseInt(st.nextToken());
            k_origin[i] = k[i];
        }

        int id, d;
        while (Q-->0) {
            st = new StringTokenizer(br.readLine());
            id = Integer.parseInt(st.nextToken());
            d = Integer.parseInt(st.nextToken());
            move(id, d);
        }

        int ans = 0;
        for (int i=1; i<=N; i++) {
            if (k[i] > 0) {
                ans += k_origin[i] - k[i];
            }
        }

        System.out.println(ans);
    }

    static void move(int id, int d) {
        if (k[id] <= 0) return;

        if (tryMove(id, d)) {
            for (int i=1; i<=N; i++) {
                r[i]= nr[i];
                c[i] = nc[i];
                k[i] -= dmg[i];
            }
        }
    }

    static boolean tryMove(int id, int d) {
        
        for (int i=1; i<=N; i++) {
            dmg[i] = 0;
            nr[i] = r[i];
            nc[i] = c[i];
            v[i] = false;
        }

        Queue<Integer> q = new LinkedList<>();
        q.add(id);
        v[id] = true;

        while (!q.isEmpty()) {
            int x = q.poll();

            nr[x] += dx[d];
            nc[x] += dy[d];

            if (nr[x] < 1 || nc[x] < 1 || nr[x] + h[x] - 1 > L || nc[x] + w[x] - 1 > L) {
                return false;
            }
            
            // 함정, 벽 충돌 검사
            for (int i=nr[x]; i<=nr[x]+h[x]-1; i++) {
                for (int j=nc[x]; j<=nc[x]+w[x]-1; j++) {
                    if (board[i][j] == 1)
                        dmg[x]++;
                    if (board[i][j] == 2)
                        return false;
                }
            }

            // 다른 조각과 충돌 시, 해당 조각도 큐에 추가
            for (int i=1; i<=N; i++) {
                if (v[i] || k[i] <= 0)
                    continue;
                if (r[i] > nr[x]+h[x]-1 || nr[x] > r[i]+h[i]-1)
                    continue;
                if (c[i] > nc[x]+w[x]-1 || nc[x] > c[i]+w[i]-1)
                    continue;
                
                q.add(i);
                v[i] = true;
            }
        }

        dmg[id] = 0; //단, 명령을 받은 기사는 피해를 입지 않으며
        return true;
    }
}