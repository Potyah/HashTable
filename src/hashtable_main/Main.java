package hashtable_main;

import hashtable.HashTable;

public class Main {
    public static void main(String[] args) {
        HashTable<Integer> hashTable1 = new HashTable<>();
        hashTable1.add(4);
        hashTable1.add(56);
        hashTable1.add(2);
        hashTable1.add(2);
        hashTable1.add(2);
        hashTable1.add(4574);
        hashTable1.add(5367);

        HashTable<Integer> hashTable2 = new HashTable<>(5);
        hashTable2.add(4);
        hashTable2.add(56);
        hashTable2.add(2);

        System.out.println(hashTable1.containsAll(hashTable2));
    }
}