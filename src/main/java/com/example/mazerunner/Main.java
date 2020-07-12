package com.example.mazerunner;

import com.example.mazerunner.excptions.GameException;
import com.example.mazerunner.excptions.MazeException;
import com.example.mazerunner.models.Cell;
import com.example.mazerunner.models.enums.GameType;
import com.example.mazerunner.models.game.Game;
import com.example.mazerunner.models.maze.Maze;
import com.example.mazerunner.utils.ColorPrinter;
import org.fusesource.jansi.AnsiConsole;

import java.util.Deque;

public class Main {

    public static void main(String[] args) throws MazeException {
        int width = 52;
        int height = 36;
        int stonesCount = 500;
        if (args.length != 0) {
            if (args.length == 3) {
                width = Integer.parseInt(args[0]);
                height = Integer.parseInt(args[1]);
                stonesCount = Integer.parseInt(args[2]);
            } else {
                System.out.println("Wrong parameters. Please use integers {width} {height} {stones} or nothing");
            }
        }

        AnsiConsole.systemInstall();

        Maze maze = Maze.builder()
                .width(width)
                .height(height)
                .stones(stonesCount)
                .build();

        for (GameType gameType : GameType.values()) {
            System.out.println();
            ColorPrinter.printGreenTextLine(gameType.toString());
            Game game;
            try {
                game = Game.builder()
                        .maze(maze)
                        .build(gameType);
            } catch (GameException ex) {
                ColorPrinter.printRedTextLine(ex.getMessage());
                continue;
            }

            PathFinder pathFinder = new PathFinder(maze);
            Deque<Cell> path = pathFinder.getRoute(game.getStartPosition(), game.getTargetPosition());
            if (path.isEmpty()) {
                System.out.println(game);
                ColorPrinter.printRedTextLine("There is no way to target");
                System.out.println();
            } else {
                System.out.println(game.toString(path));
            }
        }
    }
}
