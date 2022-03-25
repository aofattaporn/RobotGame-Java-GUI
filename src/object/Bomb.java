package object;

import entity.ElementPosition;
import mainGame.Handler;

import java.awt.*;
import java.util.ArrayList;

public class Bomb extends GameObject{

    Handler handler;
    ArrayList<ElementPosition> ABombP;

    public Bomb(int x, int y, ID id, Handler handler, ArrayList<ElementPosition> ABombP) {
        super(x, y, id);
        this.handler = handler;
        this.ABombP = ABombP;
    }

    @Override
    public void tick() {

        for (int i = 0; i < handler.object.size(); i++ ){
            GameObject tempObject = handler.object.get(i);

            if (getBounds().intersects(tempObject.getBounds())) {
                if (tempObject.getId() == ID.BulletRobot || tempObject.getId() == ID.BulletEnemy) {
                    handler.removeObject(this);
                    handler.removeObject(tempObject);

                    ABombP.removeIf(a -> a.getElemX() == getBounds().x && a.getElemY() == getBounds().y);
                    System.out.println(ABombP.size());
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.pink);
        g.fillRect(x, y, 32, 32);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }

}
