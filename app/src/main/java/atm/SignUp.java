package atm;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.sql.*;


public class SignUp extends JFrame implements ActionListener{
    private Font labelFont = new Font("Raleway",Font.BOLD,16), bigFont = new Font("Arial",Font.BOLD, 50);
    private final int WIDTH = 700;
    private final int HEIGHT = 900;
    private int width;
    private String accountID, cardNo; //7, 16
    JLabel lWelcome,lName, lMiddlename, lSurname, lGender, lEmail, lPhoneNumber, lAdress, lCity, lVoivodeship, lZipCode;
    JTextField tfName, tfMiddlename, tfSurname, tfEmail, tfPhoneNumber, tfAdress, tfCity, tfVoivodeship, tfZipCode;
    JRadioButton rbMale, rbFemale, rbOther;
    JButton bSignUp;


    public SignUp(){
        setTitle("Automated Teller Machine");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        lWelcome = new JLabel("SIGN UP");
        lWelcome.setFont(bigFont);
        lWelcome.setForeground(Color.BLACK);
        width = calculateWidth(lWelcome);
        lWelcome.setBounds(WIDTH/2 - width/2, 10, width, 50);
        add(lWelcome);

        lName = new JLabel("Enter your name:");
        lName.setFont(labelFont);
        width = calculateWidth(lName);
        lName.setBounds(40, 80, width, 20);
        System.out.println(width);
        add(lName);

        tfName = new JTextField();
        tfName.setFont(labelFont);
        tfName.setBounds(35 , 110, width + 10, 30);
        add(tfName);

        lMiddlename = new JLabel("Enter your middle name:");
        lMiddlename.setFont(labelFont);
        width = calculateWidth(lMiddlename);
        System.out.println(width);
        lMiddlename.setBounds(230, 80, width, 20);
        add(lMiddlename);

        tfMiddlename = new JTextField();
        tfMiddlename.setFont(labelFont);
        tfMiddlename.setBounds(240 , 110, width- 20, 30);
        add(tfMiddlename);

        lSurname = new JLabel("Enter your surname:");
        lSurname.setFont(labelFont);
        width = calculateWidth(lSurname);
        lSurname.setBounds(475, 80, width, 20);
        add(lSurname);

        tfSurname = new JTextField();
        tfSurname.setFont(labelFont);
        tfSurname.setBounds(470 , 110, width + 10, 30);
        add(tfSurname);

        lGender = new JLabel();


        setVisible(true);
    }

    private void generateLoginDetails(){
        Random random = new Random();
        Connector connector = new Connector();

        //Generating accountID
        while(true){
            long randomNumber = Math.abs(random.nextLong()) % 10000000L;
        
            accountID = String.format("%07d", randomNumber);

            String checkQuery = "SELECT COUNT(*) FROM login WHERE accountID = ? ";
            
            try (PreparedStatement statement = connector.conn.prepareStatement(checkQuery)){
                statement.setString(1, accountID); // -> ? = accountID in checkQuery statement 

                ResultSet rs = statement.executeQuery();
                if(rs.next()){
                    int count = rs.getInt(1);
                    if (count == 0){
                        break;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(accountID);

        //Generating cardNo
        while(true){
            long randomNumber = Math.abs(random.nextLong()) % 10000000000000000L;
        
            cardNo = String.format("%016d", randomNumber);

            String checkQuery = "SELECT COUNT(*) FROM login WHERE cardNo = ? ";
            
            try (PreparedStatement statement = connector.conn.prepareStatement(checkQuery)){
                statement.setString(1, cardNo); // -> ? = cardNo in checkQuery statement 

                ResultSet rs = statement.executeQuery();
                if(rs.next()){
                    int count = rs.getInt(1);
                    if (count == 0){
                        break;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    }
    private int calculateWidth(JLabel label){
        return label.getFontMetrics(label.getFont()).stringWidth(label.getText());
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        try {
            if(ae.getSource() == bSignUp){
                generateLoginDetails();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new SignUp();
    }
}
