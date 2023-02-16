package p2.sorts;

import java.util.Comparator;


public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        quicksort(comparator, array, 0, array.length);
    }
    private static <E> void quicksort(Comparator<E> comparator, E[] array, int low, int high) {
        if (low < high) {
            E pivot = array[high - 1];

            int i = (low - 1);
            for (int j = low; j < high; j++)
                if (comparator.compare(array[j], pivot) <= 0) {
                    i++;
                    E temp = array[i];

                    array[i] = array[j];
                    array[j] = temp;
                }
            quicksort(comparator, array, low, i);
            quicksort(comparator, array, i + 1, high);
        }
    }
}