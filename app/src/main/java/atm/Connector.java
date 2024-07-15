package atm;
import java.sql.*;

public class Connector {
    Connection conn;
    public Statement statement;
    public Connector(){
        String connectionString = "jdbc:mysql://localhost:3306/atmdatabase";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(connectionString, "root", "root123");
            statement = conn.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
