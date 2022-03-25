package object;

import entity.ElementPosition;
import mainGame.Handler;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Bomb extends GameObject{

    Handler handler;
    ArrayList<ElementPosition> ABombP;
    BufferedWriter bufferedWriter;

    public Bomb(int x, int y, ID id, Handler handler, ArrayList<ElementPosition> ABombP, BufferedWriter bufferedWriter) {
        super(x, y, id);
        this.handler = handler;
        this.ABombP = ABombP;
        this.bufferedWriter = bufferedWriter;
    }

    @Override
    synchronized public void tick() {

        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if (getBounds().intersects(tempObject.getBounds())) {
                if (tempObject.getId() == ID.Robot) {
                    Robot.hp -= 5;
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
