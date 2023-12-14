import java.util.*;
import java.io.*;

public class Main {

    static int T, N, M;
    static int[][] map;
    static int[] dx = {-100, -1, 0, 0, 1}; // URLD
    static int[] dy = {-100, 0, 1, -1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        T = Integer.parseInt(st.nextToken());

        while (T-- > 0) {    
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            map = new int[N][N];

            for (int i=0; i<M; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken()) - 1;
                int y = Integer.parseInt(st.nextToken()) - 1;
                char dir = st.nextToken().charAt(0);

                if (dir == 'U') {
                    map[x][y] = 1;
                } else if (dir == 'R') {
                    map[x][y] = 2;
                } else if (dir == 'L') {
                    map[x][y] = 3;
                } else if (dir == 'D') {
                    map[x][y] = 4;
                }
                
            }

            // N*N번 확인하면 될 것 ..?
            for (int q=0; q<N*N; q++) {
                int[][] nextMap = new int[N][N];

                for (int i=0; i<N; i++) {
                    for (int j=0; j<N; j++) {
                        if(map[i][j] > 0) {
                            int d = map[i][j];
                            int nx = i + dx[d];
                            int ny = j + dy[d];

                            if (d == 1 || d == 4) {
                                if (nx < 0 || nx >= N) d = 5-d;
                                if (nx < 0) nx = 0;
                                if (nx >= N) nx = N-1;
                            } else if (d == 2 || d == 3) {
                                if (ny < 0 || ny >= N) d = 5-d;
                                if (ny < 0) ny = 0;
                                if (ny >= N) ny = N-1;
                            }

                            if (nextMap[nx][ny] > 0) {
                                nextMap[nx][ny] = 100; // collapse
                            } else {
                                nextMap[nx][ny] = d;
                            }
                        }
                    }
                }

                //nextMap -> map
                for (int i=0; i<N; i++) {
                    for (int j=0; j<N; j++) {
                        map[i][j] = nextMap[i][j];
                        if (map[i][j] == 100) map[i][j] = 0;
                    }
                }

                // 테스트
                // for (int i=0; i<N; i++) {
                //     for (int j=0; j<N; j++) {
                //         System.out.print(map[i][j]);
                //     }
                //     System.out.println();
                // }
                // System.out.println("===");
            }

            // 출력
            int answer = 0;
            for (int i=0; i<N; i++) {
                for (int j=0; j<N; j++) {
                    if (map[i][j] > 0)
                        answer ++;
                }
            }
            System.out.println(answer);
        }
    }
}