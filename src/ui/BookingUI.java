package src.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.awt.event.ActionEvent;
import src.dao.UserDAO;
import src.dao.WorkerDAO;
import src.dao.BookingDAO;
import src.dao.ProfessionDAO;
import src.model.*;
import java.util.List;
public class BookingUI extends JFrame {
    private JTextField datetime;
    private JButton findWorkers;
    private JTextField workerid_book;
    private JTable workers_tbl;
    private JButton bookButton;
     private WorkerDAO w_dao;
private  ProfessionDAO p_dao;
 private UserDAO u_dao;
 private BookingDAO b_dao;

   public BookingUI(int userid){
     setTitle("Booking A worker");
     setSize(500,500);
   setDefaultCloseOperation(EXIT_ON_CLOSE);
   setBackground(Color.GRAY);
   setLayout(null);
w_dao=new WorkerDAO();
 p_dao=new ProfessionDAO();
 u_dao=new UserDAO();
 b_dao=new BookingDAO();


    JLabel projectTitle=new JLabel("HomeService --- Booking");
projectTitle.setBounds(50,20,200,40);
projectTitle.setFont(new Font("Arial",Font.BOLD,16));
add(projectTitle);
JLabel welcometxt=new JLabel("Welcome to Booking Portal--"+u_dao.findUsernamebyId(userid));
welcometxt.setBounds(10,70,300,30);
add(welcometxt);

findWorkers= new JButton("Show workers.");
findWorkers.setBounds(50,120, 200, 40);
add(findWorkers);


JLabel bookid =new JLabel("Write workerid you want to book: ");
bookid.setBounds(50,180, 200,30);
add(bookid);
workerid_book=new JTextField();
workerid_book.setBounds(50,210,100,20);
add(workerid_book);

JLabel bookTime =new JLabel("Write dateTime you want to book(yyyy-mm-dd hh:mm:ss): ");
bookTime.setBounds(50,240, 350,30);
add(bookTime);
datetime=new JTextField();
datetime.setBounds(50,270,200,30);
add(datetime);
bookButton=new JButton("Book.");
bookButton.setBounds(50,310,100,30);
add(bookButton);

bookButton.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent ae){
        String workerid=workerid_book.getText().trim();
        try{
            int w_id=Integer.parseInt(workerid);
            Timestamp dt=Timestamp.valueOf(datetime.getText().trim());
       if(workerid==null || datetime.getText()==null){
        JOptionPane.showMessageDialog(null, "Please fill all required fields..");
       }
        boolean booked= b_dao.insertBooking(userid, w_id, dt);
         if(booked)
    JOptionPane.showMessageDialog(null, "Your booking request is sent to Worker.");
else{
    JOptionPane.showMessageDialog(null,"Registeration of your booking request failed!!");
}
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Invalid Workerid."+ e.getMessage());
        }catch(DateTimeException e){
            JOptionPane.showMessageDialog(null,"Invalid datetime value."+ e.getMessage());
        }

    }
});



findWorkers.addActionListener(new ActionListener(){
  public void actionPerformed(ActionEvent ae){
  User u= u_dao.findUserbyId(userid);
    String usercity=u.getu_City();
   showWorkersListPanel(usercity);

  }
});

    }

private void showWorkersListPanel(String cityofUser){
  List <Worker> workers=w_dao.filterWorkersbyCity(cityofUser);
  String[][] data=new String[workers.size()][9];
  for(int i=0; i<workers.size(); i++){
    Worker w= workers.get(i);
    data[i][0]=String.valueOf(w.getWorkerid());
    data[i][1]=w.getworkerName();
    data[i][2]=w.getw_Phone();
    data[i][3]=w.getw_City();
    data[i][4]=w.getw_Street();
    data[i][5]=w.getw_Gender();
    data[i][6]=String.valueOf(w.getw_Age());
    data[i][7]=p_dao.findProfbyId(w.getw_profId());
    data[i][8]=String.valueOf(w.getw_isOnduty());
  }
  String [] columns={"ID","WorkerName","Phone_no:","City","Street","Gender","Age","Profession","On duty"};
  workers_tbl=new JTable(data, columns);
  workers_tbl.setEnabled(false);
JScrollPane scroll=new JScrollPane(workers_tbl);
scroll.setPreferredSize(new Dimension(750,150));
JOptionPane.showMessageDialog(null,scroll,"Workerlist",JOptionPane.PLAIN_MESSAGE);
}

}
