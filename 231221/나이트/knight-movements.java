import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        Pos start = new Pos(sc.nextInt() - 1, sc.nextInt() - 1, 0);
        Pos end = new Pos(sc.nextInt() - 1, sc.nextInt() - 1, 0);

        if (start.x == end.x && start.y == end.y) {
            System.out.println(0);
            System.exit(0);
        }

        int[] dx = {-2, -1, 1, 2, 2, 1, -1, -2};
        int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};
        
        Queue<Pos> q = new ArrayDeque<>();
        boolean[][] v = new boolean[n][n];

        q.offer(start);
        v[start.x][start.y] = true;

        int answer = -1;
        while (!q.isEmpty()) {
            Pos cur = q.poll();

            for (int d=0; d<8; d++) {
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];

                if (nx < 0 || nx >= n || ny < 0 || ny >=n) continue;
                if (v[nx][ny]) continue;

                q.offer(new Pos(nx, ny, cur.z + 1));
                v[nx][ny] = true;

                if (nx == end.x && ny == end.y) {
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