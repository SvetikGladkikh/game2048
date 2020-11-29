package ru.sbrf.game2048.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import ru.sbrf.game2048.boards.Direction;
import ru.sbrf.game2048.boards.Key;
import ru.sbrf.game2048.boards.Board;
import ru.sbrf.game2048.exeptions.GameOverException;
import ru.sbrf.game2048.games.Game;
import ru.sbrf.game2048.games.Game2048;

// drawing based on https://github.com/bulenkov/2048
public class Game2048Panel extends JPanel {
    private static final Color BG_COLOR = new Color(0xbbada0);
    private static final String FONT_NAME = "Arial";
    private static final int TILE_SIZE = 64;
    private static final int TILES_MARGIN = 16;

    private final Game game = new Game2048();
    private boolean myWin;
    private boolean myLose;

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public Game2048Panel() {
        setPreferredSize(new Dimension(340, 400));
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    game.init();
                    myWin = false;
                    myLose = false;
                }

                try {
                    if (!myWin && !myLose) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_LEFT:
                                game.move(Direction.LEFT);
                                break;
                            case KeyEvent.VK_RIGHT:
                                game.move(Direction.RIGHT);
                                break;
                            case KeyEvent.VK_DOWN:
                                game.move(Direction.DOWN);
                                break;
                            case KeyEvent.VK_UP:
                                game.move(Direction.UP);
                                break;
                        }
                    }
                } catch (GameOverException gameOverException) {
                    myLose = !game.hasWin();
                } finally {
                    myWin = game.hasWin();
                }

                repaint();
            }
        });
        game.init();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        Board<Key, Integer> board = game.getGameBoard();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                drawTile(g, board.getValue(board.getKey(y, x)), x, y);
            }
        }
    }

    private void drawTile(Graphics g2, Integer value, int x, int y) {
        Graphics2D g = ((Graphics2D) g2);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y);
        int newValue = value == null ? 0 : value;
        g.setColor(getBackground(newValue));
        g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, 14, 14);
        g.setColor(getForeground(newValue));
        final int size = newValue < 100 ? 36 : newValue < 1000 ? 32 : 24;
        final Font font = new Font(FONT_NAME, Font.BOLD, size);
        g.setFont(font);

        String s = String.valueOf(value);
        final FontMetrics fm = getFontMetrics(font);

        final int w = fm.stringWidth(s);
        final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

        if (newValue != 0)
            g.drawString(s, xOffset + (TILE_SIZE - w) / 2, yOffset + TILE_SIZE - (TILE_SIZE - h) / 2 - 2);

        if (myWin || myLose) {
            g.setColor(new Color(255, 255, 255, 30));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(new Color(78, 139, 202));
            g.setFont(new Font(FONT_NAME, Font.BOLD, 48));
            if (myWin) {
                g.drawString("You won!", 68, 150);
            }
            if (myLose) {
                g.drawString("Game over!", 50, 130);
                g.drawString("You lose!", 64, 200);
            }
            if (myWin || myLose) {
                g.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
                g.setColor(new Color(128, 128, 128, 128));
                g.drawString("Press ESC to play again", 80, getHeight() - 40);
            }
        }
        g.setFont(new Font(FONT_NAME, Font.PLAIN, 18));

    }

    public Color getBackground(int value) {
        switch (value) {
            case 2:
                return new Color(0xeee4da);
            case 4:
                return new Color(0xede0c8);
            case 8:
                return new Color(0xf2b179);
            case 16:
                return new Color(0xf59563);
            case 32:
                return new Color(0xf67c5f);
            case 64:
                return new Color(0xf65e3b);
            case 128:
                return new Color(0xedcf72);
            case 256:
                return new Color(0xedcc61);
            case 512:
                return new Color(0xedc850);
            case 1024:
                return new Color(0xedc53f);
            case 2048:
                return new Color(0xedc22e);
        }
        return new Color(0xcdc1b4);
    }

    public Color getForeground(int value) {
        return value < 16 ? new Color(0x776e65) : new Color(0xf9f6f2);
    }

    private static int offsetCoors(int arg) {
        return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN;
    }

}
