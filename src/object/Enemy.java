package object;

import controller.BufferImagesLoader;
import minaGame.Handler;

import java.awt.*;

public class Enemy extends GameObject{

    Handler handler;

    public Enemy(int x, int y, ID id, Handler handler,String username, BufferImagesLoader loader) {
        super(x, y, id);
        this.handler = handler;
    }

    @Override
    public void tick() {

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
