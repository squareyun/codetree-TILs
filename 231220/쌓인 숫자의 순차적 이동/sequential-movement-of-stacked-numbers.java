import java.util.*;
import java.io.*;

public class Main {

    static int n, m;
    static ArrayList<Integer>[][] map;
    static int[] dx = {-1, -1, -1, 0, 1, 1, 1, 0};
    static int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        map = new ArrayList[n][n];
        for (int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<n; j++) {
                map[i][j] = new ArrayList<>();
                map[i][j].add(Integer.parseInt(st.nextToken()));
            }
        }

        st = new StringTokenizer(br.readLine());
        for (int i=0; i<m; i++) {
            int findM = Integer.parseInt(st.nextToken());

            Pos now = findMPos(findM);
            Pos next = findMovePos(now, findM);
            if (next != null) {
                updateMap(now, next);
            }
        }

        printMap();
    }

    static Pos findMPos(int m) {
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (map[i][j].size() > 0) {
                    for (int k=0; k<map[i][j].size(); k++) {
                        if(map[i][j].get(k) == m) {
                            return new Pos(i, j, k);
                        }
                    }
                }
            }
        }
        return null;
    }

    static Pos findMovePos(Pos cur, int nowM) {
        int maxV = -1;
        Pos ret = new Pos(-1, -1, -1);
        for (int d=0; d<8; d++) {
            int nx = cur.x + dx[d];
            int ny = cur.y + dy[d];

            if (nx < 0 || nx >= n || ny < 0 || ny >= n)
                continue;
            
            if (map[nx][ny].size() > 0) {
                for (int i=0; i<map[nx][ny].size(); i++) {
                    if (maxV < map[nx][ny].get(i)) {
                        maxV = map[nx][ny].get(i);
                        ret.x = nx;
                        ret.y = ny;
                        ret.z = i;
                    }
                }
            }
        }

        if (maxV == -1) 
            return null;
        else
            return ret;
    }

    static void updateMap(Pos now, Pos next) {
        int cnt = map[now.x][now.y].size();

        for (int i=now.z; i<cnt; i++) {
            int moveV = map[now.x][now.y].get(now.z);
            map[next.x][next.y].add(moveV);
            map[now.x][now.y].remove(now.z);
        }
    }

    static void printMap() {
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (map[i][j].size() == 0) {
                    System.out.println("None");
                } else {
                    for (int k=map[i][j].size() - 1; k>=0; k--) {
                        System.out.print(map[i][j].get(k) + " ");
                    }
                    System.out.println();
                }
            }
        }
    }

    static class Pos {
        int x, y, z;

        public Pos (int x, int y, int z) {
            this.x=x;
            this.y=y;
            this.z=z;
        }
    }
}