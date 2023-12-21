import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] map = new int[n][n];
        int[][] answer = new int[n][n];
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};
        ArrayList<Pos> people = new ArrayList<>();
        
        for (int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 2) {
                    people.add(new Pos(i, j, 0));
                }
            }
        }

        for (Pos start : people) {
            Queue<Pos> q = new ArrayDeque<>();
            boolean[][] v = new boolean[n][n];
            q.offer(start);
            v[start.x][start.y] = true;

            boolean isDone = false;
            while (!q.isEmpty()) {
                Pos cur = q.poll();

                for (int d=0; d<4; d++) {
                    int nx = cur.x + dx[d];
                    int ny = cur.y + dy[d];

                    if (nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
                    if (v[nx][ny]) continue;
                    if (map[nx][ny] == 1) continue;

                    q.offer(new Pos(nx, ny, cur.z + 1));
                    v[nx][ny] = true;

                    if (map[nx][ny] == 3) {
                        answer[start.x][start.y] = cur.z + 1;
                        isDone = true;
                        break;
                    }
                }

                if (isDone) break;
            }

            if (!isDone) answer[start.x][start.y] = -1;
        }

        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                System.out.print(answer[i][j] + " ");
            }
            System.out.println();
        }
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