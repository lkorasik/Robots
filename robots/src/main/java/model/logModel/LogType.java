package model.logModel;

import lombok.Getter;

public enum LogType {
    Trace(0),
    Debug(1),
    Info(2),
    Warning(3),
    Error(4),
    Fatal(5);

    @Getter
    private final int type;

    LogType(int type) {       // конструктор для энума? -> зачем? и почему он private
        this.type = type;
    }
}

