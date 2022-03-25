package net;

import controller.BufferImagesLoader;
import mainGame.Game;
import mainGame.Handler;
import object.*;

import java.io.BufferedWriter;
import java.util.ArrayList;

public class TranslateMessage {

    private Handler handler;

    public TranslateMessage(Handler handler) {
        this.handler = handler;
    }


    public ArrayList<Integer> msgArea(String msgFromGroupChat, ArrayList<Integer> bombP1) {

        if (msgFromGroupChat.contains("BombX")) {
            int indexColon = msgFromGroupChat.indexOf(":");
            indexColon += 1;

            String newMsgFromGroupChat = msgFromGroupChat.substring(indexColon);

            String x[] = newMsgFromGroupChat.split(",");

            for (int i = 0; i < x.length; i++) {
                bombP1.add(Integer.parseInt(x[i]));
            }

            return bombP1;
        } else if (msgFromGroupChat.contains("BombY")) {

            int indexColon = msgFromGroupChat.indexOf(":");
            indexColon += 1;
            String newMsgFromGroupChat = msgFromGroupChat.substring(indexColon);

            String x[] = newMsgFromGroupChat.split(",");

            for (int i = 0; i < x.length; i++) {
                bombP1.add(Integer.parseInt(x[i]));
            }

            return bombP1;
        } else {
            return null;
        }

    }

    public void msgEnterNewClient(String msgFromGroupChat, BufferImagesLoader loader){
        String position[] = subMessage(msgFromGroupChat, 2).split(" ");

        // create enemy
        handler.addObject(new Enemy(
                Game.BOX_SIZE * Integer.parseInt(position[0]),
                Game.BOX_SIZE * Integer.parseInt(position[1]),
                ID.Enemy, handler,
                "enemy",
                loader));
    }


    public void msgEnemyMove(String msgFromGroupChat){
        String position[] = subMessage(msgFromGroupChat, 1).split(" ");

        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ID.Enemy) {
                handler.object.get(i).setX(Integer.parseInt(position[0]));
                handler.object.get(i).setY(Integer.parseInt(position[1]));
            }
        }

    }

    public void msgHPPlayer1(String msgFromGroupChat){
        String hpPlayer1 = subMessage(msgFromGroupChat, 2);

        Enemy.hp = Integer.parseInt(hpPlayer1);

    }

    public void msgEnemyDirect(String msgFromGroupChat){
        String newMsgFromGroupChat = subMessage(msgFromGroupChat, 1);

        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ID.Enemy) {
                Enemy.direct = newMsgFromGroupChat;
            }
        }
    }

    public void msgEnemyShoot(String msgFromGroupChat, BufferedWriter bufferedWriter) {

        String positionBullet[] = subMessage(msgFromGroupChat, 1).split(" ");

        handler.object.add(new Bullet(
                Integer.parseInt(positionBullet[0]), Integer.parseInt(positionBullet[1]), ID.BulletEnemy, handler));
    }

    public ArrayList<Integer> msgCreateBombXP2(String msgFromGroupChat,ArrayList<Integer> bombP2) {


        String newMsgFromGroupChat = subMessage(msgFromGroupChat, 2);

        String x[] = newMsgFromGroupChat.split(",");

        for (int i = 0; i < x.length; i++) {
            bombP2.add(Integer.parseInt(x[i]));
        }

        return bombP2;
    }

    public void msgCreateLoadPlayer(String msgFromGroupChat, String username, BufferImagesLoader loader) {

        if (msgFromGroupChat.contains("Loading player 1")){

            System.out.println("Loading pic player 1");

            Robot.robotUP = loader.loadImage("/res/player2_up.png");
            Robot.rotbotDown = loader.loadImage("/res/player2_down.png");
            Robot.robotLeft = loader.loadImage("/res/player2_left.png");
            Robot.robotRight = loader.loadImage("/res/player2_right.png");

            Robot.image = Robot.rotbotDown;

            Enemy.robotUP = loader.loadImage("/res/player1_up.png");
            Enemy.rotbotDown = loader.loadImage("/res/player1_down.png");
            Enemy.robotLeft = loader.loadImage("/res/player1_left.png");
            Enemy.robotRight = loader.loadImage("/res/player1_right.png");

            Enemy.image = Enemy.robotUP;

        }

        else if (msgFromGroupChat.contains("Loading player 2") && !msgFromGroupChat.contains(username)){

            System.out.println("Loading pic player 2");

            Robot.robotUP = loader.loadImage("/res/player1_up.png");
            Robot.rotbotDown = loader.loadImage("/res/player1_down.png");
            Robot.robotLeft = loader.loadImage("/res/player1_left.png");
            Robot.robotRight = loader.loadImage("/res/player1_right.png");

            Robot.image = Robot.rotbotDown;

            Enemy.robotUP = loader.loadImage("/res/player2_up.png");
            Enemy.rotbotDown = loader.loadImage("/res/player2_down.png");
            Enemy.robotLeft = loader.loadImage("/res/player2_left.png");
            Enemy.robotRight = loader.loadImage("/res/player2_right.png");

            Enemy.image = Enemy.robotUP;

        }


    }

    private String subMessage(String msgFromGroupChat, int indexCount) {

        int indexColon = msgFromGroupChat.indexOf(":");
        indexColon += indexCount;

        return msgFromGroupChat.substring(indexColon);

    }


}
