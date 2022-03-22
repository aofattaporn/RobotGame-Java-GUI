package minaGame;

import controller.BufferImagesLoader;
import controller.Camera;
import controller.KeyInput;
import controller.timer;
import entity.ElementPosition;
import entity.MyPosition;
import object.*;
import object.Robot;
import testServer.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Game extends Canvas implements Runnable {

    // variable
    private final int WIDTH = 800;
    private final int HEIGHT = 640;
    public static int BOX_SIZE = 32;
    public MyPosition player1;
    public MyPosition player2;
    public ArrayList<ElementPosition> ABomb;
    public ArrayList<ElementPosition> AET;
    public String username;
    private int indexEnemy;

    // dependency injection
    private boolean isRunning = false;
    private Thread mainThread;
    private Thread timerThread;
    private Handler handler;
    private Camera camera;
    private BufferedImage tile = null;

    // network
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;


    //  constructor method
    public Game(Socket socket) throws IOException {

        // create window
        new Window(WIDTH, HEIGHT, "Robot Game", this);

        // network connection
        try {

            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }


        handler = new Handler();
        camera = new Camera(this);

        // run main threading
        start();

        this.addKeyListener(new KeyInput(handler));

        // create background
        BufferImagesLoader loader = new BufferImagesLoader();
        tile = loader.loadImage("/res/tile.png");
        createTileMap();

        // add robot character -> send message
        String username = JOptionPane.showInputDialog(this, "Please enter a name");
        this.username = username;
        player1 = new MyPosition(getRandomPlayer(2, 90), getRandomPlayer(2, 90));
        handler.addObject(new Robot(BOX_SIZE * player1.getPositionX(), BOX_SIZE * player1.getPositionY(), ID.player, handler, username, loader));
        sendMSG( username + " enter server : " + player1.getPositionX() + " " + player1.getPositionY());


    }

    public void start() {
        isRunning = true;
        mainThread = new Thread(this);
        timerThread = new Thread(new timer(this));

        // start thread
        timerThread.start();
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

    public void tick() {
        for (int i = 0; i < handler.object.size(); i++) {
            camera.tick(handler.object.get(i));
            GameObject tempObject = handler.object.get(i);

            if (getBounds().intersects(tempObject.getBounds())) {
                if (tempObject.getId() == ID.Bullet) {
                    Robot.hp -= 50;
                }
            }
        }
        handler.tick();
    }

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

        // create HP
        createHP(g);


        /////////////////////////////////
        g.dispose();
        bs.show();

    }

    private void createHP(Graphics g) {

        // create HP
        g.setColor(Color.gray);
        g.fillRect(5, 5, 200, 32);
        if (Robot.hp < 40) g.setColor(Color.red);
        else g.setColor(Color.green);
        g.fillRect(5, 5, Robot.hp * 2, 32);
        g.setColor(Color.BLACK);
        g.drawRect(5, 5, 200, 32);

        g.setColor(Color.white);
        g.drawString("HP : " + String.valueOf(Robot.hp), 6, 52);

        g.setColor(Color.white);
    }

    private void createPosition(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(600, 10, 150, 50);
        g.setColor(Color.WHITE);
        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ID.player) {
                g.drawString("X : " +
                                String.valueOf((handler.object.get(i).getX() - 2) / 32) + ", Y :" +
                                String.valueOf((handler.object.get(i).getY() - 2) / 32),
                        625, 40);
                sendMSG(username + " move to : " + String.valueOf((handler.object.get(i).getX() - 2) / 32) + " " + String.valueOf((handler.object.get(i).getY() - 2) / 32));
            }
        }

    }

    public void randElement(ArrayList<ElementPosition> element, ID id, int size) {

        int randBombX = 0;
        int randBombY = 0;

        for (int i = 0; i < size; i++) {

            randBombX = getRandomPlayer(2, 102);
            randBombY = getRandomPlayer(2, 82);

            if (element == ABomb) {
                element.add(new ElementPosition(randBombX, randBombY));
                handler.addObject(new Bomb(randBombX * BOX_SIZE, randBombY * BOX_SIZE, ID.Bomb, handler));
            } else if (element == AET) {
                for (int j = 0; j < ABomb.size(); j++) {
                    if (randBombX == ABomb.get(j).getElemX() && randBombY == ABomb.get(j).getElemY()) {
                        element.add(new ElementPosition(randBombX, randBombY));
                        randBombX = getRandomPlayer(2, 102);
                        randBombY = getRandomPlayer(2, 82);
                        j = 0;
                    }
                }
                handler.addObject(new ET(randBombX * BOX_SIZE, randBombY * BOX_SIZE, ID.ET, handler));

            }

        }
    }

    private void createTileMap() {
        handler.addObject(new BlockTile(2, 2, tile, ID.Block));
    }

    private int getRandomPlayer(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    ////////////////////////////////////////////////////////////////////
    // Network -> method
    ////////////////////////////////////////////////////////////////////

    public void sendMSG(String msg) {
        try {
            bufferedWriter.write(String.valueOf(msg));
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // send message
//    public void sendMessage() {
//        try {
//            bufferedWriter.write(username);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//
//            Scanner scanner = new Scanner(System.in);
//
//            // send Message to another client
//            while (socket.isConnected()) {
//
////                String messageToSend = scanner.nextLine();
////                bufferedWriter.write(username + " : " + messageToSend);
////                bufferedWriter.newLine();
////                bufferedWriter.flush();
//
//            }
//        } catch (IOException e) {
//            closeEverything(socket, bufferedReader, bufferedWriter);
//
//        }
//    }

    // listen msg from another client
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;

                while (socket.isConnected()) {
                    try {

                        // check object
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);

                        // check whos instruction
                        if (!msgFromGroupChat.contains(username)) {
                            commandEnemy(msgFromGroupChat);
                        }

                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    private void commandEnemy(String msgFromGroupChat){
        // when someone enter server
        if (msgFromGroupChat.contains("enter server")){
            // substring
            int indexColon = msgFromGroupChat.indexOf(":");
            indexColon += 2;
            String newMsgFromGroupChat = msgFromGroupChat.substring(indexColon);
            String position[] = newMsgFromGroupChat.split(" ");

            // create enemy
            handler.addObject(new Enemy(BOX_SIZE * Integer.parseInt(position[0]), BOX_SIZE * Integer.parseInt(position[1]), ID.Enemy, handler, username, null));

        }

        // when someone move
        else if (!msgFromGroupChat.contains(username) && msgFromGroupChat.contains("move to")){

            int indexColon = msgFromGroupChat.indexOf(":");
            indexColon += 2;

            String newMsgFromGroupChat = msgFromGroupChat.substring(indexColon);
            String position[] = newMsgFromGroupChat.split(" ");

            for (int i = 0 ; i < handler.object.size(); i++){
                if (handler.object.get(i).getId() == ID.Enemy){
                    handler.object.get(i).setX(Integer.parseInt(position[0]));
                    handler.object.get(i).setY(Integer.parseInt(position[1]));

                }
            }

        }


    }

    // close evetyting
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9999);
        Game client = new Game(socket);
        client.listenForMessage();



    }


}
