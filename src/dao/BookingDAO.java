package src.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import src.model.Booking;
public class BookingDAO {
    public List<Booking> filterBookingsbyUser(int userid){
Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
List<Booking> booking=new ArrayList<>();
String query="Select * from Booking where userid=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
stmt.setInt(1,userid);
rs=stmt.executeQuery();

while(rs.next()){
    Booking b= new Booking();
    b.setBookingId(rs.getInt("bookingId"));
    b.setb_Userid(rs.getInt("userid"));
     b.setb_Workerid(rs.getInt("workerid"));
     b.setb_Status(rs.getString("status"));
    b.setb_DateTime(rs.getTimestamp("date_time"));
    booking.add(b);
    }
 }catch(Exception e){
    System.out.println("Error while fetching user's booking list : "+e.getMessage());
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

 return booking;

}


    public List<Booking> filterBookingsbyWorker(int workerid){
Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
List<Booking> booking=new ArrayList<>();
String query="Select * from Booking where workerid=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
stmt.setInt(1,workerid);
rs=stmt.executeQuery();

while(rs.next()){
    Booking b= new Booking();
    b.setBookingId(rs.getInt("bookingId"));
    b.setb_Userid(rs.getInt("userid"));
     b.setb_Workerid(rs.getInt("workerid"));
     b.setb_Status(rs.getString("status"));
    b.setb_DateTime(rs.getTimestamp("date_time"));
    booking.add(b);
    }
 }catch(Exception e){
    System.out.println("Error while fetching worker's booking list: "+e.getMessage());
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

 return booking;

}


public boolean updateBookingStatus(int bookingId, String newstatus){
    Connection con=null;
 PreparedStatement stmt=null;
 int rs=-1;
String query="Update Booking set status=? where bookingId=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
boolean statusValid=false;
if(newstatus=="confirmed"|| newstatus=="cancelled"|| newstatus=="pending"){
  statusValid=true;
}else{
    statusValid=false;
    System.out.println("Invalid status of booking!");
    return false;
}
stmt.setString(1, newstatus);
stmt.setInt(2,bookingId);
rs=stmt.executeUpdate();
 stmt.close();
con.close();
if(rs>0){
System.out.println("status updated successfully");
return true;
}else{
    System.out.println("oops! Could not update status ..");
    return false;
}
 }catch(Exception e){
    System.out.println("Error while updating booking status: "+e.getMessage());
    return false;
 }
}


public Booking findBookingbyId(int bookingId){
 Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
 Booking b;
String query="Select * from Booking where bookingId=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
b=new Booking();
stmt.setInt(1,bookingId);
rs=stmt.executeQuery();
while(rs.next()){
    b.setBookingId(rs.getInt("bookingId"));
    b.setb_Userid(rs.getInt("userid"));
     b.setb_Workerid(rs.getInt("workerid"));
     b.setb_Status(rs.getString("status"));
    b.setb_DateTime(rs.getTimestamp("date_time"));
    return b;
}
rs.close();
stmt.close();
con.close();
 }catch(Exception e){
    System.out.println("Error while fetching booking data: "+e.getMessage());
 }
return null;
    }

public boolean bookingExists(int userid, int workerid,Timestamp datetime){
    Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
String query="Select bookingid from Booking where date_time =? and workerid=? and userid=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
stmt.setInt(3,userid);
stmt.setTimestamp(1,datetime);
stmt.setInt(2,workerid);
rs=stmt.executeQuery();
if(rs.next()){
return true;
}
rs.close();
stmt.close();
con.close();
 }catch(Exception e){
    System.out.println("Error while checking existence of this booking: "+e.getMessage());
 }
return false;
}

public Boolean isDatetimeTaken(Timestamp datetime, int workerid){
    Connection con=null;
 PreparedStatement stmt=null;
 ResultSet rs=null;
String query="Select bookingId from Booking where date_time =? and workerid=?";
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);

stmt.setTimestamp(1,datetime);
stmt.setInt(2,workerid);
rs=stmt.executeQuery();
if(rs.next()){
return true;
}
rs.close();
stmt.close();
con.close();
 }catch(Exception e){
    System.out.println("Error while checking Worker's availabilty at this time: "+e.getMessage());
 }
return false;
}

    public boolean insertBooking(int userid, int workerid, Timestamp datetime){
      WorkerDAO wDao=new WorkerDAO();
        if(wDao.isWorkerOnduty(workerid)){
              if(!isDatetimeTaken(datetime,workerid)){
                Connection con=null;
                PreparedStatement stmt=null;
String query="Insert into Booking (userid, workerid, date_time) values (?,?,?)";
int result=-1;
 try{
con=DBconnection.getConnection();
stmt=con.prepareStatement(query);
stmt.setInt(1,userid);
stmt.setInt(2,workerid);
stmt.setTimestamp(3, datetime);

 result=stmt.executeUpdate();
 stmt.close();
con.close();
 }catch(Exception e){
    System.out.println("Error while inserting new booking entry: "+e.getMessage());
 }
 if(result>0){ System.out.println("Registered booking successfully!");
    return true;
 }
 else{ System.out.println("OOPS! registration of booking failed!");
 return false;}
              }
              else{
                System.out.println("This slot is already booked for your desired worker!\n");
               return false;
              }
        }else{
            System.out.println("Sorry, Your desired worker is off currently!\n");
            return false;
        }
    }


}
