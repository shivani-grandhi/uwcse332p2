package datastructures.worklists;

import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    private int front;
    private int end;
    private int size;
    private E[] array;
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.front = 0;
        this.end = 0;
        this.size = 0;
        this.array = (E[])new Comparable[capacity];
    }

    @Override
    public void add(E work) {
        if(isFull()){
            throw new IllegalStateException();
        }
        array[end] = work;
        end = (end + 1) % array.length;
        size++;
        if(front == -1) {
            front = end;
        }
    }

    @Override
    public E peek() {
        if (!hasWork()){
            throw new NoSuchElementException();
        }
        return array[(front)];
    }

    @Override
    public E peek(int i) {

        return array[(front + i) % array.length];
    }

    @Override
    public E next() {
        if(size == 0){
            throw new NoSuchElementException();
        }
        E first = array[front];
        array[front] = null;
        front = (front + 1) % array.length;
        size--;
        return first;
    }

    @Override
    public void update(int i, E value) {
        if( 0 <= i && i < size()) {
            array[front + i] = value;
        } else {
            throw new IndexOutOfBoundsException();
        }
        if(!this.hasWork()) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int size() {

        return size;
    }

    @Override
    public void clear() {
        front = end = size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        int minLength = Math.min(this.size(), other.size());
        int compare = 0;
        for (int i = 0; i < minLength; i++) {
            compare = this.peek(i).compareTo(other.peek(i));
            if (compare != 0) {
                return compare;
            }
        }

        return this.size() - other.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            if (other.size() != this.size()) {
                return false;
            } else {
                return (this.compareTo(other) == 0);
            }
        }
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for(int i = 0; i < this.size; i ++) {
            hashCode += array[(front + i) % array.length].hashCode() * (this.front + i);
        }
        return hashCode * (this.size - this.front);
    }
}
