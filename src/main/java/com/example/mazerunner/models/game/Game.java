package com.example.mazerunner.models.game;

import com.example.mazerunner.models.Cell;
import com.example.mazerunner.models.maze.Maze;
import lombok.Getter;
import org.fusesource.jansi.Ansi;

import java.util.Deque;

@Getter
public class Game {

    private final Maze maze;

    private final Cell startPosition;
    private final Cell targetPosition;
    private final Cell currentPosition;

    Game(Maze maze, Cell startPosition, Cell targetPosition) {
        this.maze = maze;
        this.startPosition = startPosition;
        this.targetPosition = targetPosition;
        this.currentPosition = startPosition;
    }

    @Override
    public String toString() {
        return toString(null);
    }

    public String toString(Deque<Cell> path) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < maze.getHeight(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                if (currentPosition.getX() == j
                        && currentPosition.getY() == i) {
                    stringBuilder.append(Ansi.ansi().fg(Ansi.Color.RED).a("[*]").reset());
                } else if (startPosition.getX() == j
                        && startPosition.getY() == i) {
                    stringBuilder.append(Ansi.ansi().fg(Ansi.Color.BLUE).a("[S]").reset());
                } else if (targetPosition.getX() == j
                        && targetPosition.getY() == i) {
                    stringBuilder.append(Ansi.ansi().fg(Ansi.Color.GREEN).a("[F]").reset());
                } else if (path != null && path.contains(new Cell(j, i))) {
                    stringBuilder.append(Ansi.ansi().bg(Ansi.Color.WHITE).a("[-]").reset());
                } else  {
                    stringBuilder.append(maze.getAvailableCells()[j][i] ? "[ ]" : "[X]");
                }
            }
            stringBuilder.append(System.getProperty("line.separator"));
        }
        return stringBuilder.toString();
    }

    public static GameBuilder builder() {
        return new GameBuilder();
    }
}
