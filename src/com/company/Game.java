package com.company;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    // variable
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private boolean isRunning = false;
    private Thread thread;

    public Game(){
        // create window
        new Window(WIDTH, HEIGHT, "Robot Game", this);

        // run main threading
        start();

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
    public void tick(){}

    // render screen
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        /////////////////////////////////

        g.setColor(Color.pink);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        /////////////////////////////////
        g.dispose();
        bs.show();

    }

    public static void main(String[] args) {
        new Game();
    }
}
