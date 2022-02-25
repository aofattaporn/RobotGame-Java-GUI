package com.company;

import java.awt.*;

public class Game extends Canvas implements Runnable {

    // variable

    private boolean isRunning = false;
    private Thread thread;

    public Game(){
        // create window
        new Window(800, 600, "Robot Game", this);

        // run main threading
        start();

    }

    public void start(){
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() throws InterruptedException {
        isRunning = false;
        thread.join();
    }


    @Override
    public void run() {

    }

    public static void main(String[] args) {
        new Game();
    }
}
