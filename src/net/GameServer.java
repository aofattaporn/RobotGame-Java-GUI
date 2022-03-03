package net;

import minaGame.Game;

import java.io.IOException;
import java.net.*;

public class GameServer implements Runnable{

    private DatagramSocket socket;
    private Game game;

    public GameServer(Game game) {
        this.game = game;
        try {
            this.socket = new DatagramSocket(1331);
        } catch (SocketException e) {
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
            System.out.println("CLIENT > [" + packet.getAddress().getHostAddress() +" : " + packet.getPort() + "] > " + message.trim() );

            if (message.trim().equalsIgnoreCase("ping")){
                System.out.println("Returning pong");
                sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
            }
        }
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port){
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
