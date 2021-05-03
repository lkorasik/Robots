package model.logModel;

import lombok.Getter;

public class LogEntry {
    @Getter
    private final LogType logType;
    @Getter
    private final String message;

    public LogEntry(LogType logType, String message) {
        this.message = message;
        this.logType = logType;
    }
}

