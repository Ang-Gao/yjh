import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {


    public static void main(String[] args) throws IOException {
        System.out.println("Client is running");
        String artistName,serverMessage;
        Socket cSocket = null;

        PrintWriter pw = null;

        BufferedReader br = null;
        InetAddress cServer = null;

        cServer = InetAddress.getByName(Credentials.HOST);
        cSocket = new Socket(cServer, Credentials.PORT);

        pw = new PrintWriter(cSocket.getOutputStream(),true);

        br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader((System.in)));


        do {
            System.out.println("Enter the artist name:");
            artistName = inFromUser.readLine();
            System.out.println("You entered " + artistName);
            pw.println(artistName);
            serverMessage = br.readLine();
            System.out.println("FROM SERVER: " + serverMessage);
        } while (!artistName.equals("stop"));




        br.close();
        pw.close();
        inFromUser.close();
        cSocket.close();
    }
}
