package net;

import minaGame.Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;



public class client1 {

    private Game gamePlayer1;

    public client1(Game gamePlayer1) {
        this.gamePlayer1 = gamePlayer1;
    }

    public static void main(String[] arg) {


        try {

            // CONNECT SOCKET
            Socket socketConnection = new Socket("127.0.0.1", 11111);
            DataOutputStream outToServer = new DataOutputStream(socketConnection.getOutputStream());

            String SQL="I am  Client 1";
            while (true) {
                outToServer.writeUTF(SQL);


                // START GAME
//            new client2(new Game(socketConnection));


                //QUERY PASSING
                DataInputStream infromClient = new DataInputStream(socketConnection.getInputStream());
                System.out.println(infromClient.readUTF());
            }





        } catch (Exception e) {System.out.println(e); }
    }
}