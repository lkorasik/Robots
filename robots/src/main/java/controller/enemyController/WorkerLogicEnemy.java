package controller.enemyController;

import model.robotsModels.enemyModel.EnemyLogic;
import model.robotsModels.playerModel.PlayerLogic;
import view.robotFrame.RobotVisualizer;

import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class WorkerLogicEnemy implements Runnable {
    private final EnemyLogic enemyLogic;
    private final AtomicBoolean isStoppedWorker;
    private final PlayerLogic robotLogic;
    private final RobotVisualizer robotVisualizer;

    private List<EnemyLogic> listEnemyLogic;
    private ScheduledFuture<?> future;
    private final int index;

    public WorkerLogicEnemy(List<EnemyLogic> listEnemyLogic, EnemyLogic enemyLogic,
                            RobotVisualizer robotVisualizer, PlayerLogic robotLogic, int index) {
        this.robotLogic = robotLogic;
        this.listEnemyLogic = listEnemyLogic;
        this.index = index;
        this.enemyLogic = enemyLogic;
        this.robotVisualizer = robotVisualizer;

        isStoppedWorker = new AtomicBoolean(false);
    }

    public void setFuture(ScheduledFuture<?> future) {
        this.future = future;
    }

    @Override
    public void run() {
        if (!isStoppedWorker.get())
            enemyLogic.updatePosition(robotVisualizer.getWidth(), robotVisualizer.getHeight(), robotLogic.getRobotPosition());
        else
            listEnemyLogic.remove(index);
    }

    public int getIndex() {
        return index;
    }

    public void stopWorker() {
        isStoppedWorker.set(false);
    }
}
