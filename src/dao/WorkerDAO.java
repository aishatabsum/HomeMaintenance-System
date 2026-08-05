package src.dao;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import src.model.Worker;

public class WorkerDAO {

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
 public String findWorkernamebyId(int workerid){
 Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
String query="Select workername from Workers where workerid=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
String worker_name="";
stmt.setInt(1,workerid);
rs=stmt.executeQuery();
if(rs.next()){
worker_name=rs.getString("workername");

}
rs.close();
stmt.close();
con.close();
return worker_name;
 }catch(Exception e){
    System.out.println("Error while fetching worker name: "+e.getMessage());
 }
return "null";
    }


public Worker findWorkerbyId(int workerid){
 Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
 Worker w;
String query="Select * from Workers where workerid=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
w=new Worker();
stmt.setInt(1,workerid);
rs=stmt.executeQuery();
while(rs.next()){
w.setWorkerid(rs.getInt("workerid"));
w.setworkerName(rs.getString("workername"));
w.setw_Age(rs.getInt("age"));
w.setw_Gender(rs.getString("gender"));
w.setw_City(rs.getString("city"));
w.setw_Street(rs.getString("street"));
w.setw_Indicator(rs.getString("indicator"));
w.setw_Phone(rs.getString("phone_no"));
w.setw_passHash(rs.getString("pass_hash"));
w.setw_passSalt(rs.getString("pass_salt"));
w.setw_isOnduty(rs.getBoolean("is_onduty"));
w.setw_profId(rs.getInt("profid"));
}
rs.close();
stmt.close();
con.close();
return w;
 }catch(Exception e){
    System.out.println("Error while fetching worker data: "+e.getMessage());
 }
return null;
    }


    public boolean workernameTaken(String workername){
        Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
 boolean workernameUsed=false;
String query="Select workerid from Workers where workername=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);

stmt.setString(1,workername);
rs=stmt.executeQuery();
if(rs.next()){
workernameUsed=true;
}
rs.close();
stmt.close();
con.close();
return workernameUsed;
 }catch(Exception e){
    System.out.println("Error while checking worker name existence: "+e.getMessage());
 }
return false;
    }


public boolean registerNewWorker(String workername,String phone_no,int age,String gender,String city,String street, String indicator,String plain_password,Boolean is_onduty, int profid){
    if(age<18){
        System.out.println("OOPs , Registration Failed!. you are Under 18! ");
        return false;
    }
    if(!workernameTaken(workername)){
Connection con=null;
 PreparedStatement stmt=null;

 String salt=generateSalt();
 String hash=hashPassword(salt+plain_password);
String query="Insert into Workers (workername, phone_no, age,gender,city,street, indicator,pass_hash,pass_salt,is_onduty, profid) values (?,?,?,?,?,?,?,?,?,?,?)";
int result=-1;
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
stmt.setString(1,workername);
stmt.setInt(3,age);
stmt.setString(2,phone_no);
stmt.setString(4,gender);
stmt.setString(5,city);
stmt.setString(6,street);
stmt.setString(7,indicator);
stmt.setString(8,hash);
stmt.setString(9,salt);
stmt.setBoolean(10,is_onduty);
stmt.setInt(11,profid);
result=stmt.executeUpdate();
stmt.close();
con.close();
 
 }catch(Exception e){
    System.out.println("Error while registering new worker: "+e.getMessage());
    return false;
 }
 if(result>0) {System.out.println("Registered successfully!");
 return true;
 }
 else {System.out.println("OOPS! registration failed!");
 return false;
 }
}
 else {
    System.out.println("This workername already exists: Try using a different name.\n");
 return false;
 }
    }

    public boolean verifyWorkerLogin(String workername, String plainpassword){
 Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
String query="Select pass_hash, pass_salt from Workers where workername=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
stmt.setString(1,workername);
rs=stmt.executeQuery();
if(!rs.next()){
System.out.println("No such worker found");
return false;
}else{
     String storedSalt = rs.getString("pass_salt");
    String storedHash = rs.getString("pass_hash");
 String testhash=hashPassword(plainpassword+storedSalt);
 return testhash.equals(storedHash);
}
}catch(Exception e){
    System.out.println("Error while verifying worker credentials: "+e.getMessage());
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



    public void updateWorkeraddress(String city, String street, String indicator, int workerid){
Connection con=null;
 PreparedStatement stmt=null;

String query="Update Workers set city=?, street=?, indicator=? where workerid=?";
int result=-1;
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
stmt.setString(1,city);
stmt.setString(2,street);
stmt.setString(3,indicator);
stmt.setInt(4,workerid);


 result=stmt.executeUpdate();
  stmt.close();
 con.close();

 }catch(Exception e){
    System.out.println("Error while updating worker address: "+e.getMessage());
 }
 if(result>0) System.out.println("Updated address successfully!");
 else System.out.println("OOPS! Updation failed!");
 }

 public void updateWorkerPhone(String phone_no, int workerid){
Connection con=null;
 PreparedStatement stmt=null;

String query="Update Workers set phone_no=? where workerid=?";
int result=-1;
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
stmt.setString(1,phone_no);
stmt.setInt(4,workerid);


 result=stmt.executeUpdate();
 stmt.close();
 con.close();
 }catch(Exception e){
    System.out.println("Error while updating worker's phone number: "+e.getMessage());
 }
 if(result>0) System.out.println("Updated phone number successfully!");
 else System.out.println("OOPS! Updation failed!");
 }
 

public void togglew_Onduty(int workerid){
    Connection con=null;
 PreparedStatement stmt=null;
 int rs=-1;
String query="Update Workers set is_onduty=? where workerid=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
boolean currentOnduty=isWorkerOnduty(workerid);
boolean newOnduty=!currentOnduty;

stmt.setBoolean(1,newOnduty);
stmt.setInt(2,workerid);
rs=stmt.executeUpdate();
 
if(rs>0){
System.out.println("Toggled successfully");
}else
    System.out.println("oops! Could not toggle ..");
stmt.close();
con.close();
 }catch(Exception e){
    System.out.println("Error while toggling worker's presence : "+e.getMessage());
 }
 }

 public boolean isWorkerOnduty(int workerid){
    Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
 boolean onduty=false;
String query="Select is_onduty from Workers where workerid=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);

stmt.setInt(1,workerid);
rs=stmt.executeQuery();
 
if(rs.next()){
onduty=rs.getBoolean("is_onduty");
}
rs.close();
stmt.close();
con.close();
return onduty;
 }catch(Exception e){
    System.out.println("Error while checking worker's presence in : "+e.getMessage());
 }
return false;
 }


 


public String worker_profession(int workerid){
Connection con=null; PreparedStatement stmt=null;
ResultSet rs=null;
String query="Select profid from Workers where workerid=?";
ProfessionDAO pDao=new ProfessionDAO();

String profession="";
try{
    con=DBconnection.getConnection();
    stmt=con.prepareStatement(query);
    stmt.setInt(1,workerid);
    rs=stmt.executeQuery();
    if(rs.next()){
        int profid=rs.getInt("profid");
        profession=pDao.findProfbyId(profid);
    }
    rs.close();stmt.close();con.close();
    return profession;
}catch(Exception e){
    System.out.println("Error while fetching profession of worker: "+e.getMessage());
}
return "null";
}

public List<Worker> filterWorkersbyCity(String usercity){
Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
List<Worker> workers=new ArrayList<>();
String query="Select * from Workers where city=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
stmt.setString(1,usercity);
rs=stmt.executeQuery();

while(rs.next()){
    Worker w= new Worker();
    w.setWorkerid(rs.getInt("workerid"));
  w.setworkerName(rs.getString("workername"));
w.setw_Age(rs.getInt("age"));
w.setw_Gender(rs.getString("gender"));
w.setw_City(rs.getString("city"));
w.setw_Street(rs.getString("street"));
w.setw_Indicator(rs.getString("indicator"));
w.setw_Phone(rs.getString("phone_no"));
w.setw_passHash(rs.getString("pass_hash"));
w.setw_passSalt(rs.getString("pass_salt"));
w.setw_isOnduty(rs.getBoolean("is_onduty"));
w.setw_profId(rs.getInt("profid"));
    workers.add(w);
    }
 }catch(Exception e){
    System.out.println("Error while fetching workers list: "+e.getMessage());
 }
 finally{
    try{
        if(rs!=null)rs.close();
         if(stmt!=null)stmt.close();
          if(con!=null)con.close();
    }catch(SQLException e){
        System.out.println("Close error: "+e.getMessage());
        }
 }

 return workers;

}
 public int findWorkeridbyname(String workername){
 Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
String query="Select workerid from Workers where workername=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
int worker_id=-1;
stmt.setString(1,workername);
rs=stmt.executeQuery();
if(rs.next()){
worker_id=rs.getInt("workerid");
}
rs.close();
stmt.close();
con.close();
return worker_id;
 }catch(Exception e){
    System.out.println("Error while fetching worker id: "+e.getMessage());
 }
return -1;
    }

}
