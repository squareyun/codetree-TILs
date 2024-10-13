import java.util.*;
import java.io.*;

public class Main {

	static final int N_large = 5;
	static final int N_small = 3;

	static int[] dy = {0, 1, 0, -1};
	static int[] dx = {1, 0, -1, 0};

	static class Board {

		int[][] a = new int[N_large][N_large];

		public Board() {
			for (int i = 0; i < N_large; i++) {
				for (int j = 0; j < N_large; j++) {
					a[i][j] = 0;
				}
			}
		}

		public Board rotate(int sy, int sx, int cnt) {
			Board result = new Board();
			for (int i = 0; i < N_large; i++) {
				for (int j = 0; j < N_large; j++) {
					result.a[i][j] = this.a[i][j];
				}
			}

			for (int k = 0; k < cnt; k++) {
				int temp = result.a[sy][sx + 2];
				result.a[sy][sx + 2] = result.a[sy][sx];
				result.a[sy][sx] = result.a[sy + 2][sx];
				result.a[sy + 2][sx] = result.a[sy + 2][sx + 2];
				result.a[sy + 2][sx + 2] = temp;
				temp = result.a[sy + 1][sx + 2];
				result.a[sy + 1][sx + 2] = result.a[sy][sx + 1];
				result.a[sy][sx + 1] = result.a[sy + 1][sx];
				result.a[sy + 1][sx] = result.a[sy + 2][sx + 1];
				result.a[sy + 2][sx + 1] = temp;
			}
			return result;
		}

		public int calcurate() {
			int score = 0;
			boolean[][] v = new boolean[N_large][N_large];

			for (int i = 0; i < N_large; i++) {
				for (int j = 0; j < N_large; j++) {
					if (v[i][j])
						continue;

					Queue<int[]> q = new ArrayDeque<>();
					Queue<int[]> trace = new ArrayDeque<>();

					q.offer(new int[] {i, j});
					trace.offer(new int[] {i, j});
					v[i][j] = true;

					while (!q.isEmpty()) {
						int[] cur = q.poll();
						for (int d = 0; d < 4; d++) {
							int ny = cur[0] + dy[d];
							int nx = cur[1] + dx[d];

							if (nx < 0 || nx >= N_large || ny < 0 || ny >= N_large)
								continue;
							if (v[ny][nx])
								continue;

							if (a[ny][nx] == a[cur[0]][cur[1]]) {
								q.offer(new int[] {ny, nx});
								trace.offer(new int[] {ny, nx});
								v[ny][nx] = true;
							}
						}
					}

					if (trace.size() >= 3) {
						score += trace.size();
						while (!trace.isEmpty()) {
							int[] t = trace.poll();
							a[t[0]][t[1]] = 0;
						}
					}
				}
			}

			return score;
		}

		public void fill(Queue<Integer> q) {
			for (int j = 0; j < N_large; j++) {
				for (int i = N_large - 1; i >= 0; i--) {
					if (a[i][j] == 0 && !q.isEmpty()) {
						a[i][j] = q.poll();
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		int K = sc.nextInt();
		int M = sc.nextInt();

		Queue<Integer> q = new ArrayDeque<>();
		Board board = new Board();

		for (int i = 0; i < N_large; i++) {
			for (int j = 0; j < N_large; j++) {
				board.a[i][j] = sc.nextInt();
			}
		}

		for (int i = 0; i < M; i++) {
			q.offer(sc.nextInt());
		}

		while (K-- > 0) {
			int maxScore = 0;
			Board maxScoreBoard = null;

			for (int cnt = 1; cnt <= 3; cnt++) {
				for (int sx = 0; sx <= N_large - N_small; sx++) {
					for (int sy = 0; sy <= N_large - N_small; sy++) {
						Board rotated = board.rotate(sy, sx, cnt);
						int score = rotated.calcurate();
						if (maxScore < score) {
							maxScore = score;
							maxScoreBoard = rotated;
						}
					}
				}
			}

			if (maxScoreBoard == null)
				break;

			board = maxScoreBoard;

			while (true) {
				board.fill(q);
				int newScore = board.calcurate();
				if (newScore == 0)
					break;
				maxScore += newScore;
			}
			System.out.printf(maxScore + " ");
		}

	}

}