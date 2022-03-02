package object;

import controller.BufferImagesLoader;
import minaGame.Game;
import minaGame.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Robot extends GameObject {

    private Handler handler;
    private BufferedImage robotUP, rotbotDown, robotLeft, robotRight, image;
    private String direct;
    private boolean state = false;
    private BufferImagesLoader loader;
    public static int hp = 100;

    public Robot(int x, int y, ID id, Handler handler, BufferImagesLoader loader) {
        super(x, y, id);
        this.handler = handler;
        this.loader = loader;

        // loader image
        robotUP = loader.loadImage("/res/up.png");
        rotbotDown = loader.loadImage("/res/down.png");
        robotLeft = loader.loadImage("/res/left.png");
        robotRight = loader.loadImage("/res/righ.png");

        // default image
        image = rotbotDown;

    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        // movement
        if (handler.isUp() && y > Game.BOX_SIZE *2 ) {
            if (image == robotUP) {
                velY = -32;
            }
            image = robotUP;
            direct = "up";

        } else if (!handler.isDown() ) {
            velY = 0;
        }

        if (handler.isDown() && y < Game.BOX_SIZE * 81) {
            if (image == rotbotDown) {
                velY = 32;
            }
            image = rotbotDown;
            direct = "down";

        } else if (!handler.isUp()) velY = 0;

        if (handler.isRight() && x < Game.BOX_SIZE * 101) {
            if (image == robotRight) {
                velX = 32;
            }
            image = robotRight;
            direct = "right";
        } else if (!handler.isLeft()) velX = 0;

        if (handler.isLeft() && x > Game.BOX_SIZE * 2) {
            if (image == robotLeft) velX = -32;
            image = robotLeft;
            direct = "left";
        } else if (!handler.isRight()) velX = 0;

        if (handler.isSpaceBar()) {
            // TODO : set bullet create object
            if (!state){
                handler.addObject(new Gun(x, y, ID.Bullet, this, handler));
                state = true;
            }else {
                state = false;
            }
        }


        for (int i = 0; i < handler.object.size(); i++ ){
            GameObject tempObject = handler.object.get(i);

            if (getBounds().intersects(tempObject.getBounds())) {
                if (tempObject.getId() == ID.Bomb) {

                    Robot.hp -= 5;
                    handler.removeObject(tempObject);

                }
            }
        }



    }

    @Override
    public void render(Graphics g) {

        g.drawImage(image, x, y, 40, 40, null);

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
