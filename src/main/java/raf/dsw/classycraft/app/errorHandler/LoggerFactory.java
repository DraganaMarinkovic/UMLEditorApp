package raf.dsw.classycraft.app.errorHandler;

public class LoggerFactory {

    public static FileLogger getFileLogger() {
        return new FileLogger();
    }

    public static ConsoleLogger getConsoleLogger() {
        return new ConsoleLogger();
    }
}
