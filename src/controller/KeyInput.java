package controller;

import mainGame.Game;
import mainGame.Handler;
import object.GameObject;
import object.ID;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    Handler handler;


    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Robot) {
                if (key == KeyEvent.VK_W) handler.setUp(true);
                if (key == KeyEvent.VK_S) handler.setDown(true);
                if (key == KeyEvent.VK_A) handler.setLeft(true);
                if (key == KeyEvent.VK_D) handler.setRight(true);

                if (key == KeyEvent.VK_SPACE) handler.setSpaceBar(true);

            }
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Robot) {
                if (key == KeyEvent.VK_W) handler.setUp(false);
                if (key == KeyEvent.VK_S) handler.setDown(false);
                if (key == KeyEvent.VK_A) handler.setLeft(false);
                if (key == KeyEvent.VK_D) handler.setRight(false);

                if (key == KeyEvent.VK_SPACE) handler.setSpaceBar(false);

            }
        }
    }

}
