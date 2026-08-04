package src.model;

import java.sql.Timestamp;

public class Booking {
    private int bookingId;
    private int userid;
    private int workerid;
    private Timestamp date_time;
    private String status;

    public Booking(){

    }
//getters
    public int getBookingId(){
        return bookingId;
    }

    public int getb_Userid(){
        return userid;
    }

    public int getb_Workerid(){
        return workerid;
    }

    public Timestamp getb_DateTime(){
        return date_time;
    }

    public String getb_Status(){
        return status;
    }
//setters
    public void setBookingId(int bookingId){
        this.bookingId = bookingId;
    }

    public void setb_Userid(int userid){
        this.userid = userid;
    }

    public void setb_Workerid(int workerid){
        this.workerid = workerid;
    }

    public void setb_DateTime(Timestamp date_time){
        this.date_time = date_time;
    }

    public void setb_Status(String status){
        this.status = status;
    }
}