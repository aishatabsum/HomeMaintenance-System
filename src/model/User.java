package src.model;

public class User {
private int userid ;
private String username ;
private String phone_no;
private String city ;
private String street ;
private String indicator; 
private String pass_hash; 
private String pass_salt;
private int age ;
private String gender;

public User(){

}
//getters
public int getu_Age(){
    return age;
}

public int getUserid(){
    return userid;
}
public String getu_passHash(){
    return pass_hash;
}

public String getu_passSalt(){
    return pass_salt;
}
public String getuserName(){
    return username;
}

public String getu_City(){
    return city;
}

public String getu_Street(){
    return street;
}
public String getu_Indicator(){
    return indicator;
}

public String getu_Phone(){
    return phone_no;
}
public String getu_Gender(){
    return gender;
}

//setters
public void setu_Age(int age){
    this.age= age;
}

public void setUserid(int userid){
    this.userid= userid;
}
public void setu_passHash(String pass_hash){
    this.pass_hash= pass_hash;
}

public void setu_passSalt(String pass_salt){
    this.pass_salt= pass_salt;
}
public void setuserName(String username){
    this.username= username;
}

public void setu_City(String city){
    this.city= city;
}

public void setu_Street(String street){
    this.street= street;
}
public void setu_Indicator(String indicator){
    this.indicator= indicator;
}

public void setu_Phone(String phone_no){
    this.phone_no= phone_no;
}
public void setu_Gender(String gender){
    this.gender= gender;
}

}
