public class BankAccount {
    //data members to store values
    //account Type
    //C - Checking
    //S - Saving
    //D - CD
    private Depositor depositor;
    private int accNum;
    private String accType;
    private double balance;
    //constructor
    BankAccount(Depositor depositor, int accNum, String accType, double balance){
        this.accNum = accNum;
        this.accType = accType;
        this.balance = balance;
        this.depositor = depositor;
    }
    //getters
    public Depositor getDepositor(){
        return this.depositor;
    }
    public int getAccNum(){
        return this.accNum;
    }
    public String getAccType(){
        return this.accType;
    }
    public double getBalance(){
        return this.balance;
    }
    //setters
    public void setDepositor(Depositor depositor){
        this.depositor = depositor;
    }
    public void setAccNum(int accNum){
        this.accNum = accNum;
    }
    public void setAccType(String accType){
        this.accType = accType;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }
    public String toString(){
        return ""+this.depositor+this.accNum+this.accType+this.balance;
    }

}
