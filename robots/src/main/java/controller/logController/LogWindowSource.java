package controller.logController;

import lombok.Getter;
import model.logModel.LogChangeListener;
import model.logModel.LogEntry;
import model.logModel.LogType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Что починить:
 * 1. Этот класс порождает утечку ресурсов (связанные слушатели оказываются
 * удерживаемыми в памяти)
 * 2. Этот класс хранит активные сообщения лога, но в такой реализации он
 * их лишь накапливает. Надо же, чтобы количество сообщений в логе было ограничено
 * величиной m_iQueueLength (т.е. реально нужна очередь сообщений
 * ограниченного размера)
 */
public class LogWindowSource {
    @Getter
    private final List<LogEntry> logEntries;
    private final List<LogChangeListener> listeners;
    private volatile LogChangeListener[] activeListeners;
    private final int queueLength;

    public LogWindowSource(int queueLength) {
        this.queueLength = queueLength;
        logEntries = Collections.synchronizedList(new ArrayList<>(queueLength));
        listeners = Collections.synchronizedList(new ArrayList<>());
    }

    public void registerListener(LogChangeListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
            activeListeners = null;
        }
    }

    public void unregisterListener(LogChangeListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
            activeListeners = null;
        }
    }

    public void append(LogType logType, String message) {
        LogEntry logEntry = new LogEntry(logType, message);

        logEntries.add(logEntry);
        if (logEntries.size() == queueLength)
            logEntries.remove(0);

        LogChangeListener[] activeListeners = this.activeListeners;
        if (activeListeners == null) {
            synchronized (listeners) {
                if (this.activeListeners == null) {
                    activeListeners = listeners.toArray(new LogChangeListener[0]);
                    this.activeListeners = activeListeners;
                }
            }
        }
        for (LogChangeListener listener : activeListeners) {
            listener.onLogChanged();
        }
    }

    public Iterable<LogEntry> range(int startFrom, int count) {
        if (startFrom < 0 || startFrom >= logEntries.size()) {
            return Collections.emptyList();
        }
        int indexTo = Math.min(startFrom + count, logEntries.size());
        return logEntries.subList(startFrom, indexTo);
    }
}
