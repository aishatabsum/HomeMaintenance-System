package src.ui;
import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import src.dao.UserDAO;
import src.dao.WorkerDAO;
import src.dao.BookingDAO;
import src.dao.ProfessionDAO;
import src.model.*;
import java.util.List;
public class DashboardUI extends JFrame{
private JButton updaddress_btn;
private JButton updphone_btn;
private JPanel updadd_panel;
 private WorkerDAO w_dao;
private  ProfessionDAO p_dao;
 private UserDAO u_dao;
 private BookingDAO b_dao;
private int id;
private JLabel address;
private JLabel phone_no;
private JLabel is_onduty;

private JButton onduty_togglebtn;
private JPanel updStatus_Panel;

private JButton bookWorker;
private JTable bookings_tbl;
private JButton showBookings;
private JButton changeBook_status;
private JRadioButton cancel_rbtn;
private JRadioButton confirm_rbtn;
private ButtonGroup status_grp;


   public  DashboardUI(String role, int id){
  this.id=id;
  
w_dao=new WorkerDAO();
 p_dao=new ProfessionDAO();
 u_dao=new UserDAO();
 b_dao=new BookingDAO();
   setSize(700,700);
   setDefaultCloseOperation(EXIT_ON_CLOSE);
   setBackground(Color.GRAY);
   setLayout(null);

   if(role=="User" && id!=-1){
   
     setTitle("Home Assistance Dashboard---Home owner");
     JLabel projectTitle=new JLabel("HomeService --- Owner Dashboard");
projectTitle.setBounds(50,40,270,40);
projectTitle.setFont(new Font("Arial",Font.BOLD,20));
add(projectTitle);
JLabel welcomeUser= new JLabel("WELCOME , "+ u_dao.findUsernamebyId(id));
welcomeUser.setBounds(10,100,200,30);
add(welcomeUser);
User u =u_dao.findUserbyId(id);
if(u!=null){
 phone_no=new JLabel("Phone number: "+ u.getu_Phone());
phone_no.setBounds(20,140,280,30);
add(phone_no);
updphone_btn=new JButton("Update Phone");
updphone_btn.setBounds(300,140,150,30);
add(updphone_btn);
 address=new JLabel("Address--> City:"+ u.getu_City()+" Street: "+u.getu_Street()+"\n Indicator: "+u.getu_Indicator());
address.setBounds(20,170,500,30);
add(address);
updaddress_btn=new JButton("Update Address");
updaddress_btn.setBounds(70,220,150,30);
add(updaddress_btn);
JLabel age_n_gender= new JLabel("Age: "+u.getu_Age()+"   Gender: "+u.getu_Gender());
age_n_gender.setBounds(20,260,200,30);
add(age_n_gender);
bookWorker= new JButton("Book worker with selected id.");
bookWorker.setBounds(50,300, 250, 30);
add(bookWorker);
showBookings=new JButton("Show My Bookings");
showBookings.setBounds(130,340,200,30);
add(showBookings);




showBookings.addActionListener(new ActionListener(){
  public void actionPerformed(ActionEvent ae){
    showBookingsbyUser();
  }
});



bookWorker.addActionListener(new ActionListener(){
  public void actionPerformed(ActionEvent ae){
    new BookingUI(id).setVisible(true);
    dispose();
  }
});


updphone_btn.addActionListener(new ActionListener() {
  public void actionPerformed(ActionEvent ae){
    String phone_new=JOptionPane.showInputDialog("Enter new phone number: ");
    if(phone_new==null){System.out.println("You cancelled !");}
    else if(phone_new.trim().isEmpty()) 
      JOptionPane.showMessageDialog(null, "OOPS!, you entered empty phone number!");
else{
    u_dao.updateUserPhone( phone_new, id);
  JOptionPane.showMessageDialog(null, "Updated successfully!.");
  }
}
});

updaddress_btn.addActionListener(new ActionListener() {
  public void actionPerformed(ActionEvent ae){
    showUpdatePanel("User");
  }
});

}
   }

   if (role=="Worker"&& id!=-1){
     setTitle("Home Assistance Dashboard---Worker");
      JLabel projectTitle=new JLabel("HomeService --- Worker Dashboard");
projectTitle.setBounds(50,40,270,40);
projectTitle.setFont(new Font("Arial",Font.BOLD,20));
add(projectTitle);
status_grp=new ButtonGroup();

JLabel welcomeWorker= new JLabel("WELCOME , "+ w_dao.findWorkernamebyId(id));
welcomeWorker.setBounds(10,100,200,30);
add(welcomeWorker);
Worker w =w_dao.findWorkerbyId(id);
if(w!=null){
 phone_no=new JLabel("Phone number: "+ w.getw_Phone());
phone_no.setBounds(20,140,280,30);
add(phone_no);
updphone_btn=new JButton("Update Phone");
updphone_btn.setBounds(310,140,150,30);
add(updphone_btn);
 address=new JLabel("Address--> \nCity:"+ w.getw_City()+" Street: "+w.getw_Street()+"\n Indicator: "+w.getw_Indicator());
address.setBounds(20,180,400,20);
add(address);
updaddress_btn=new JButton("Update Address");
updaddress_btn.setBounds(70,220,150,30);
add(updaddress_btn);
JLabel age_n_gender= new JLabel("Age: "+w.getw_Age()+"   Gender: "+w.getw_Gender());
age_n_gender.setBounds(20,260,200,30);
add(age_n_gender);
JLabel prof= new JLabel("Profession: "+p_dao.findProfbyId(w.getw_profId()));
prof.setBounds(20,300,200,30);
add(prof);
is_onduty=new JLabel("Availablity status: "+w.getw_isOnduty());
is_onduty.setBounds(20,340,150,30);
add(is_onduty);
onduty_togglebtn=new JButton("Toggle availablity");
onduty_togglebtn.setBounds(180,340,200,30);
add(onduty_togglebtn);
showBookings=new JButton("Show Bookings for me");
showBookings.setBounds(130,380,200,30);
add(showBookings);
changeBook_status=new JButton("Change Booking status for selected bookingid");
changeBook_status.setBounds(130,420,400,40);
add(changeBook_status);





changeBook_status.addActionListener(new ActionListener() {
  public void actionPerformed(ActionEvent ae){
    showUpdateStatuspanel();
  }
});




showBookings.addActionListener(new ActionListener(){
  public void actionPerformed(ActionEvent ae){
    showBookingsForWorker();
  }
});


onduty_togglebtn.addActionListener(new ActionListener(){
  public void actionPerformed(ActionEvent ae){
   w_dao.togglew_Onduty(id);
   is_onduty.setText("Availability status: "+ w_dao.isWorkerOnduty(id));
  }
});

updphone_btn.addActionListener(new ActionListener(){
  public void actionPerformed(ActionEvent ae){
    String phone_new=JOptionPane.showInputDialog("Enter new phone number: ");
    if(phone_new==null){System.out.println("You cancelled !");}
    else if(phone_new.trim().isEmpty()) 
      JOptionPane.showMessageDialog(null, "OOPS!, you entered empty phone number!");
else{
    w_dao.updateWorkerPhone( phone_new, id);
  JOptionPane.showMessageDialog(null, "Updated successfully!.");
  }
}
});

updaddress_btn.addActionListener(new ActionListener(){
  public void actionPerformed(ActionEvent ae){
  showUpdatePanel("Worker");
}
  });
 
   
    }
  }
}

private void showBookingsForWorker(){
  List <Booking> bookings=b_dao.filterBookingsbyWorker(id);
  String[][] data=new String[bookings.size()][7];
  for(int i=0; i<bookings.size(); i++){
    Booking b= bookings.get(i);
  User u=u_dao.findUserbyId(b.getb_Userid());
    data[i][0]=String.valueOf(b.getBookingId());
    data[i][1]=w_dao.findWorkernamebyId(b.getb_Workerid());
    data[i][2]=u_dao.findUsernamebyId(b.getb_Userid());
    data[i][3]=u.getu_City()+","+u.getu_Street()+","+u.getu_Indicator();
    data[i][4]=u.getu_Phone();
    data[i][5]=b.getb_Status();
    data[i][6]=String.valueOf(b.getb_DateTime());
   
  }
  String [] columns={"BookingID","WorkerName","HomeOwner name","Owner's Address","Owner's Phone no ","DateTime","BookingStatus"};
  bookings_tbl=new JTable(data, columns);
  bookings_tbl.setEnabled(false);
JScrollPane scroll=new JScrollPane(bookings_tbl);
scroll.setPreferredSize(new Dimension(1000,150));
JOptionPane.showMessageDialog(null,scroll,"Bookings list",JOptionPane.PLAIN_MESSAGE);
}


private void showBookingsbyUser(){
  List <Booking> bookings=b_dao.filterBookingsbyUser(id);
  String[][] data=new String[bookings.size()][8];
  for(int i=0; i<bookings.size(); i++){
    Booking b= bookings.get(i);
  Worker w=w_dao.findWorkerbyId(b.getb_Workerid());
    data[i][0]=String.valueOf(b.getBookingId());
      data[i][1]=u_dao.findUsernamebyId(b.getb_Userid());
    data[i][2]=w_dao.findWorkernamebyId(b.getb_Workerid());
  data[i][3]=w_dao.worker_profession(b.getb_Workerid());
    data[i][4]=w.getw_City()+","+w.getw_Street()+","+w.getw_Indicator();
    data[i][5]=w.getw_Phone();
    data[i][6]=b.getb_Status();
    data[i][7]=String.valueOf(b.getb_DateTime());
   
  }
  String [] columns={"BookingID","HomeOwner name","WorkerName","Worker's Address","Worker's Phone no ","DateTime","BookingStatus"};
  bookings_tbl=new JTable(data, columns);
  bookings_tbl.setEnabled(false);
JScrollPane scroll=new JScrollPane(bookings_tbl);
scroll.setPreferredSize(new Dimension(1000,150));
JOptionPane.showMessageDialog(null,scroll,"Bookings list",JOptionPane.PLAIN_MESSAGE);
}



private void showUpdatePanel(String role){
  updadd_panel=new JPanel(new GridLayout(4,2,10,10));
updadd_panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
   updadd_panel.add(new JLabel("Enter city: "));
   JTextField cityField=new JTextField();
   updadd_panel.add(cityField);
 updadd_panel.add(new JLabel("Enter Street: "));
   JTextField streetField=new JTextField();
   updadd_panel.add(streetField);
    updadd_panel.add(new JLabel("Enter Indicator(landmark): "));
   JTextField indicatorField=new JTextField();
   updadd_panel.add(indicatorField);
 
   int result=JOptionPane.showConfirmDialog(this, updadd_panel,"Address update ",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
   if(result==JOptionPane.OK_OPTION){
    if(cityField.getText().isEmpty()||streetField.getText().isEmpty()){
      JOptionPane.showMessageDialog(this, "Please fill must-fill fields(city and street)!");
      return;
    }else{
     if(role=="Worker"){
    w_dao.updateWorkeraddress(cityField.getText(),streetField.getText(),indicatorField.getText(), id);
    JOptionPane.showMessageDialog(null, "Updated successfully!.");
    address.setText("City: "+cityField.getText()+",Street: "+streetField.getText()+(indicatorField.getText().isEmpty()? "null":"Indicator: "+indicatorField.getText()));
   }
   if(role=="User"){
     u_dao.updateUseraddress(cityField.getText(),streetField.getText(),indicatorField.getText(), id);
    JOptionPane.showMessageDialog(null, "Updated successfully!.");
    address.setText("City: "+cityField.getText()+",Street: "+streetField.getText()+(indicatorField.getText().isEmpty()? "null":"Indicator: "+indicatorField.getText()));
   }
  }
}
}

private void showUpdateStatuspanel(){
   boolean updated=false;
   updStatus_Panel=new JPanel(new GridLayout(4,2,10,10));
updStatus_Panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
   updStatus_Panel.add(new JLabel("Enter Bookingid to update its status: "));
   JTextField takeid_forstatus=new JTextField();
   updStatus_Panel.add(takeid_forstatus);
   cancel_rbtn=new JRadioButton("cancelled");
   confirm_rbtn=new JRadioButton("confirmed");
   status_grp.add(cancel_rbtn); status_grp.add(confirm_rbtn);
   updStatus_Panel.add(confirm_rbtn); updStatus_Panel.add(cancel_rbtn);
   
   int result=JOptionPane.showConfirmDialog(this, updStatus_Panel,"Status update for booking ",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
   String bookingid=takeid_forstatus.getText().trim();
   if(result==JOptionPane.OK_OPTION){
    if(takeid_forstatus.getText().isEmpty()||!(confirm_rbtn.isSelected()||cancel_rbtn.isSelected())){
      JOptionPane.showMessageDialog(this, "Please fill must-fill fields!");
      return;
    }else{
     if(cancel_rbtn.isSelected())
  updated= b_dao.updateBookingStatus(Integer.parseInt(bookingid), "cancelled");
   if(confirm_rbtn.isSelected())
      updated= b_dao.updateBookingStatus(Integer.parseInt(bookingid), "confirmed");
   if(updated) JOptionPane.showMessageDialog(null, "Updated successfully!.");
   else JOptionPane.showMessageDialog(null, "Updation of status failed!.");
  }
}

}
public static void main(String args[]){
    DashboardUI du= new DashboardUI("User", 12);
    du.setVisible(true);
   }
}