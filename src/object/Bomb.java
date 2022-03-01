package object;

import minaGame.Handler;

import java.awt.*;

public class Bomb extends GameObject{

    Handler handler;

    public Bomb(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    @Override
    public void tick() {

        for (int i = 0; i < handler.object.size(); i++ ){
            GameObject tempObject = handler.object.get(i);

            if (getBounds().intersects(tempObject.getBounds())) {
                if (tempObject.getId() == ID.Bullet) {
                    handler.removeObject(this);
                    handler.removeObject(tempObject);
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
