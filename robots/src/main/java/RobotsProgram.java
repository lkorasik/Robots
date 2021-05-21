import controller.enemyController.EnemyController;
import controller.playerController.PlayerController;
import model.robotsModels.playerModel.PlayerLogic;
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
            PlayerLogic robotLogic = new PlayerLogic();
            EnemyController enemyController = new EnemyController(50, robotLogic);
            MainAppFrame mainAppFrame = new MainAppFrame(new PlayerController(robotLogic), robotLogic, enemyController);
            mainAppFrame.pack();
            mainAppFrame.setVisible(true);
            mainAppFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}
