package kamene.core;

import java.util.Objects;
import java.util.Random;

public class Field {

    private final Tile[][] tiles;
    private Tile helpTile;

    private Number number;
    private final Empty empty = new Empty();
    private final int rowCount;
    private final int columnCount;
    private GameState state = GameState.PLAYING;

    public Field(int rowCount, int columCount) {
        this.rowCount = rowCount;
        this.columnCount = columCount;
        tiles = new Tile[rowCount][columCount];
        generate();
    }

    public Empty getEmpty() {
        return empty;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Tile getTile(int row, int column) {
        return tiles[row][column];
    }

    public GameState getState() {
        return state;
    }

    private boolean isSolved() {
        int i = 1;
        int isEqual = (rowCount * columnCount) - 1;
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                if (getTile(row, col).equals(i)) {
                    i++;
                    isEqual--;
                }
            }
        }
        return isEqual == 0;
    }

    private void generate() {
        int num = 1;
        int ran = 0;
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                if (row == (rowCount - 1) && col == (columnCount - 1)) {
                    tiles[row][col] = empty;
                } else {
                    tiles[row][col] = new Number(num);
                    num++;
                }
            }
        }
        randomSwap();
    }

    private void randomSwap() {
        //up = 0 || down = 1 || left = 2 || right = 3
        int i = 0;
        Random r = new Random();
        int ran = r.nextInt(1000);
        while (i <= ran) {
            int x = r.nextInt(4);
            for (int row = 0; row < rowCount; row++) {
                for (int col = 0; col < columnCount; col++) {
                    if (tiles[row][col].equals(empty)) {
                        if (x == 0 && row < (rowCount - 1)) {
                            goUp(row, col);
                        }
                        if (x == 1 && row > 0) {
                            goDown(row, col);
                        }
                        if (x == 2 && col < (columnCount - 1)) {
                            goLeft(row, col);
                        }
                        if (x == 3 && col > 0) {
                            goRight(row, col);
                        }
                    }
                }
            }
            i++;
        }
    }

    public void goUp(int row, int col) {
        helpTile = tiles[row][col];
        tiles[row][col] = tiles[row + 1][col];
        tiles[row + 1][col] = helpTile;
        if (isSolved()) {
            state = GameState.SOLVED;
        }
    }

    public void goDown(int row, int col) {
        helpTile = tiles[row][col];
        tiles[row][col] = tiles[row - 1][col];
        tiles[row - 1][col] = helpTile;
        if (isSolved()) {
            state = GameState.SOLVED;
        }
    }

    public void goLeft(int row, int col) {
        helpTile = tiles[row][col];
        tiles[row][col] = tiles[row][col + 1];
        tiles[row][col + 1] = helpTile;
        if (isSolved()) {
            state = GameState.SOLVED;
        }
    }

    public void goRight(int row, int col) {
        helpTile = tiles[row][col];
        tiles[row][col] = tiles[row][col - 1];
        tiles[row][col - 1] = helpTile;
        if (isSolved()) {
            state = GameState.SOLVED;
        }
    }

}
