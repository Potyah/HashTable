package hashtable;

import java.util.*;

public class HashTable<T> implements Collection<T> {
    private int size;
    private final ArrayList<T>[] array;
    private int modCount;

    @SuppressWarnings("unchecked")
    public HashTable() {
        array = new ArrayList[10];
    }

    public HashTable(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("HashTable length must be > 0");
        }

        //noinspection unchecked
        array = new ArrayList[length];
    }

    private int getIndex(Object o) {
        return Math.abs(Objects.hashCode(o) % array.length);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyListIterator();
    }

    private class MyListIterator implements Iterator<T> {
        private int arrayIndex = 0;
        private int listIndex = -1;
        private int collectionIndex = 0;
        private final int iteratorModCount = modCount;

        public boolean hasNext() {
            return collectionIndex < size;
        }

        public T next() {
            if (iteratorModCount != modCount) {
                throw new ConcurrentModificationException("The collection element is changed");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("The collection element is ended");
            }

            while ((array[arrayIndex] == null) || (array[arrayIndex].size() <= listIndex + 1)) {
                ++arrayIndex;
                listIndex = -1;
            }

            ++listIndex;
            ++collectionIndex;

            return array[arrayIndex].get(listIndex);
        }
    }

    @Override
    public Object[] toArray() {
        //noinspection unchecked
        T[] dataArray = (T[]) new Object[size];
        int i = 0;

        for (T t : this) {
            dataArray[i] = t;
            i++;
        }

        return dataArray;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a == null) {
            throw new NullPointerException("The specified array is null");
        }

        int i = 0;

        if (a.length < size) {
            //noinspection unchecked
            T1[] dataArray = (T1[]) new Object[size];

            for (T t : this) {
                //noinspection unchecked
                dataArray[i] = (T1) t;
                i++;
            }

            return dataArray;
        }

        for (T t : this) {
            //noinspection unchecked
            a[i] = (T1) t;
            i++;
        }

        if (i < a.length) {
            a[i] = null;
        }

        return a;
    }

    @Override
    public boolean add(T t) {
        int index = getIndex(t);

        if (array[index] == null) {
            array[index] = new ArrayList<>();
        }

        array[index].add(t);

        size++;
        modCount++;

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException("The specified collection is null");
        }

        if (c.isEmpty()) {
            return false;
        }

        for (T t : c) {
            add(t);
        }

        modCount++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = getIndex(o);

        if (array[index] != null && array[index].remove(o)) {
            size--;
            modCount++;

            return true;
        }

        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("The specified collection is null");
        }

        if (c.isEmpty()) {
            return false;
        }

        boolean isResult = false;

        for (ArrayList<T> t : array) {
            if (t != null && t.removeAll(c)) {
                isResult = true;
            }
        }

        if (isResult) {
            modCount++;
        }

        return isResult;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("The specified collection is null");
        }

        if (c.isEmpty()) {
            return false;
        }

        boolean isResult = false;

        for (ArrayList<T> t : array) {
            if (t != null && t.retainAll(c)) {
                isResult = true;
            }
        }

        if (isResult) {
            modCount++;
        }

        return isResult;
    }

    @Override
    public boolean contains(Object o) {
        int index = getIndex(o);

        if (array[index] == null) {
            return false;
        }

        return array[index].contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("The specified collection is null");
        }

        if (c.isEmpty()) {
            return false;
        }

        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void clear() {
        Arrays.fill(array, null);
        size = 0;

        modCount++;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}