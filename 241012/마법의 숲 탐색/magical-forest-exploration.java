import java.util.*;
import java.io.*;

public class Main {

	static final int MAX_L = 70;

	static int R, C, K;
	static int[][] A = new int[MAX_L + 3][MAX_L];
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	static boolean[][] isExit = new boolean[MAX_L + 3][MAX_L];
	static int answer = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		for (int i = 1; i <= K; i++) {
			st = new StringTokenizer(br.readLine());
			int c = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken());
			down(0, c, d, i);
		}
		System.out.println(answer);
	}

	private static void down(int x, int y, int d, int id) {
		if (canGo(x + 1, y)) {
			down(x + 1, y, d, id);
		} else if (canGo(x + 1, y - 1)) {
			down(x + 1, y - 1, (d + 3) % 4, id);
		} else if (canGo(x + 1, y + 1)) {
			down(x + 1, y + 1, (d + 1) % 4, id);
		} else {
			if (!inRange(x - 1, y - 1) || !inRange(x + 1, y + 1)) {
				reset();
			} else {
				A[x][y] = id;
				for (int i = 0; i < 4; i++) {
					A[x + dx[i]][y + dy[i]] = id;
				}
				isExit[x + dx[d]][y + dy[d]] = true;
				answer += bfs(x, y) - 3 + 1;
			}
		}
	}

	private static int bfs(int x, int y) {
		int ret = x;
		Queue<int[]> q = new ArrayDeque<>();
		boolean[][] v = new boolean[MAX_L + 3][MAX_L];
		q.offer(new int[] {x, y});
		v[x][y] = true;
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			for (int i = 0; i < 4; i++) {
				int nx = cur[0] + dx[i];
				int ny = cur[1] + dy[i];

				if (!inRange(nx, ny)) continue;
				if (v[nx][ny]) continue;
				if (A[nx][ny] == A[cur[0]][cur[1]] || (A[nx][ny] != 0 && isExit[cur[0]][cur[1]])) {
					q.offer(new int[] {nx, ny});
					v[nx][ny] = true;
					ret = Math.max(ret, nx);
				}
			}
		}
		return ret;
	}

	private static void reset() {
		for (int i = 0; i < R + 3; i++) {
			for (int j = 0; j < C; j++) {
				A[i][j] = 0;
				isExit[i][j] = false;
			}
		}
	}

	private static boolean inRange(int x, int y) {
		return 3 <= x && x < R + 3 && 0 <= y && y < C;
	}

	private static boolean canGo(int x, int y) {
		boolean flag = x + 1 < R + 3 && 0 <= y - 1 && y + 1 < C;
		flag = flag && (A[x - 1][y - 1] == 0);
		flag = flag && (A[x - 1][y + 1] == 0);
		// flag = flag && (A[x - 2][y] == 0);
		flag = flag && (A[x][y - 1] == 0);
		flag = flag && (A[x][y + 1] == 0);
		flag = flag && (A[x][y] == 0);
		flag = flag && (A[x + 1][y] == 0);
		return flag;
	}
}