import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Prinel {
	//functie care-mi calculeaza divizorii unui nr.
	public static ArrayList<Integer> divizor(int x) {
		ArrayList<Integer> v = new ArrayList<>();
		//merg pana la radicalul numarului
		for (int i = 1; i * i <= x; i++) {
			if (x % i == 0) {
				//adaug numerele care divid nr meu
				v.add(x / i);
				//adaug si complementele lor
				v.add(i);
			}
		}
		return v;
	}
	//algoritmul din laboratorul de pe OCW
	public static int puncte_calificari(int N,int K,ArrayList<Integer> costs,ArrayList<Integer> p) {
		int[][] matrix = new int[N + 1][K + 1];
		
		for (int i = 0; i <= K; i++) {
			matrix[0][i] = 0;
		}
		
		for (int i = 1; i <= N; i++) {
			for (int j = 0; j <= K; j++) {
				matrix[i][j] = matrix[i - 1][j];
				if (j - costs.get(i - 1) >= 0) {
					int sol_aux = matrix[i - 1][j - costs.get(i - 1)] + p.get(i - 1);
					
					matrix[i][j] = Math.max(matrix[i][j], sol_aux);
				}
			}
		}
		
		return matrix[N][K];
	}
	
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(new File("prinel.in"));
		
		int N = scanner.nextInt();
		final int K = scanner.nextInt();
		
		//citesc vectorul de target-uri
		ArrayList<Integer> target = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			int target_aux = scanner.nextInt();
			target.add(target_aux);
		}
		
		//citesc vectorul de p-uri
		ArrayList<Integer> p = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			int p_aux = scanner.nextInt();
			p.add(p_aux);
		}
		//aleg maximul din target
		int max = Collections.max(target);
		int[] v = new int[max + 1];
		for (int i = 1; i <= max; i++) {
			v[i] = 100_000;
		}
		v[1] = 0;
		//calculez divizorii fiecarui numar
		for (int i = 1; i <= max; i++) {
			ArrayList<Integer> divizori = divizor(i);
			for (int j = 0; j < divizori.size(); j++) {
				if (i + divizori.get(j) <= max) {
					v[i + divizori.get(j)] = Math.min(v[i + divizori.get(j)], 1 + v[i]);
				}
			}
		}
		//aplic dinamica si calculez costul minim:
		//costul de a ajunge la un numar plus un divizor
		//de al sau este cu unul mai mult decat cel la care
		//sunt sau cel deja calculat anterior
		ArrayList<Integer> costs = new ArrayList<>();
		for (int i = 0; i < target.size(); i++) {
			costs.add(v[target.get(i)]);
		}
		FileWriter writer = new FileWriter("prinel.out");
		writer.write(puncte_calificari(N, K, costs, p) + "");
		writer.close();
	}
}