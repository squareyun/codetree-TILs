import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][m];
        for (int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        Queue<Pos> q = new ArrayDeque<>();
        boolean[][] v = new boolean[n][m];
        q.offer(new Pos(0, 0, 0));
        v[0][0] = true;

        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};

        int answer = -1;
        while (!q.isEmpty()) {
            Pos cur = q.poll();

            for (int d=0; d<4; d++) {
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];

                if (nx < 0 || nx >= n || ny < 0 || ny >=m) continue;
                if (v[nx][ny]) continue;
                if (map[nx][ny] == 0) continue;

                q.offer(new Pos(nx, ny, cur.z + 1));
                v[nx][ny] = true;

                if (nx == n-1 && ny == m-1) {
                    answer = cur.z + 1;
                    break;
                }
            }

            if (answer != -1) break;
        }

        if (answer != -1) System.out.println(answer);
        else System.out.println(-1);
    }

    static class Pos {
        int x, y, z;
        public Pos(int x, int y, int z) {
            this.x=x;
            this.y=y;
            this.z=z;
        }
    }
}