package view.robotFrame;

import controller.enemyController.EnemyController;
import controller.playerController.PlayerController;
import model.Constants;
import model.robotsModels.playerModel.PlayerLogic;
import view.frameClosing.InternalFrameClosing;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends InternalFrameClosing {
    private final RobotVisualizer robotVisualizer;

    public GameFrame(PlayerController robotController, PlayerLogic robotLogic, EnemyController enemyController) {
        super(Constants.GameWindow.WINDOW_TITLE, true, true, true, true);
        robotVisualizer = new RobotVisualizer(robotController, robotLogic, enemyController);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(robotVisualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();

        setCloseDialog();
    }

    public RobotVisualizer getView() {
        return robotVisualizer;
    }

    private void setCloseDialog() {
        setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
    }
}
