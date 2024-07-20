package atm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.sql.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class SignUp extends JFrame implements ActionListener{
    private Font labelFont = new Font("Raleway",Font.BOLD,16), bigFont = new Font("Arial",Font.BOLD, 50);
    private final int WIDTH = 600;
    private final int HEIGHT = 800;
    private int width;
    private String accountID, cardNo, pin; //7, 16
    JLabel lWelcome,lName, lMiddlename, lSurname, lGender, lEmail, lPhoneNumber, lAdress, lCity, lState, lZipCode, lDoB, lPin, lCountry;
    JTextField tfName, tfMiddlename, tfSurname, tfEmail, tfPhoneNumber, tfAdress, tfCity, tfState, tfZipCode, tfDoB, tfCountry;
    JPasswordField pfPin;
    JRadioButton rbMale, rbFemale, rbOther;
    JButton bSignUp;

    Connector connector;


    public SignUp(){
        setTitle("Automated Teller Machine");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon(getClass().getResource("/images/atmIcon.png"));
        Image image = icon.getImage();
        setIconImage(image);

        lWelcome = new JLabel("SIGN UP");
        lWelcome.setFont(bigFont);
        lWelcome.setForeground(Color.BLACK);
        width = calculateWidth(lWelcome);
        lWelcome.setBounds(WIDTH/2 - width/2, 10, width, 50);
        add(lWelcome);

        // name, middle name, surname
        lName = new JLabel("Enter your name:");
        lName.setFont(labelFont);
        width = calculateWidth(lName);
        lName.setBounds(40, 80, width, 20);
        add(lName);

        tfName = new JTextField();
        tfName.setFont(labelFont);
        tfName.setBounds(35 , 110, width + 10, 30);
        add(tfName);

        lMiddlename = new JLabel("Enter your middle name:");
        lMiddlename.setFont(labelFont);
        width = calculateWidth(lMiddlename);
        lMiddlename.setBounds(200, 80, width, 20);
        add(lMiddlename);

        tfMiddlename = new JTextField();
        tfMiddlename.setFont(labelFont);
        tfMiddlename.setBounds(210 , 110, width- 20, 30);
        add(tfMiddlename);

        lSurname = new JLabel("Enter your surname:");
        lSurname.setFont(labelFont);
        width = calculateWidth(lSurname);
        lSurname.setBounds(400, 80, width, 20);
        add(lSurname);

        tfSurname = new JTextField();
        tfSurname.setFont(labelFont);
        tfSurname.setBounds(400 , 110, width, 30);
        add(tfSurname);

        // date of birth
        lDoB = new JLabel("Enter your date of birth (DD/MM/YYYY):");
        lDoB.setFont(labelFont);
        width = calculateWidth(lDoB);
        lDoB.setBounds(40, 170, width, 20);
        add(lDoB);
        

        String format = "DD/MM/YYYY";
        tfDoB = new JTextField(format);
        tfDoB.setFont(labelFont.deriveFont(Font.BOLD,22));
        tfDoB.setForeground(Color.GRAY);
        tfDoB.setEditable(false);
        tfDoB.setBounds(40 + width + 10, 165, 150, 30);
        tfDoB.addMouseListener(new MouseAdapter() {
            boolean firstClicked = false; 
            @Override
            public void mouseEntered(MouseEvent e) {
                if (tfDoB.getText().equals(format)) {
                    tfDoB.setText("");              
                    tfDoB.getCaret().setVisible(true);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                tfDoB.setEditable(true);
                tfDoB.getCaret().setVisible(true);
                tfDoB.setForeground(Color.BLACK);
                firstClicked = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (tfDoB.getText().isEmpty() && !firstClicked) {
                    tfDoB.setText(format);
                    tfDoB.setForeground(Color.GRAY);
                    tfDoB.getCaret().setVisible(false);
                }
            }
        });
        add(tfDoB);

        // Picking gender
        lGender = new JLabel("Pick your gender:");
        lGender.setFont(labelFont);
        width = calculateWidth(lGender);
        lGender.setBounds(40, 230, width, 20);
        add(lGender);
        
        Font rbFont = new Font("Raleway",Font.BOLD,14);
        rbMale = new JRadioButton("Male");
        rbMale.setFont(rbFont);
        rbMale.setForeground(Color.black);
        rbMale.setBounds(40 + width + 10, 230, 70, 20);
        add(rbMale);

        rbFemale = new JRadioButton("Female");
        rbFemale.setFont(rbFont);
        rbFemale.setForeground(Color.black);
        rbFemale.setBounds(40 + width + 90, 230, 100, 20);
        add(rbFemale);

        rbOther = new JRadioButton("Other");
        rbOther.setFont(rbFont);
        rbOther.setForeground(Color.black);
        rbOther.setBounds(40 + width + 190, 230, 100, 20);
        add(rbOther);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);
        genderGroup.add(rbOther);

        // email 
        lEmail = new JLabel("Enter your e-mail:");
        lEmail.setFont(labelFont);
        width = calculateWidth(lEmail);
        lEmail.setBounds(40, 280, width, 20);
        add(lEmail);
        
        tfEmail = new JTextField();
        tfEmail.setFont(labelFont);
        tfEmail.setBounds(40 + width + 10 , 275, 350, 30);
        add(tfEmail);

        // phone number
        lPhoneNumber = new JLabel("Enter your phone number:");
        lPhoneNumber.setFont(labelFont);
        width = calculateWidth(lPhoneNumber);
        lPhoneNumber.setBounds(40, 330, width, 20);
        add(lPhoneNumber);
        
        tfPhoneNumber = new JTextField();
        tfPhoneNumber.setFont(labelFont);
        tfPhoneNumber.setBounds(40 + width + 10 , 325, 170, 30);
        add(tfPhoneNumber);

        // adress
        lAdress = new JLabel("Enter your adress:");
        lAdress.setFont(labelFont);
        width = calculateWidth(lAdress);
        lAdress.setBounds(40, 370, width, 20);
        add(lAdress);
        
        tfAdress = new JTextField();
        tfAdress.setFont(labelFont);
        tfAdress.setBounds(40 + width + 10 , 365, 350, 30);
        add(tfAdress);

        lCity = new JLabel("City:");
        lCity.setFont(labelFont);
        width = calculateWidth(lCity);
        lCity.setBounds(40, 410, width, 20);
        add(lCity);
        
        tfCity = new JTextField();
        tfCity.setFont(labelFont);
        tfCity.setBounds(40 + width + 10 , 405, 130, 30);
        add(tfCity);

        lState = new JLabel("State:");
        lState.setFont(labelFont);
        width = calculateWidth(lState);
        lState.setBounds(230, 410, width, 20);
        add(lState);
        
        tfState = new JTextField();
        tfState.setFont(labelFont);
        tfState.setBounds(230 + width +10 , 405, 180, 30);
        add(tfState);

        lCountry = new JLabel("Country:");
        lCountry.setFont(labelFont);
        width = calculateWidth(lCountry);
        lCountry.setBounds(40, 450, width, 20);
        add(lCountry);
        
        tfCountry = new JTextField();
        tfCountry.setFont(labelFont);
        tfCountry.setBounds(40 + width + 10 , 445, 130, 30);
        add(tfCountry);

        lZipCode = new JLabel("Zip code:");
        lZipCode.setFont(labelFont);
        width = calculateWidth(lZipCode);
        lZipCode.setBounds( 260, 450, width, 20);
        add(lZipCode);
        
        tfZipCode = new JTextField();
        tfZipCode.setFont(labelFont);
        tfZipCode.setBounds(260 +width +10  , 445, 100, 30);
        add(tfZipCode);

        // pin
        lPin = new JLabel("Enter your 4-digit pin number:");
        lPin.setFont(labelFont);
        width = calculateWidth(lPin);
        lPin.setBounds(40, 500, width, 20);
        add(lPin);
        
        pfPin = new JPasswordField();
        pfPin.setFont(labelFont.deriveFont(Font.BOLD,26));
        pfPin.setBounds(40 + width + 10 , 500, 50, 30);
        pfPin.setEchoChar('*');
        add(pfPin);
        
        bSignUp = new JButton("Sign Up");
        bSignUp.setFont(labelFont.deriveFont(14F));
        bSignUp.setBackground(Color.black);
        bSignUp.setForeground(Color.white);
        bSignUp.setBorderPainted(true);
        bSignUp.setBounds(WIDTH/2 - 50, 550, 100, 50);
        add(bSignUp);
        bSignUp.addActionListener(this);

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
    public void actionPerformed(ActionEvent actionEvent) {

            if(actionEvent.getSource() == bSignUp){
                //Getting data
                String name = tfName.getText();
                String middlename = tfMiddlename.getText().strip();
                String surname = tfSurname.getText().strip();
                String dob = tfDoB.getText().strip();
                String gender = null;
                if(rbMale.isSelected()){
                    gender = "Male";
                }else if(rbFemale.isSelected()){
                    gender = "Female";
                }else if(rbOther.isSelected()){
                    gender = "Other";
                }
                String email = tfEmail.getText().strip();
                String phoneNumber = tfPhoneNumber.getText().strip();
                String adress = tfAdress.getText().strip();
                String city = tfCity.getText().strip();
                String state = tfState.getText().strip();
                String country = tfCountry.getText().strip();
                String zipCode = tfZipCode.getText().strip();
                pin = String.valueOf(pfPin.getPassword());

                generateLoginDetails();
                connector = new Connector();
                String userDataInsert = "INSERT INTO userdata VALUES('"+accountID+"','"+name+"','"+middlename+"','"+surname+"','"+dob+"','"+gender+"','"+email+"','"+phoneNumber+"','"
                +adress+"','"+city+"','"+state+"','"+country+"','"+zipCode+"')";
                String loginInsert = "INSERT INTO login VALUES('"+accountID+"','"+cardNo+"','"+pin+"',"+0+")";
                try{
                    connector.statement.executeUpdate(userDataInsert);
                    connector.statement.executeUpdate(loginInsert);
                }catch(SQLException e){
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Your card number is " + cardNo);
                setVisible(false);
                new Login();
            }
    }
}
