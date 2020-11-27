package ru.sbrf.game2048.boards;

import java.util.*;

public abstract class Board<K, V>{
   private int weight;
   private int height;
   protected Map<K, V> board = new HashMap<>();

    public Board(int weight, int height) {
        this.height = height;
        this.weight = weight;
    }

    public abstract void fillBoard(List<V> list);
    public abstract List<K> availableSpace();
    public abstract void addItem(K key, V value);
    public abstract K getKey(int i, int j);
    public abstract V getValue(K key);
    public abstract List<K> getColumn(int j);
    public abstract List<K> getRow(int i);
    public abstract boolean hasValue(V value);
    public abstract List<V> getValues(List<K> keys);

}
