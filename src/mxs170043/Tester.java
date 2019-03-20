package mxs170043;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tester {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		SkipList<Integer> skipList = new SkipList<>();
		while (true) {
			System.out.println("1.Add 2.Remove 3.PrintList 4.Size 5.First 6.Last 7.GetLinear 8.Ceiling 9.Floor 10.Find");
			int choice = Integer.parseInt(br.readLine());
			switch (choice) {
			case 1:
				skipList.add(Integer.parseInt(br.readLine()));
				break;
			case 2:
				skipList.remove(Integer.parseInt(br.readLine()));
				break;
			case 3:
				skipList.printList();
				break;
			case 4:
				System.out.println(skipList.size());
				break;
			case 5:
				System.out.println(skipList.first());
				break;
			case 6:
				System.out.println(skipList.last());
				break;
			case 7:
				System.out.println(skipList.getLinear(Integer.parseInt(br.readLine())));
				break;
			case 8:
				System.out.println(skipList.ceiling(Integer.parseInt(br.readLine())));
				break;
			case 9:
				System.out.println(skipList.floor(Integer.parseInt(br.readLine())));
				break;
			case 10:
				System.out.println(skipList.find(Integer.parseInt(br.readLine())));
				break;
			}
		}
	}

}
