package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {

    public static ServerSocket serverSocket;
    public static Socket socket;
    public static DataOutputStream dout;
    public static DataInputStream din;
    public static Users[] user = new Users[10];

    public static void main(String[] args) throws IOException {
        System.out.println("Server staring ...");
        serverSocket = new ServerSocket(7777);

        while (true) {
            socket = serverSocket.accept();

            for (int i = 0; i < 10; i++) {
                System.out.println("Connection from : " + socket.getInetAddress());
                dout = new DataOutputStream(socket.getOutputStream());
                din = new DataInputStream(socket.getInputStream());

                if (user[i] == null) {
                    // create thread
                    user[i] = new Users(dout, din, user);
                    Thread thread = new Thread(user[i]);
                    thread.start();
                    break;
                }
            }

        }
    }
}

// inner class
class Users implements Runnable{

    public DataOutputStream dout;
    public DataInputStream din;
    public Users[] user;

    public Users(DataOutputStream dout, DataInputStream din, Users[] user) {
        this.dout = dout;
        this.din = din;
        this.user = user;
    }


    @Override
    public void run() {
        while (true){
            try {
                String message = din.readUTF();
                for (int i = 0; i < 10; i++){
                    if (user[i] != null){
                        user[i].dout.writeUTF(message);

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}