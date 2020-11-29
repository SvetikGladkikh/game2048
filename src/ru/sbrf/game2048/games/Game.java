package ru.sbrf.game2048.games;

import ru.sbrf.game2048.boards.Direction;
import ru.sbrf.game2048.boards.Board;
import ru.sbrf.game2048.exeptions.GameOverException;

public interface Game {
    void init();
    boolean canMove();
    void move(Direction direction) throws GameOverException;
    void addItem() throws GameOverException;
    Board getGameBoard();
    boolean hasWin();
}
