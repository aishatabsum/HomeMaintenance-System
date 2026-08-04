
package src.model;

public class Profession {
    private int profId;
    private String profname;

    Profession(){

    }

    int getProfId(){
        return profId;
    }

    String getprofName(){
        return profname;
    }

    void setProfId(int profId){
        this.profId = profId;
    }

    void setprofName(String profname){
        this.profname = profname;
    }
}