package controller.logController;

import lombok.Getter;
import model.logModel.LogType;

public final class Logger {
    @Getter
    private static final LogWindowSource defaultLogSource;

    static {
        defaultLogSource = new LogWindowSource(100);
    }

    public static void debug(String message) {
        defaultLogSource.append(LogType.Debug, message);
    }

    public static void error(String message) {
        defaultLogSource.append(LogType.Error, message);
    }
}
