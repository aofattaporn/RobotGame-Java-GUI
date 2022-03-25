package net;

import entity.ElementPosition;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    private ArrayList<ElementPosition> ABomb;
    private ArrayList<ElementPosition> AET;

    private StringBuilder listX;
    private StringBuilder listY;

    public ClientHandler(Socket socket) {
        try {

            this.listX = new StringBuilder();
            this.listY = new StringBuilder();

            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);

            broadcastMessage(clientUsername);

        } catch (IOException e) {

            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    // create thread
    @Override
    public void run() {

        String messageFromClient;

        while (socket.isConnected()) {

            try {
                messageFromClient = bufferedReader.readLine();

                // check client enter server
                if (messageFromClient.contains("enter server")){
                    ++Server.countClient;

                    if (Server.countClient == 1){

                        // send bomb to client 1
                        randElement(ABomb, 160);
                        broadcastMessage("areaPlayer1 BombX :" + String.valueOf(listX));
                        broadcastMessage("areaPlayer1 BombY :" + String.valueOf(listY));
                        broadcastMessage("Loading player 1 :");

                    }
                }

                broadcastMessage(messageFromClient);

            } catch (IOException e) {
                e.printStackTrace();
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;

            }
        }
    }

    public void broadcastMessage(String messageToSend) {

        // send message to all client
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(clientHandler)) {

                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUsername + " has left the chat! ");

    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void randElement(ArrayList<ElementPosition> element, int size) {

        int randBombX = 0;
        int randBombY = 0;

        for (int i = 0; i < size; i++) {

            randBombX = getRandomPlayer(2, 102);
            randBombY = getRandomPlayer(2, 82);

            this.listX.append(randBombX).append(",");
            this.listY.append(randBombY).append(",");

        }
    }

    private int getRandomPlayer(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


}
