package ru.sbrf.game2048.boards;

import ru.sbrf.game2048.Key;
import ru.sbrf.game2048.boards.Board;

import java.util.ArrayList;
import java.util.List;

public class SquareBoard <V> extends Board <Key, V> {
    private int size;

    public SquareBoard(int size) {
        super(size, size);
        this.size = size;
    }

    @Override
    public void fillBoard(List<V> list) {
        int counter = 0;
        if (list == null || list.size() < Math.pow(this.size, 2))
            throw new RuntimeException("Invalid parameters");

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                Key key = new Key(i, j);
                this.board.put(key, list.get(counter));
                counter++;
            }
        }
    }

    @Override
    public List<Key> availableSpace() {
        List<Key> keys = new ArrayList<Key>();

        for (Key key : this.board.keySet()) {
            V value = this.board.get(key);

            if (value == null) {
                keys.add(key);
            }
        }

        return keys;
    }

    @Override
    public void addItem(Key key, V value) {
        this.board.put(key, value);
    }

    @Override
    public Key getKey(int i, int j) {
        if (i < 0 || i >= this.size || j < 0 || j >= this.size)
            return null;

        Integer neededI = new Integer(i);
        Integer neededJ = new Integer(j);

        for (Key key : this.board.keySet()) {
            if (neededI.equals(key.getI()) && neededJ.equals(key.getJ()))
                return key;
        }

        return null;
    }

    @Override
    public V getValue(Key key) {
        return this.board.get(key);
    }

    @Override
    public List<Key> getColumn(int j) {
        List<Key> keys = new ArrayList<>();

        for(int i = 0; i < size; i++) {
            Key key = new Key(i, j);
            keys.add(key);
        }

        return keys;
    }

    @Override
    public List<Key> getRow(int i) {
        List<Key> keys = new ArrayList<Key>();

        for(int j = 0; j < size; j++) {
            Key key = new Key(i, j);
            keys.add(key);
        }
        
        return keys;
    }

    @Override
    public boolean hasValue(V value) {
        return this.board.containsValue(value);
    }

    @Override
    public List<V> getValues(List<Key> keys) {
        List values = new ArrayList<V>();
        
        for (Key iterKey : keys) {
            values.add(this.getValue(iterKey));
        }
        return values;
    }
}
