package com.example.mazerunner.utils;

import lombok.AllArgsConstructor;
import org.fusesource.jansi.Ansi;

@AllArgsConstructor
public class ColorPrinter {

    private final String text;
    private final Ansi.Color textColor;
    private final Ansi.Color backgroundColor;

    @Override
    public String toString() {
        return Ansi.ansi().bg(backgroundColor).fg(textColor).a(text).reset().toString();
    }

    public static void printRedTextLine(String text) {
        System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a(text).reset());
    }

    public static void printRedText(String text) {
        System.out.print(Ansi.ansi().fg(Ansi.Color.RED).a(text).reset());
    }

    public static void printGreenTextLine(String text) {
        System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a(text).reset());
    }

    public static void printGreenText(String text) {
        System.out.print(Ansi.ansi().fg(Ansi.Color.GREEN).a(text).reset());
    }

    public static void printBlueTextLine(String text) {
        System.out.println(Ansi.ansi().fg(Ansi.Color.BLUE).a(text).reset());
    }

    public static void printBlueText(String text) {
        System.out.print(Ansi.ansi().fg(Ansi.Color.BLUE).a(text).reset());
    }
}
