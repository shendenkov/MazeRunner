package com.example.mazerunner.excptions;

public class MazeException extends Exception {

    public MazeException(String message) {
        super(message);
    }

    public MazeException(String message, Throwable cause) {
        super(message, cause);
    }
}
