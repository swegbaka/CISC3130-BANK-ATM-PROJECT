public class Depositor {
    private Name name;
    private String secNum;
    Depositor(Name name, String secNum){
        this.name = name;
        this.secNum = secNum;
    }
    //setters
    public void setName(Name name){
        this.name = name;
    }
    public void setSecNum(String secNum){
        this.secNum = secNum;
    }
    //getters
    public Name getName(){
        return this.name;
    }
    public String getSecNum(){
        return this.secNum;
    }
    public String toString(){
        return ""+this.name+this.secNum;
    }
}
