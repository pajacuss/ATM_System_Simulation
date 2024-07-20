package atm;

import javax.swing.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;

public class Withdrawl extends JFrame implements ActionListener {
    private String accountID;
    private int width;

    private Connector connector;
    private JLabel l1, l2, l3;
    private JButton bConfirm, bBack;
    private JTextField tfWithdrawl;
    private JPasswordField pfPin;

    private final int WIDTH = 480;
    private final int HEIGHT = 600;

    Font basicFont = new Font("Arial",Font.BOLD,20), welcomeFont = new Font("Arial",Font.BOLD, 40);
    public Withdrawl(String accountID){
        this.accountID = accountID;

        setSize(WIDTH,HEIGHT);
        setTitle("Automated Teller Machine");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/atmIcon.png"));
        Image image = icon.getImage();
        setIconImage(image);

        l1 = new JLabel("WITHDRAW");
        l1.setFont(welcomeFont);
        width = calculateWidth(l1);
        l1.setBounds(WIDTH/2 - width/2, 50, width, 30);
        add(l1);

        l2 = new JLabel("Enter the amount you want to withdraw:");
        l2.setFont(basicFont);
        width = calculateWidth(l2);
        l2.setBounds(WIDTH/2 - width/2, 130, width, 60);
        add(l2);
        
        tfWithdrawl = new JTextField();
        tfWithdrawl.setFont(basicFont.deriveFont(Font.BOLD,30));
        tfWithdrawl.setBounds(240 -170/2, 190, 170, 50);
        add(tfWithdrawl);

        l3 = new JLabel("Confirm with your pin:");
        l3.setFont(basicFont);
        width = calculateWidth(l3);
        l3.setBounds(WIDTH/2 - width/2, 240, width, 60);
        add(l3);
        

        pfPin = new JPasswordField(4);
        pfPin.setEchoChar('*');
        pfPin.setFont(basicFont.deriveFont(Font.BOLD,18F));
        pfPin.setBounds(WIDTH / 2 - 40 / 2, 300, 40, 32);
        add(pfPin);

        bConfirm = new JButton("Confirm");
        bConfirm.setFont(basicFont);
        bConfirm.setBackground(Color.black);
        bConfirm.setForeground(Color.white);
        bConfirm.setBorderPainted(true);
        bConfirm.setBounds(240 - 75, 350, 150, 40);
        add(bConfirm);

        bBack = new JButton("Go back");
        bBack.setFont(basicFont);
        bBack.setBackground(Color.black);
        bBack.setForeground(Color.white);
        bBack.setBorderPainted(true);
        bBack.setBounds(240 - 75, 400, 150, 40);
        add(bBack);


        bConfirm.addActionListener(this);
        bBack.addActionListener(this);

        setVisible(true);
    }

    private int calculateWidth(JLabel label){
        return label.getFontMetrics(label.getFont()).stringWidth(label.getText());
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == bConfirm){
            Date date = new Date();
            connector = new Connector();
            int balance = 0;
            String pin = String.valueOf(pfPin.getPassword());
            try {
                ResultSet rs = connector.statement.executeQuery("SELECT * FROM login WHERE accountID = '"+accountID+"' AND pin = '"+pin+"'");
                if(rs.next()){
                    balance = rs.getInt("balance");
                    int withdrawn = Integer.parseInt(tfWithdrawl.getText().strip());
                    if(balance>withdrawn){
                        balance -= withdrawn;
                    try {
                        connector.statement.executeUpdate("UPDATE login SET balance = "+balance+" WHERE accountID = '"+accountID+"'");
                        connector.statement.executeUpdate("INSERT INTO bank VALUES('"+accountID+"',"+withdrawn+",'Withdrawl',"+balance+",'"+date+"')");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    setVisible(false);
                    new BankStatement(accountID);
                    }else{
                        JOptionPane.showMessageDialog(null, "Insufficent funds to make this withdrawl");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Wrong pin!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } 
        }else if(actionEvent.getSource() == bBack){
            setVisible(false);
            new BankStatement(accountID);
        }
    }
}
