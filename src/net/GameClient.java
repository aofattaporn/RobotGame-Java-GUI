package net;

import minaGame.Game;

import java.io.IOException;
import java.net.*;

public class GameClient implements Runnable{

    private InetAddress ipAddress;
    private DatagramSocket socket;
    private Game game;

    // constructor
    public GameClient(Game game, String ipAddress)  {
        this.game = game;
        try {
            this.socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while (true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);

            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String message = new String(packet.getData());
            System.out.println("SEVER > " + message.trim());

        }
    }

    public void sendData(byte[] data){
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
