package object;

import minaGame.Handler;

import java.awt.*;

public class Gun extends GameObject implements Runnable{

    private Robot robot;
    private Handler handler;

    public Gun(int x, int y, ID id, Robot robot, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        // set bullet

        if (robot.getDirect().equals("up")) velX = -40;
        else if (robot.getDirect().equals("down")) velX = 40;
        else if (robot.getDirect().equals("right")) velY = 40;
        else if (robot.getDirect().equals("left")) velY = -40;

        this.robot = robot;
    }

    @Override
    public void tick() {

        x += velY;
        y += velX;

        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if (getBounds().intersects(tempObject.getBounds())) {
                if (tempObject.getId() == ID.player) {
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
