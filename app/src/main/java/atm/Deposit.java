package atm;


import java.awt.Font;
import java.awt.Image;

import javax.swing.*;
import java.sql.*;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class Deposit extends JFrame implements ActionListener{
    private String accountID;

    private JButton bConfirm, bBack;
    private JLabel l1, l2;
    private JTextField tfDeposit;
    private Connector connector;

    private final int WIDTH = 480;
    private final int HEIGHT = 600;

    Font basicFont = new Font("Arial",Font.BOLD,20), welcomeFont = new Font("Arial",Font.BOLD, 40);

    private int width;

    public Deposit(String accountID){
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

        l1 = new JLabel("DEPOSIT");
        l1.setFont(welcomeFont);
        width = calculateWidth(l1);
        l1.setBounds(WIDTH/2 - width/2, 50, width, 30);
        add(l1);

        l2 = new JLabel("Enter the amount you want to deposit:");
        l2.setFont(basicFont);
        width = calculateWidth(l2);
        l2.setBounds(WIDTH/2 - width/2, 130, width, 60);
        add(l2);
        
        tfDeposit = new JTextField();
        tfDeposit.setFont(basicFont.deriveFont(Font.BOLD,30));
        tfDeposit.setBounds(140, 220, 200, 50);
        add(tfDeposit);

        bConfirm = new JButton("Confirm");
        bConfirm.setFont(basicFont);
        bConfirm.setBackground(Color.black);
        bConfirm.setForeground(Color.white);
        bConfirm.setBorderPainted(true);
        bConfirm.setBounds(240 - 75, 300, 150, 40);
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
            try {
                ResultSet rs = connector.statement.executeQuery("SELECT * FROM login WHERE accountID = '"+accountID+"'");
                while(rs.next()){
                    balance = rs.getInt("balance");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int deposited = Integer.parseInt(tfDeposit.getText().strip());
            balance += deposited;
            try {
                connector.statement.executeUpdate("UPDATE login SET balance = "+balance+" WHERE accountID = '"+accountID+"'");
                connector.statement.executeUpdate("INSERT INTO bank VALUES('"+accountID+"',"+deposited+",'Deposit',"+balance+",'"+date+"')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            setVisible(false);
            new BankStatement(accountID);
        }else if(actionEvent.getSource() == bBack){
            setVisible(false);
            new BankStatement(accountID);
        }
    }
}
