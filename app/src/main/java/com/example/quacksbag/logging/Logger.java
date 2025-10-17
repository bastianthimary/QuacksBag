package com.example.quacksbag.logging;

public class Logger {

    public enum Level {
        RESULTBASE,
        RESULT,
        RESULTDEEP,
        ERROR,
        WARN,
        INFO,
        DEBUG
    }

    private static Level currentLevel = Level.RESULT;

    public static void setLevel(Level level) {
        currentLevel = level;
    }

    public static void log(Level level, String message) {
        if (level.ordinal() <= currentLevel.ordinal()) {
            System.out.println("[" + level.name() + "] " + message);
        }
    }

    public static void resultBase(String message) {
        log(Level.RESULTBASE, message);
    }

    public static void resultDeep(String message) {
        log(Level.RESULTDEEP, message);
    }

    public static void result(String message) {
        log(Level.RESULT, message);
    }

    public static void debug(String message) {
        log(Level.DEBUG, message);
    }

    public static void info(String message) {
        log(Level.INFO, message);
    }

    public static void warn(String message) {
        log(Level.WARN, message);
    }

    public static void error(String message) {
        log(Level.ERROR, message);
    }
}
