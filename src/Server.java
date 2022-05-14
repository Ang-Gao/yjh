import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) throws IOException {
        Database database = new Database();
        System.out.println("Server is running" );
        ServerSocket tPort = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        InputStream inputStream = null;
        Socket serverSocket = null;

        if (!database.establishDBConnection()){
            System.out.println("DB connection fail, stopping.");
            return;
        }

        System.out.println("Server is now connected to DB");

        tPort = new ServerSocket(Credentials.PORT);

        int clientId = 0;
        while (true) {
            Socket cs = tPort.accept();
            clientId ++;
            System.out.println("Client " + clientId + " connected with IP " + cs.getInetAddress().getHostAddress());
            ClientHandler clientHandler = new ClientHandler(cs,clientId,database);
            Thread t =  new Thread(clientHandler);
            t.start();
        }

    }
}

