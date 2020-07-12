package com.example.mazerunner.models.game;

import com.example.mazerunner.excptions.GameException;
import com.example.mazerunner.models.Cell;
import com.example.mazerunner.models.enums.GameType;
import com.example.mazerunner.models.maze.Maze;

public class GameBuilder {

    private Maze maze;
    private Cell start;
    private Cell target;

    public GameBuilder maze(Maze maze) {
        this.maze = maze;
        return this;
    }

    public Game build(GameType gameType) throws GameException {
        if (maze == null) {
            throw new GameException("Maze is not set");
        }
        if (maze.getStonesCount() > maze.getWidth() * maze.getHeight() - 2) {
            System.out.println(maze);
            throw new GameException("Can not build game. Too many stones");
        }

        while (start == null) {
            Cell cell1;
            Cell cell2;
            switch (gameType) {
                case RANDOM_TO_RANDOM: {
                    cell1 = maze.getRandomCell();
                    cell2 = maze.getRandomCell();
                    break;
                }
                case RANDOM_TO_EDGE: {
                    if (!isMazeHasAvailableEdgeCells(true)) {
                        System.out.println(maze);
                        throw new GameException("Can not build game. There are no place for target on the edge");
                    }
                    cell1 = maze.getRandomCell();
                    cell2 = maze.getRandomEdgeCell();
                    break;
                }
                case RANDOM_TO_CORNER: {
                    if (!isMazeHasAvailableCornerCells(true)) {
                        System.out.println(maze);
                        throw new GameException("Can not build game. There are no place for target in the corner");
                    }
                    cell1 = maze.getRandomCell();
                    cell2 = maze.getRandomCornerCell();
                    break;
                }
                case EDGE_TO_RANDOM: {
                    if (!isMazeHasAvailableEdgeCells(true)) {
                        System.out.println(maze);
                        throw new GameException("Can not build game. There are no place for start on the edge");
                    }
                    cell1 = maze.getRandomEdgeCell();
                    cell2 = maze.getRandomCell();
                    break;
                }
                case EDGE_TO_EDGE: {
                    if (!isMazeHasAvailableEdgeCells(false)) {
                        System.out.println(maze);
                        throw new GameException("Can not build game. There are no places for start and target on the edge");
                    }
                    cell1 = maze.getRandomEdgeCell();
                    cell2 = maze.getRandomEdgeCell();
                    break;
                }
                case EDGE_TO_CORNER: {
                    if (!isMazeHasAvailableEdgeCells(true)) {
                        System.out.println(maze);
                        throw new GameException("Can not build game. There are no place for start on the edge");
                    }
                    if (!isMazeHasAvailableCornerCells(true)) {
                        System.out.println(maze);
                        throw new GameException("Can not build game. There are no place for target in the corner");
                    }
                    cell1 = maze.getRandomEdgeCell();
                    cell2 = maze.getRandomCornerCell();
                    break;
                }
                case CORNER_TO_RANDOM: {
                    if (!isMazeHasAvailableCornerCells(true)) {
                        System.out.println(maze);
                        throw new GameException("Can not build game. There are no place for start in the corner");
                    }
                    cell1 = maze.getRandomCornerCell();
                    cell2 = maze.getRandomCell();
                    break;
                }
                case CORNER_TO_EDGE: {
                    if (!isMazeHasAvailableCornerCells(true)) {
                        System.out.println(maze);
                        throw new GameException("Can not build game. There are no place for start in the corner");
                    }
                    if (!isMazeHasAvailableEdgeCells(true)) {
                        System.out.println(maze);
                        throw new GameException("Can not build game. There are no place for target on the edge");
                    }
                    cell1 = maze.getRandomCornerCell();
                    cell2 = maze.getRandomEdgeCell();
                    break;
                }
                case CORNER_TO_CORNER: {
                    if (!isMazeHasAvailableCornerCells(false)) {
                        System.out.println(maze);
                        throw new GameException("Can not build game. There are no places for start and target in the corners");
                    }
                    cell1 = maze.getRandomCornerCell();
                    cell2 = maze.getRandomCornerCell();
                    break;
                }
                default: throw new UnsupportedOperationException();
            }

            if (cell1.equals(cell2)) continue;
            if (maze.getAvailableCells()[cell1.getX()][cell1.getY()]
                    && maze.getAvailableCells()[cell2.getX()][cell2.getY()]) {
                start = cell1;
                target = cell2;
            }
        }
        return new Game(maze, start, target);
    }

    private boolean isMazeHasAvailableEdgeCells(boolean onlyOne) {
        boolean firstFound = onlyOne;
        for (int i = 0; i < maze.getWidth(); i++) {
            if (maze.getAvailableCells()[i][0]) {
                if (firstFound) {
                    return true;
                } else {
                    firstFound = true;
                }
            }
        }
        for (int i = 0; i < maze.getWidth(); i++) {
            if (maze.getAvailableCells()[i][maze.getHeight() - 1]) {
                if (firstFound) {
                    return true;
                } else {
                    firstFound = true;
                }
            }
        }
        for (int i = 0; i < maze.getHeight(); i++) {
            if (maze.getAvailableCells()[0][i]) {
                if (firstFound) {
                    return true;
                } else {
                    firstFound = true;
                }
            }
        }
        for (int i = 0; i < maze.getHeight(); i++) {
            if (maze.getAvailableCells()[maze.getWidth() - 1][i]) {
                if (firstFound) {
                    return true;
                } else {
                    firstFound = true;
                }
            }
        }
        return false;
    }

    private boolean isMazeHasAvailableCornerCells(boolean onlyOne) {
        boolean firstFound = onlyOne;
        if (maze.getAvailableCells()[0][0]) {
            firstFound = true;
        }
        if (maze.getAvailableCells()[0][maze.getHeight() - 1]) {
            if (firstFound) {
                return true;
            } else {
                firstFound = true;
            }
        }
        if (maze.getAvailableCells()[maze.getWidth() - 1][0]) {
            if (firstFound) {
                return true;
            } else {
                firstFound = true;
            }
        }
        if (maze.getAvailableCells()[maze.getWidth() - 1][maze.getHeight() - 1] && firstFound) {
            return true;
        }
        return false;
    }
}
