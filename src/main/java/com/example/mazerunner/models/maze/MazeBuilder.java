package com.example.mazerunner.models.maze;

import com.example.mazerunner.excptions.MazeException;
import com.example.mazerunner.models.Cell;

import java.util.Arrays;

public class MazeBuilder {

    private int width = 10;
    private int height = 10;
    private int stonesCount = 20;

    public MazeBuilder width(int width) {
        this.width = width;
        return this;
    }

    public MazeBuilder height(int height) {
        this.height = height;
        return this;
    }

    public MazeBuilder stones(int stonesCount) {
        this.stonesCount = stonesCount;
        return this;
    }

    public Maze build() throws MazeException {
        int cellsCount = width * height;
        if (stonesCount > cellsCount) throw new MazeException("Can not build maze. Too many stones");

        boolean[][] availableCells;
        if (stonesCount > cellsCount / 2) { // set stones
            availableCells = doWork(stonesCount, false);
        } else { // set empty cells
            availableCells = doWork(cellsCount - stonesCount, true);
        }
        return new Maze(this.width, this.height, stonesCount, availableCells);
    }

    private boolean[][] doWork(int operationsCount, boolean empty) {
        boolean[][] gameField = new boolean[width][height]; // false if stone exists

        if (!empty) {
            for (int i = 0; i < width; i++) {
                Arrays.fill(gameField[i], true);
            }
        }

        for (int i = 0; i < operationsCount; i++) {
            Cell cell = Cell.getRandomCell(width, height);
            if (gameField[cell.getX()][cell.getY()] == empty) {
                i--;
            } else {
                gameField[cell.getX()][cell.getY()] = empty;
            }
        }

        return gameField;
    }
}
