package controller;

import mainGame.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public  class MouseInput implements MouseListener {


    private final Game game;

    public MouseInput(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();

        if (mx >= 270 && mx <= 540){
            if (my >=  300 && my <= 350){
                // Press exit botton
                System.out.println("exit");
                game.stop();
            }
        }

    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
