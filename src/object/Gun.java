package object;

import java.awt.*;

public class Gun extends GameObject implements Runnable{

    private Robot robot;
    public Gun(int x, int y, ID id, Robot robot) {
        super(x, y, id);

        // set bullet

        if (robot.getDirect().equals("up")) velX = -20;
        else if (robot.getDirect().equals("down")) velX = 20;
        else if (robot.getDirect().equals("right")) velY = 20;
        else if (robot.getDirect().equals("left")) velY = -20;

        this.robot = robot;
    }

    @Override
    public void tick() {

        x += velY;
        y += velX;
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
