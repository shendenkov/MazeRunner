package com.example.mazerunner.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PathDirection {

    UP(0),
    RIGHT(1),
    DOWN(2),
    LEFT(3);

    private final int code;

    public static PathDirection parse(int code) {
        switch (code) {
            case 0: {
                return UP;
            }
            case 1: {
                return RIGHT;
            }
            case 2: {
                return DOWN;
            }
            case 3: {
                return LEFT;
            }
            default: throw new UnsupportedOperationException();
        }
    }
}
