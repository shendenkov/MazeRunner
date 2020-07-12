package com.example.mazerunner.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Corner {

    UP_RIGHT(0),
    DOWN_RIGHT(1),
    DOWN_LEFT(2),
    UP_LEFT(3);

    private final int code;

    public static Corner parse(int code) {
        switch (code) {
            case 0: {
                return UP_RIGHT;
            }
            case 1: {
                return DOWN_RIGHT;
            }
            case 2: {
                return DOWN_LEFT;
            }
            case 3: {
                return UP_LEFT;
            }
            default: throw new UnsupportedOperationException();
        }
    }
}
