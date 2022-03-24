package object;

import mainGame.Handler;

import java.awt.*;
import java.io.BufferedWriter;

public class BulletRobot extends GameObject implements Runnable{

    private Robot robot;
    private Enemy enemy;
    private Handler handler;
    private String username;
    private BufferedWriter bufferedWriter;

    public BulletRobot(int x, int y, ID id, String username, Robot robot, Handler handler, BufferedWriter bufferedWriter) {
        super(x, y, id);
        this.handler = handler;
        this.bufferedWriter = bufferedWriter;
        this.username = username;

        // set bullet

        if (robot.getDirect().equals("up")) velX = -40;
        else if (robot.getDirect().equals("down")) velX = 40;
        else if (robot.getDirect().equals("right")) velY = 40;
        else if (robot.getDirect().equals("left")) velY = -40;

        this.robot = robot;
    }

    public BulletRobot(int x, int y, ID id, Handler handler, BufferedWriter bufferedWriter) {
        super(x, y, id);
        this.handler = handler;
        this.bufferedWriter = bufferedWriter;
        this.username = username;

        // set bullet

        if (Enemy.direct.equals("up")) velX = -40;
        else if (Enemy.direct.equals("down")) velX = 40;
        else if (Enemy.direct.equals("right")) velY = 40;
        else if (Enemy.direct.equals("left")) velY = -40;

    }


    @Override
    public void tick() {

        x += velY;
        y += velX;

        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if (getBounds().intersects(tempObject.getBounds())) {
                if (tempObject.getId() == ID.Robot) {
                    Robot.hp -= 20;
                }
            }
        }

    }

    @Override
    public void render(Graphics g) {

        g.setColor(Color.GREEN);
        g.fillRect(x , y , 10, 10);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 10, 10);
    }

    @Override
    public void run() {
        tick();
    }

}
