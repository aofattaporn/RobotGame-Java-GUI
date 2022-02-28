package object;

import com.company.BufferImagesLoader;
import com.company.Handler;
import com.company.ID;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Robot extends GameObject {

    Handler handler;
    BufferedImage robotUP, rotbotDown, robotLeft, robotRight, image;
    BufferImagesLoader loader;

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
    public void tick(){
        x += velX;
        y += velY;


        // movement
        if (handler.isUp()) {
            if (image == robotUP) velY = -32;
            image = robotUP;
        }
        else if (!handler.isDown()){ velY = 0;}

        if (handler.isDown()){
            if (image == rotbotDown) velY = 32;
            image = rotbotDown;
        }
        else if (!handler.isUp()) velY = 0;

        if (handler.isRight()){
            if (image == robotRight) velX = 32;
            image = robotRight;
        }
        else if (!handler.isLeft()) velX = 0;

        if (handler.isLeft()){
            if (image == robotLeft) velX = -32;
            image = robotLeft;
        }

        else if (!handler.isRight()) velX = 0;

        if (handler.isSpaceBar()){
            // TODO : set bullet create object
            handler.addObject(new Gun(x, y, ID.Bullet));
        }


    }

    @Override
    public void render(Graphics g) {


        g.drawImage(image , x, y, 40, 40, null);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 40);
    }

}
