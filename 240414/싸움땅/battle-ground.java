import java.util.*;

public class Main {

    static class Player {
        int num, x, y, d, s, a;

        public Player(int num, int x, int y, int d, int s, int a) {
            this.num = num;
            this.x = x;
            this.y = y;
            this.d = d;
            this.s = s;
            this.a = a;
        }
    }

    static class Tuple {
        int x, y, dir;

        public Tuple(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }

    static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean isSame(Pair p) {
            return this.x == p.x && this.y == p.y;
        }
    }

    public static final Player EMPTY = new Player(-1, -1, -1, -1, -1, -1);
    public static final int MAX_M = 30;
    public static final int MAX_N = 20;

    public static int n, m, k;

    public static ArrayList<Integer>[][] gun = new ArrayList[MAX_N][MAX_N];
    public static Player[] players = new Player[MAX_M];
    public static int[] points = new int[MAX_M];
    public static int[] dx = new int[]{-1, 0, 1,  0};
    public static int[] dy = new int[]{ 0, 1, 0, -1};

    public static boolean inRange(int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < n;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        k = sc.nextInt();

        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++) {
                gun[i][j] = new ArrayList<>();

                int num = sc.nextInt();
                if(num != 0)
                    gun[i][j].add(num);
            }

        for(int i = 0; i < m; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int d = sc.nextInt();
            int s = sc.nextInt();
            players[i] = new Player(i, x - 1, y - 1, d, s, 0);
        }

        while (k-->0) {
            simulate();
        }

        for(int i = 0; i < m; i++) {
            System.out.print(points[i] + " ");
        }
    }

    public static void simulate() {
        for (int i=0; i<m; i++) {
            int num = players[i].num;
            int x = players[i].x;
            int y = players[i].y;
            int d = players[i].d;
            int s = players[i].s;
            int a = players[i].a;

            Tuple next = getNext(x, y, d);
            int nx = next.x;
            int ny = next.y;
            int ndir = next.dir;

            Player nextPlayer = findPlayer(new Pair(nx, ny));

            Player currPlayer = new Player(num, nx, ny, ndir, s, a);
            update(currPlayer);

            if (nextPlayer == EMPTY) {
                move(currPlayer, new Pair(nx, ny));
            } else {
                duel(currPlayer, nextPlayer, new Pair(nx, ny));
            }
        }
    }

    public static Tuple getNext(int x, int y, int d) {
        int nx = x + dx[d];
        int ny = y + dy[d];

        if (!inRange(nx, ny)) {
            d = (d < 2) ? (d + 2) : (d - 2);
            nx = x + dx[d];
            ny = y + dy[d];
        }

        return new Tuple(nx, ny, d);
    }

    public static Player findPlayer(Pair pos) {
        for (int i=0; i<m; i++) {
            int x = players[i].x, y = players[i].y;
            if (pos.isSame(new Pair(x, y)))
                return players[i];
        }

        return EMPTY;
    }

    public static void update(Player p) {
        for (int i=0; i<m; i++) {
            if (players[i].num == p.num) {
                players[i] = p;
                break;
            }
        }
    }

    public static void move(Player p, Pair pos) {
        int num = p.num;
        int x = p.x;
        int y = p.y;
        int d = p.d;
        int s = p.s;
        int a = p.a;
        int nx = pos.x;
        int ny = pos.y;

        gun[nx][ny].add(a);
        Collections.sort(gun[nx][ny]);
        a = gun[nx][ny].get(gun[nx][ny].size() - 1);
        gun[nx][ny].remove(gun[nx][ny].size() - 1);

        p = new Player(num, ny, ny, d, s, a);
        update(p);
    }

    public static void duel(Player p1, Player p2, Pair pos) {
        int num1 = p1.num, d1 = p1.d, s1 = p1.s, a1 = p1.a;
        int num2 = p2.num, d2 = p2.d, s2 = p2.s, a2 = p2.a;

        if (s1 + a1 > s2 + a2 || (s1 + a1 == s2 + a2 && s1 > s2)) {
            points[num1] += (s1 + a1) - (s2 + a2);
            loserMove(p2);
            move(p1, pos);
        } else {
            points[num2] += (s2 + a2) - (s1 + a1);
            loserMove(p1);
            move(p2, pos);
        }
    }

    public static void loserMove(Player p) {
        int num = p.num;
        int x = p.x;
        int y = p.y;
        int d = p.d;
        int s = p.s;
        int a = p.a;

        gun[x][y].add(a);

        for (int i=0; i<4; i++) {
            int ndir = (d + i) % 4;
            int nx = x + dx[ndir];
            int ny = y + dy[ndir];

            if (inRange(nx, ny) && findPlayer(new Pair(nx, ny)) == EMPTY) {
                p = new Player(num, x, y, ndir, s, 0);
                move(p, new Pair(nx, ny));
                break;
            }
        }
    }
}