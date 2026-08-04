package src.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ProfessionDAO {
    
    public String findProfbyId(int profid){
 Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
String query="Select profname from Profession where profid=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
String prof_name="";
stmt.setInt(1,profid);
rs=stmt.executeQuery();
if(rs.next()){
prof_name=rs.getString("profname");
return prof_name;
}
rs.close();
stmt.close();
con.close();
 }catch(Exception e){
    System.out.println("Error while fetching profession name: "+e.getMessage());
 }
return "null";
    }


        public int findProfidbyname(String profname){
 Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
String query="Select profid from Profession where profname=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
int profid=-1;
stmt.setString(1,profname);
rs=stmt.executeQuery();
if(rs.next()){
profid=rs.getInt("profid");
rs.close();
stmt.close();
con.close();

return profid;
}

 }catch(Exception e){
    System.out.println("Error while fetching profession name: "+e.getMessage());
 }
return -1;
    }

     public String[] getprofessionList(){
 Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
List<String> profs=new ArrayList<>();
String query="Select profname from Profession";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
rs=stmt.executeQuery();
while(rs.next()){
    String value=rs.getString("profname");
    profs.add(value);
    }
 }catch(Exception e){
    System.out.println("Error while fetching profession list: "+e.getMessage());
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

 return profs.toArray(new String[0]);
    }
}
