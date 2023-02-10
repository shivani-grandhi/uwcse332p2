package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;
import java.util.function.Supplier;

/**
 * 1. You must implement a generic chaining hashtable. You may not
 * restrict the size of the input domain (i.e., it must accept
 * any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 * shown in class!).
 * 5. HashTable should be able to resize its capacity to prime numbers for more
 * than 200,000 elements. After more than 200,000 elements, it should
 * continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 * list: http://primes.utm.edu/lists/small/100000.txt
 * NOTE: Do NOT copy the whole list!
 * 7. When implementing your iterator, you should NOT copy every item to another
 * dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    //prime sizes
    private final int[] sizes = {17, 37, 79, 164, 331, 673, 1361, 2729, 5471, 10949, 21911, 43853, 87719, 175447, 350899, 701819};

    private int beginning;
    private Dictionary<K,V>[] arr;
    private double count;
    private double loadFactor;

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {

        this.newChain = newChain;
        arr = new Dictionary[7];
        for(int i = 0; i < 7; i++) {
            arr[i] =newChain.get();
        }
        beginning = 0;
        count = 0;
        loadFactor = 0.0;
    }

    @Override
    public V insert(K key, V value) {
        if(loadFactor >= 1) {
            this.arr = rehash(arr);
        }
        int index = Math.abs(key.hashCode() % arr.length);
        if(index >= 0) {
            if(arr[index] == null) {
                arr[index] =newChain.get();
            }
            V val = null;
            if(this.find(key) == null) {
                size++;
            } else {
                val = this.find(key);
            }
            arr[index].insert(key, value);
            loadFactor = (++count) / arr.length;
            return val;
        } else {
            return null;
        }
    }
    private Dictionary<K,V>[] rehash(Dictionary<K,V> arrayChange[]) {
        Dictionary<K,V>[] dict;
        if(beginning > 15) {
            dict = new Dictionary[arrayChange.length * 2];
        } else {
            dict = new Dictionary[sizes[beginning]];
        }
        for(int i = 0; i < arrayChange.length; i++) {
            if(arrayChange[i] != null) {
                for(Item<K,V> item : arrayChange[i]) {
                    int index = Math.abs(item.key.hashCode() % dict.length);
                    if(index >= 0) {
                        if(dict[index] == null) {
                            dict[index] = newChain.get();
                        }
                        dict[index].insert(item.key, item.value);
                    } else {
                        return new Dictionary[0];
                    }
                }
            }
        }
        beginning++;
        return dict;
    }

    @Override
    public V find(K key) {
        int index = Math.abs(key.hashCode() % arr.length);
        if(index >= 0) {
            if(arr[index] == null) {
                arr[index] =newChain.get();
                return null;
            }
            return arr[index].find(key);
        } else {
            return null;
        }
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new ChainingHTIterator();
    }
    @SuppressWarnings("unchecked")
    private class ChainingHTIterator extends SimpleIterator<Item<K, V>> {
        private final Iterator<Item<K, V>>[] iterators;
        private int bucketIndex;
        private int count;

        public ChainingHTIterator() {
            iterators = (Iterator<Item<K, V>>[]) new Iterator[arr.length];
            bucketIndex = 0;
            count = 0;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] != null) {
                    iterators[i] = arr[i].iterator();
                }
            }
        }

        public boolean hasNext() {
            return (count < size);
        }

        public Item<K, V> next() {
            while (bucketIndex < arr.length - 1 && (iterators[bucketIndex] == null || !iterators[bucketIndex].hasNext())) {
                bucketIndex++;
            }

            count++;
            return iterators[bucketIndex].next();
        }
    }
}
