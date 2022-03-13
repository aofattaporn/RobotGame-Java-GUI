package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {





    public static void main(String args[]) throws IOException,
            InterruptedException {

        ServerSocket ss = new ServerSocket(11111);

        while (true) {

            Socket s1 = ss.accept();
            Socket s2 = ss.accept();


            Multi t = new Multi(s1, s2);
            t.start();

            Thread.sleep(2000);
            ss.close();
        }

    }
}


class Multi extends Thread {

    private Socket s1 = null;
    private Socket s2 = null;

    private final DataInputStream infromClient1;
    private final DataInputStream infromClient2;

    private final DataOutputStream client1utfromSever;
    private final DataOutputStream client2utfromSever;


    public Multi(Socket s1, Socket s2) throws IOException {

        String SQL = "";

        this.s1 = s1;
        this.s2 = s2;

        infromClient1 = new DataInputStream(s1.getInputStream());
        infromClient2 = new DataInputStream(s2.getInputStream());

        client1utfromSever = new DataOutputStream(s1.getOutputStream());
        client2utfromSever = new DataOutputStream(s2.getOutputStream());

    }

    public void run() {

        System.out.println("========================");

        String SQL_c1 = "";
        String SQL_c2 = "";

        // read SQL
        try {
            SQL_c1 = infromClient1.readUTF();
            SQL_c2 = infromClient2.readUTF();

        } catch (IOException ex) {
            Logger.getLogger(Multi.class.getName()).log(Level.SEVERE, null, ex);
        }

        // check messagge
        try {

            checkMessage(SQL_c1);
//            checkMessage(SQL_c2);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // print check SQL
        System.out.println("Query-c1: " + SQL_c1);
//        System.out.println("Query-c2: " + SQL_c2);

        System.out.println("========================");

    }


    private void checkMessage(String msg) throws IOException {

        if (msg.equals("I am  Client 1")){
            // send message to client 2

            client1utfromSever.writeUTF("Client 1 connect");
//            client2utfromSever.writeUTF("Client 1 connect");

        }else if (msg.equals("I am  Client 2")){

            client1utfromSever.writeUTF("Client 2 connect");
//            client2utfromSever.writeUTF("Client 2 connect");
        }

    }
}
