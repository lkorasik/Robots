package controller.enemyController;

import com.google.common.primitives.Ints;
import model.robotsModels.enemyModel.EnemyLogic;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedObject implements Delayed {
    private String data;
    public long startTime;
    private EnemyLogic enemyLogic;

    public DelayedObject(EnemyLogic enemyLogic, long delayInMilliseconds) {
        this.startTime = System.currentTimeMillis() + delayInMilliseconds;
        this.enemyLogic = enemyLogic;
    }

    public EnemyLogic getEnemyLogic() {
        return enemyLogic;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Ints.saturatedCast(
                this.startTime - ((DelayedObject) o).startTime);
    }
}
