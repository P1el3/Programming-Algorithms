import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Walsh {
	static int zeroORone = 0;
	
	static int divide_matrix(int x, int y, int N) {
		//testez cazul in care nu mai pot
		//impartii matricea in mai multe cadrane
		if (x == 0 && y == 0) {
			if (zeroORone % 2 == 0) {
				return 0;
			} else {
				return 1;
			}
		}
		
		//cadranul 1 (stanga sus)
		int x_quad1 = N / 2;
		int y_quad1 = N / 2;
		if (x < x_quad1 && y < y_quad1) {
			return divide_matrix(x, y, N / 2);
		}
		
		//cadranul 2 (dreapta sus)
		int x_quad2 = N / 2;
		int y_quad2 = N;
		if (x < x_quad2 && y < y_quad2 && y >= N / 2) {
			return divide_matrix(x, y - N / 2, N / 2);
		}
		
		//cadranul 3 (stanga jos)
		int x_quad3 = N;
		int y_quad3 = N / 2;
		if (x < x_quad3 && y < y_quad3 && x >= N / 2) {
			return divide_matrix(x - N / 2, y, N / 2);
		}
		
		//cadranul 4 (dreapta jos)
		//aici vreau sa tin minte de cate ori am fost
		int x_quad4 = N;
		int y_quad4 = N;
		if (x < x_quad4 && y <= y_quad4 && x >= N / 2 && y >= N / 2) {
			zeroORone++;
			return divide_matrix(x - N / 2, y - N / 2, N / 2);
		}
		
		return 0;
	}
	
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(new File("walsh.in"));
		FileWriter writer = new FileWriter("walsh.out", true);
		int N = scanner.nextInt();
		int K = scanner.nextInt();
		
		for (int i = 0; i < K; i++) {
			int x = scanner.nextInt();
			int y = scanner.nextInt();
			zeroORone = 0;
			writer.write(divide_matrix(x - 1, y - 1, N) + "\n");
		}
		writer.close();
		
		
	}
}
