import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientGui extends JFrame {
    private DataOutputStream output;
    private DataInputStream input;
    public int ServerPort = 1234;
    private Socket s;
    private JTextArea display;
    public String username;
    public String currReciever;

    ClientGui() {
        JSplitPane splitPane = new JSplitPane();
        JPanel rightContainerPanel = new JPanel();
        rightContainerPanel.setLayout(new BorderLayout());

        JPanel leftContainterPanel = new JPanel();
        leftContainterPanel.setLayout(new BorderLayout());

        JPanel leftTop = new JPanel();
        leftTop.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();

        rightPanel.setLayout(new BorderLayout());
        leftPanel.setLayout(new BorderLayout());
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        //set Logo
        Image icon = Toolkit.getDefaultToolkit().getImage("orbLogo.png");
        setIconImage(icon);

        display = new JTextArea(16, 58);
        display.setEditable(false); // set textArea non-editable
        JScrollPane scroll = new JScrollPane(display);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        rightPanel.add(scroll);
        JTextField messageField = new JTextField();
        JButton button1 = new JButton("SendMessage");
        button1.addActionListener(e -> sendMessage(messageField.getText()));

        JMenuBar menuBar = new JMenuBar();
        JMenu jMenu = new JMenu("Options");
        menuBar.add(jMenu);
        JMenuItem changeUsername = new JMenuItem("Change username");
        jMenu.add(changeUsername);
        /*changeUsername.addActionListener(e -> {
            ChangeUsername usernameChange = new ChangeUsername();
            usernameChange.usernameChange();
        });*/
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> list = new JList<>(listModel);
        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JTextField searchUser = new JTextField();


        JButton searchButton = new JButton("User");
        searchButton.addActionListener(e -> {
            //DefaultListModel<String> listmodel = new DefaultListModel<>();
            String receipient = searchUser.getText();
            listModel.addElement(receipient);
            list.setModel(listModel);
        });

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                currReciever = list.getSelectedValue();
            }
        });

        leftPanel.add(listScroll);

        leftTop.add(searchButton, BorderLayout.EAST);
        leftTop.add(searchUser);
        leftContainterPanel.add(leftTop, BorderLayout.NORTH);

        leftContainterPanel.add(leftPanel);

        bottomPanel.add(button1, BorderLayout.WEST);
        bottomPanel.add(messageField);

        rightContainerPanel.add(rightPanel);
        rightContainerPanel.add(bottomPanel, BorderLayout.SOUTH);

        splitPane.setRightComponent(rightContainerPanel);
        splitPane.setLeftComponent(leftContainterPanel);

        add(splitPane);

        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setJMenuBar(menuBar);

    }

    public void runClient() {
        try {
            System.out.println("Attempting to connect to Server");
            try {
                InetAddress ip = InetAddress.getByName("localhost");
                s = new Socket(ip, ServerPort);

                //Send username

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e, "Warning", JOptionPane.WARNING_MESSAGE);
            }
            System.out.println("connection successful");
            output = new DataOutputStream(s.getOutputStream());
            output.flush();
            input = new DataInputStream(s.getInputStream());
            //call method
            startChat();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e, "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void startChat() throws IOException {
        try {
            String message = input.readUTF();
            //display.append("\n" + message);
            if(message.equals("Connected ")){
                sendUserName(username);
            } else {
                display.append("\n" + message);
                message = "";
            }
        } catch (IOException e) {
            throw e;
        }
    }

    public void setUsername(String user) {
        username = user;
        System.out.println(user);
    }
    public void sendUserName(String username) {
        try {
            output.writeUTF(username);
            output.flush();
        } catch (IOException e) {
            display.append("Username could not be sent");
        }
    }

    public void sendMessage(String message) {
        try {
            display.append("\t" + "\n" + message);
            output.writeUTF(currReciever + ":" + message);
            output.flush();
        } catch (IOException e) {
            display.append("OOOPs message was unsuccessful");
        }
    }
}
