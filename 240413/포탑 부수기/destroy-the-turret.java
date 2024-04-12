import java.util.*;
import java.io.*;

public class Main {

    static final int MAX_N = 11;

    static int n, m, k;
    static int[][] map = new int[MAX_N][MAX_N];
    static int[][] backX = new int[MAX_N][MAX_N];
    static int[][] backY = new int[MAX_N][MAX_N];
    static int[][] turnMap = new int[MAX_N][MAX_N];
    static boolean[][] damaged;
    static int gTurn;

    static class Potap {
        int x, y, power, turn;

        Potap(int x, int y, int power, int turn) {
            this.x=x;
            this.y=y;
            this.power=power;
            this.turn=turn;
        }

        Potap(int x, int y) {
            this.x=x;
            this.y=y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ") " + power + " " + turn;
        }
    }

    static PriorityQueue<Potap> weak, strong;

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        k = sc.nextInt();

        makePQ();
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
                map[i][j] = sc.nextInt();
                if (map[i][j] != 0) {
                    Potap p = new Potap(i, j, map[i][j], gTurn); 
                    weak.add(p);
                    strong.add(p);
                }
            }
        }

        while (k-->0 && strong.size() > 1) {
            Potap w = weak.peek();
            Potap s = strong.peek();
            
            // System.out.println(w);
            // System.out.println(s);

            w.power += n + m;
            turnMap[w.x][w.y] = ++gTurn;
            map[w.x][w.y] = w.power;

            if (!lazerAttack(w, s))
                potanAttack(w, s);

            // 4. 포탑 정비
            for (int i=0; i<n; i++) {
                for (int j=0; j<m; j++) {
                    if (!damaged[i][j] && map[i][j] != 0) {
                        map[i][j] += 1;
                        // System.out.println("up");
                    }
                }
            }

            // map기준으로 pq update
            makePQ();
            for (int i=0; i<n; i++) {
                for (int j=0; j<m; j++) {
                    if (map[i][j] < 0) map[i][j] = 0;
                    if (map[i][j] == 0) continue;
                    Potap p = new Potap(i, j, map[i][j], turnMap[i][j]); 
                    weak.add(p);
                    strong.add(p);
                }
            }

            // System.out.println("공격자: " + w);
            // System.out.println("당한자: " + s);
            // print();
            // System.out.println("after");
        }

        // while(!weak.isEmpty()) {
        //     Potap p = weak.poll();
        //     if (p.power != 0)
        //         System.out.println(p);
        // }

        printAnswer();

        // while(!strong.isEmpty()) {
        //     Potap p = strong.poll();
        //     if (p.power != 0)
        //         System.out.println(p);
        // }
    }

    static boolean lazerAttack(Potap w, Potap s) {

        int[] dx = {0, 1, 0, -1}; //우하좌상
        int[] dy = {1, 0, -1, 0};

        Queue<Potap> q = new LinkedList<>();
        boolean[][] v = new boolean[MAX_N][MAX_N];
        q.offer(new Potap(w.x, w.y));
        v[w.x][w.y] = true;

        while(!q.isEmpty()) {
            Potap cur = q.poll();

            if (cur.x == s.x && cur.y == s.y)
                break;

            for (int d=0; d<4; d++) {
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];

                if (nx < 0) nx = n - 1;
                else if (nx >= n) nx = 0;
                if (ny < 0) ny = m - 1;
                else if (ny >= m) ny = 0;

                if (v[nx][ny]) continue;
                if (map[nx][ny] <= 0) continue;

                backX[nx][ny] = cur.x;
                backY[nx][ny] = cur.y;
                q.add(new Potap(nx, ny));
                v[nx][ny] = true;
            }
        }

        // System.out.println("-------");
        // for (int i=0; i<n; i++) {
        //     for (int j=0; j<m; j++) {
        //         System.out.print("(" + backX[i][j] + "," + backY[i][j] + ") ");
        //     }
        //     System.out.println();
        // }

        if (!v[s.x][s.y]) return false;

        damaged = new boolean[MAX_N][MAX_N];

        // 지나온 길 피해 입히기
        int nx = s.x, ny = s.y;
        damaged[nx][ny] = true;
        while (true) {

            int tempX = nx;
            nx = backX[nx][ny];
            ny = backY[tempX][ny];
            damaged[nx][ny] = true;

            if (nx == w.x && ny == w.y)
                break;

            map[nx][ny] -= w.power / 2;
        }
        map[s.x][s.y] -= w.power;

        return true;
    }

    static void potanAttack(Potap w, Potap s) {

        damaged = new boolean[MAX_N][MAX_N];
        int[] dx = {0, 1, 0, -1, -1, -1, 1, 1};
        int[] dy = {1, 0, -1, 0, -1, 1, 1, -1};
        damaged[w.x][w.y] = true;
        damaged[s.x][s.y] = true;

        for (int d=0; d<8; d++) {
            int nx = s.x + dx[d];
            int ny = s.y + dy[d];

            if (nx < 0) nx = n - 1;
            else if (nx >= n) nx = 0;
            if (ny < 0) ny = m - 1;
            else if (ny >= m) ny = 0;

            if (nx == w.x && ny == w.y) continue;

            if (map[nx][ny] != 0) {
                map[nx][ny] -= w.power / 2;
                damaged[nx][ny] = true;
            }
        }

        map[s.x][s.y] -= w.power;

    }

    static void print() {
        System.out.println("-------");
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void printAnswer() {
        while (!strong.isEmpty()) {
            Potap p = strong.poll();
            if (p.power != 0) {
                System.out.println(p.power);
                return;
            }
        }
        System.out.println(0);
    }

    static void makePQ() {
        weak = new PriorityQueue<>((o1, o2) -> {
            if (o1.power != o2.power) {
                return Integer.compare(o1.power, o2.power);
            } else if (o1.turn != o2.turn) {
                return -Integer.compare(o1.turn, o2.turn);
            } else if (o1.x + o1.y != o2.x + o2.y) {
                return -Integer.compare(o1.x + o1.y, o2.x + o2.y);
            } else {
                return -Integer.compare(o1.y, o2.y);
            }
        });

        strong = new PriorityQueue<>((o1, o2) -> {
            if (o1.power != o2.power) {
                return -Integer.compare(o1.power, o2.power);
            } else if (o1.turn != o2.turn) {
                return Integer.compare(o1.turn, o2.turn);
            } else if (o1.x + o1.y != o2.x + o2.y) {
                return Integer.compare(o1.x + o1.y, o2.x + o2.y);
            } else {
                return Integer.compare(o1.y, o2.y);
            }
        });
    }
}