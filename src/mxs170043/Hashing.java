package mxs170043;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Hashing<K, V> {

	static class Entry<K, V> {
		K key;
		V value;
		boolean isDeleted;

		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
			isDeleted = false;
		}

		public boolean equals(Entry<K, V> entry) {
			if (this.key == entry.key && this.value == entry.value)
				return true;
			return false;
		}

		public String toString() {
			return ("Key: " + this.key + ", Value: " + this.value);
		}
	}

	Entry<K, V> table[];
	int size;
	int capacity;
	int primeSize;

	public Hashing(int capacity) {
		this.capacity = capacity;
		this.size = 0;
		table = new Entry[this.capacity];
		primeSize = getPrime();
	}

	public int getPrime() {
		for (int i = capacity - 1; i >= 1; i--) {
			int fact = 0;
			for (int j = 2; j <= (int) Math.sqrt(i); j++)
				if (i % j == 0)
					fact++;
			if (fact == 0)
				return i;
		}
		return 3;
	}

	public int myHash1(K key) {
		int hashVal = key.hashCode();
		return hashVal %= capacity;
	}

	private int myHash2(K key) {
		int hashVal = key.hashCode();
		hashVal %= capacity;
		return primeSize - hashVal % primeSize;
	}

	public int find(K key) {
		int k = 0;
		// Get index from first hash
		int hash1 = myHash1(key);
		// get index from second hash
		int hash2 = myHash2(key);
		int ik = (hash1 + k * hash2) % capacity;
		while (true) {
			ik = (hash1 + k * hash2) % capacity;
			if (table[ik] == null || table[ik].key == key)
				return ik;
			else if (table[ik].isDeleted)
				break;
			else
				k++;
		}
		int xspot = ik;
		while (true) {
			ik = (hash1 + k * hash2) % capacity;
			k++;
			if (table[ik].key == key)
				return ik;
			if (table[ik] == null)
				return xspot;
		}
	}

	public boolean contains(K key) {
		int loc = find(key);
		if (table[loc].key == key)
			return true;
		return false;
	}

	public boolean add(K key, V value) {
		int loc = find(key);
		if (table[loc] != null) {
			if (table[loc].key == key)
				return false;
			table[loc].key = key;
			table[loc].value = value;
			table[loc].isDeleted = false;
			return true;
		} else {
			table[loc] = new Entry<K, V>(key, value);
			return true;
		}
	}

	public Entry<K, V> remove(K key) {
		int loc = find(key);
		if (table[loc].key == key) {
			Entry<K, V> result = table[loc];
			table[loc].isDeleted = true;
			return result;
		}
		return null;
	}

	public void printHash() {
		for (int i = 0; i < capacity; i++) {
			if (table[i] != null)
				System.out.println(i + ": " + table[i]);
		}
	}

	static <T> int distinctElements(T[] arr) {
		return -1;
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int choice = 0;
		Hashing<Integer, Integer> hashing = new Hashing<Integer, Integer>(5);
		do {
			System.out.println("1.Add 2.Contains 3.Remove 4.Print");
			choice = Integer.parseInt(br.readLine());
			int key, value;
			switch (choice) {
			case 1:
				System.out.println("Enter the key and value: ");
				key = Integer.parseInt(br.readLine());
				value = Integer.parseInt(br.readLine());
				System.out.println(hashing.add(key, value) ? "Added" : "Cannot Add");
				break;
			case 2:
				System.out.println("Enter the key to find: ");
				key = Integer.parseInt(br.readLine());
				System.out.println(hashing.contains(key) ? "Found" : "Not Found");
				break;
			case 3:
				System.out.println("Enter the key to remove: ");
				key = Integer.parseInt(br.readLine());
				System.out.println((hashing.remove(key) != null) ? "Removed" : "Error Removing");
				break;
			case 4:
				hashing.printHash();
				break;
			}
		} while (choice != 0);
		br.close();
	}

}
