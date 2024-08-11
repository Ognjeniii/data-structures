package HashTableSeparateChainingPackage;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class HashTableSeparateChaining<K, V> implements Iterable<K> {

    private  static final int DEFAULT_CAPACITY = 3;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    private double maxLoadFactor;
    private int capacity, threshold, size = 0;
    private LinkedList <Entry<K, V>>[] table;

//    public HashTableSeparateChaining() {
//        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
//    }
//
//    public HashTableSeparateChaining(int capacity) {
//        this(capacity, DEFAULT_LOAD_FACTOR);
//    }

    public HashTableSeparateChaining() {

    }

    public HashTableSeparateChaining(int capacity) {
        this.capacity = capacity;
    }

    public HashTableSeparateChaining(int capacity, double maxLoadFactor) {
        if (capacity < 0)
            throw new IllegalArgumentException("Illegal capacity");

        // NaN - Not a Number
        if (maxLoadFactor <= 0 || Double.isNaN(maxLoadFactor) || Double.isInfinite(maxLoadFactor))
            throw new IllegalArgumentException("Illegal maxLoadCapacity");

        this.maxLoadFactor = maxLoadFactor;
        this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
        threshold = (int) (this.capacity * maxLoadFactor);
        table = new LinkedList[this.capacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Converts a hash value to an index. Essentially, this strips the nagative sign
    // and places the hash value in the domain [0, capacity)
    private int normalizeIndex(int keyHash) {
        return (keyHash & 0x7FFFFFFF) % capacity;
    }

    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    public boolean containsKey(K key) {
        return hasKey(key);
    }

    public boolean hasKey(K key) {
        int bucketIndex = normalizeIndex(key.hashCode());
        return bucketSeekEntry(bucketIndex, key) != null;
    }

    public V insert(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("Null key");

        Entry<K, V> newEntry = new Entry<>(key, value);
        int bucketIndex = normalizeIndex(newEntry.hash);
        return bucketInsertEntry(bucketIndex, newEntry);
    }

    public V get(K key){
        if (key == null)
            return null;

        int bucketIndex = normalizeIndex(key.hashCode());
        Entry<K, V> entry = bucketSeekEntry(bucketIndex, key);
        if (entry != null)
            return entry.value;
        return null;
    }

    public V remove(K key) {
        if (key == null)
            return null;

        int bucketIndex = normalizeIndex(key.hashCode());
        return bucketRemoveEntry(bucketIndex, key);
    }

    private V bucketRemoveEntry(int bucketIndex, K key) {
        Entry<K, V> entry = bucketSeekEntry(bucketIndex, key);
        if (entry != null) {
            LinkedList<Entry<K, V>> links = table[bucketIndex];
            links.remove(entry);
            --size;
            return entry.value;
        }
        return null;
    }

    // we need to specify bucket index, where entry will be inserted
    private V bucketInsertEntry(int bucketIndex, Entry<K,V> entry) {

        // get the linked list
        LinkedList<Entry<K, V>> bucket = table[bucketIndex];

        // if this bucket is empty, add new linked list
        if (bucket == null)
            table[bucketIndex] = bucket = new LinkedList<>(); // bucket = new LinkedList<>(); table[buckIndex] = bucket;

        // trying to figure out does entry already exists in linked list bucket
        Entry<K, V> existentEntry = bucketSeekEntry(bucketIndex, entry.key);

        // if not, add new entry to the end of the linked list
        if (existentEntry == null) {
            bucket.add(entry);
            if (++size > threshold)
                resizeTable();
            return null; // use null to indicate that there was no previous entry
        }
        // if so, update entry to new entry
        else {
            V oldVal = existentEntry.value;
            existentEntry.value = entry.value;
            return oldVal;
        }
    }

    private Entry<K, V> bucketSeekEntry(int bucketIndex, K key) {
        if (key == null)
            return null;

        LinkedList<Entry<K, V>> bucket = table[bucketIndex];
        if (bucket == null)
            return null;

        for (var entry : bucket) {
            if (entry.key.equals(key))
                return entry;
        }
        return null;
    }

    private void resizeTable() {
        capacity *= 2;
        threshold = (int) (capacity * maxLoadFactor);

        LinkedList<Entry<K, V>>[] newTable = new LinkedList[capacity];

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                for (Entry<K, V> entry : table[i]) {
                    int bucketIndex = normalizeIndex(entry.hash);
                    LinkedList<Entry<K, V>> bucket = newTable[bucketIndex];
                    if (bucket == null)
                        newTable[bucketIndex] = bucket = new LinkedList<>();
                    bucket.add(entry);
                }

                table[i].clear();
                table[i] = null;
            }
        }
        table = newTable;
    }


    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
