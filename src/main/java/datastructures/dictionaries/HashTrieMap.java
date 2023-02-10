package datastructures.dictionaries;


import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new HashMap<A, HashTrieNode>();
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null){
            throw new IllegalArgumentException();
        }
        if (this.root == null) {
            this.root = new HashTrieNode();
        }
        V temp = null;
        if (key.isEmpty()) {
            temp = this.root.value;
            this.root.value = value;
        } else {
            HashTrieNode root = (HashTrieNode) this.root;
            for (A search : key) {
                if (!root.pointers.containsKey(search)) {
                    root.pointers.put(search, new HashTrieNode());
                }
                root = root.pointers.get(search);
            }
            temp = root.value;
            root.value = value;
        }
        if(temp == null){
            size++;
        };
        return temp;
    }

    @Override
    public V find(K key) {
        if (key == null){
            throw new IllegalArgumentException();
        }
        HashTrieNode root = (HashTrieNode) this.root;
        for (A temp : key){
            if (!root.pointers.containsKey(temp)){
                return null;
            }
            root = root.pointers.get(temp);
        }
        return root.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null){
            throw new IllegalArgumentException();
        }
        HashTrieNode node = (HashTrieNode) this.root;
        for (A search : key){
            if (!node.pointers.containsKey(search)){
                return false;
            }
            node = node.pointers.get(search);
            if (node == null){
                return false;
            }
        }
        return true;
    }

    @Override
    public void delete(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
