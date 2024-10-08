package HashTableSeparateChainingPackage;

// this class representing individual item, which is going to be inserted into hash table
public class Entry <K, V> {
    int hash;
    K key;
    V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.hash = key.hashCode();
    }

    public boolean equals(Entry<K, V> other) {
        if(hash != other.hash)
            return false;
        return key.equals(other.key);
    }

    public String toString() {
        return key + " => " + value;
    }
}
