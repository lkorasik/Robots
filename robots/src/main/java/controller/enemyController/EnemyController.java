package controller.enemyController;

import model.robotsModels.enemyModel.EnemyLogic;
import model.robotsModels.PlayerRobotModel.PlayerRobotLogic;
import view.robotFrame.RobotVisualizer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class EnemyController {

    private ThreadPoolExecutor threadPoolEnemies;
    private RobotVisualizer robotVisualizer;
    private PlayerRobotLogic robotLogic;
    private Point sizeRobotVisualizer;

    private int numEnemies;

    private List<EnemyLogic> listEnemies;

    public void setRobotVisualizer(RobotVisualizer robotVisualizer) {
        this.robotVisualizer = robotVisualizer;
        sizeRobotVisualizer = new Point(robotVisualizer.getWidth(),robotVisualizer.getHeight() );
        robotVisualizer.setListEnemies(listEnemies);
    }

    public EnemyController(int numEnemies, PlayerRobotLogic robotLogic) {
        this.robotLogic = robotLogic;
        threadPoolEnemies = (ThreadPoolExecutor) Executors.newFixedThreadPool(numEnemies);
        listEnemies = getListEnemies(numEnemies);

        this.numEnemies = numEnemies;
    }

    private List<EnemyLogic> getListEnemies(int numEnemies){
        listEnemies = Collections.synchronizedList(new ArrayList<>(numEnemies));
        for (int i = 0; i<numEnemies; i++){
            listEnemies.add(new EnemyLogic());
        }
        return listEnemies;
    }

    public void startEnemyLogicThread() {
        for (int i = 0; i < listEnemies.size(); i++) {
            threadPoolEnemies.execute(new WorkerLogicEnemy(listEnemies.get(i), robotVisualizer, robotLogic));
        }
    }

    public void stopPoolExecutor() {
        threadPoolEnemies.shutdown();
    }
}
