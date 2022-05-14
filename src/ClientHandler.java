import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    Socket sSocket = null ;
    PrintWriter pw = null;
    Database database = null;
    BufferedReader br = null;
    InputStream is  = null;
    int clientId = 0;
    OutputStream os = null;
    public ClientHandler (Socket socket, int clientId, Database db) {
        //complete the constructor
        this.sSocket = socket;
        this.clientId= clientId;
        this.database = db;
    }

    public void run() {
        System.out.println("ClientHandler started...");
        try {
            is = sSocket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            os = sSocket.getOutputStream();
            pw = new PrintWriter(os,true);

            while (true) {
                String clientMessage = br.readLine();
                if (!clientMessage.equals("stop")) {
                    System.out.println("Client sent the artist name " + clientMessage);
                    int titlesNum = database.getTitles(clientMessage);
                    pw.println("Number of titles: " + titlesNum + " records found");
                }
                if (clientMessage.equals("stop")) {
                    System.out.println("Client " + clientId + " has disconnected");
                    pw.println("Connection closed, Goodbye!");
                    break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                pw.close();
                br.close();
                os.close();
                sSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
