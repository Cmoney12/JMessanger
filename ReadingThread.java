import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadingThread implements Runnable {
    public Socket socket;
    public ClientGui gui;
    public DataInputStream input;
    ReadingThread(Socket socket, ClientGui gui) {
        this.socket = socket;
        this.gui = gui;
    }
    @Override
    public void run() {
        while(true) {
            try {
                //Create data input stream
                input = new DataInputStream(socket.getInputStream());

                //get input from the client
                String message = input.readUTF();

                //append message of the Text Area of UI (GUI Thread)
                SwingUtilities.invokeLater(() -> {
                    //display the message in the textarea
                    gui.display.append(message + "\n");
                });
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }
}
