package atm;

import java.awt.Font;
import java.awt.Image;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;


public class BankStatement extends JFrame implements ActionListener{
    
    private String name, cardNo, accountID;
    private int balance, width;
    private Connector connector;
    private List<String> history = new ArrayList<>(); 

    Font basicFont = new Font("Arial",Font.BOLD,20), welcomeFont = new Font("Arial",Font.BOLD, 30), historyFont = new Font("Raleway",Font.BOLD,14);

    private JLabel lWelcome, lBalance, lCardNo, lHistory;
    private JButton bDeposit, bWithdraw, bLogout;

    private final int WIDTH = 480;
    private final int HEIGHT = 600;

    public BankStatement(String accountID){
        this.accountID = accountID;
        getUserInfo();
        setTitle("Automated Teller Machine");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon(getClass().getResource("/images/atmIcon.png"));
        Image image = icon.getImage();
        setIconImage(image);

        lWelcome = new JLabel("Hello, "+name);
        lWelcome.setFont(welcomeFont);
        width = calculateWidth(lWelcome);
        lWelcome.setBounds(WIDTH/2 - width/2, 50, width, 30);
        add(lWelcome);

        lCardNo = new JLabel("Your card number: "+cardNo);
        lCardNo.setFont(basicFont);
        width = calculateWidth(lCardNo);
        lCardNo.setBounds(WIDTH/2 - width/2, 100, width, 30);
        add(lCardNo);

        lBalance = new JLabel("Your balance: $"+ balance);
        lBalance.setFont(basicFont);
        width = calculateWidth(lBalance);
        lBalance.setBounds(WIDTH/2 - width/2, 150, width, 30);
        add(lBalance);

        bDeposit = new JButton("Deposit");
        bDeposit.setFont(basicFont);
        bDeposit.setBackground(Color.black);
        bDeposit.setForeground(Color.white);
        bDeposit.setBorderPainted(true);
        bDeposit.setBounds(80, 200, 150, 40);
        add(bDeposit);

        bWithdraw = new JButton("Withdraw");
        bWithdraw.setFont(basicFont);
        bWithdraw.setBackground(Color.black);
        bWithdraw.setForeground(Color.white);
        bWithdraw.setBorderPainted(true);
        bWithdraw.setBounds(250, 200, 150, 40);
        add(bWithdraw);

        bDeposit.addActionListener(this);
        bWithdraw.addActionListener(this);

        lHistory = new JLabel("Your recent transactions:");
        lHistory.setFont(basicFont);
        width = calculateWidth(lHistory);
        lHistory.setBounds(WIDTH/2 - width/2, 270, width, 30);
        add(lHistory);


        int limit;
        if(history.size()< 5){
            limit = history.size();
        }
        else{limit = 5;}
        for(int i = 0; i < limit; i++){
            JLabel label = new JLabel(history.get(history.size()-1-i));
            label.setFont(historyFont);
            width = calculateWidth(label);
            label.setBounds(WIDTH/2 - width/2, 300 + i *20, width, 25);
            add(label);
        }

        bLogout = new JButton("Log out");
        bLogout.setFont(basicFont);
        bLogout.setBackground(Color.black);
        bLogout.setForeground(Color.white);
        bLogout.setBorderPainted(true);
        bLogout.setBounds(240 - 75, 500, 150, 40);
        add(bLogout);
        bLogout.addActionListener(this);

        setVisible(true);
    }

    private void getUserInfo(){
        connector = new Connector();
        String record="";
        try {
            ResultSet nameSet = connector.statement.executeQuery("SELECT * FROM userdata WHERE accountID = '"+accountID+"'");
            while (nameSet.next()){
                name = nameSet.getString("name");
            }
            nameSet.close();

            ResultSet balanceSet = connector.statement.executeQuery("SELECT * FROM login WHERE accountID = '"+accountID+"'");
            while(balanceSet.next()){
                cardNo = balanceSet.getString("cardNo");
                balance = balanceSet.getInt("balance");
            }
            balanceSet.close();
            ResultSet historySet = connector.statement.executeQuery("SELECT * FROM bank WHERE accountID = '"+accountID+"'");
            while(historySet.next()){
                record = historySet.getString("transaction")+", Amount: "+historySet.getString("amount")+", "+historySet.getString("date")+".";
                history.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int calculateWidth(JLabel label){
        return label.getFontMetrics(label.getFont()).stringWidth(label.getText());
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == bDeposit){
            setVisible(false);
            new Deposit(accountID);
        }
        else if(actionEvent.getSource() == bWithdraw){
            setVisible(false);
            new Withdrawl(accountID);
        }
        else if(actionEvent.getSource() == bLogout){
            setVisible(false);
            new Login();
        }
    }
}
