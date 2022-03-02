package object;

import minaGame.Handler;

import java.awt.*;
import java.util.Random;

public class ET extends GameObject{

    private final int[] randomET  = {50, 65, 90, 95};
    private int random;
    private Handler handler;
    private static int destroy;

    public ET(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        destroy = 0;

        random = getRandom(randomET);
    }

    @Override
    public void tick() {

        for (int i = 0; i < handler.object.size(); i++ ){
            GameObject tempObject = handler.object.get(i);

            if (getBounds().intersects(tempObject.getBounds())) {
                if (tempObject.getId() == ID.Bullet) {
                    handler.removeObject(tempObject);
                }

                if ((tempObject.getId() == ID.player)){
                    if (Robot.hp < 100) {
                        random -= 5;
                        Robot.hp += 5;
                    }
                    if (random <= 0){
                        handler.removeObject(this);
                        destroy++;
                    }
                }
            }
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(x, y, 32, 32);

        g.setColor(Color.white);
        g.drawString(String.valueOf(random), x + 10, y + 20);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }

    public int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
}
