package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    int size;
    int defaultSize;
    private Comparator<E> comparator;

    public MinFourHeap(Comparator<E> c) {
        this.defaultSize = 10;
        this.data = (E[]) new Object[defaultSize];
        this.size = 0;
        this.comparator = c;
    }

    @Override
    public boolean hasWork() {
        return this.size>0;
    }

    @Override
    public void add(E work) {
        if (this.size == this.defaultSize) {
            defaultSize = defaultSize * 2;
            E[] temp = (E[]) new Object[defaultSize];
            for(int i = 0; i < data.length; i++) {
                temp[i] = data[i];
            }
            data = temp;
        }
        data[size] = work;
        if (this.size > 0) {
            int parent = (this.size - 1)/4;
            percolateUp(parent, this.size);
        }
        size++;
    }
    private void percolateUp(int parent, int child) {
        while (child != 0 && this.comparator.compare(this.data[child], this.data[parent]) < 0) {
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
        this.data[0] = this.data[this.size - 1];
        this.size--;
        if (this.size > 0) {
            int min = 1;
            for (int i = min + 1; i < min + 4; i++) {
                if (i < this.size && this.comparator.compare(this.data[i], this.data[min]) < 0) {
                    min = i;
                }
            }
            percolateDown(0, min);
        }
        return value;
    }
    private void percolateDown(int parent, int child) {
        while  (child != 0 && this.comparator.compare(this.data[child], this.data[parent]) < 0) {

            E temp = this.data[child];
            this.data[child] = this.data[parent];
            this.data[parent] = temp;
            parent = child;
            int first = 4 * parent+1;
            int min = first;
            if (first > this.size - 1)
                break;
            for (int i = first + 1; i < first + 4; i++) {
                if (i < this.size && this.comparator.compare(this.data[i], this.data[min]) < 0) {
                    min = i;
                }
            }
            child = min;
        }

    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        defaultSize = 10;
        data = (E[]) new Object[defaultSize];
        size = 0;
    }
}
