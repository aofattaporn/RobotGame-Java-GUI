package object;

import controller.BufferImagesLoader;
import mainGame.Game;
import mainGame.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.IOException;

public class Robot extends GameObject {

    public static Handler handler;
    public static BufferedImage robotUP, rotbotDown, robotLeft, robotRight, image;
    public static String direct;
    private boolean state = false;
    private BufferImagesLoader loader;
    private BufferedWriter bufferedWriter;
    private String username;
    public static int hp = 100;

    public Robot(int x, int y, ID id, Handler handler, String username, BufferImagesLoader loader, BufferedWriter bufferedWriter) {
        super(x, y, id);
        this.handler = handler;
        this.loader = loader;
        this.username = username;
        this.bufferedWriter = bufferedWriter;

        // default image
        image = rotbotDown;
        direct = "down";

    }

    @Override
    synchronized public void tick() {
        x += velX;
        y += velY;

        if (handler != null) {

            // movement
            if (handler.isUp() && y > Game.BOX_SIZE * 2) {
                if (image == robotUP) {
                    velY = -32;
                }
                image = robotUP;
                direct = "up";

            } else if (!handler.isDown()) {
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

            // event spaceBar
            if (handler.isSpaceBar()) {
                if (!state) {
                    sendMSG(username + " " + "shoot :" + x + " " + y);
                    handler.addObject(new Bullet(x, y, ID.BulletRobot, handler));
                    state = true;
                } else {
                    state = false;
                }
            }


            // manage function Bomb
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);

                if (getBounds().intersects(tempObject.getBounds())) {
                    if (tempObject.getId() == ID.Bomb) {

                        Robot.hp -= 5;
                        handler.removeObject(tempObject);

                        // remove in array ABomb
                        int x = tempObject.getX();
                        int y = tempObject.getY();

                        for (int j = 0; j < Game.ABombP1.size(); j++){

                            if (
                                    x == (Game.ABombP1.get(j).getElemX() * Game.BOX_SIZE)
                                    && y  == (Game.ABombP1.get(j).getElemY() * Game.BOX_SIZE)
                            )
                            {
                                Game.ABombP1.remove(j);

                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public void render(Graphics g) {

        if (Robot.hp != 0) {
            sendMSG(username + " change direct :" + direct);
            sendMSG(username + " move to :" + x + " " + y);
        }
        g.drawImage(image, x, y, 40, 40, null);

        if (username != null) {
            g.drawString(username, x + 10, y + 10);
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
