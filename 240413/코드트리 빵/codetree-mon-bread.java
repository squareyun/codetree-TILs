import java.util.*;
import java.io.*;

public class Main {

    static final int MAX_N = 16;
    static final int MAX_M = 31;
    static final Pair EMPTY = new Pair(-1, -1);

    static int n, m;
    static int[][] map = new int[MAX_N][MAX_N]; // 1 베이스 캠프, 2 갈 수 없는 곳
    static Pair[] store = new Pair[MAX_M];
    static Pair[] people = new Pair[MAX_M];
    static boolean[][] red = new boolean[MAX_N][MAX_N];
    static int time = 0;

    static int[] dx = {-1, 0, 0, 1}; // 상좌우하
    static int[] dy = {0, -1, 1, 0};

    static int[][] step = new int[MAX_N][MAX_N]; // 최단거리 결과 기록
    static boolean[][] v = new boolean[MAX_N][MAX_N];

    static class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x=x;
            this.y=y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        for (int i=1; i<=n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=1; j<=n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i=1; i<=m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            store[i] = new Pair(x, y);
            people[i] = EMPTY; //격자 밖
        }

        while (true) {
            time++;
            simulate();
            if(end()) break;
        }

        System.out.println(time);
    }

    static void simulate() {
        // step1
        for (int i=1; i<=m; i++) {
            if (people[i] == EMPTY || (people[i].x == store[i].x && people[i].y == store[i].y))
                continue;

            // 편의점 위치를 시작으로 현재 위치까지 오는 최단거리 구하기
            bfs(store[i]);

            // 현위치에서 상하좌우로 step 배열에 값이 가장 작은 곳으로 이동해야함
            int minDist = Integer.MAX_VALUE;
            int minX = -1, minY = -1;
            for (int d=0; d<4; d++) {
                int nx = people[i].x + dx[d];
                int ny = people[i].y + dy[d];

                if (nx < 1 || nx > n || ny < 1 || ny > n) continue;
                if (minDist > step[nx][ny] && map[nx][ny] != 2 && v[nx][ny]) {
                    minDist = step[nx][ny];
                    minX = nx; minY = ny;
                }
            }
            people[i] = new Pair(minX, minY);
        }

        // step2
        for (int i=1; i<=m; i++) {
            if (people[i].x == store[i].x && people[i].y == store[i].y)
                map[people[i].x][people[i].y] = 2;
        }

        // step3
        if (time > m) return;

        // 편의점으로부터 가장 가까운 베이스 캠프 찾기
        bfs(store[time]);

        int minDist = Integer.MAX_VALUE;
        int minX = -1, minY = -1;
        for (int i=1; i<=n; i++) {
            for (int j=1; j<=n; j++) {
                if (v[i][j] && map[i][j] == 1 && minDist > step[i][j]) {
                    minDist = step[i][j];
                    minX = i; minY = j;
                }
            }
        }

        people[time] = new Pair(minX, minY);
        map[minX][minY] = 2;
    }

    static boolean end() {
        for (int i=1; i<=m; i++) {
            if (!(people[i].x == store[i].x && people[i].y == store[i].y))
                return false;
        }

        return true;
    }

    static void bfs(Pair start) {
        // init
        for (int i=1; i<=n; i++) {
            for (int j=1; j<=n; j++) {
                v[i][j] = false;
                step[i][j] = 0;
            }
        }

        Queue<Pair> q = new LinkedList<>();
        q.add(start);
        v[start.x][start.y] = true;

        while (!q.isEmpty()) {
            Pair cur = q.poll();

            for (int d=0; d<4; d++) {
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];

                if (nx < 1 || nx > n || ny < 1 || ny > n) continue;
                if (v[nx][ny]) continue;
                if (map[nx][ny] == 2) continue;

                v[nx][ny] = true;
                step[nx][ny] = step[cur.x][cur.y] + 1;
                q.add(new Pair(nx, ny));
            }
        }
    }
}