import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Pereche {
	public int x;
	public int y;

	public Pereche(int x, int y) {
		this.x = x;
		this.y = y;
	}
}


public class Curatare {
	static int n;
	static int m;
	static char[][] map = new char[1000][1000];
	static int[] ajutor = new int[10];
	static List<Pereche> roboti = new ArrayList<>();
	static List<Pereche> mizerii = new ArrayList<>();

	private static List<List<Integer>> createMatrix(int n, int m, int val) {
		List<List<Integer>> matrice = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			List<Integer> rand = new ArrayList<>();
			for (int j = 0; j < m; j++) {
				rand.add(val);
			}
			matrice.add(rand);
		}
		return matrice;
	}

	private static List<List<Integer>> distanteMinime(Pereche start) {
		List<List<Integer>> d = createMatrix(n, m, 0);
		List<Pereche> directions = List.of(new Pereche(-1, 0), new Pereche(0, 1),
				new Pereche(1, 0), new Pereche(0, -1));
		List<List<Integer>> vizitat = createMatrix(n, m, 0);

		Queue<Pereche> q = new LinkedList<>();
		q.add(start);
		vizitat.get(start.x).set(start.y, 1);
		d.get(start.x).set(start.y, 0);

		while (!q.isEmpty()) {
			Pereche p = q.peek();
			q.remove();

			for (var direction : directions) {
				int x = p.x + direction.x;
				int y = p.y + direction.y;

				if (x >= 0 && y >= 0 && x < n && y < m && vizitat.get(x).get(y) == 0
						&& map[x][y] != 'X') {
					vizitat.get(x).set(y, 1);
					d.get(x).set(y, d.get(p.x).get(p.y) + 1);
					q.add(new Pereche(x, y));
				}
			}
		}

		return d;
	}

	private static void swap(List<Integer> input, int a, int b) {
		int tmp = input.get(a);
		input.set(a, input.get(b));
		input.set(b, tmp);
	}

	public static void permutari(int n, List<Integer> elements, List<List<Integer>> res) {
		if (n == 1) {
			List<Integer> p = new ArrayList<>();
			for (var el : elements) {
				p.add(el);
			}
			res.add(p);
		} else {
			for (int i = 0; i < n - 1; i++) {
				permutari(n - 1, elements, res);
				if (n % 2 == 0) {
					swap(elements, i, n - 1);
				} else {
					swap(elements, 0, n - 1);
				}
			}
			permutari(n - 1, elements, res);
		}
	}

	public static void partitii_aux(List<Integer> l, int nr,
					List<List<List<Integer>>> res, int step) {
		if (step == l.size()) {
			List<List<Integer>> x = new ArrayList<>(nr);
			for (int i = 0; i < nr; i++) {
				x.add(new ArrayList<>());
			}

			for (int i = 0; i < l.size(); i++) {
				x.get(ajutor[i]).add(l.get(i));
			}
			res.add(x);
			return;
		}

		for (int i = 0; i < nr; i++) {
			ajutor[step] = i;
			partitii_aux(l, nr, res, step + 1);
		}
	}

	public static void partitii(List<Integer> l, int nr, List<List<List<Integer>>> res) {
		partitii_aux(l, nr, res, 0);
	}

	public static int minDrum(int start, List<List<Integer>> adj, List<Integer> n) {
		List<List<Integer>> res = new LinkedList<>();
		permutari(n.size(), n, res);

		int mini = Integer.MAX_VALUE;
		for (var p : res) {
			int a = start;
			int d = 0;

			for (var x : p) {
				if (adj.get(a).get(x) != 0) {
					d += adj.get(a).get(x);
					a = x;
				}
			}

			mini = Math.min(mini, d);
		}

		return mini;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(new File("curatare.in"));
		n = sc.nextInt();
		m = sc.nextInt();

		for (int i = 0; i < n; i++) {
			String line = sc.next();
			for (int j = 0; j < m; j++) {
				map[i][j] = line.charAt(j);

				if (map[i][j] == 'R') {
					roboti.add(new Pereche(i, j));
				}
				if (map[i][j] == 'S') {
					mizerii.add(new Pereche(i, j));
				}
			}
		}
		sc.close();

		List<List<Integer>> grafMizerie = createMatrix(mizerii.size(), mizerii.size(), 0);
		for (int i = 0; i < mizerii.size(); i++) {
			List<List<Integer>> d = distanteMinime(mizerii.get(i));
			for (int j = i + 1; j < mizerii.size(); j++) {
				grafMizerie.get(i).set(j, d.get(mizerii.get(j).x).get(mizerii.get(j).y));
				grafMizerie.get(j).set(i, d.get(mizerii.get(j).x).get(mizerii.get(j).y));
			}
		}


		List<List<List<Integer>>> grafRoboti = new ArrayList<>();
		for (int i = 0; i < roboti.size(); i++) {
			List<List<Integer>> grafRobot = createMatrix(mizerii.size() + 1, mizerii.size() + 1, 0);
			List<List<Integer>> d = distanteMinime(roboti.get(i));
			for (int j = 0; j < mizerii.size(); j++) {
				grafRobot.get(0).set(j + 1, d.get(mizerii.get(j).x).get(mizerii.get(j).y));
				grafRobot.get(j + 1).set(0, d.get(mizerii.get(j).x).get(mizerii.get(j).y));
			}

			for (int j = 0; j < mizerii.size(); j++) {
				for (int k = 0; k < mizerii.size(); k++) {
					grafRobot.get(j + 1).set(k + 1, grafMizerie.get(j).get(k));
					grafRobot.get(k + 1).set(j + 1, grafMizerie.get(k).get(j));
				}
			}

			grafRoboti.add(grafRobot);
		}

		List<Integer> noduri = new ArrayList<>();
		for (int i = 0; i < mizerii.size(); i++) {
			noduri.add(i + 1);
		}

		List<List<List<Integer>>> parts = new ArrayList<>();
		partitii(noduri, roboti.size(), parts);

		int mini = Integer.MAX_VALUE;
		for (var part : parts) {
			int maxi = 0;
			for (int i = 0; i < part.size(); i++) {
				if (!part.get(i).isEmpty()) {
					maxi = Math.max(maxi, minDrum(0, grafRoboti.get(i), part.get(i)));
				}
			}
			mini = Math.min(mini, maxi);
		}

		FileWriter fw = new FileWriter("curatare.out");
		fw.write(mini + "");
		fw.close();
	}
}
