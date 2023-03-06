import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Statistics {
	public static int appearances(ArrayList<String> vect) {
		int max = 0;
		//aici trec prin fiecare litera din alfabet
		for (int i = 0; i < 26; i++) {
			//vectorul cu nr aparitiilor
			ArrayList<Integer> v = new ArrayList<>();
			//litera "a" din alfabet
			char aux = (char) (i + 97);
			//parcurg vectorul cu cuvinte
			for (String s : vect) {
				int count = 0;//retin dominanta, daca gasesc litera ++
				//daca e o alta litera --
				//parcurg cuvantul
				for (int k = 0; k < s.length(); k++) {
					if (s.charAt(k) == aux) {
						count++;
					} else {
						count--;
					}
				}
				//adaug valoare in vector
				v.add(count);
			}
			//sortez descrescator
			v.sort((o1, o2) -> o2 - o1);
			int sum = 0;
			int count = 0;
			for (int j : v) {
				sum += j;
				//testez daca o litera este dominanta
				if (sum > 0) {
					count++;
				}
			}
			//vad care e cea mai dominanta litera
			if (count > max) {
				max = count;
			}
		}
		if (max > 0) {
			return max;
		} else {
			return -1;
		}
	}
	
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(new File("statistics.in"));
		FileWriter writer = new FileWriter("statistics.out");
		int N = scanner.nextInt();
		scanner.nextLine();//imi citea un \n si nu mi dadea ce trb
		ArrayList<String> vect = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			String v_aux = scanner.nextLine();
			vect.add(v_aux);
		}
		
		writer.write(appearances(vect) + "");
		
		writer.close();
	}
}
