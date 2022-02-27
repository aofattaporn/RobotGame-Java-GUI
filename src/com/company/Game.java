package com.company;

import object.Block;
import object.Robot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Game extends Canvas implements Runnable {

    // variable
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    private BufferedImage level = null;

    public Game() {
        // create window
        new Window(WIDTH, HEIGHT, "Robot Game", this);

        // run main threading
        start();

        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));

        BufferImagesLoader loader = new BufferImagesLoader();
        level = loader.loadImage("board.png");
//        loadLevel(level);

        handler.addObject(new Robot(32, 32, ID.player, handler));

    }

    public void start(){
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop(){
        isRunning = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        this.requestFocus();

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1){
                tick();
                delta--;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                frames = 0;
                // updates = 0;
            }
        }
        stop();
    }

    // update method
    public void tick(){
        handler.tick();
    }

    // render screen
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        /////////////////////////////////

//        g.setColor(Color.red);
//        g.fillRect(0, 0, WIDTH, HEIGHT);
        tileMap(level, g);

        handler.render(g);

        /////////////////////////////////
        g.dispose();
        bs.show();

    }

    private void loadLevel(BufferedImage image){
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx = 0; xx < w; xx++){
            for (int yy = 0; yy < h; yy++){
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16 ) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if (red == 255){
                    handler.addObject(new Block(xx*32, yy*32, ID.Block));
                }

                if (blue == 255){
                    handler.addObject(new Robot(xx*32, yy*32, ID.player , handler));
                }
            }
        }
    }

    private void tileMap(BufferedImage image, Graphics g){
        int x = 0, y = 0;
        for (int i = 0; i < 100; i++){
            for (int j = 0; j < 100; j++){
                g.drawImage(image, x, y , 32, 32, null);
                x += 32;
            }
            y += 32;
            x = 0;
        }
    }

    public static void main(String[] args) {
        new Game();
    }

}
