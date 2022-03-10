package testing;

import java.io.*;
import java.net.Socket;

public class testClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9999);
        OutputStream out = socket.getOutputStream();
        DataOutputStream dataOut = new DataOutputStream(out);

        String login = "user";
        String password = "password";
        dataOut.writeUTF(login);
        dataOut.writeUTF(password);

        InputStream in = socket.getInputStream();
        DataInputStream dataIn = new DataInputStream(in);
        String str = dataIn.readUTF();

        System.out.println("client 1");


        System.out.println(str);
        dataOut.close();
    }
}
