package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Clientest {

    public static Socket socket;
    public static DataInputStream din;
    public static DataOutputStream dout;


    public static void main(String[] args) throws IOException {
        System.out.println("Connecting..");

        socket = new Socket("localhost", 7777);
        System.out.println("Connection successful");
        din = new DataInputStream(socket.getInputStream());
        dout = new DataOutputStream(socket.getOutputStream());
        Input input = new Input(din);
        Thread thread = new Thread(input);
        thread.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {

            String sendMessage = scanner.nextLine();
            dout.writeUTF(sendMessage);

        }


    }
}

class Input implements Runnable {

    public DataInputStream din;

    public Input(DataInputStream din) {
        this.din = din;
    }

    @Override
    public void run() {
        while (true) {
            try {

                String message = din.readUTF();
                System.out.println(message);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
