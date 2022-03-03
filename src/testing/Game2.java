//package testing;
//
//import controller.BufferImagesLoader;
//import controller.Camera;
//import controller.KeyInput;
//import controller.timer;
//import entity.ElementPosition;
//import minaGame.Game;
//import minaGame.Handler;
//import minaGame.Window;
//import net.GameClient;
//import net.GameServer;
//import object.Robot;
//import object.*;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.image.BufferStrategy;
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//
//public class Game2 extends Canvas implements Runnable {
//
//    // variable
//    private final int WIDTH = 800;
//    private final int HEIGHT = 640;
//    public static int BOX_SIZE = 32;
//    public ArrayList<ElementPosition> ABomb;
//    public ArrayList<ElementPosition> AET;
//
//    // dependency injection
//    private boolean isRunning = false;
//    private Thread mainThread;
//    private Thread timerThread;
//    private Thread clientThread;
//    private Thread severThread;
//    private Handler handler;
//    private Camera camera;
//    private BufferedImage tile = null;
//
//    // multiplayer
//    private GameClient socketClient;
//    private GameServer socketSever;
//
//
//    public Game2() {
//
//        // create window
//        new Window(WIDTH, HEIGHT, "Robot Game", this);
//
//        ABomb = new ArrayList<ElementPosition>();
//        AET = new ArrayList<ElementPosition>();
//
//        handler = new Handler();
//        camera = new Camera(this);
//
//        // run main threading
//        start();
//        connectSever();
//
//        this.addKeyListener(new KeyInput(handler));
//
//        // create background
//        BufferImagesLoader loader = new BufferImagesLoader();
//        tile = loader.loadImage("/res/tile.png");
//        createTileMap();
//
//        // add robot character
//        handler.addObject(new Robot(BOX_SIZE * getRandomPlayer(2, 102), BOX_SIZE * getRandomPlayer(2, 82), ID.player, handler, JOptionPane.showInputDialog(this, "Please enter a name"),  loader));
//
//        // create bomb
//        randElement(ABomb, ID.Bomb, 240);
//
//        // create ET
//        randElement(AET, ID.ET, 120);
//
//        socketClient.sendData("ping".getBytes());
//
//
//
//
//
//    }
//
//    public void start() {
//        isRunning = true;
//        mainThread = new Thread(this);
//        timerThread = new Thread(new timer(this));
//
//        // start thread
//        timerThread.start();
//        mainThread.start();
//
//    }
//
//    private void connectSever(){
//        if (JOptionPane.showConfirmDialog(this, "Do you want to run need a sever") == 0){
//            socketSever = new GameServer(this);
//            severThread = new Thread(socketSever);
//
//            severThread.start();
//        }
//
//        // connect client
//        socketClient = new GameClient(this, "localhost");
//        clientThread = new Thread(socketClient);
//
//        clientThread.start();
//    }
//
//    public void stop() {
//        isRunning = false;
//
//        try {
//            mainThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void run() {
//        this.requestFocus();
//
//        long lastTime = System.nanoTime();
//        double amountOfTicks = 12;
//        double ns = 1000000000 / amountOfTicks;
//        double delta = 0;
//        long timer = System.currentTimeMillis();
//        int frames = 0;
//
//        while (isRunning) {
//            long now = System.nanoTime();
//            delta += (now - lastTime) / ns;
//            lastTime = now;
//
//            while (delta >= 1) {
//                tick();
//                delta--;
//            }
//
//            render();
//            frames++;
//
//            if (System.currentTimeMillis() - timer > 1000) {
//                timer += 1000;
//
//                frames = 0;
//                // updates = 0;
//            }
//        }
//        stop();
//    }
//
//    public void tick() {
//        for (int i = 0; i < handler.object.size(); i++) {
//            camera.tick(handler.object.get(i));
//            GameObject tempObject = handler.object.get(i);
//
//            if (getBounds().intersects(tempObject.getBounds())) {
//                if (tempObject.getId() == ID.Bullet) {
//                    Robot.hp -= 50;
//                }
//            }
//        }
//        handler.tick();
//    }
//
//    public void render() {
//        BufferStrategy bs = this.getBufferStrategy();
//
//        if (bs == null) {
//            this.createBufferStrategy(3);
//            return;
//        }
//
//        Graphics g = bs.getDrawGraphics();
//        Graphics2D g2d = (Graphics2D) g;
//        /////////////////////////////////
//
//
//        g.setColor(Color.getHSBColor(0.59f, 0.85f, 0.50f));
//        g.fillRect(0, 0, WIDTH, HEIGHT);
//
//        g.setColor(Color.GREEN);
//
//
//        g2d.translate(-camera.getCamX(), -camera.getCamY());
//        handler.render(g);
//        g2d.translate(camera.getCamX(), camera.getCamY());
//
//        // create position box
//        createPosition(g);
//
//        // create HP
//        createHP(g);
//
//
//        /////////////////////////////////
//        g.dispose();
//        bs.show();
//
//    }
//
//    private void createHP(Graphics g) {
//
//        // create HP
//        g.setColor(Color.gray);
//        g.fillRect(5, 5, 200, 32);
//        if (Robot.hp < 40) g.setColor(Color.red);
//        else g.setColor(Color.green);
//        g.fillRect(5, 5, Robot.hp * 2, 32);
//        g.setColor(Color.BLACK);
//        g.drawRect(5, 5, 200, 32);
//
//        g.setColor(Color.white);
//        g.drawString("HP : " + String.valueOf(Robot.hp), 6, 52);
//
//        g.setColor(Color.white);
//    }
//
//    private void createPosition(Graphics g) {
//        g.setColor(Color.GRAY);
//        g.fillRect(600, 10, 150, 50);
//        g.setColor(Color.WHITE);
//        for (int i = 0; i < handler.object.size(); i++) {
//            if (handler.object.get(i).getId() == ID.player) {
////                System.out.println("x : " + (handler.object.get(i).getX() / 32 )  + ", y : " + (handler.object.get(i).getY() / 32));
//                g.drawString("X : " +
//                                String.valueOf((handler.object.get(i).getX() - 2) / 32) + ", Y :" +
//                                String.valueOf((handler.object.get(i).getY() - 2) / 32),
//                        625, 40);
//            }
//        }
//
//    }
//
//    public void randElement(ArrayList<ElementPosition> element, ID id, int size) {
//
//        int randBombX = 0;
//        int randBombY = 0;
//
//        for (int i = 0; i < size; i++) {
//
//            randBombX = getRandomPlayer(2, 102);
//            randBombY = getRandomPlayer(2, 82);
//
//            if (element == ABomb) {
//                element.add(new ElementPosition(randBombX, randBombY));
//                handler.addObject(new Bomb(randBombX * BOX_SIZE, randBombY * BOX_SIZE, ID.Bomb, handler));
//            } else if (element == AET) {
//                for (int j = 0; j < ABomb.size(); j++) {
//                    if (randBombX == ABomb.get(j).getElemX() && randBombY == ABomb.get(j).getElemY()) {
//                        element.add(new ElementPosition(randBombX, randBombY));
//                        randBombX = getRandomPlayer(2, 102);
//                        randBombY = getRandomPlayer(2, 82);
//                        j = 0;
//                    }
//                }
//                handler.addObject(new ET(randBombX * BOX_SIZE, randBombY * BOX_SIZE, ID.ET, handler));
//
//            }
//
//        }
//    }
//
//    private void createTileMap() {
//        handler.addObject(new BlockTile(2, 2, tile, ID.Block));
//    }
//
//    private int getRandomPlayer(int min, int max) {
//        return (int) ((Math.random() * (max - min)) + min);
//    }
//
//    public static void main(String[] args) {
//
////        new HomeWindow(800, 600, "Robot Game");
//
//        new Game();
//    }
//
//
//
//}
