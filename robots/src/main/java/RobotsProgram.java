import controller.enemyController.EnemyController;
import controller.robotController.RobotController;
import model.robotsModels.PlayerRobotModel.PlayerRobotLogic;
import translation.LanguageBundle;
import translation.Locales;
import view.MainAppFrame;

import javax.swing.*;
import java.awt.*;

public class RobotsProgram {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LanguageBundle.create(Locales.EN);

        SwingUtilities.invokeLater(() -> {
            PlayerRobotLogic robotLogic = new PlayerRobotLogic();
            EnemyController enemyController = new EnemyController(30, robotLogic);
            MainAppFrame mainAppFrame = new MainAppFrame(new RobotController(robotLogic), robotLogic, enemyController);
            mainAppFrame.pack();
            mainAppFrame.setVisible(true);
            mainAppFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}
