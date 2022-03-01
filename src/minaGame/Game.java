package minaGame;

import controller.BufferImagesLoader;
import controller.Camera;
import controller.KeyInput;
import entity.ElementPosition;
import object.Block;
import object.BlockTile;
import object.ID;
import object.Robot;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.sql.Array;
import java.util.ArrayList;

public class Game extends Canvas implements Runnable {

    // variable
    private final int WIDTH = 800;
    private final int HEIGHT = 640;
    public static int BOX_SIZE = 32;
    ArrayList<ElementPosition> ABomb;

    // dependency injection
    private boolean isRunning = false;
    private Thread mainThread;
    private Handler handler;
    private Camera camera;
    private BufferedImage tile = null;

    // constructor
    public Game() {

        // create window
        new Window(WIDTH, HEIGHT, "Robot Game", this);

        // run main threading
        start();
        ABomb = new ArrayList<ElementPosition>();

        handler = new Handler();
        camera = new Camera(this);
        this.addKeyListener(new KeyInput(handler));

        // create background
        BufferImagesLoader loader = new BufferImagesLoader();
        tile = loader.loadImage("/res/tile.png");
        createTileMap();

        // add robot character
        handler.addObject(new Robot(BOX_SIZE * 2, BOX_SIZE * 2, ID.player, handler, loader));

        // create bomb
        randElement(ABomb, 240);

        // create ET



    }

    private void randElement(ArrayList<ElementPosition> element, int size){

        int randBombX = 0;
        int randBombY = 0;

        for (int i = 0; i < size; i++) {

            randBombX = getRandomPlayer(2, 102);
            randBombY = getRandomPlayer(2, 82);

            ABomb.add(new ElementPosition(randBombX, randBombY));
            handler.addObject(new Block(randBombX * BOX_SIZE, randBombY * BOX_SIZE, ID.Block));

        }
    }

    private void createTileMap() {
        handler.addObject(new BlockTile(2, 2, tile, ID.Block));
    }

    private int getRandomPlayer(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void start() {
        isRunning = true;
        mainThread = new Thread(this);
        mainThread.start();
    }

    public void stop() {
        isRunning = false;

        try {
            mainThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.requestFocus();

        long lastTime = System.nanoTime();
        double amountOfTicks = 12;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                tick();
                delta--;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
                // updates = 0;
            }
        }
        stop();
    }

    // update method
    public void tick() {
        for (int i = 0; i < handler.object.size(); i++) {
            camera.tick(handler.object.get(i));
        }
        handler.tick();
    }

    // render screen
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        /////////////////////////////////


        g.setColor(Color.getHSBColor(0.59f, 0.85f, 0.50f));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.GREEN);


        g2d.translate(-camera.getCamX(), -camera.getCamY());
        handler.render(g);
        g2d.translate(camera.getCamX(), camera.getCamY());

        // create position box
        createPosition(g);


        /////////////////////////////////
        g.dispose();
        bs.show();

    }

    private void createPosition(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(600, 10, 150, 50);
        g.setColor(Color.WHITE);
        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ID.player) {
//                System.out.println("x : " + (handler.object.get(i).getX() / 32 )  + ", y : " + (handler.object.get(i).getY() / 32));
                g.drawString("X : " +
                                String.valueOf((handler.object.get(i).getX() - 2) / 32) + ", Y :" +
                                String.valueOf((handler.object.get(i).getY() - 2) / 32),
                        625, 40);
            }
        }

    }

    public static void main(String[] args) {
        new Game();
    }

}
