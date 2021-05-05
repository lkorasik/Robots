package view.robotFrame;

import controller.robotController.RobotController;
import lombok.SneakyThrows;
import model.robotModel.RobotLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class RobotVisualizer extends JPanel {
    private final RobotController robotController;
    private final RobotLogic robotLogic;

    private final AtomicInteger mouseX = new AtomicInteger();
    private final AtomicInteger mouseY = new AtomicInteger();

    private final AtomicBoolean isUp = new AtomicBoolean(false);
    private final AtomicBoolean isDown = new AtomicBoolean(false);
    private final AtomicBoolean isRight = new AtomicBoolean(false);
    private final AtomicBoolean isLeft = new AtomicBoolean(false);

    private static final int DX = 2;
    private static final int DY = 2;

    public RobotVisualizer(RobotController robotController, RobotLogic robotLogic) {
        this.robotController = robotController;
        this.robotLogic = robotLogic;

        this.robotController.setRobotVisualizer(this);
        this.robotController.initEventTimer();

        setFocusable(true);

        startLiveUpdatePlayersState();

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseMoveAction(e);
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyPressAction(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keyReleaseAction(e);
            }
        });
    }

    private void mouseMoveAction(MouseEvent event) {
        mouseX.set(event.getX());
        mouseY.set(event.getY());

        robotController.setSeePosition(new Point(mouseX.get(), mouseY.get()));
    }

    private void keyReleaseAction(KeyEvent event) {
        var code = event.getExtendedKeyCode();

        if (code == KeyEvent.VK_RIGHT) {
            isRight.compareAndSet(true, false);
        } else if (code == KeyEvent.VK_LEFT) {
            isLeft.compareAndSet(true, false);
        } else if (code == KeyEvent.VK_DOWN) {
            isDown.compareAndSet(true, false);
        } else if (code == KeyEvent.VK_UP) {
            isUp.compareAndSet(true, false);
        }
    }

    private void keyPressAction(KeyEvent event) {
        var code = event.getExtendedKeyCode();

        if (code == KeyEvent.VK_RIGHT) {
            isRight.compareAndSet(false, true);
        } else if (code == KeyEvent.VK_LEFT) {
            isLeft.compareAndSet(false, true);
        } else if (code == KeyEvent.VK_DOWN) {
            isDown.compareAndSet(false, true);
        } else if (code == KeyEvent.VK_UP) {
            isUp.compareAndSet(false, true);
        }
    }

    private void startLiveUpdatePlayersState() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isDown.get() && isRight.get())
                        movePlayer(DX, DY, mouseX.get(), mouseY.get());
                    else if (isDown.get() && isLeft.get())
                        movePlayer(-DX, DY, mouseX.get(), mouseY.get());
                    else if (isUp.get() && isRight.get())
                        movePlayer(DX, -DY, mouseX.get(), mouseY.get());
                    else if (isUp.get() && isLeft.get())
                        movePlayer(-DX, -DY, mouseX.get(), mouseY.get());
                    else if (isDown.get())
                        movePlayer(0, DY, mouseX.get(), mouseY.get());
                    else if (isUp.get())
                        movePlayer(0, -DY, mouseX.get(), mouseY.get());
                    else if (isRight.get())
                        movePlayer(DX, 0, mouseX.get(), mouseY.get());
                    else if (isLeft.get())
                        movePlayer(-DX, 0, mouseX.get(), mouseY.get());
                }
            }
        }.start();
    }

    private void movePlayer(int dx, int dy, int mouseX, int mouseY) {
        var position = robotController.getRobotPosition();
        position = new Point(position.x + dx, position.y + dy);
        robotController.setTargetPosition(position, new Point(mouseX, mouseY));
    }

    private static void fillOval(Graphics graphics, int centerX, int centerY, int diam1, int diam2) {
        graphics.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics graphics, int centerX, int centerY, int diam1, int diam2) {
        graphics.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    public void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    public void drawRobot(Graphics2D graphics2D, int x, int y, double direction) {
        AffineTransform t = AffineTransform.getRotateInstance(direction, x, y);
        graphics2D.setTransform(t);
        graphics2D.setColor(Color.MAGENTA);
        fillOval(graphics2D, x, y, 30, 10);
        graphics2D.setColor(Color.BLACK);
        drawOval(graphics2D, x, y, 30, 10);
        graphics2D.setColor(Color.WHITE);
        fillOval(graphics2D, x + 10, y, 5, 5);
        graphics2D.setColor(Color.BLACK);
        drawOval(graphics2D, x + 10, y, 5, 5);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g2d = (Graphics2D) graphics;
        drawRobot(g2d,
                RobotLogic.round(robotLogic.getPositionX()),
                RobotLogic.round(robotLogic.getPositionY()),
               robotLogic.getRobotDirection());
    }
}
