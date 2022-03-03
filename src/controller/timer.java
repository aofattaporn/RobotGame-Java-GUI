package controller;

import entity.ElementPosition;
import minaGame.Game;
import object.Bomb;
import object.ET;
import object.ID;

import java.util.ArrayList;

public class timer implements Runnable{

    Game game;

    public timer(Game game) {
        this.game = game;
    }

    @Override
    public void run() {

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 60; i++) {  // 1 second

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (ET.destroy > 0 && i % 20 == 0) game.randElement(game.AET, ID.ET, ET.destroy);
//                System.out.println(i);
            }
        }
    }


}
