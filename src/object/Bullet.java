package object;

import mainGame.Handler;

import java.awt.*;

public class Bullet extends GameObject implements Runnable{

    private Robot robot;
    private Enemy enemy;
    private Handler handler;
    private String username;

    public Bullet(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        if (id.equals(ID.BulletRobot)) {

            // set bullet
            if (Robot.direct.equals("up")) velX = -40;
            else if (Robot.direct.equals("down")) velX = 40;
            else if (Robot.direct.equals("right")) velY = 40;
            else if (Robot.direct.equals("left")) velY = -40;
        }

        else if (id.equals(ID.BulletEnemy)){
            if (Enemy.direct.equals("up")) velX = -40;
            else if (Enemy.direct.equals("down")) velX = 40;
            else if (Enemy.direct.equals("right")) velY = 40;
            else if (Enemy.direct.equals("left")) velY = -40;
        }
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

        g.setColor(Color.ORANGE);
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
