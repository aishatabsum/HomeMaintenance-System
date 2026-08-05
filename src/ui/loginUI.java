package src.ui;
import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import src.dao.UserDAO;
import src.dao.WorkerDAO;
public class loginUI extends JFrame {
    
private JTextField signup_name;
private JPasswordField signup_password;
private JButton UserLogin_btn;
private JButton WorkerLogin_btn;
private UserDAO user_dao=new UserDAO();
private WorkerDAO worker_dao=new WorkerDAO();

    public loginUI(){
   setTitle("Home Assistance Login portal");
   setSize(500,500);
   setDefaultCloseOperation(EXIT_ON_CLOSE);
   setBackground(Color.GRAY);
   setLayout(null);

JLabel projectTitle=new JLabel("HomeService --- LOGIN.");
projectTitle.setBounds(50,40,250,40);
projectTitle.setFont(new Font("Arial",Font.BOLD,20));
add(projectTitle);
   JLabel name_lbl=new JLabel("UserName: ");
   name_lbl.setBounds(50,100,150,30);
   add(name_lbl);
   signup_name=new JTextField();
    signup_name.setBounds(200,100,150,30);
 add(signup_name);

JLabel password_lbl=new JLabel("Password: ");
   password_lbl.setBounds(50,150,150,30);
   add(password_lbl);
   signup_password=new JPasswordField();
   signup_password.setBounds(200,150,150,30);
   add(signup_password);

   UserLogin_btn= new JButton("Login as Home owner");
   UserLogin_btn.setBounds(40,230,200,50);
   add(UserLogin_btn);

   WorkerLogin_btn= new JButton("Login as Worker");
   WorkerLogin_btn.setBounds(250,230,200,50);
   add(WorkerLogin_btn);

   UserLogin_btn.addActionListener(new ActionListener(){
  public void actionPerformed(ActionEvent ae){
          handleUserLogin();
  }
   });
     WorkerLogin_btn.addActionListener(new ActionListener(){
  public void actionPerformed(ActionEvent ae){
          handleWorkerLogin();
  }
   });

    }

    private void handleUserLogin(){
    String username=signup_name.getText().trim();
String plainPassword=new String(signup_password.getPassword()).trim();
try{
    if(username.isEmpty()||plainPassword.isEmpty()){
JOptionPane.showMessageDialog(this, "Please fill all fields before logging in.");
return;
}
if(!user_dao.usernameExists(username)){
        JOptionPane.showMessageDialog(this, "OOPS, there is no such record registered.");
        int result=JOptionPane.showConfirmDialog(this,"Do you want to register? ");
        if(result==JOptionPane.YES_OPTION){
            new RegisterUI("User").setVisible(true);
           this.dispose();
        }else{
            signup_name.setText("");
             signup_password.setText("");
             return;
        }
    }else{
if(user_dao.verifyUserLogin(username, plainPassword)){
    JOptionPane.showMessageDialog(this, " Logged in successfully. Welcome Back .."+username);
    new DashboardUI("User",user_dao.findUseridbyname(username)).setVisible(true);
    this.dispose();   
}else{
   JOptionPane.showMessageDialog(this, "Invalid username or Password.", "Error", JOptionPane.ERROR_MESSAGE);
  }
}
}
catch(Exception e){
    JOptionPane.showMessageDialog(this, "ERROR: "+e.getMessage());
}

    
    }

    private void handleWorkerLogin(){
String workername=signup_name.getText().trim();
String plainPassword=new String(signup_password.getPassword()).trim();
try{
    if(workername.isEmpty()||plainPassword.isEmpty()){
JOptionPane.showMessageDialog(this, "Please fill all fields before logging in.");
return;
}
if(!worker_dao.workernameTaken(workername)){
        JOptionPane.showMessageDialog(this, "OOPS, there is no such record registered.");
        int result=JOptionPane.showConfirmDialog(this,"Do you want to register? ");
        if(result==JOptionPane.YES_OPTION){
           new RegisterUI("Worker").setVisible(true);
           this.dispose();
        }else{
            signup_name.setText("");
             signup_password.setText("");
             return;
        }
    }else{
if(worker_dao.verifyWorkerLogin(workername, plainPassword)){
    JOptionPane.showMessageDialog(this, " Logged in successfully. Welcome Back .. "+workername);
    new DashboardUI("Worker",worker_dao.findWorkeridbyname(workername)).setVisible(true);
    this.dispose();
}
else{
        JOptionPane.showMessageDialog(this, "Invalid username or Password.", "Error", JOptionPane.ERROR_MESSAGE);
       }
    }
   }catch(Exception e){
    JOptionPane.showMessageDialog(this, "ERROR: "+e.getMessage());
}

    }
}
