package p2.sorts;



import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    /**
     * Behaviour is undefined when k > array.length
     */
    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        if (array.length < k) {
            k = array.length;
        }
        MinFourHeap<E> sorted = new MinFourHeap<E>(comparator);
        for (int i = 0; i < Math.min(k, array.length); i++) {
            sorted.add(array[i]);
        }
        for (E e : array) {
            if (comparator.compare(e, sorted.peek()) > 0) {
                sorted.add(e);
                sorted.next();
            }
        }
        for (int i = 0; i < array.length; i++) {
            if (i < k) {
                array[i] = sorted.next();
            } else {
                array[i] = null;
            }
        }
    }
}
