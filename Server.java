import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
    // Vector to store active clients
    static Vector<ClientHandler> ar = new Vector<>();

    // counter for clients
    static int i = 0;

    public static void main(String[] args) throws IOException {
        //start listening
        ServerSocket ss = new ServerSocket(1234);

        Socket s;

        while (true) {
            s = ss.accept();

            System.out.println("New Client Connected " + s);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            System.out.println("Creating a new handler");
            dos.writeUTF("Connected ");
            String username = dis.readUTF();
            System.out.print(username);

            ClientHandler mtch = new ClientHandler(s, username, dis, dos);

            Thread t = new Thread(mtch);

            ar.add(mtch);

            t.start();

            i++;
        }
    }
}
