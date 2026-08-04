package src.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBconnection_sample{
    private static final String URL="jdbc:mysql://localhost:3306/your_database";
    private static final String USER="your_username";
    private static final String PASS="your password";
    private static Connection con= null;
public static Connection getConnection(){
 try {
    Class.forName("com.mysql.cj.jdbc.Driver");
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Bridge to database Connected!"); 
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Mysql driver not found: " + e.getMessage());
        }catch (SQLException e) {
            System.out.println("Bridge Failed: " + e.getMessage());
        }
        return con;
   }
   
}


