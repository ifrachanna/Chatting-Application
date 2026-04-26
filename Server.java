import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server implements ActionListener {
    
    JTextField textServer;
    JPanel panelold;
    static Box vertical = Box.createVerticalBox();
    static JFrame mainframe = new JFrame();
    static DataOutputStream dout;
    
    Server() {
        
        mainframe.setLayout(null);
        JPanel panelnew = new JPanel();
        panelnew.setBackground(new Color(78, 159, 229));
        panelnew.setBounds(0, 0, 450, 70);
        panelnew.setLayout(null);
        mainframe.add(panelnew);
        
        ImageIcon img1 = new ImageIcon(ClassLoader.getSystemResource("icons/arrow.jpg"));
        Image img2 = img1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon img3 = new ImageIcon(img2);
        JLabel back = new JLabel(img3);
        back.setBounds(5, 20, 25, 25);
        panelnew.add(back);
        
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });
        
        ImageIcon img4 = new ImageIcon(ClassLoader.getSystemResource("icons/min.png"));
        Image img5 = img4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon img6 = new ImageIcon(img5);
        JLabel profile = new JLabel(img6);
        profile.setBounds(40, 10, 50, 50);
        panelnew.add(profile);
        
        ImageIcon img7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image img8 = img7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon img9 = new ImageIcon(img8);
        JLabel video = new JLabel(img9);
        video.setBounds(300, 20, 30, 30);
        panelnew.add(video);
        
        ImageIcon img10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image img11 = img10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon img12 = new ImageIcon(img11);
        JLabel phone = new JLabel(img12);
        phone.setBounds(360, 20, 35, 30);
        panelnew.add(phone);
        
        ImageIcon img13 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image img14 = img13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon img15 = new ImageIcon(img14);
        JLabel morevert = new JLabel(img15);
        morevert.setBounds(420, 20, 10, 25);
        panelnew.add(morevert);
        
        JLabel name = new JLabel("Ifra Channa");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        panelnew.add(name);
        
        JLabel status = new JLabel("Active Now");
        status.setBounds(110, 35, 100, 18);
        status.setForeground(Color.white);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        panelnew.add(status);
        
        panelold = new JPanel();
        panelold.setBounds(5, 75, 440, 570);
        mainframe.add(panelold);
        
        textServer = new JTextField();
        textServer.setBounds(5, 655, 310, 40);
        textServer.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        mainframe.add(textServer);
        
        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(78, 159, 229));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        mainframe.add(send);
        
        mainframe.setSize(450, 700);
        mainframe.setLocation(200, 50);
        mainframe.setUndecorated(true);
        mainframe.getContentPane().setBackground(Color.WHITE);
        
        mainframe.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        try {
            String out = textServer.getText();

            JPanel p2 = formatLabel(out);

            panelold.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            panelold.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            textServer.setText("");

            mainframe.repaint();
            mainframe.invalidate();
            mainframe.validate();   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(3, 169, 244));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        
        return panel;
    }
    
    public static void main(String[] args) {
        new Server();
        
        try {
            ServerSocket skt = new ServerSocket(6001);
            while(true) {
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                
                while(true) {
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    mainframe.validate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
