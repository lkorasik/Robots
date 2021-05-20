package controller.enemyController;

import model.robotsModels.enemyModel.EnemyLogic;
import model.robotsModels.PlayerRobotModel.PlayerRobotLogic;
import view.robotFrame.RobotVisualizer;

import java.util.concurrent.atomic.AtomicBoolean;

public class WorkerLogicEnemy implements Runnable{
    private final EnemyLogic enemyLogic;
    private final AtomicBoolean isStoppedWorker;
//    private final Point sizeRobotVisualizer;
    private final PlayerRobotLogic robotLogic;
    private RobotVisualizer robotVisualizer;


    public WorkerLogicEnemy(EnemyLogic enemyLogic, RobotVisualizer robotVisualizer, PlayerRobotLogic robotLogic){
        this.robotLogic = robotLogic;
        this.enemyLogic = enemyLogic;
        isStoppedWorker = new AtomicBoolean(false);
        this.robotVisualizer = robotVisualizer;
//        this.sizeRobotVisualizer = sizeRobotVisualizer;
    }

    @Override
    public void run() {
        while (!isStoppedWorker.get()){
            enemyLogic.updatePosition(robotVisualizer.getWidth(), robotVisualizer.getHeight(), robotLogic.getRobotPosition());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopWorker(){
        isStoppedWorker.set(false);
    }
}
