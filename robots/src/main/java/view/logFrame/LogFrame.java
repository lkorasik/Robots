package view.logFrame;

import controller.LogController.LogWindowSource;
import model.Constants;
import model.logModel.LogChangeListener;
import model.logModel.LogEntry;
import view.ExitDialogBuilder;

import javax.swing.*;
import java.awt.*;

public class LogFrame extends JInternalFrame implements LogChangeListener {
    private LogWindowSource windowSource;
    private TextArea logContent;

    public LogFrame(LogWindowSource windowSource, int x, int y, int width, int height) {
        super(Constants.LogWindow.WINDOW_TITLE, true, true, true, true);
        this.windowSource = windowSource;
        this.windowSource.registerListener(this);

        setLocation(x, y);

        logContent = new TextArea("");
        //logContent.setSize(Constants.LogWindow.WIDTH, Constants.LogWindow.HEIGHT);
        logContent.setSize(width, height);
        setSize(width, height);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();

        setExitDialog();
    }

    private void setExitDialog() {
        setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);

        addInternalFrameListener(new ExitDialogBuilder().setSource(windowSource).buildInternalFrameAdapter());
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
