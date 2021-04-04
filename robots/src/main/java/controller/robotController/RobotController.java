package controller.robotController;

import model.robotModel.RobotLogic;
import view.robotView.RobotVisualizer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class RobotController {
    private Timer timer;
    private RobotVisualizer robotVisualizer;
    private RobotLogic robotLogic;

    public RobotController(RobotLogic robotLogic) {
        this.robotLogic = robotLogic;
    }

    public void setRobotVisualizer(RobotVisualizer robotVisualizer) {
        this.robotVisualizer = robotVisualizer;
    }

    public void initEventTimer() {
        timer = new Timer("events generator", true);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                robotVisualizer.onRedrawEvent();
            }
        }, 0, 50);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                robotLogic.onModelUpdateEvent();
            }
        }, 0, 10);

        robotVisualizer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                robotLogic.setTargetPosition(e.getPoint());
                robotVisualizer.repaint();
            }
        });
        robotVisualizer.setDoubleBuffered(true);

    }
}
