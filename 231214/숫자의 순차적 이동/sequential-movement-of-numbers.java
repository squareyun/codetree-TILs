import java.util.*;
import java.io.*;

public class Main {

    static int n, m;
    static int[][] map;
    static int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        for (int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        while (m-- > 0) {
            // 탐색할 위치 검색
            for (int i=1; i<=n*n; i++) {
                Pos pos = findNext(i);

                // 인접한 칸 중 큰 곳
                int maxV = -1;
                int moveD = 0;
                for (int d=0; d<8; d++) {
                    int nx = pos.x + dx[d];
                    int ny = pos.y + dy[d];

                    if (nx < 0 || nx >= n || ny < 0 || ny >= n)
                        continue;

                    if (maxV < map[nx][ny]) {
                        maxV = map[nx][ny];
                        moveD = d;
                    }
                }

                int temp = map[pos.x][pos.y];
                map[pos.x][pos.y] = maxV;
                map[pos.x + dx[moveD]][pos.y + dy[moveD]] = temp;
            }
        }

        // 출력
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    static Pos findNext(int x) {
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (map[i][j] == x)
                    return new Pos(i, j);
            }
        }
        return null;
    }

    static class Pos {
        int x, y;
        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}