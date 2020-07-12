package com.example.mazerunner.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Random;

@Data
@AllArgsConstructor
public class Cell {

    private static Random random;

    private int x;
    private int y;

    public static Cell getRandomCell(int width, int height) {
        return new Cell(getRandomInstance().nextInt(width), getRandomInstance().nextInt(height));
    }

    private static Random getRandomInstance() {
        if (random == null) {
            random = new Random();
            random.nextInt();
        }
        return random;
    }
}
