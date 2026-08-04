package src.model;

public class Worker {
    private int workerid;
    private String workername;
    private String phone_no;
    private String city;
    private String street;
    private String indicator;
    private String pass_hash;
    private String pass_salt;
    private int age;
    private String gender;
    private int profId;
    private boolean is_onduty;

    public Worker(){

    }

    public int getWorkerid(){
        return workerid;
    }

    public String getworkerName(){
        return workername;
    }

    public String getw_Phone(){
        return phone_no;
    }

    public String getw_City(){
        return city;
    }

    public String getw_Street(){
        return street;
    }

    public String getw_Indicator(){
        return indicator;
    }

    public String getw_passHash(){
        return pass_hash;
    }

    public String getw_passSalt(){
        return pass_salt;
    }

   public  int getw_Age(){
        return age;
    }

    public String getw_Gender(){
        return gender;
    }

    public int getw_profId(){
        return profId;
    }

    public boolean getw_isOnduty(){
        return is_onduty;
    }

    public void setWorkerid(int workerid){
        this.workerid = workerid;
    }

    public void setworkerName(String workername){
        this.workername = workername;
    }

    public void setw_Phone(String phone_no){
        this.phone_no = phone_no;
    }

    public void setw_City(String city){
        this.city = city;
    }

    public void setw_Street(String street){
        this.street = street;
    }

    public void setw_Indicator(String indicator){
        this.indicator = indicator;
    }

    public void setw_passHash(String pass_hash){
        this.pass_hash = pass_hash;
    }

    public void setw_passSalt(String pass_salt){
        this.pass_salt = pass_salt;
    }

    public void setw_Age(int age){
        this.age = age;
    }

    public void setw_Gender(String gender){
        this.gender = gender;
    }

    public void setw_profId(int profId){
        this.profId = profId;
    }

    public void setw_isOnduty(boolean is_onduty){
        this.is_onduty = is_onduty;
    }
}