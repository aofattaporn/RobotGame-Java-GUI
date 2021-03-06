package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public static int countClient = 0;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {

        System.out.println("Starting Server ...");
        System.out.println("======================");

        try{

            while (!serverSocket.isClosed()){

                Socket socket = serverSocket.accept();

                System.out.print("A new client has connected! => ");
                ClientHandler clientHandler = new ClientHandler(socket);

                // add each client to server for multi client
                Thread thread = new Thread(clientHandler);
                thread.start();

            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void closeServerSocket(){
        try{
            if (serverSocket != null){
                serverSocket.close();
            }
        }catch (IOException e){

            e.printStackTrace();
        }
    }

    public static int setCountClient(int countClient) {
        return Server.countClient = countClient;
    }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(9999);
        Server server = new Server(serverSocket);
        server.startServer();

    }

}
