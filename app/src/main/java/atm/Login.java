package atm;

import java.awt.Font;
import java.awt.Image;

import javax.swing.*;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame implements ActionListener{
    private JLabel lWelcome, lCardNo, lPin;
    private JTextField tfCardNo;
    private JPasswordField pfPin;
    private JButton bSignIn, bClear, bSignUp;
    private Font commonFont = new Font("Arial",Font.BOLD, 24);
    private final int WIDTH = 480;
    private final int HEIGHT = 600;
    private int width;
    private String format = "XXXX-XXXX-XXXX-XXXX";

    public Login(){
        final int BUTTONWIDTH = 100;
        setTitle("Automated Teller Machine");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/atmIcon.png"));
        Image image = icon.getImage();
        setIconImage(image);

        lWelcome = new JLabel("WELCOME TO ATM");
        lWelcome.setFont(commonFont);
        width = lWelcome.getFontMetrics(commonFont).stringWidth(lWelcome.getText());
        lWelcome.setBounds(WIDTH/2 - width/2, 30, width, 40);
        add(lWelcome);

        lCardNo = new JLabel("Card Number:");
        lCardNo.setFont(commonFont.deriveFont(20F));
        width = lCardNo.getFontMetrics(lCardNo.getFont()).stringWidth(lCardNo.getText());
        lCardNo.setBounds(WIDTH/2 - width/2, 100, width, 40);
        add(lCardNo);

        tfCardNo = new JTextField(format,19);
        tfCardNo.setFont(commonFont.deriveFont(16F));
        tfCardNo.setForeground(Color.GRAY);
        tfCardNo.setBounds(WIDTH / 2 - 200 / 2, 140, 200, 32);
        tfCardNo.setEditable(false);
        tfCardNo.addMouseListener(new MouseAdapter() {
            boolean firstClicked = false; 
            // PLACEHOLDER XXXX-XXXX-XXXX-XXXX
            @Override
            public void mouseEntered(MouseEvent e) {
                if (tfCardNo.getText().equals(format)) {
                    tfCardNo.setText("");              
                    tfCardNo.getCaret().setVisible(true);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                tfCardNo.setEditable(true);
                tfCardNo.getCaret().setVisible(true);
                firstClicked = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (tfCardNo.getText().isEmpty() && !firstClicked) {
                    tfCardNo.setText(format);
                    tfCardNo.setForeground(Color.GRAY);
                    tfCardNo.getCaret().setVisible(false);
                }
            }
        });
        add(tfCardNo);
        
        lPin = new JLabel("Pin:");
        lPin.setFont(commonFont.deriveFont(20F));
        width = lPin.getFontMetrics(lPin.getFont()).stringWidth(lPin.getText());
        lPin.setBounds(WIDTH/2 - width/2, 200, width, 40);
        add(lPin);

        pfPin = new JPasswordField(4);
        pfPin.setEchoChar('*');
        pfPin.setFont(commonFont.deriveFont(Font.BOLD,18F));
        pfPin.setBounds(WIDTH / 2 - 40 / 2, 240, 40, 32);
        add(pfPin);

        bSignIn = new JButton("Sign In");
        bSignIn.setFont(commonFont.deriveFont(14F));
        bSignIn.setBackground(Color.black);
        bSignIn.setForeground(Color.white);
        bSignIn.setBorderPainted(true);
        width = bSignIn.getFontMetrics(bSignIn.getFont()).stringWidth(bSignIn.getText());
        bSignIn.setBounds(190, 300, BUTTONWIDTH, 50);
        add(bSignIn);

        bClear = new JButton("Clear");
        bClear.setFont(commonFont.deriveFont(14F));
        bClear.setBackground(Color.black);
        bClear.setForeground(Color.white);
        bClear.setBorderPainted(true);
        bClear.setBounds(90, 300, BUTTONWIDTH, 50);
        add(bClear);

        bSignUp = new JButton("Sign Up");
        bSignUp.setFont(commonFont.deriveFont(14F));
        bSignUp.setBackground(Color.black);
        bSignUp.setForeground(Color.white);
        bSignUp.setBorderPainted(true);
        bSignUp.setBounds(290, 300, BUTTONWIDTH, 50);
        add(bSignUp);

        //* Adding actionListeners to buttons
        bSignIn.addActionListener(this);
        bSignUp.addActionListener(this);
        bClear.addActionListener(this);


        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == bSignIn){
            String cardNo = tfCardNo.getText().replaceAll("-", "").strip();
            String pin = String.valueOf(pfPin.getPassword());
            String query = "SELECT * FROM login WHERE cardNo = '" +cardNo+ "' AND pin = '"+pin+"'";
            Connector connector = new Connector();
            try{
                ResultSet rs = connector.statement.executeQuery(query);
                String accountID;
                if(rs.next()){
                    accountID = rs.getString("accountID");
                    rs.close();
                    connector.statement.close();
                    connector.conn.close();
                    setVisible(false);
                    new BankStatement(accountID);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Incorrect card number or pin code!");
                    tfCardNo.setText(format);
                    pfPin.setText("");
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }else if(actionEvent.getSource() == bClear){
            tfCardNo.setText(format);
            pfPin.setText("");
        }else if(actionEvent.getSource() == bSignUp){
            setVisible(false);
            new SignUp();
        }
            
    }

    public static void main(String[] args) {
        new Login();
    }

    
}
