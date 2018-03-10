package com.tekleo.language_classifier.utils;

import java.util.*;

public class MapUtils {
    public static <K, V extends Comparable<? super V>> LinkedHashMap<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort( list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return - (o1.getValue()).compareTo(o2.getValue());
            }
        });

        LinkedHashMap<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static <K, V> Map<K, V> getFirstN(Map<K, V> map, int n) {
        // Create new map
        Map<K, V> first = new LinkedHashMap<>();

        // Create counter
        int count = 0;

        // For each entry in the map
        for (Map.Entry<K, V> e : map.entrySet()) {
            // Push it to new map
            first.put(e.getKey(), e.getValue());

            // Increment counter
            count++;

            // Check if the counter hits our target
            if (count == n)
                break;;
        }

        // Return result
        return first;
    }

    public static <K, V> K getFirstKey(Map<K, V> map) {
        for (Map.Entry<K, V> e : map.entrySet())
            return e.getKey();
        return null;
    }

    public static <K, V> V getFirstValue(Map<K, V> map) {
        for (Map.Entry<K, V> e : map.entrySet())
            return e.getValue();
        return null;
    }
}