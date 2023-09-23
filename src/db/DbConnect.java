
package db;
import java.sql.*;
import javax.swing.JOptionPane;
public class DbConnect {
    public static Connection c;
    public static Statement st;
    static{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");//Optional
            c=DriverManager.getConnection("jdbc:mysql://localhost:3306/policysoldb", "root", "Garima0511@");
            st=c.createStatement();
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }
}
