package model.logModel;

import lombok.Getter;

public class LogEntry {
    @Getter
    private LogType logType;
    @Getter
    private String message;

    public LogEntry(LogType logType, String message) {
        this.message = message;
        this.logType = logType;
    }
}

