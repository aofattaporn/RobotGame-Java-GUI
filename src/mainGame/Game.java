package mainGame;

import controller.*;
import entity.ElementPosition;
import entity.MyPosition;
import net.TranslateMessage;
import object.*;
import object.Robot;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Game extends Canvas implements Runnable {

    // variable
    private final int WIDTH = 800;
    private final int HEIGHT = 640;
    public static final int BOX_SIZE = 32;
    public MyPosition player1;
    public static ArrayList<ElementPosition> ABombP1, ABombP2;
    public static ArrayList<Integer> bombXP1, bombYP1, ETXP1, ETYP1, randETP1;
    public static ArrayList<Integer> bombXP2, bombYP2, ETXP2, ETYP2, randETP2;
    public ArrayList<ElementPosition> AETP1, AETP2;
    public static String username;

    // dependency injection
    private boolean isRunning = false;
    private Window window;
    private Thread mainThread;
    private Thread timerThread;
    private Handler handler;
    private Camera camera;
    private BufferedImage tile = null;
    private BufferImagesLoader loader;
    private TranslateMessage translate;

    // network
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;


    //  constructor method
    public Game(Socket socket) throws IOException {

        // create window
        this.window = new Window(WIDTH, HEIGHT, "Robot Game", this);

        // network connecting
        try {

            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

        this.handler = new Handler();
        this.camera = new Camera(this);
        ABombP1 = new ArrayList<>();
        ABombP2 = new ArrayList<>();
        AETP1 = new ArrayList<>();
        AETP2 = new ArrayList<>();

        bombXP2 = new ArrayList<>();
        bombYP2 = new ArrayList<>();
        bombXP1 = new ArrayList<>();
        bombYP1 = new ArrayList<>();
        ETXP1 = new ArrayList<>();
        ETYP1 = new ArrayList<>();
        ETXP2 = new ArrayList<>();
        ETYP2 = new ArrayList<>();
        randETP1 = new ArrayList<>();
        randETP2 = new ArrayList<>();

        // run main threading
        start();
        this.addKeyListener(new KeyInput(handler));

        // create background
        loader = new BufferImagesLoader();
        tile = loader.loadImage("/res/tile.png");
        createTileMap();

        // add robot character -> send message
        username = JOptionPane.showInputDialog(this, "Please enter a name");
        player1 = new MyPosition(getRandomPlayer(2, 90), getRandomPlayer(2, 90));
        System.out.println(username
                + "entered server !!! => in position  X : "
                + player1.getPositionX()
                + " Y : " + player1.getPositionY());
        handler.addObject(
                new Robot(BOX_SIZE * player1.getPositionX(),
                        BOX_SIZE * player1.getPositionY(),
                        ID.Robot, handler, username, loader,
                        bufferedWriter));

        translate = new TranslateMessage(handler);
        sendMSG(username + " enter server : " + player1.getPositionX() + " " + player1.getPositionY());

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

        sendMSG(username + " end game ");
        window.closeWindow();

        try {
            mainThread.join();
            timerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        isRunning = false;
        closeEverything(socket, bufferedReader, bufferedWriter);

        System.exit(0);

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

        if (Robot.hp <= 0) {
            g.setColor(new Color(192, 192, 192, 90));
            g.fillRect(0, 0, WIDTH, HEIGHT);

            g.setColor(Color.orange);
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
            g.drawString("You Lose", 270, 200);

            g2d.fillRect(270, 300, 270, 50);
            g.setColor(Color.white);
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
            g.drawString("click to exit", 280, 335);

            this.addMouseListener(new MouseInput(this));

            Robot.handler = null;

        }


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
        g.drawString("HP : " + Robot.hp, 6, 52);

        // check hp
        if (Robot.hp <= 0) {
            Robot.hp = 0;

            g.setColor(Color.white);
            g.drawString("HP : " + Robot.hp, 6, 52);
        }

        g.setColor(Color.white);
    }

    private void createPosition(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(600, 10, 150, 50);
        g.setColor(Color.WHITE);
        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ID.Robot) {
                g.drawString("X : " + (handler.object.get(i).getX() - 2) / 32 +
                                ", Y :" + (handler.object.get(i).getY() - 2) / 32,
                        625, 40);
            }
        }

    }

    synchronized public void randElement(ArrayList<ElementPosition> element, ID id, int size) {

        for (int i = 0; i < size; i++) {

            if (element == ABombP1) {
                handler.addObject(
                        new Bomb(
                                ABombP1.get(i).getElemX() * BOX_SIZE,
                                ABombP1.get(i).getElemY() * BOX_SIZE,
                                ID.Bomb,
                                handler,
                                ABombP1,
                                bufferedWriter));
            }

            else if (element == ABombP2) {
                handler.addObject(
                        new Bomb(
                                ABombP2.get(i).getElemX() * BOX_SIZE,
                                ABombP2.get(i).getElemY() * BOX_SIZE,
                                ID.Bomb,
                                handler,
                                ABombP1,
                                bufferedWriter));
            }


            else if (element == AETP1) {

                if (AETP1.get(i) != null) {
                    ET et = new ET(
                            AETP1.get(i).getElemX() * BOX_SIZE,
                            AETP1.get(i).getElemY() * BOX_SIZE,
                            ID.ET,
                            handler);


                    handler.addObject(et);

                    // get random
                    randETP1.add(et.getRandom());
                }

            } else if (element == AETP2) {

                handler.addObject(
                        new ET(
                                AETP2.get(i).getElemX() * BOX_SIZE,
                                AETP2.get(i).getElemY() * BOX_SIZE,
                                ID.ET,
                                handler));
            }

        }
    }

    synchronized public void randElement(ArrayList<ElementPosition> element, ID id, int size, ArrayList<Integer> rand)
    {

        for (int i = 0; i < size; i++) {

           if (element == AETP2) {

                handler.addObject(
                        new ET(
                                AETP2.get(i).getElemX() * BOX_SIZE,
                                AETP2.get(i).getElemY() * BOX_SIZE,
                                ID.ET,
                                handler, rand.get(i)));
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

                        if (msgFromGroupChat.contains("areaPlayer1")) {
                            // translate command create area
                            if (msgFromGroupChat.contains("BombX")) {
                                bombXP1 = translate.msgAreaBomb(msgFromGroupChat, bombXP1);
                                ETXP1 = translate.msgAreaET(msgFromGroupChat, ETXP1);
                            }

                            else if (msgFromGroupChat.contains("BombY")) {
                                bombYP1 = translate.msgAreaBomb(msgFromGroupChat, bombYP1);
                                ETYP1 = translate.msgAreaET(msgFromGroupChat, ETYP1);
                            }

                            // check value in bombXP1 and bombYP1
                            if (bombXP1.size() == 160 && bombYP1.size() == 160) {
                                // create Bomb
                                for (int i = 0; i < bombXP1.size(); i++) {
                                    ABombP1.add(new ElementPosition(bombXP1.get(i), bombYP1.get(i)));
                                }
                                // create Bomb in map
                                randElement(ABombP1, ID.Bomb, ABombP1.size());
                            }

                            if (ETXP1.size() == 40 && ETYP1.size() == 40) {
                                // create ET
                                for (int i = 0; i < ETXP1.size(); i++) {
                                    AETP1.add(new ElementPosition(ETXP1.get(i), ETYP1.get(i)));
                                }
                                // create ET in map
                                randElement(AETP1, ID.ET, AETP1.size());

                                // get
                            }
                        }

                        else if (msgFromGroupChat.contains("Loading player")) {
                            translate.msgCreateLoadPlayer(msgFromGroupChat, username, loader);
                        } else if (!msgFromGroupChat.contains(username) && msgFromGroupChat.contains("HP HPlayer1")) {
                            translate.msgHPPlayer1(msgFromGroupChat);
                        } else if (!msgFromGroupChat.contains(username) && msgFromGroupChat.contains("enter server")) {
                            // create player2 in server
                            translate.msgEnterNewClient(msgFromGroupChat, loader);

                            StringBuilder listX = new StringBuilder();
                            StringBuilder listY = new StringBuilder();
                            StringBuilder listXET = new StringBuilder();
                            StringBuilder listYET = new StringBuilder();
                            StringBuilder listRand = new StringBuilder();

                            // send msg bomb position
                            for (ElementPosition position : ABombP1) {
                                listX.append(position.getElemX()).append(",");
                                listY.append(position.getElemY()).append(",");
                            }

                            for (ElementPosition elementPosition : AETP1) {
                                listXET.append(elementPosition.getElemX()).append(",");
                                listYET.append(elementPosition.getElemY()).append(",");
                            }

                            for (Integer integer : randETP1) {
                                listRand.append(integer).append(",");
                            }

                            sendMSG(username + "have entered server : " + player1.getPositionX() + " " + player1.getPositionY());
                            sendMSG(username + "HP HPlayer1: " + Robot.hp);
                            sendMSG(username + " Loading player 2 :");
                            sendMSG(username + " areaPlayer2 BombX : " + listX + "areaPlayer2 BombY : " + listY );
                            sendMSG(username + " areaPlayer2 ETX : " + listXET + "areaPlayer2 ETY : " + listYET + "randET :" + listRand);

                        } else if (!msgFromGroupChat.contains(username) && msgFromGroupChat.contains("have entered server")) {
                            translate.msgEnterNewClient(msgFromGroupChat, loader);
                        } else if (!msgFromGroupChat.contains(username) && msgFromGroupChat.contains("move to")) {
                            translate.msgEnemyMove(msgFromGroupChat);
                        } else if (!msgFromGroupChat.contains(username) && msgFromGroupChat.contains("direct")) {
                            translate.msgEnemyDirect(msgFromGroupChat);
                        } else if (!msgFromGroupChat.contains(username) && msgFromGroupChat.contains("shoot")) {
                            translate.msgEnemyShoot(msgFromGroupChat, bufferedWriter);
                        } else if (!msgFromGroupChat.contains(username) && msgFromGroupChat.contains("end game")) {
                            translate.msgPlayerEnd(msgFromGroupChat);
                        } else if (!msgFromGroupChat.contains(username) && msgFromGroupChat.contains("decrease HP")) {
                            translate.msgDecreaseHP(msgFromGroupChat);
                        }

                        if (msgFromGroupChat.contains("areaPlayer2") && msgFromGroupChat.contains("BombX") &&
                                !msgFromGroupChat.contains(username)
                        ) {

                            bombXP2 = translate.msgCreateBombXP2(msgFromGroupChat, bombXP2);
                            bombYP2 = translate.msgCreateBombYP2(msgFromGroupChat, bombYP2);

                            // create bomb and ET
                            if (bombXP2.size() == 160 && bombYP2.size() == 160) {
                                // create Bomb
                                for (int i = 0; i < bombXP2.size(); i++) {
                                    ABombP2.add(new ElementPosition(bombXP2.get(i), bombYP2.get(i)));
                                }
                                randElement(ABombP2, ID.Bomb, ABombP2.size());
                            }
                        }

                        if (msgFromGroupChat.contains("areaPlayer2") && msgFromGroupChat.contains("ET")  &&
                                !msgFromGroupChat.contains(username)) {

                            System.out.println(msgFromGroupChat);

                            ETXP2 = translate.msgCreateETXP2(msgFromGroupChat, ETXP2);
                            ETYP2 = translate.msgCreateETYP2(msgFromGroupChat, ETYP2);
                            randETP2 = translate.msgCreateRandET(msgFromGroupChat, randETP2);

                            if (ETXP2.size() == 40 && ETYP2.size() == 40) {
                                for (int i = 0; i < ETXP2.size(); i++) {
                                    AETP2.add(new ElementPosition(ETXP2.get(i), ETYP2.get(i)));
                                }
                                randElement(AETP2, ID.ET, AETP2.size(), randETP2);
                            }
                        }


                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    // close everything
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
}
