package com.yapirokue.logger;

import com.yapirokue.config.Configuration;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static PrintWriter sharedWriter;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String className;
    private static final Configuration configuration = Configuration.getInstance();

    private Logger(String className) {
        this.className = className;
    }

    public static Logger getLogger(Class<?> cls) {
        synchronized (Logger.class) {
            try {
                if (sharedWriter == null) initSharedWriter();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new Logger(cls.getSimpleName());
    }

    private static void initSharedWriter() throws IOException {
        try {
            sharedWriter = new PrintWriter(new FileWriter((String )configuration.get("LOG_PATH"), true), true);
        } catch (IOException e) {
            throw new IOException("Failed to initialize shared log writer: " + e.getMessage());
        }
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    private void log(LogLevel level, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logMessage = String.format("[%s] [%s] [%s] %s", timestamp, level, className, message);

        synchronized (Logger.class) {
            if (sharedWriter != null) {
                sharedWriter.println(logMessage);
            }
            else {
                throw new RuntimeException("Shared log writer is null");
            }
        }
    }

    public static synchronized void close() {
        if (sharedWriter != null) {
            sharedWriter.close();
            sharedWriter = null;
        }
    }
}