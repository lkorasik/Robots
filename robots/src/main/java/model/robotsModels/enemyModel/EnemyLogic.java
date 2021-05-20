package model.robotsModels.enemyModel;


import model.Constants;
import model.robotsModels.RobotLogic;

import java.awt.geom.Point2D;

public class EnemyLogic extends RobotLogic {

    public EnemyLogic(){
        super();
        robotPosition = getRandomPos();
        targetPosition = getRandomPos();
    }

    private Point2D getRandomPos(){
        var x  = ((Math.random() * (320 - 20)) + 20);
        var y  = ((Math.random() * (320 - 20)) + 20);
        return new Point2D.Double(x,y);
    }

    private void moveRobot(double angularVelocity, int width, int height) {
        var newDirection = robotDirection + angularVelocity * Constants.Robot.DEFAULT_DURATION;

        double newX = robotPosition.getX() + Constants.Robot.MAX_VELOCITY / angularVelocity * (Math.sin(newDirection) - Math.sin(robotDirection));
        if (!Double.isFinite(newX)) {
            newX = robotPosition.getX() + Constants.Robot.MAX_VELOCITY * Constants.Robot.DEFAULT_DURATION * Math.cos(robotDirection);
        }

        double newY = robotPosition.getY() - Constants.Robot.MAX_VELOCITY / angularVelocity * (Math.cos(newDirection) - Math.cos(robotDirection));
        if (!Double.isFinite(newY)) {
            newY = robotPosition.getY() + Constants.Robot.MAX_VELOCITY * Constants.Robot.DEFAULT_DURATION * Math.sin(robotDirection);
        }

        robotPosition.setLocation(newX, newY);
        robotDirection = asNormalizedRadians(correctDirection(newDirection, width, height));
    }

    private double correctDirection(double direction, int width, int height) {
        if ((robotPosition.getX() <= 0) || (robotPosition.getY() >= height))
            return direction + Math.PI;
        else if ((robotPosition.getX() >= width) || (robotPosition.getY() <= 0))
            return direction - Math.PI;
        return direction;
    }

    public void updatePosition(int width, int height, Point2D targetPosition) {
//        System.out.println(robotPosition.getX() + " " + robotPosition.getY());
        this.targetPosition.setLocation(targetPosition.getX(), targetPosition.getY());
        double distance = distance(targetPosition.getX(), targetPosition.getY(), robotPosition.getX(), robotPosition.getY());
        if (distance >= 0.5)
            moveRobot(getAngularVelocity(), width, height);
    }
}
