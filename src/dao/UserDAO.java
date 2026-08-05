
package src.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.UUID;


import src.model.User;
public class UserDAO {
    

    //helper methods
    private String generateSalt(){
        return UUID.randomUUID().toString().substring(0,8);
    }
    private String hashPassword(String input){
        try{
             MessageDigest md= MessageDigest.getInstance("SHA-256");
        byte []bytes=md.digest(input.getBytes());
        return bytesToHex(bytes);
        }catch(NoSuchAlgorithmException e){System.out.println("SHA not available..");
        }
        return "";
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    //regular methods
 public String findUsernamebyId(int userid){
 Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
String query="Select username from Users where userid=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
String user_name="";
stmt.setInt(1,userid);
rs=stmt.executeQuery();
if(rs.next()){
user_name=rs.getString("username");

}
rs.close();
stmt.close();
con.close();
return user_name;
 }catch(Exception e){
    System.out.println("Error while fetching user name: "+e.getMessage());
 }
return "null";
    }

 public int findUseridbyname(String username){
 Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
String query="Select userid from Users where username=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
int user_id=-1;
stmt.setString(1,username);
rs=stmt.executeQuery();
if(rs.next()){
user_id=rs.getInt("userid");

}
rs.close();
stmt.close();
con.close();
return user_id;
 }catch(Exception e){
    System.out.println("Error while fetching user id: "+e.getMessage());
 }
return -1;
    }
public User findUserbyId(int userid){
 Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
 User u;
String query="Select * from Users where userid=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
u=new User();
stmt.setInt(1,userid);
rs=stmt.executeQuery();
while(rs.next()){
u.setUserid(rs.getInt("userid"));
u.setuserName(rs.getString("username"));
u.setu_Age(rs.getInt("age"));
u.setu_Gender(rs.getString("gender"));
u.setu_City(rs.getString("city"));
u.setu_Street(rs.getString("street"));
u.setu_Indicator(rs.getString("indicator"));
u.setu_Phone(rs.getString("phone_no"));
u.setu_passHash(rs.getString("pass_hash"));
u.setu_passSalt(rs.getString("pass_salt"));

    return u;
}
rs.close();
stmt.close();
con.close();
 }catch(Exception e){
    System.out.println("Error while fetching user name: "+e.getMessage());
 }
return null;
    }


    public boolean usernameExists(String username){
        Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
String query="Select userid from Users where username=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);

stmt.setString(1,username);
rs=stmt.executeQuery();
if(rs.next()){
return true;
}
rs.close();
stmt.close();
con.close();
 }catch(Exception e){
    System.out.println("Error while checking user name existence: "+e.getMessage());
 }
return false;
    }


public boolean registerNewUser(String username,String phone_no,int age,String gender,String city,String street, String indicator,String plain_password){
    if(age<18){
        System.out.println("OOPs , Registration Failed!. you are Under 18! ");
        return false;
    }
 if(!usernameExists(username)){
Connection con=null;
 PreparedStatement stmt=null;

 String salt=generateSalt();
 String hash=hashPassword(salt+plain_password);
String query="Insert into Users (username, phone_no, age,gender,city,street, indicator,pass_hash,pass_salt) values (?,?,?,?,?,?,?,?,?)";
int result=-1;
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
stmt.setString(1,username);
stmt.setInt(3,age);
stmt.setString(2,phone_no);
stmt.setString(4,gender);
stmt.setString(5,city);
stmt.setString(6,street);
stmt.setString(7,indicator);
stmt.setString(8,hash);
stmt.setString(9,salt);

 result=stmt.executeUpdate();
stmt.close();
con.close();
 }catch(Exception e){
    System.out.println("Error while fetching user name: "+e.getMessage());
    return false;
 }
 if(result>0) {System.out.println("Registered successfully!"); return true;}
 else {System.out.println("OOPS! registration failed!"); return false;}
 }
 else {
    System.out.println("This username already exists: Try usin a different name.\n");
    return false;
}
    
    }

    public boolean verifyUserLogin(String username, String plainpassword){
 Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
String query="Select pass_hash, pass_salt from Users where username=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
stmt.setString(1,username);
rs=stmt.executeQuery();
if(!rs.next()){
System.out.println("No such user found");
return false;
}else{
     String storedSalt = rs.getString("pass_salt");
    String storedHash = rs.getString("pass_hash");
 String testhash=hashPassword(plainpassword + storedSalt);
 return testhash.equals(storedHash);
}
}catch(Exception e){
    System.out.println("Error while verifying user credentials: "+e.getMessage());
 }finally{
    try{
        if(rs!=null)rs.close();
         if(stmt!=null)stmt.close();
          if(con!=null)con.close();
    }catch(SQLException e){
        System.out.println("Close error: "+e.getMessage());
        }
 }
return false;
    }

    public void updateUseraddress(String city, String street, String indicator, int userid){
Connection con=null;
 PreparedStatement stmt=null;

String query="Update Users set city=?, street=?, indicator=? where userid=?";
int result=-1;
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
stmt.setString(1,city);
stmt.setString(2,street);
stmt.setString(3,indicator);
stmt.setInt(4,userid);

 result=stmt.executeUpdate();
stmt.close();
 con.close();
 
 }catch(Exception e){
    System.out.println("Error while updating user address: "+e.getMessage());
 }
 if(result>0) System.out.println("Updated address successfully!");
 else System.out.println("OOPS! Updation failed!");
 }

 public void updateUserPhone(String phone_no, int userid){
Connection con=null;
 PreparedStatement stmt=null;

String query="Update Users set phone_no=? where userid=?";
int result=-1;
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
stmt.setString(1,phone_no);
stmt.setInt(4,userid);


 result=stmt.executeUpdate();
 stmt.close();
 con.close();
 }catch(Exception e){
    System.out.println("Error while updating user phone number: "+e.getMessage());
 }
 if(result>0) System.out.println("Updated phone number successfully!");
 else System.out.println("OOPS! Updation failed!");
 }
 
    }



