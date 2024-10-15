import java.util.*;
import java.io.*;

public class Main {

	static int N, M;
	static Pos home, end;
	static boolean[][] map;
	static boolean[][] isLight;
	static int[][] pMap;
	static List<Pos> people;

	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	static int[] d2x = {-1, -1, 1, 1};
	static int[] d2y = {-1, 1, 1, -1};

	static int ans1, ans2, ans3;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		// Scanner sc = new Scanner(input1);
		// Scanner sc = new Scanner(myInput2);
		N = sc.nextInt();
		M = sc.nextInt();
		home = new Pos(sc.nextInt(), sc.nextInt());
		end = new Pos(sc.nextInt(), sc.nextInt());
		people = new ArrayList<>();
		pMap = new int[N][N];
		for (int i = 0; i < M; i++) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			pMap[x][y]++;
			people.add(new Pos(x, y));
		}
		map = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (sc.nextInt() == 1)
					map[i][j] = true;
			}
		}

		while (true) {
			ans1 = 0;
			ans2 = 0;
			ans3 = 0;

			homeAct();
			if (home.x == end.x && home.y == end.y) {
				System.out.println(0);
				break;
			}
			lightAct();
			peopleAct();
			System.out.println(ans1 + " " + ans2 + " " + ans3);
		}
	}

	private static void peopleAct() {
		// printBoolean(isLight);

		for (int i = 0; i < people.size(); i++) {
			Pos cur = people.get(i);

			if (isLight[cur.x][cur.y])
				continue;

			movePeople(cur, true);

			if (cur.x == home.x && cur.y == home.y) {
				ans3++;
				pMap[cur.x][cur.y]--;
				people.remove(i);
				i--;
				continue;
			}

			movePeople(cur, false);

			if (cur.x == home.x && cur.y == home.y) {
				ans3++;
				pMap[cur.x][cur.y]--;
				people.remove(i);
				i--;
			}
		}
	}

	private static void movePeople(Pos cur, boolean first) {
		int curDist = distance(cur, home);

		for (int d = 0; d < 4; d++) {
			int nx = cur.x + (first ? dx[d] : dx[(d + 2) % 4]);
			int ny = cur.y + (first ? dy[d] : dy[(d + 2) % 4]);

			if (!inRange(nx, ny))
				continue;
			if (isLight[nx][ny])
				continue;
			if (distance(new Pos(nx, ny), home) > curDist)
				continue;

			ans1++;
			pMap[cur.x][cur.y]--;
			cur.x = nx;
			cur.y = ny;
			pMap[cur.x][cur.y]++;

			return;
		}
	}

	static int distance(Pos p, Pos q) {
		return Math.abs(p.x - q.x) + Math.abs(p.y - q.y);
	}

	private static void lightAct() {
		int[] cnt = new int[4];
		List<boolean[][]> list = new ArrayList<>();

		for (int d = 0; d < 4; d++) {
			isLight = new boolean[N][N];

			int nx = home.x;
			int ny = home.y;

			do {
				isLight[nx][ny] = true;
				if (d == 0)
					turnLight(nx, ny, 0, 1);
				else if (d == 1)
					turnLight(nx, ny, 3, 2);
				else if (d == 2)
					turnLight(nx, ny, 0, 3);
				else
					turnLight(nx, ny, 1, 2);

				nx += dx[d];
				ny += dy[d];
			} while (inRange(nx, ny));

			isLight[home.x][home.y] = false;

			// 그림자
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (isLight[i][j] && pMap[i][j] > 0) {
						int ni = i;
						int nj = j;

						do {
							isLight[ni][nj] = false;

							if (ni != home.x && nj != home.y) {
								if (d == 0) {
									if (nj < home.y) turnLight(ni, nj, 0, -1);
									else turnLight(ni, nj, 1, -1);
								} else if (d == 1) {
									if (nj < home.y) turnLight(ni, nj, 3, -1);
									else turnLight(ni, nj, 2, -1);
								} else if (d == 2) {
									if (ni < home.x) turnLight(ni, nj, 0, -1);
									else turnLight(ni, nj, 3, -1);
								} else {
									if (ni < home.x) turnLight(ni, nj, 1, -1);
									else turnLight(ni, nj, 2, -1);
								}
							}

							ni += dx[d];
							nj += dy[d];
						} while (inRange(ni, nj));

						isLight[i][j] = true;
					}
				}
			}

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (isLight[i][j] && pMap[i][j] > 0) {
						cnt[d] += pMap[i][j];
					}
				}
			}

			// printBoolean(isLight);
			list.add(isLight);
		}

		int maxDir = 0;
		int maxCnt = 0;
		for (int i = 0; i < 4; i++) {
			if (maxCnt < cnt[i]) {
				maxCnt = cnt[i];
				maxDir = i;
			}
		}
		isLight = list.get(maxDir);
		ans2 = maxCnt;
	}

	private static void turnLight(int x, int y, int d1, int d2) {
		int nx = x;
		int ny = y;

		while (true) {
			nx += d2x[d1];
			ny += d2y[d1];

			if (!inRange(nx, ny))
				break;

			isLight[nx][ny] = d2 != -1;
		}

		if (d2 == -1) return;

		nx = x;
		ny = y;

		while (true) {
			nx += d2x[d2];
			ny += d2y[d2];

			if (!inRange(nx, ny))
				break;

			isLight[nx][ny] = true;
		}
	}

	private static void homeAct() {
		int[] dist = new int[4];
		Arrays.fill(dist, -1);

		for (int d = 0; d < 4; d++) {
			int nx = home.x + dx[d];
			int ny = home.y + dy[d];

			if (!inRange(nx ,ny))
				continue;
			if (map[nx][ny])
				continue;

			dist[d] = bfs(nx, ny, d);
		}

		int minDist = Integer.MAX_VALUE;
		int minDir = -1;
		for (int i = 0; i < 4; i++) {
			if (dist[i] == -1) continue;
			if (minDist > dist[i]) {
				minDist = dist[i];
				minDir = i;
			}
		}

		home.x += dx[minDir];
		home.y += dy[minDir];

		if (pMap[home.x][home.y] > 0) {
			// ans3 += pMap[home.x][home.y];
			pMap[home.x][home.y] = 0;

			for (int i = 0; i < people.size(); i++) {
				Pos p = people.get(i);
				if (p.x == home.x && p.y == home.y) {
					people.remove(i);
					i--;
				}
			}
		}
	}

	private static int bfs(int x, int y, int dir) {
		PriorityQueue<Data> pq = new PriorityQueue<>();
		boolean[][] v = new boolean[N][N];
		pq.offer(new Data(x, y, 1));
		v[x][y] = true;
		// v[home.x][home.y] = true;

		while (!pq.isEmpty()) {
			Data cur = pq.poll();

			if (cur.x == end.x && cur.y == end.y) {
				return cur.d;
			}

			for (int d = 0; d < 4; d++) {
				int nx = cur.x + dx[d];
				int ny = cur.y + dy[d];

				if (!inRange(nx, ny)) continue;
				if (map[nx][ny]) continue;
				if (v[nx][ny]) continue;

				v[nx][ny] = true;
				pq.offer(new Data(nx, ny, cur.d + 1));
			}
		}

		return -1;
	}

	private static boolean inRange(int nx, int ny) {
		return 0 <= nx && nx < N && 0 <= ny && ny < N;
	}

	static void printBoolean(boolean[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				if (a[i][j]) System.out.print(1);
				else System.out.print(0);
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println("======================");
	}

	static class Pos {
		int x, y;

		public Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Pos{" +
				"x=" + x +
				", y=" + y +
				'}';
		}
	}

	static class Data implements Comparable<Data> {
		int x, y, d;

		public Data(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}

		@Override
		public int compareTo(Data o) {
			return Integer.compare(this.d, o.d);
		}
	}

	static String input1 = "4 4\n"
		+ "1 3 3 3\n"
		+ "3 1 0 3 1 0 2 2\n"
		+ "0 0 0 0\n"
		+ "0 0 0 0\n"
		+ "0 0 1 1\n"
		+ "1 0 0 0\n";

	static String input2 = "6 4\n"
		+ "3 1 1 2\n"
		+ "3 5 1 4 0 4 1 3\n"
		+ "0 0 0 0 1 0\n"
		+ "0 1 0 0 1 1\n"
		+ "1 1 0 0 0 0\n"
		+ "0 0 1 0 1 1\n"
		+ "0 0 0 0 0 0\n"
		+ "0 0 0 0 1 1\n";

	static String myInput1 = "9 3\n"
		+ "0 4 0 0\n"
		+ "4 2 4 4 6 6\n"
		+ "0 0 0 0 0 0 0 0 0 0\n"
		+ "0 0 0 0 0 0 0 0 0 0\n"
		+ "0 0 0 0 0 0 0 0 0 0\n"
		+ "0 0 0 0 0 0 0 0 0 0\n"
		+ "0 0 0 0 0 0 0 0 0 0\n"
		+ "0 0 0 0 0 0 0 0 0 0\n"
		+ "0 0 0 0 0 0 0 0 0 0\n"
		+ "0 0 0 0 0 0 0 0 0 0\n"
		+ "0 0 0 0 0 0 0 0 0 0";

	static String myInput2 = "9 6\n"
		+ "4 4 0 0\n"
		+ "2 2 4 2 4 5 4 6 4 7 4 8\n"
		+ "0 0 0 0 0 0 0 0 0 0\n"
		+ "0 0 0 0 0 0 0 0 0 0\n"
		+ "0 0 0 0 0 0 0 0 0 0\n"
		+ "0 0 0 0 0 0 0 0 0 0\n"
		+ "0 0 0 0 0 0 0 0 0 0\n"
		+ "0 0 0 0 0 0 0 0 0 0\n"
		+ "0 0 0 0 0 0 0 0 0 0\n"
		+ "0 0 0 0 0 0 0 0 0 0\n"
		+ "0 0 0 0 0 0 0 0 0 0";
}