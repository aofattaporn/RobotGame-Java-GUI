package object;

import controller.Injury;
import mainGame.Handler;

import java.awt.*;

public class Bullet extends GameObject implements Runnable{

    private Robot robot;
    private Enemy enemy;
    private Handler handler;
    private String username;
    private Injury injury;

    public Bullet(int x, int y, ID id, Handler handler) {


         super(x , y, id);

        if (Robot.direct.equals("up")) x = -10;
        else if (Robot.direct.equals("down")) x = 10;
        else if (Robot.direct.equals("right")) y = 10;
        else if (Robot.direct.equals("left")) y = -10;

        this.handler = handler;

        if (id.equals(ID.BulletRobot)) {

            // set bullet
            if (Robot.direct.equals("up")) velX = -10;
            else if (Robot.direct.equals("down")) velX = 10;
            else if (Robot.direct.equals("right")) velY = 10;
            else if (Robot.direct.equals("left")) velY = -10;

        }

        else if (id.equals(ID.BulletEnemy)){
            if (Enemy.direct.equals("up")) velX = -10;
            else if (Enemy.direct.equals("down")) velX = 10;
            else if (Enemy.direct.equals("right")) velY = 10;
            else if (Enemy.direct.equals("left")) velY = -10;
        }
    }


    @Override
    synchronized public void tick() {

        x += velY;
        y += velX;

        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if (getBounds().intersects(tempObject.getBounds())) {


                 if (tempObject.getId() == ID.Bomb){
                    handler.removeObject(this);
                    handler.removeObject(tempObject);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {

        if (id.equals(ID.BulletEnemy)) g.setColor(Color.ORANGE);
        else if (id.equals(ID.BulletRobot)) g.setColor(Color.GREEN);

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
