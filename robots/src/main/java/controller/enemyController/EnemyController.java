package controller.enemyController;

import model.robotsModels.enemyModel.EnemyLogic;
import model.robotsModels.playerModel.PlayerLogic;
import view.robotFrame.RobotVisualizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class EnemyController {

    private ScheduledExecutorService threadPoolEnemies;
    private RobotVisualizer robotVisualizer;
    private PlayerLogic robotLogic;

    private List<EnemyLogic> listEnemyLogic;

    private int numEnemies;

    public void setRobotVisualizer(RobotVisualizer robotVisualizer) {
        this.robotVisualizer = robotVisualizer;
        robotVisualizer.setListEnemies(listEnemyLogic);
    }

    public EnemyController(int numEnemies, PlayerLogic robotLogic) {
        this.robotLogic = robotLogic;
        this.numEnemies = numEnemies;
        listEnemyLogic = getFilledListEnemy();

        threadPoolEnemies =  Executors.newScheduledThreadPool(numEnemies / 5);
    }

    public List<EnemyLogic> getFilledListEnemy(){
        listEnemyLogic = Collections.synchronizedList(new ArrayList<>(numEnemies));
        for (int i = 0; i < numEnemies; i++) {
            listEnemyLogic.add(new EnemyLogic());
        }
        return  listEnemyLogic;
    }

    public void startEnemyLogicThread() {
        for (int i = 0; i < numEnemies; i++) {
            var worker = new WorkerLogicEnemy(listEnemyLogic, listEnemyLogic.get(i), robotVisualizer, robotLogic, i);
            var future = threadPoolEnemies.scheduleWithFixedDelay(worker, 1, 10, TimeUnit.MILLISECONDS);
            worker.setFuture(future);
        }
    }

    public void stopPoolExecutor() {
        threadPoolEnemies.shutdown();
    }
}
