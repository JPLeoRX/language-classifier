package com.tekleo.language_classifier.utils;

import java.util.ArrayList;
import java.util.LinkedList;

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
}