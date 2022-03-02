package controller;

import entity.ElementPosition;
import object.Bomb;
import object.ET;
import object.ID;

import java.util.ArrayList;

public class timer implements Runnable{

    @Override
    public void run() {

        for (int i = 0; i < 100; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            randElement(AET, ID.ET, 120);
            System.out.println(i);
        }
    }


}
