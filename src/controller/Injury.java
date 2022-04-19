package controller;

import mainGame.Game;


public class Injury implements Runnable {

    public static Boolean check;


    @Override
    public void run() {

        check = true;

        while (true){
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setCheck(true);
        }
    }

    public static void setCheck(Boolean check) {
        Injury.check = check;
    }

}
