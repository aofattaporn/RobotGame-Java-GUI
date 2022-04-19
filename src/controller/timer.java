package controller;

import mainGame.Game;
import object.ET;
import object.ID;

public class timer implements Runnable {

    Game game;

    public timer(Game game) {
        this.game = game;
    }

    @Override
    public void run() {

        // 3 round : 120 second
        for (int i = 0; i < 180; i++) {  // 1 second

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // create ET every 60 second
            if (ET.destroy > 0 && i % 60 == 0) game.randElement(game.AETP1, ID.ET, ET.destroy);
        }

    }


}
