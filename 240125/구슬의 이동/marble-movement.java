import java.util.*;
import java.io.*;

public class Main {

    static int n, m, t, k;
    static int[] dx = {0, -1, 1, 0}; // LUDR
    static int[] dy = {-1, 0, 0, 1};
    static ArrayList<Data>[][] map;
    static ArrayList<Data>[][] newMap;

    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        map = new ArrayList[n][n];
        newMap = new ArrayList[n][n];
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                map[i][j] = new ArrayList<>();
            }
        }

        for (int i=0; i<m; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            String d_s = st.nextToken();
            int v = Integer.parseInt(st.nextToken());
            int d = 0;
            switch(d_s) {
                case "L":
                    d = 0; break;
                case "R":
                    d = 1; break;
                case "U":
                    d = 2; break;
                case "D":
                    d = 3; break;                    
            }

            map[r][c].add(new Data(v, i, d));
        }

        while (t-->0) {
            initNewMap();
            moveAll();
            select();
            copyToMap();
        }

        int answer = 0;
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                answer += map[i][j].size();
            }
        }

        System.out.println(answer);
    }

    static void copyToMap() {
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                map[i][j] = (ArrayList<Data>) newMap[i][j].clone();
            }
        }
    }

    static void select() {
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (newMap[i][j].size() >= k) {
                    Collections.sort(newMap[i][j]);
                    while (newMap[i][j].size() > k)
                        newMap[i][j].remove(newMap[i][j].size() - 1);
                }
            }
        }
    }

    static void moveAll() {
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                for (int k=0; k<map[i][j].size(); k++) {
                    Data now = map[i][j].get(k);
                    int v = now.v;
                    int idx = now.i;
                    int dir = now.d;

                    int x = i, y = j;
                    while (v-->0) {
                        int nx = x + dx[dir];
                        int ny = y + dy[dir];

                        if (nx < 0 || nx >= n || ny < 0 || ny >= n) {
                            dir = 3 - dir;
                            nx = x + dx[dir];
                            ny = y + dy[dir];
                        }
                        x = nx;
                        y = ny;
                    }
                    newMap[x][y].add(new Data(v, idx, dir));
                }
            }
        }
    }

    static void initNewMap() {
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                newMap[i][j] = new ArrayList<>();
            }
        }
    }

    static class Data implements Comparable<Data> {
        int v, i ,d; // 속도, 번호, 방향

        public Data(int v, int i, int d) {
            this.d=d;
            this.v=v;
            this.i=i;
        }

        @Override
        public int compareTo(Data o) {
            if (this.v == o.v) {
                return Integer.compare(o.i, this.i);
            }

            return Integer.compare(o.v, this.v);
        }
    }
}