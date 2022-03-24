package object;

import minaGame.Game;
import minaGame.Handler;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;

public class BulletEnemy extends GameObject implements Runnable{

    private Robot robot;
    private Enemy enemy;
    private Handler handler;
    private BufferedWriter bufferedWriter;

    public BulletEnemy(int x, int y, ID id, Enemy enemy, Handler handler, BufferedWriter bufferedWriter) {
        super(x, y, id);
        this.handler = handler;
        this.bufferedWriter = bufferedWriter;

        // set bullet

        if (enemy.getDirect().equals("up")) velX = -40;
        else if (enemy.getDirect().equals("down")) velX = 40;
        else if (enemy.getDirect().equals("right")) velY = 40;
        else if (enemy.getDirect().equals("left")) velY = -40;

        this.enemy = enemy;
    }


    @Override
    public void tick() {

        x += velY;
        y += velX;

        if (x > Game.BOX_SIZE * (100+ 4) ) handler.object.remove(this);

        if (x < 0 ) handler.object.remove(this);

        if (y > Game.BOX_SIZE * (80 + 4)) handler.object.remove(this);

        if (y < 0 ) handler.object.remove(this);

    }

    @Override
    public void render(Graphics g) {

        sendMSG("shoot : " + x + " " + y);
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

    public void sendMSG(String msg) {
        try {
            bufferedWriter.write(String.valueOf(msg));
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
