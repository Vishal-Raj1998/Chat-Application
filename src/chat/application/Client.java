/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat.application;
import static chat.application.Server.f;
import static chat.application.Server.formatLabel;
import static chat.application.Server.vertical;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import static java.awt.Font.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.font.TextAttribute.FONT;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
/**
 *
 * @author Dell
 */
public class Client implements ActionListener{
    static JFrame f=new JFrame();
    JTextField field;
   static  JPanel ChatArea;
     static Box vertical = Box.createVerticalBox();
     static DataInputStream din;
      static DataOutputStream dout;
    Client(){
        f.setLayout(null);
        JPanel p1=new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
         f.add(p1);
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2=i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
     
        JLabel back=new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);
        back.addMouseListener(new MouseAdapter(){
             public void mouseClicked(MouseEvent e) {
//                 setVisible(false);
                    System.exit(0); 
             }
        }
        );
        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5=i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
     
        JLabel profile=new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);
       
        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8=i7.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);
        JLabel video=new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);
        
        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11=i10.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);
     
        JLabel audio=new JLabel(i12);
        audio.setBounds(360,20,30,30);
        p1.add(audio);
        
        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14=i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);
           
        JLabel ThreeDot=new JLabel(i15);
        ThreeDot.setBounds(410,20,10,25);
        p1.add(ThreeDot);
            
        JLabel name=new JLabel("Kunal");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF",BOLD, 16));
        p1.add(name);
        
        JLabel status=new JLabel("Active now");
        status.setBounds(110,40,100,18);
        status.setForeground(Color.white);
        status.setFont(new Font("SAN_SERIF",BOLD, 10));
        p1.add(status);
            
        ChatArea=new JPanel();
        ChatArea.setBounds(0,70,435,550);
         f.add(ChatArea);
         field=new JTextField();
        field.setBounds(5,625,315,35);
        field.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
         f.add(field);
        
        JButton send=new JButton("Send");
        send.setBounds(325,625,105,35);
        send.setBackground(new Color(5,94,84));
        send.setForeground(Color.WHITE);
       send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
         f.add(send);
        send.addActionListener(this);
         f.setSize(450,700);
         f.setLocation(200,50);
         f.getContentPane().setBackground(Color.white);
         f.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        String out=field.getText();
//        JLabel output = new JLabel(out);
        JPanel  p2=formatLabel(out);
//        p2.add(output);
        System.out.println(out);
        ChatArea.setLayout(new BorderLayout());
        JPanel right=new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);
     
        vertical.add(right);
        
        vertical.add(Box.createVerticalStrut(15));
        ChatArea.add(vertical,BorderLayout.PAGE_START);
        try {
            dout.writeUTF(out);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        field.setText("");
         f.repaint();
         f.invalidate();
         f.validate();
    }
    public static JPanel formatLabel(String out){
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel output=new JLabel("<html><p style=\" width: 150px \">"+out+"</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,10,50));
        panel.add(output); 
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        JLabel time=new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
    }
    public static void main(String args[]){
        new Client();
        try{
            
            Socket s=new Socket("127.0.0.1",6001);
            din=new DataInputStream(s.getInputStream());
            dout=new DataOutputStream(s.getOutputStream());
            while(true){
                    ChatArea.setLayout(new BorderLayout());
                     String msg=din.readUTF();
                     JPanel panel=formatLabel(msg);
                     JPanel left =new JPanel(new BorderLayout());
                     left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    vertical.add(Box.createVerticalStrut(15));
                    ChatArea.add(vertical,BorderLayout.PAGE_START);
                     f.validate();
                 }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
