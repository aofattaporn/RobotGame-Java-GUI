package object;

import controller.BufferImagesLoader;
import controller.Injury;
import mainGame.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends GameObject{

    public static BufferedImage robotUP, rotbotDown, robotLeft, robotRight, image;
    public static String direct;
    private BufferImagesLoader loader;
    private Handler handler;
    private String enemyName;
    public static int hp = 100;

    public Enemy(int x, int y, ID id, Handler handler,String enemyName, BufferImagesLoader loader) {
        super(x, y, id);
        this.handler = handler;

        // loader image


        // default image
        image = rotbotDown;
        direct = "down";
    }

    @Override
    public void tick() {

        if (direct.equals("down")) image = rotbotDown;

        else if (direct.equals("up")) image = robotUP;

        else if (direct.equals("left")) image = robotLeft;

        else if (direct.equals("right")) image = robotRight;

        // manage function Bomb
        for (int i = 0; i < handler.object.size(); i++ ){
            GameObject tempObject = handler.object.get(i);

              if (getBounds().intersects(tempObject.getBounds())) {
                if (tempObject.getId() == ID.Bomb) {
                    Enemy.hp -= 5;
                    handler.removeObject(tempObject);
                }

                if (tempObject.getId() == ID.BulletRobot && Injury.check) {
                    Enemy.hp -= 20;
                    Injury.setCheck(false);
                }
            }
        }

    }

    @Override
    public void render(Graphics g) {
        createHP(g);

        g.drawImage(image, x, y, 40, 40, null);

        if (enemyName != null){
            g.drawString(enemyName, x + 10, y + 10);
        }

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    private void createHP(Graphics g){

        // create enemy HP

        g.setColor(Color.gray);
        g.fillRect(x, y - 20, 40, 10);

        // set color
        if (Enemy.hp < 40) g.setColor(Color.red);
        else g.setColor(Color.green);

        g.fillRect(x, y - 20, (int) ( Enemy.hp * 0.4), 10);
        g.setColor(Color.white);

    }
}
