package com.example.mazerunner.models.maze;

import com.example.mazerunner.models.Cell;
import com.example.mazerunner.models.enums.Corner;
import com.example.mazerunner.models.enums.PathDirection;
import lombok.Getter;

import java.util.Random;

@Getter
public class Maze {

    private final Random random;

    private final int width;
    private final int height;
    private final int stonesCount;
    private final boolean[][] availableCells;

    Maze(int width, int height, int stonesCount, boolean[][] availableCells) {
        random = new Random();
        random.nextInt();

        this.width = width;
        this.height = height;
        this.stonesCount = stonesCount;
        this.availableCells = availableCells;
    }

    public static MazeBuilder builder() {
        return new MazeBuilder();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                stringBuilder.append(availableCells[j][i] ? "[ ]" : "[X]");
            }
            stringBuilder.append(System.getProperty("line.separator"));
        }
        return stringBuilder.toString();
    }

    public Cell getRandomCell() {
        return Cell.getRandomCell(width, height);
    }

    public Cell getRandomEdgeCell() {
        PathDirection edge = PathDirection.parse(random.nextInt(4));
        switch (edge) {
            case LEFT : {
                return new Cell(0, random.nextInt(height));
            }
            case UP : {
                return new Cell(random.nextInt(width), 0);
            }
            case RIGHT : {
                return new Cell(width - 1, random.nextInt(height));
            }
            default: { // DOWN
                return new Cell(random.nextInt(width), height - 1);
            }
        }
    }

    public Cell getRandomCornerCell() {
        Corner corner = Corner.parse(random.nextInt(4));
        switch (corner) {
            case UP_LEFT: {
                return new Cell(0, 0);
            }
            case UP_RIGHT: {
                return new Cell(width - 1, 0);
            }
            case DOWN_LEFT: {
                return new Cell(0, height - 1);
            }
            default: { // DOWN_RIGHT
                return new Cell(width - 1, height - 1);
            }
        }
    }
}
