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
    private DelayQueue<DelayedObject> delayQueueEnemies;
    private List<EnemyLogic> syncQueueEnemies;

    private int numEnemies;

//    private List<EnemyLogic> listEnemies;

    public void setRobotVisualizer(RobotVisualizer robotVisualizer) {
        this.robotVisualizer = robotVisualizer;
        sizeRobotVisualizer = new Point(robotVisualizer.getWidth(), robotVisualizer.getHeight());
        robotVisualizer.setListEnemies(syncQueueEnemies);
    }

    public EnemyController(int numEnemies, PlayerRobotLogic robotLogic) {
        this.robotLogic = robotLogic;
        syncQueueEnemies = Collections.synchronizedList(new ArrayList<>(numEnemies / 5));
        delayQueueEnemies = fillListsEnemies(numEnemies);
        threadPoolEnemies = (ThreadPoolExecutor) Executors.newFixedThreadPool(numEnemies / 5);

        this.numEnemies = numEnemies;
    }

    private DelayQueue<DelayedObject> fillListsEnemies(int numEnemies) {
        delayQueueEnemies = new DelayQueue<>();
//        syncQueueEnemies =  Collections.synchronizedList(new ArrayList<>(numEnemies/5));
        for (int i = 0; i < numEnemies; i++) {
            var enemyLogic = new EnemyLogic();
            delayQueueEnemies.put(new DelayedObject(enemyLogic, 1));
//            syncQueueEnemies.add(enemyLogic);
        }
        return delayQueueEnemies;

    }

    public void startEnemyLogicThread() {
        for (int i = 0; i < delayQueueEnemies.size() / 5; i++) {
            threadPoolEnemies.execute(new WorkerLogicEnemy(syncQueueEnemies, delayQueueEnemies, robotVisualizer, robotLogic));
        }

    }


    public void stopPoolExecutor() {
        threadPoolEnemies.shutdown();
    }
}
