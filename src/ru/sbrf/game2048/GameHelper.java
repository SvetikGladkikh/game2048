package ru.sbrf.game2048;

import java.util.*;

public class GameHelper {
    public static List<Integer> moveAndMergeEqual(List<Integer> list) {
        List result = new ArrayList<Integer>();
        Integer prevItem = null;

        for (Integer item : list) {
            if (item == null) {
                continue;
            }

            if (prevItem != null) {
                if (item.equals(prevItem)) {
                    result.add(item * 2);
                    prevItem = null;
                    continue;
                } else {
                    result.add(prevItem);
                }
            }

            prevItem = item.intValue();
        }

        if (prevItem != null) {
            result.add(prevItem);
        }

        for (int i = result.size(); i < list.size(); i++) {
            result.add(null);
        }

        return result;
    }
}
