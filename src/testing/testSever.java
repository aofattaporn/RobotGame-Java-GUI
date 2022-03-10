package testing;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class testSever {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket socket;

        // waiting for client
        InputStream in;
        DataInputStream dataIn;
        OutputStream out;
        DataOutputStream dataOut;


        while (true){
            socket = serverSocket.accept();
            in = socket.getInputStream();
            dataIn = new DataInputStream(in);

            String login = dataIn.readUTF();
            String password = dataIn.readUTF();
            String result = "Login and Password ";
            if (login.equals("java") && password.equals("Password")){
                result += " correct ";
            }else {
                result += "wrong";
            }

            out = socket.getOutputStream();
            dataOut = new DataOutputStream(out);
            dataOut.writeUTF(result);
        }
    }
}
