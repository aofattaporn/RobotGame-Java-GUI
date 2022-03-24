package controller;

import mainGame.Game;
import object.ET;
import object.ID;

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
                if (ET.destroy > 0 && i % 20 == 0) game.randElement(game.AETP1, ID.ET, ET.destroy);
            }
        }
    }


}
