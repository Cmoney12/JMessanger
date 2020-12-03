import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Log extends JFrame {

    public static void main(String[] args) {
        Log frameTabel = new Log();
    }

    public JButton blogin;
    public JPanel panel;
    public JTextField txuser;
    public JPasswordField pass;
    public JLabel user_label, password_label, message;
    public JButton register;

    Log(){
        super("JMessenger Login");
        user_label = new JLabel();
        user_label.setText("User Name:");
        password_label = new JLabel();
        password_label.setText("Password:");
        panel = new JPanel();
        txuser = new JTextField();
        pass = new JPasswordField();
        blogin = new JButton("Login");
        register = new JButton("Register");

        Image icon = Toolkit.getDefaultToolkit().getImage("orbLogo.png");
        setIconImage(icon);

        GridLayout layout = new GridLayout(3,1, 25, 75);
        panel.setLayout(layout);

        //borders
        EmptyBorder emptyBorder = new EmptyBorder(20,20,20,20);
        //panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        Font font = new Font("Magneto", Font.BOLD, 12);
        TitledBorder titledBorder = new TitledBorder("JMessenger");
        titledBorder.setTitleFont(font);
        titledBorder.setTitleColor(Color.BLUE);
        CompoundBorder compoundBorder = new CompoundBorder(emptyBorder, titledBorder);

        panel.setBorder(compoundBorder);

        //panel.setBorder(BorderFactory.createTitledBorder("JMessenger").setTitleFont(
        //        new Font("Magneto", Font.BOLD,16)));


        setSize(525,350);

        panel.add(user_label);
        panel.add(txuser);
        panel.add(password_label);
        panel.add(pass);
        panel.add(register);
        panel.add(blogin);

        add(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        actionlogin();
    }

    public void actionlogin(){
        blogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String puname = txuser.getText();
                String ppaswd = pass.getText();
                if(!puname.isEmpty() && !ppaswd.isEmpty()) {
                    ClientGui clientgui = new ClientGui();
                    clientgui.setVisible(true);
                    clientgui.setUsername(puname);
                    clientgui.runClient();
                    //clientgui.username = puname;
                    //clientgui.setUsername(puname);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null,"Wrong Password / Username");
                    txuser.setText("");
                    pass.setText("");
                    txuser.requestFocus();
                }
            }
        });
    }
}
