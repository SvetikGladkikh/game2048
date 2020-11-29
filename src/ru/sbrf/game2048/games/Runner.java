package ru.sbrf.game2048.games;

import ru.sbrf.game2048.boards.Board;
import ru.sbrf.game2048.boards.SquareBoard;
import ru.sbrf.game2048.games.Game;
import ru.sbrf.game2048.games.Game2048;

public class Runner {
    public static void main(String[] args) {
        Game game2048 = new Game2048();
        System.out.println(game2048.canMove());
    }
}

