package src.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import src.dao.UserDAO;
import src.dao.WorkerDAO;
import src.dao.ProfessionDAO;
public class RegisterUI extends JFrame {
   
private JTextField name;
private JTextField phone_no;
private JTextField city;
private JTextField street;
private JTextField indicator;
private JTextField gender;
private JPasswordField plain_password;
private JComboBox<String> profDropdown;
private JTextField age;
private JButton Register_btn;
private JRadioButton yes_btn;
private JRadioButton no_btn;
private ButtonGroup yesno_grp;

    public RegisterUI(String role){
 setTitle("Home Assistance Registration portal");
   setSize(500,600);
   setDefaultCloseOperation(EXIT_ON_CLOSE);
   setBackground(Color.GRAY);
   setLayout(null);
   JLabel projectTitle=new JLabel("HomeService --- Registration");
projectTitle.setBounds(50,30,300,30);
projectTitle.setFont(new Font("Arial",Font.BOLD,20));
add(projectTitle);
JLabel name_lbl= new JLabel("Enter Your UserName: ");
name_lbl.setBounds(50,70,200,30);
add(name_lbl);
name=new JTextField();
name.setBounds(250,70,150,30);
add(name);
JLabel phone_lbl= new JLabel("Enter Your Phone number: ");
phone_lbl.setBounds(50,110,200,30);
add(phone_lbl);
phone_no=new JTextField();
phone_no.setBounds(250,110,150,30);
add(phone_no);
JLabel age_lbl= new JLabel("Enter Your Age: ");
age_lbl.setBounds(50,150,200,30);
add(age_lbl);
age=new JTextField();
age.setBounds(250,150,150,30);
add(age);
JLabel gender_lbl= new JLabel("Enter Your Gender(male/female): ");
gender_lbl.setBounds(50,190,200,30);
add(gender_lbl);
gender=new JTextField();
gender.setBounds(250,190,150,30);
add(gender);
JLabel city_lbl= new JLabel("Enter Your City name: ");
city_lbl.setBounds(50,220,200,30);
add(city_lbl);
city=new JTextField();
city.setBounds(250,220,150,30);
add(city);
JLabel street_lbl= new JLabel("Enter Your Street name: ");
street_lbl.setBounds(50,260,200,30);
add(street_lbl);
street=new JTextField();
street.setBounds(250,260,150,30);
add(street);
JLabel indicator_lbl= new JLabel("Write any indicator(address): ");
indicator_lbl.setBounds(50,300,200,30);
add(indicator_lbl);
indicator=new JTextField();
indicator.setBounds(250,300,150,30);
add(indicator);
JLabel password_lbl=new JLabel("Enter you Password(8 characters): ");
password_lbl.setBounds(50,340,200,30);
add(password_lbl);
plain_password=new JPasswordField();
plain_password.setBounds(250,340,150,30);
add(plain_password);


Register_btn= new JButton("Register ");
   Register_btn.setBounds(200,500,170,50);
   add(Register_btn);


   if(role=="User"){
Register_btn.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent ae){
       handleUserRegister();
    }
});



   }
if(role=="Worker"){
JLabel prof_dd=new JLabel("Select your profession: ");
prof_dd.setBounds(50,380,200,30);
add(prof_dd);
ProfessionDAO p_dao=new ProfessionDAO();
String[] professions=p_dao.getprofessionList();
profDropdown=new JComboBox<>(professions);
profDropdown.setBounds(250,380,150,30);
add(profDropdown);

JLabel onduty_lbl=new JLabel("Are you marking your availability now?: ");
onduty_lbl.setBounds(50,420,250,30);
add(onduty_lbl);
yes_btn=new JRadioButton("Yes");
yes_btn.setBounds(300,420,100,20);
no_btn=new JRadioButton("No");
no_btn.setBounds(300,450,100,20);
yesno_grp=new ButtonGroup();
yesno_grp.add(yes_btn);
yesno_grp.add(no_btn);
add(yes_btn); 
add(no_btn);


Register_btn.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent ae){
       handleWorkerRegister();
    }
});
}

setVisible(true);
    }

    private void handleUserRegister(){
 UserDAO u_dao=new UserDAO();
 try{
    boolean registered = false;
String username=name.getText().trim();
String phone=phone_no.getText().trim();
String plainPass=new String(plain_password.getPassword()).trim();
String u_city=city.getText().trim();
String u_street=street.getText().trim();
String u_age=age.getText().trim();
String u_gender=gender.getText().trim();
String u_indicator=indicator.getText().trim();
int int_age=Integer.parseInt(u_age);
if(u_indicator.isEmpty()){
    u_indicator="null";
}
if(u_gender.isEmpty()) u_gender="not to mention";

    if(username.isEmpty()||plainPass.isEmpty()||u_city.isEmpty()||u_street.isEmpty()||phone.isEmpty()||u_age.isEmpty()){
JOptionPane.showMessageDialog(this, "Please fill must required fields before registering.");
return;    
}else{
    registered=u_dao.registerNewUser(username, phone,int_age , u_gender, u_city, u_street, u_indicator, plainPass);
}
if(registered){
    JOptionPane.showMessageDialog(this, "You are now Registered successfully!");
}else{
    JOptionPane.showMessageDialog(this, "Registration went unsuccessful.Try again.\n");
}
 }catch(NumberFormatException e){
JOptionPane.showMessageDialog(this, "inavlid age input(must be number)"+e.getMessage());
 }catch(NullPointerException e){
JOptionPane.showMessageDialog(this, "inavlid input: "+e.getMessage());
 }
    }


      private void handleWorkerRegister(){
WorkerDAO w_dao=new WorkerDAO();
ProfessionDAO p_dao=new ProfessionDAO();

 try{
    String profname= (String) profDropdown.getSelectedItem();
    Boolean onduty=false;
    if(yes_btn.isSelected()) onduty=true;
boolean registered=false;
String workername=name.getText().trim();
String phone=phone_no.getText().trim();
String plainPass=new String(plain_password.getPassword()).trim();
String w_city=city.getText().trim();
String w_street=street.getText().trim();
String w_age=age.getText().trim();
String w_gender=gender.getText().trim();
String w_indicator=indicator.getText().trim();
int int_age=Integer.parseInt(w_age);
if(w_indicator.isEmpty()){
    w_indicator="null";
}
if(w_gender.isEmpty()) w_gender="not to mention";
    if(workername.isEmpty()||plainPass.isEmpty()||w_city.isEmpty()||w_street.isEmpty()||phone.isEmpty()||w_age.isEmpty()||profname.isEmpty()){
JOptionPane.showMessageDialog(this, "Please fill must required fields before registering.");
return;    
}else{
  registered= w_dao.registerNewWorker(workername, phone,int_age , w_gender, w_city, w_street, w_indicator, plainPass,onduty, p_dao.findProfidbyname(profname));
}
if(registered){
    JOptionPane.showMessageDialog(this, "You are now Registered successfully!");
}else{
    JOptionPane.showMessageDialog(this, "Registration went unsuccessful.Try again.\n");
}
 }catch(NumberFormatException e){
JOptionPane.showMessageDialog(this, "inavlid age input(must be number)"+e.getMessage());
 }catch(NullPointerException e){
JOptionPane.showMessageDialog(this, "inavlid input: "+e.getMessage());
 }
    } 


    public static void main(String args[]){
        RegisterUI r=new RegisterUI("Worker");
r.setVisible(true);

    }
}
