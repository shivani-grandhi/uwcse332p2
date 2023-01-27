package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int capacity;
    private int currentSize;

    public MinFourHeapComparable() {
        currentSize = 0;
        capacity = 100;
        data = (E[]) new Comparable[capacity];
    }

    @Override
    public boolean hasWork() {
        return super.hasWork();
    }

    @Override
    public void add(E work) {
        if (currentSize == capacity) {
            capacity = capacity * 2;
            data = Arrays.copyOf(data, capacity);
        }
        data[currentSize] = work;
        if (this.currentSize > 0) {
            int parent = (this.currentSize - 1)/4;
            percolateUp(parent, this.currentSize);
        }
        currentSize++;
    }

    private void percolateUp(int parent, int child) {
        while (child != 0 && this.data[child].compareTo(this.data[parent]) < 0) {
            E temp = this.data[child];
            this.data[child] = this.data[parent];
            this.data[parent] = temp;
            child = parent;
            parent = (child - 1) / 4;
        }
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        E value = this.data[0];
        this.data[0] = this.data[this.currentSize - 1];
        this.currentSize--;
        if (this.currentSize > 0) {
            int min = 1;
            for (int i = min + 1; i < min + 4; i++) {
                if (i < this.currentSize && this.data[i].compareTo(this.data[min]) < 0) {
                    min = i;
                }
            }
            percolateDown(0, min);
        }
        return value;
    }

    private void percolateDown(int parent, int child) {
        while (this.data[parent].compareTo(this.data[child]) > 0) {

            E temp = this.data[child];
            this.data[child] = this.data[parent];
            this.data[parent] = temp;
            parent = child;
            int first = 4 * parent + 1;
            int min = first;
            if (first > this.currentSize - 1)
                break;
            for (int i = first + 1; i < first + 4; i++) {
                if (i < this.currentSize && this.data[i].compareTo(this.data[min]) < 0) {
                    min = i;
                }
            }
            child = min;
        }

    }

    @Override
    public int size() {
        return this.currentSize;
    }

    @Override
    public void clear() {
        capacity = 10;
        data = (E[]) new Comparable[capacity];
        currentSize = 0;
    }
}
