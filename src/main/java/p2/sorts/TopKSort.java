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
        for (int i = 0; i < k; i++) {
            sorted.add(array[i]);
        }
        for (int j = k; j < array.length; j++) {
            if (comparator.compare(array[j], sorted.peek()) > 0) {
                sorted.next();
                sorted.add(array[j]);
            }
            array[j] = null;

        }
        for (int p = 0; p < k; p++) {
            array[p] = sorted.next();
        }
    }
}
