package p2.sorts;



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
            HeapSort.sort(array, comparator);
            return;
        }
        QuickSort.sort(array,comparator);

        for (int i = 0; i < k; i++) {
            array[i] = array[array.length-k+i];
        }
    }
}
