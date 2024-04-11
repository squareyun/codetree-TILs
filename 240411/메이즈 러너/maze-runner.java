import java.util.*;
import java.io.*;

public class Main {

    static final int MAX_N = 11;

    static int N, M, K;
    static int[][] map = new int[MAX_N][MAX_N];
    static int[][] newMap = new int[MAX_N][MAX_N]; // 회전
    static Pos[] players = new Pos[MAX_N];
    static Pos end;
    static int total;
    static int sx, sy, squareSize;

    static class Pos {
        int x, y;
        Pos (int x, int y) {
            this.x=x;
            this.y=y;
        }
    }

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        K = sc.nextInt();

        for (int i=1; i<=N; i++) {
            for (int j=1; j<=N; j++) {
                map[i][j] = sc.nextInt();
            }
        }

        for (int i=1; i<=M; i++) {
            players[i] = new Pos(sc.nextInt(), sc.nextInt());
        }
        end = new Pos(sc.nextInt(), sc.nextInt());


        // System.out.println("---");
        // for (int i=1; i<=N; i++) {
        //     for (int j=1; j<=N; j++) {
        //         System.out.print(map[i][j] + " ");
        //     }
        //     System.out.println();
        // }

        while (K-->0) {
            move();

            boolean theEnd = true;
            for (int i=1; i<=M; i++) {
                if (!(players[i].x == end.x && players[i].y == end.y)) {
                    theEnd = false;
                    break;
                }
            }

            if (theEnd) break;

            findSquare();
            // System.out.println(sx + " " + sy + " " + squareSize);
            rotate();

            // System.out.println("---");
            // for (int i=1; i<=N; i++) {
            //     for (int j=1; j<=N; j++) {
            //         System.out.print(map[i][j] + " ");
            //     }
            //     System.out.println();
            // }
        }

        System.out.println(total);
        System.out.println(end.x + " " + end.y);
    }

    static void move() {
        for (int i=1; i<=M; i++) {
            if (players[i].x == end.x && players[i].y == end.y)
                continue;
            
            // 행이동
            // 상하로 움직이는 것을 우선시합니다.
            if (players[i].x != end.x) {
                int nx = players[i].x;
                int ny = players[i].y;

                if (end.x > nx) nx++;
                else nx--;

                if (map[nx][ny] == 0) {
                    players[i].x = nx;
                    total++;
                    continue;
                }
            }

            if (players[i].y != end.y) {
                int nx = players[i].x;
                int ny = players[i].y;

                if (end.y > ny) ny++;
                else ny--;

                if (map[nx][ny] == 0) {
                    players[i].y = ny;
                    total++;
                    continue;
                }
            }
        }
    }

     static void findSquare() {
        for (int sz = 2; sz <= N; sz++) {
            for (int x1=1; x1<=N-sz+1; x1++) {
                for (int y1=1; y1<=N-sz+1; y1++) {
                    int x2 = x1+sz-1;
                    int y2 = y1+sz-1;

                    if (x1 > end.x || x2 < end.x || y1 > end.y || y2 < end.y)
                        continue;
                    
                    // if(!(x1 <= end.x && end.x <= x2 && y1 <= end.y && end.y <= y2)) {
                    //     continue;
                    // }

                    boolean flag = false;
                    for (int i=1; i<=M; i++) {
                        if (players[i].x == end.x && players[i].y == end.y)
                            continue;
                        
                        if (x1 <= players[i].x && players[i].x <= x2 && y1 <= players[i].y && players[i].y <= y2) {
                            flag = true;
                            break;
                        }
                    }

                    if (flag) {
                        sx = x1;
                        sy = y1;
                        squareSize = sz;
                        return;
                    }
                }
            }
        }
    }

    static void rotate() {
        // 벽 내구성 감소
        for (int i=sx; i<sx+squareSize; i++) {
            for (int j=sy; j<sy+squareSize; j++) {
                if (map[i][j] > 0) map[i][j]--;
            }
        }

        // 벽 회전
        for (int x=sx; x<sx+squareSize; x++) {
            for (int y=sy; y<sy+squareSize; y++) {
                // 1. 원점이동
                int ox = x - sx, oy = y - sy;
                // 2. (x, y) -> (y, squareSize - x - 1)
                int rx = oy, ry = squareSize - ox - 1;
                // 3. 다시 더하기
                newMap[rx + sx][ry + sy] = map[x][y];
            }
        }

        for (int x=sx; x<sx+squareSize; x++) {
            for (int y=sy; y<sy+squareSize; y++) {
                map[x][y] = newMap[x][y];
            }
        }

        // 참가자 회전
        for (int i=1; i<=M; i++) {
            int x = players[i].x;
            int y = players[i].y;

            if (sx > x || x > sx+squareSize || sy > y || y > sy+squareSize-1)
                continue;
            
            int ox = x - sx, oy = y - sy;
            int rx = oy, ry = squareSize - ox - 1;
            players[i].x = rx + sx;
            players[i].y = ry + sy;
        }

        // 출구 회전
        int x = end.x;
        int y = end.y;

        if (sx > x || x > sx+squareSize-1 || sy > y || y > sy+squareSize-1)
            return;
        
        int ox = x - sx, oy = y - sy;
        int rx = oy, ry = squareSize - ox - 1;
        end.x = rx + sx;
        end.y = ry + sy;
    }
}