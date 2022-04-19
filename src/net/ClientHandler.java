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

    private StringBuilder listBombX, listBombY, listETX, listETY;

    public ClientHandler(Socket socket) {
        try {

            this.listBombX = new StringBuilder();
            this.listBombY = new StringBuilder();
            this.listETX = new StringBuilder();
            this.listETY = new StringBuilder();
            this.AET = new ArrayList<>();
            this.ABomb = new ArrayList<>();

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

                if (messageFromClient != null) {
                    // check client enter server
                    if (messageFromClient.contains("enter server")) {

                        System.out.println("have " + clientHandlers.size() + " client in server");
                        System.out.println("----------------------");
                        if (clientHandlers.size() == 1) {

                            // send bomb to client 1
                            randElement(160, 40);
                            broadcastMessage(
                                    "areaPlayer1 BombX :" + String.valueOf(listBombX)
                                    + "ETX :" + String.valueOf(listETX));
                            broadcastMessage(
                                    "areaPlayer1 BombY :" + String.valueOf(listBombY)
                                    + "ETY :" + String.valueOf(listETY));
                            broadcastMessage("Loading player 1 :");

                        }
                    } else if (messageFromClient.contains("end game")) {
                        System.out.println(messageFromClient);
                        removeClientHandler();
                    }

                    broadcastMessage(messageFromClient);
                }


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
        System.out.println("SERVER: " + " has some client left the server! ");
        System.out.println("----------------------");
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

    public void randElement(int sizeBomb, int sizeET) {

        int randBombX = 0;
        int randBombY = 0;

        for (int i = 0; i < sizeBomb; i++) {

            randBombX = getRandomPlayer(2, 80);
            randBombY = getRandomPlayer(2, 80);

            this.listBombX.append(randBombX).append(",");
            this.listBombY.append(randBombY).append(",");

        }

        randBombX = 0;
        randBombY = 0;

        for (int i = 0; i < sizeET; i++) {

            randBombX = getRandomPlayer(2, 80);
            randBombY = getRandomPlayer(2, 80);

            this.listETX.append(randBombX).append(",");
            this.listETY.append(randBombY).append(",");

        }

    }

    public void randElement2(ArrayList<ElementPosition> element, int size) {

        int randETX = 0;
        int randETY = 0;

        if (element.equals(AET)) {

            for (int i = 0; i < size; i++) {

                randETX = getRandomPlayer(2, 102);
                randETY = getRandomPlayer(2, 82);

                this.listETX.append(randETX).append(",");
                this.listETY.append(randETY).append(",");

            }
        }


    }

    private int getRandomPlayer(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


}
