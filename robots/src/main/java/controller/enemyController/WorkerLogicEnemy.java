package controller.enemyController;

import model.robotsModels.enemyModel.EnemyLogic;
import model.robotsModels.PlayerRobotModel.PlayerRobotLogic;
import view.robotFrame.RobotVisualizer;

import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class WorkerLogicEnemy implements Runnable{
//    private final EnemyLogic enemyLogic;
    private final AtomicBoolean isStoppedWorker;
//    private final Point sizeRobotVisualizer;
    private final PlayerRobotLogic robotLogic;
    private RobotVisualizer robotVisualizer;
    private List<EnemyLogic> syncQueueEnemies;

    private DelayQueue<DelayedObject> delayQueue;


    public WorkerLogicEnemy(List<EnemyLogic> syncQueueEnemies, DelayQueue<DelayedObject> delayQueue,  RobotVisualizer robotVisualizer, PlayerRobotLogic robotLogic){
        this.delayQueue = delayQueue;
        this.robotLogic = robotLogic;
        this.syncQueueEnemies = syncQueueEnemies;
//        this.enemyLogic = enemyLogic;
        isStoppedWorker = new AtomicBoolean(false);
        this.robotVisualizer = robotVisualizer;
    }

    @Override
    public void run() {
        try {
            var delayObject = delayQueue.take();
            var enemyLogic = delayObject.getEnemyLogic();
            enemyLogic.updatePosition(robotVisualizer.getWidth(), robotVisualizer.getHeight(), robotLogic.getRobotPosition());
            delayQueue.put(delayObject);
            syncQueueEnemies.add(delayObject.getEnemyLogic());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopWorker(){
        isStoppedWorker.set(false);
    }
}
