package ru.sbrf.game2048.games;

import ru.sbrf.game2048.Direction;
import ru.sbrf.game2048.GameHelper;
import ru.sbrf.game2048.Key;
import ru.sbrf.game2048.boards.Board;
import ru.sbrf.game2048.boards.SquareBoard;

import java.util.*;

public class Game2048 implements Game {
    private GameHelper helper = new GameHelper();
    private Random random = new Random();
    public static final int GAME_SIZE = 4;
    private final Board<Key, Integer> board = new SquareBoard<>(GAME_SIZE);

    @Override
    public void init() {
        List<Integer> values = new ArrayList<>();

        for (int i = 0; i < (int)Math.pow(GAME_SIZE, 2); i++) {
            values.add(null);
        }

        this.board.fillBoard(values);
        this.addItem();
        this.addItem();
    }

    @Override
    public boolean canMove() {
        for (int i = 0; i < GAME_SIZE; i++) {
            for (int j = 0; j < GAME_SIZE; j++) {
                Integer value = this.board.getValue(new Key(i, j));

                if (value == null) {
                    return true;
                }

                if (this.board.getValue(new Key(i - 1, j)) == value ||
                    this.board.getValue(new Key(i, j - 1)) == value ||
                    this.board.getValue(new Key(i + 1, j)) == value ||
                    this.board.getValue(new Key(i, j + 1)) == value) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void move(Direction direction) {

        if (!canMove())
            return;

        for (int i = 0; i < GAME_SIZE; i++) {
            List<Key> keys;

            if (direction == Direction.RIGHT || direction == Direction.LEFT) {
                keys = this.board.getRow(i);
            } else {
                keys = this.board.getColumn(i);
            }

            List <Integer> values = new ArrayList<>();

            if (direction == Direction.LEFT || direction == Direction.UP) {
                getValues(keys, values);
            } else {
                getValuesReversed(keys, values);
            }

            List<Integer> result_values = GameHelper.moveAndMergeEqual(values);

            if (direction == Direction.LEFT || direction == Direction.UP) {
                setValues(keys, result_values);
            } else {
                setValuesReversed(keys, result_values);
            }
        }

        addItem();
    }

    @Override
    public void addItem() {
        List<Key> keys =  this.board.availableSpace();
        int index = (int) ( Math.random() * keys.size() );
        Integer value = (int) Math.pow(2, (int)(1 + (int) ( Math.random() * 2 )));

        this.board.addItem(keys.get(index), value);
    }

    @Override
    public Board getGameBoard() {
        return this.board;
    }

    @Override
    public boolean hasWin() {
        return this.board.hasValue(2048);
    }

    private void getValuesReversed(List<Key> keys, List<Integer> values) {
        for (int k = GAME_SIZE - 1; k >= 0; k--) {
            values.add(this.board.getValue(keys.get(k)));
        }
    }

    private void setValuesReversed(List<Key> keys, List<Integer> values) {
        int i = 0;

        for (int j = GAME_SIZE - 1; j >=0; j--) {
            this.board.addItem(keys.get(j), values.get(i));
            i++;
        }
    }

    private void getValues(List<Key> keys, List<Integer> values) {
        for (Key item: keys) {
            values.add(this.board.getValue(item));
        }
    }

    private void setValues(List<Key> keys, List<Integer> values) {
        for (int j = 0; j < GAME_SIZE; j++) {
            this.board.addItem(keys.get(j), values.get(j));
        }
    }
}
