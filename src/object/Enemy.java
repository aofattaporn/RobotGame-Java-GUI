package object;

import controller.BufferImagesLoader;
import minaGame.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends GameObject{

    private BufferedImage robotUP, rotbotDown, robotLeft, robotRight, image;
    public static String direct;
    private BufferImagesLoader loader;
    private Handler handler;
    private String enemyName;

    public Enemy(int x, int y, ID id, Handler handler,String enemyName, BufferImagesLoader loader) {
        super(x, y, id);
        this.handler = handler;

        // loader image
        robotUP = loader.loadImage("/res/up.png");
        rotbotDown = loader.loadImage("/res/down.png");
        robotLeft = loader.loadImage("/res/left.png");
        robotRight = loader.loadImage("/res/righ.png");

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

    }

    @Override
    public void render(Graphics g) {

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
}
