import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Pair<T, U> {
	public T first;
	public U second;

	public Pair(T first, U second) {
		this.first = first;
		this.second = second;
	}
}

public class Beamdrone {
	static int n, m;
	static int xi, yi, xf, yf;
	static char[][] v = new char[1000][1000];
	static int[][] visited = new int[1000][1000];
	static int[][][] dp = new int[4][1000][1000];
	static List<Pair<Integer, Integer>> directions = List.of(new Pair<>(-1, 0),
			new Pair<>(0, 1),
			new Pair<>(1, 0),
			new Pair<>(0, -1));

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(new File("beamdrone.in"));

		n = sc.nextInt();
		m = sc.nextInt();
		xi = sc.nextInt();
		yi = sc.nextInt();
		xf = sc.nextInt();
		yf = sc.nextInt();

		for (int i = 0; i < n; i++) {
			String line = sc.next();
			for (int j = 0; j < m; j++) {
				v[i][j] = line.charAt(j);
			}
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				visited[i][j] = 0;
			}
		}
		dp[0][xi][yi] = 0;
		dp[1][xi][yi] = 0;
		dp[2][xi][yi] = 0;
		dp[3][xi][yi] = 0;

		Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
		queue.add(new Pair<>(xi, yi));
		visited[xi][yi] = 1;

		while (!queue.isEmpty()) {
			var aux = queue.peek();
			queue.remove();
			int x = aux.first;
			int y = aux.second;

			for (var direction : directions) {
				int dx = direction.first;
				int dy = direction.second;
				int c = dp[directions.indexOf(direction)][x][y];
				int xaux = x + dx;
				int yaux = y + dy;

				while (xaux >= 0 && yaux >= 0 && xaux < n && yaux < m
						&& visited[xaux][yaux] == 0 && v[xaux][yaux] != 'W') {
					for (var direction2 : directions) {
						if (direction != direction2) {
							dp[directions.indexOf(direction2)][xaux][yaux] = c + 1;
						} else {
							dp[directions.indexOf(direction2)][xaux][yaux] = c;
						}
					}

					queue.add(new Pair<>(xaux, yaux));
					visited[xaux][yaux] = 1;
					xaux += dx;
					yaux += dy;
				}
			}
		}

		int min = Math.min(dp[0][xf][yf], dp[1][xf][yf]);
		min = Math.min(min, dp[2][xf][yf]);
		min = Math.min(min, dp[3][xf][yf]);

		FileWriter fw = new FileWriter("beamdrone.out");
		fw.write(min + "");
		fw.close();
	}
}