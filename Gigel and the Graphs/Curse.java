import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Curse {

	public static int N, M, A;
	public static ArrayList<Integer>[] mat;
	@SuppressWarnings("unchecked")
	public static ArrayList<Integer>[] adj;

	public static ArrayList<Integer> solveDfs() {
		// vectorul rezultat (in final contine o permutare pentru 1, 2, ... n)
		ArrayList<Integer> topsort = new ArrayList<>();

		boolean[] used = new boolean[M + 1];

		// pentru fiecare nod
		for (int node = 1; node <= M; node++) {
			// daca nodul nu a fost vizitat, pornim o parcurgere DFS
			if (!used[node]) {
				dfs(node, used, topsort);
			}
		}

		// rezultatul a fost obtinut in ordine inversa
		Collections.reverse(topsort);

		return topsort;
	}

	// porneste o parcurgere DFS din node
	// foloseste vectorul used pentru a marca nodurile vizitate
	public static void dfs(int node, boolean[] used, ArrayList<Integer> topsort) {
		used[node] = true;

		for (Integer neigh : adj[node]) {
			if (!used[neigh]) {
				dfs(neigh, used, topsort);
			}
		}

		// dupa ce am terminat de vizitat nodul, adaugam nodul in sortarea topologica
		topsort.add(node);
	}

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(new File("curse.in"));

		N = scanner.nextInt();
		M = scanner.nextInt();
		A = scanner.nextInt();

		mat = new ArrayList[A + 1];
		adj = new ArrayList[M + 1];
		// initializez matricea de adiacenta
		for (int i = 1; i <= M; i++) {
			adj[i] = new ArrayList<>();
		}
		//initializez matricea de citire
		for (int i = 1; i <= A; i++) {
			mat[i] = new ArrayList<>();
		}
		//citesc input
		for (int i = 1; i <= A; i++) {
			for (int j = 1; j <= N; j++) {
				mat[i].add(scanner.nextInt());
			}
		}

		//prelucrez graful si il construiesc pe baza legaturilor
		//iau cate doua linii
		//daca elementele de pe aceeasi coloana sunt diferite, e muchie si trec la urm doua linii
		//altfel, incrementez pozitia coloanei
		int j;
		for (int i = 1; i < A; i++) {
			j = 0;
			while (j < N) {
				if (!mat[i].get(j).equals(mat[i + 1].get(j))) {
					adj[mat[i].get(j)].add(mat[i + 1].get(j));
					break;
				}
				j++;
			}
		}
		ArrayList<Integer> topsort = solveDfs();

		FileWriter writer = new FileWriter("curse.out");
		for (int i = 0; i < M; i++) {
			writer.write(topsort.get(i) + " ");
		}
		writer.close();
		scanner.close();
	}
}