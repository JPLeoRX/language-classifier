package com.tekleo.language_classifier.utils;

import org.nd4j.linalg.primitives.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListUtils {
    public static <E> LinkedList<E> toLinkedList(E[] a) {
        LinkedList<E> list = new LinkedList<>();
        for (E e : a)
            list.add(e);
        return list;
    }

    public static <E> ArrayList<E> toArrayList(E[] a) {
        ArrayList<E> list = new ArrayList<>(a.length);
        for (E e : a)
            list.add(e);
        return list;
    }

    public static <E> ArrayList<E> toArrayList(List<E> a) {
        ArrayList<E> list = new ArrayList<>(a.size());
        list.addAll(a);
        return list;
    }

    public static <E> Pair<ArrayList<E>, ArrayList<E>> split(List<E> list, double ratio) {
        int index = (int) (list.size() * ratio);
        ArrayList<E> slice1 = toArrayList(list.subList(0, index));
        ArrayList<E> slice2 = toArrayList(list.subList(index, list.size()));
        return new Pair<>(slice1, slice2);
    }
}