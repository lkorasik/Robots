package view.logFrame;

import controller.logController.LogWindowSource;
import model.Constants;
import model.logModel.LogChangeListener;
import model.logModel.LogEntry;
import view.frameClosing.InternalFrameClosing;

import javax.swing.*;
import java.awt.*;

public class LogFrame extends InternalFrameClosing implements LogChangeListener {
    private final LogWindowSource windowSource;
    private final TextArea logContent;

    public LogFrame(LogWindowSource windowSource) {
        super(Constants.LogWindow.WINDOW_TITLE, true, true, true, true);
        this.windowSource = windowSource;
        this.windowSource.registerListener(this);

        logContent = new TextArea("");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();

        setExitDialog();
    }

    private void setExitDialog() {
        setActionExiting(() -> {
            if (windowSource != null)
                windowSource.unregisterListener(this);
        });
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : windowSource.getLogEntries()) {
            content.append(entry.getMessage()).append("\n");
        }
        logContent.setText(content.toString());
        logContent.invalidate();
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }
}
