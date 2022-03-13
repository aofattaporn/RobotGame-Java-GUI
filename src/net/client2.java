package net;

import minaGame.Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


public class client2 {


    private Game gamePlayer2;

    public client2(Game gamePlayer1) {
        this.gamePlayer2 = gamePlayer1;
    }

    public static void main(String[] arg) {


        try {

            // CONNECT SOCKET
            Socket socketConnection = new Socket("127.0.0.1", 11111);
            DataOutputStream outToServer = new DataOutputStream(socketConnection.getOutputStream());
            String SQL="I am  Client 2";
            outToServer.writeUTF(SQL);

            // START GAME
//            new client2(new Game(socketConnection));



            //QUERY READING
            DataInputStream infromClient = new DataInputStream(socketConnection.getInputStream());
            System.out.println(infromClient.readUTF());




        } catch (Exception e) {System.out.println(e); }
    }
}