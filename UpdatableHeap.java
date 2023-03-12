package com.updateable.heap;

import com.entites.Student;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class UpdatableHeap<T extends Student> implements Queue<T> {

    private final BlockingQueue<Node<T>> pq = new PriorityBlockingQueue<>(1000, Comparator.comparing(Node::getPriority));
    private final Map<T, Node<T>> entries = new HashMap<>();

    public void addOrUpdate(T value, int priority) {
        if (entries.containsKey(value)) {
            entries.remove(value).removed = true;
        }
        Node<T> node = new Node<>(value, priority);
        entries.put(value, node);
        pq.add(node);
    }

    public T pop() throws InterruptedException {
        while (true) {
            Node<T> node = pq.take();
            if (!node.removed) {
                entries.remove(node.value);
                return node.value;
            }
        }
    }

    @Override
    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        if (entries.isEmpty()) {
            if (!pq.isEmpty()) pq.clear();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        addOrUpdate(t, t.getPriority());
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean offer(T t) {
        addOrUpdate(t, t.getPriority());
        return true;
    }

    @Override
    public T remove() {
        return null;
    }

    @SneakyThrows
    @Override
    public T poll() {
        return pop();
    }

    @Override
    public T element() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }

    @Data
    private static class Node<T> {
        private final T value;
        private final int priority;
        private boolean removed = false;

        private Node(T value, int priority) {
            this.value = value;
            this.priority = priority;
        }
    }
}
