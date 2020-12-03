import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

class ClientHandler implements Runnable {

    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;

    public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.name  = name;
        this.s = s;
        this.isloggedin=true;
    }
    @Override
    public void run() {

        String received;
        while(true) {
            try {
                //recieve the string
                received = dis.readUTF();

                System.out.println(received);
                if(received.equals("logout")) {
                    this.isloggedin=false;
                    this.s.close();
                    break;
                }
                StringTokenizer st = new StringTokenizer(received, ":");

                //To send reciepient
                //original toSend recipient
                String recipient = st.nextToken();
                String toSend = st.nextToken();

                for (ClientHandler mc : Server.ar) {

                    //if the Receipient is found write to output Stream

                    if(mc.name.equals(recipient) && mc.isloggedin){
                        mc.dos.writeUTF(this.name + " : " + toSend);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.dis.close();
            this.dos.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
