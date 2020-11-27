package ru.sbrf.game2048.games;

import ru.sbrf.game2048.Direction;
import ru.sbrf.game2048.boards.Board;

public interface Game {
    void init();
    boolean canMove();
    void move(Direction direction);
    void addItem();
    Board getGameBoard();
    boolean hasWin();
}
