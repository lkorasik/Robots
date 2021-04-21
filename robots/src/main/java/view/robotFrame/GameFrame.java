package view.robotFrame;

import controller.robotController.RobotController;
import model.Constants;
import model.robotModel.RobotLogic;
import view.frameClosing.InternalFrameClosing;
//import view.exitDialoBuilder.ExitDialogBuilder;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends InternalFrameClosing {
    private final RobotVisualizer robotVisualizer;

    public GameFrame(RobotController robotController, RobotLogic robotLogic) {
        super(Constants.GameWindow.WINDOW_TITLE, true, true, true, true);
        robotVisualizer = new RobotVisualizer(robotController, robotLogic);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(robotVisualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();

        setCloseDialog();
    }

    private void setCloseDialog() {
        setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
    }
}
