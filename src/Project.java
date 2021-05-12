import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;


/* This project used stack, queue, and hashmap
 *This bank app allows user to deposit, withdrawal,
 * make a new account, or delete existing account if balance
 * of existing account is zero.
 */
public class Project {
    int MAX_NUM;
    static BankAccount[] accounts;
    static int curAccts;

    public static void main(String[] args) throws IOException {
        //constant definitions
        final int MAX_NUM = 50;
        HashMap<Integer, String> hash = new HashMap<>();

        //variable declarations
        int numAccts;         //number of accounts
        char choice;         //menu item selected
        boolean notDone = true;      //loop control flag
        BankAccount [] bankact = new BankAccount [MAX_NUM];
        double balanceArray[] = new double [MAX_NUM];
        Stack s = new Stack();
        Queue<String> q = new LinkedList<>();
        int count=0;

        //create Scanner object
        Scanner input = new Scanner(System.in);

        // open the output file
        PrintWriter output = new PrintWriter("OUTPUT.txt");

        /* first part */
        /* fill and print initial database */
        numAccts = readAccts(bankact, MAX_NUM);

        /*Implement Queue*/
        /*q.peek() for first printable statement*/
        q.offer("Welcome to JZ Bank!");//first printable statement
        q.offer("Thank you for your continued support to our bank.\n");//second printable statement

        Files.write(Paths.get("OUTPUT.txt"), q.peek().getBytes(), StandardOpenOption.APPEND);
        output.println(q.peek());

        printAccts(bankact,balanceArray, numAccts, output, hash, q);

        /* second part */
        /* prompts for a transaction and then */
        /* call functions to process the requested transaction */
        menu();
        do {
            choice = input.next().charAt(0);
            switch(choice)
            {
                case 'q':
                case 'Q':
                    notDone = false;
                    printAccts(bankact,balanceArray, numAccts, output, hash, q);
                    break;
                case 'd':
                case 'D':
                    deposit(bankact,balanceArray, numAccts, input, s);
                    menu();
                    count++;
                    break;
                case 'B':
                case 'b':
                    balance(bankact, numAccts, input, s);
                    menu();
                    count++;
                    break;
                case 'w':
                case 'W':
                    withdrawal(bankact,balanceArray, numAccts, input, output);
                    menu();
                    count++;
                    break;
                case 'n':
                case 'N':
                    numAccts = newAcct(bankact, numAccts, input, output);
                    menu();
                    count++;
                    break;
                case 'x':
                case 'X':
                    numAccts = deleteAcct(bankact, numAccts, input, output);
                    menu();
                    count++;
                    break;
                default:
                    output.println("Error: " + choice + " is an invalid selection -  try again");
                    output.println();
                    output.flush();
                    break;
            }
            // give user a chance to look at output before printing menu;
        } while (notDone && input.hasNext());

        /*Implement Stack */
        /*count is the total number of the transaction we did*/
        String sa = "Transaction times(" + count + ")";
        s.push(sa);
        Files.write(Paths.get("OUTPUT.txt"), sa.getBytes(), StandardOpenOption.APPEND);
        output.println(s.peek());

        /*Implement Queue*/
        /*q.offer("The current record of all customers above.");*/
        /*q.pop() for second printable statement*/
        Files.write(Paths.get("OUTPUT.txt"), ((LinkedList<String>) q).pop().getBytes(), StandardOpenOption.APPEND);
        output.println(q.peek());

        //close the output file
        output.flush();
        output.close();

        //close the test cases input file
        input.close();

        System.out.println();
        System.out.println("The program is terminating");
    }



    Project(int max_num)
    {
        this.MAX_NUM = max_num;
        accounts = new BankAccount[MAX_NUM];
        curAccts = 0;
    }

    /*  Method  readAccts()
     * Input:
     *    acctNumArray  -  reference  to  array  of  account  numbers
     *    balanceArray  -  reference  to  array  of  account  balances
     *    maxAccts  -  maximum  number  of  active  accounts  allowed
     *  Process:
     * Reads the initial database of accounts and balances
     *  Output:
     *    Fills  in  the  initial  account  and  balance  arrays  and  returns  the  number  of  active  accounts
     */

    public static int readAccts(BankAccount [] account,int maxAccts)throws IOException
    {
        File database = new File ("database2.txt");
        Scanner input = new Scanner (database);
        int count = 0;
        String whitespace;

        while (input.hasNext() ) {
            //read next line of data
            whitespace = input.nextLine();
            StringTokenizer myLine = new StringTokenizer(whitespace);

            //create NamePrv, JobInfoPrv, PersonalInfoPrv, and ContestantPrv objects
            Name myName = new Name( myLine.nextToken (),
                    myLine.nextToken());
            Depositor mydepositor = new Depositor( myName,
                    myLine.nextToken());
            account [count] = new BankAccount( mydepositor,
                    Integer.parseInt(myLine.nextToken()),
                    myLine.nextToken(),
                    Double.parseDouble(myLine.nextToken()));

            //set the array element
            //account [count] = myInfo;
            count++;

        }
        input.close();
        return count;
    }

    public static void menu() {
        System.out.println();
        System.out.println("Select one of the following transactions:");
        System.out.println("\t****************************");
        System.out.println("\t      List of Choices        ");
        System.out.println("\t****************************");
        System.out.println("\t     W -- Withdrawal");
        System.out.println("\t     D -- Deposit");
        System.out.println("\t     N -- New Account");
        System.out.println("\t     B -- Balance Inquiry");
        System.out.println("\t     X -- Delete Account");
        System.out.println("\t     Q -- Quit");
        System.out.println();
        System.out.print("\tEnter your selection: ");
    }

    /*  Method  findAcct:
     * Input:
     *    acctNumArray  -  array  of  account  numbers
     *    numAccts  -  number  of  active  accounts
     *    requestedAccount  -  requested  account  requested_number
     *  Process:
     *    Performs  a  linear  search  on  the  acctNunArray  for  the  requested  account
     *  Output:
     * If found, the index of the requested account is returned
     *    Otherwise,  returns  -1
     */


    public static int findAcct(BankAccount [] account, int numAccts, int reqAccount, Scanner input)
    {

        int reqnum = -1;
        for (int i = 0; i < numAccts; i++) {
            if(account[i].getAccNum() == reqAccount){

                reqnum = i;//index
                break;
            }
        }
        return reqnum;
    }

    /*  Method  withdrawal:
     * Input:
     *    acctNumArray  -  array  of  account  numbers
     *    balanceArray  -  array  of  account  balances
     *    numAccts  -  number  of  active  accounts
     * output - reference to the output file
     * input - reference to the "test cases" input file
     *  Process:
     *    Prompts  for  the  requested  account
     *    Calls  findacct()  to  see  if  the  account  exists
     *    If  the  account  exists,  prompts  for  the  amount  to  withdrawal
     *    If  the  amount  is  valid,  it  makes  the  withdrawal  and  prints  the  new  balance
     *    Otherwise,  an  error  message  is  printed
     *  Output:
     *    For  a  valid  withdrawal,  the  withdrawal  transaction  is  printed
     *    Otherwise,  an  error  message  is  printed
     */


    public static void withdrawal(BankAccount [] account,double []balanceArray, int num_accts, Scanner input,
                                  PrintWriter output)
    {

        int requestedaccount, index;
        double Withdrawal;

        System.out.println();
        System.out.print("Enter the account number: ");   //prompt for account number
        requestedaccount = input.nextInt();      //read-in the account number
        index = findAcct(account, num_accts , requestedaccount,input);

        if (index == -1) {
            output.println("Transaction Type: Withdrawl");
            output.println("Error: Account number " + requestedaccount + " does not exist");
            output.println();
        }else {
            System.out.println("Enter amount to withdrawl: ");
            Withdrawal=input.nextDouble();
            if(Withdrawal<=0.00 || Withdrawal > account[index].getBalance()){
                //invalid amount to withdrawal
                output.println("Error: $%.2f is an insufficient amount" + Withdrawal);
                output.println();
            }
            output.println("Transaction Requested: Withdrawal");
            output.println("Account Number: " + requestedaccount);
            output.println("Amount to withdrawal: $" + Withdrawal);
            balanceArray[index] = balanceArray[index] - Withdrawal;
            output.println();

        }
    }


    /*  Method  deposit:
     * Input:
     *    acctNumArray  -  array  of  account  numbers
     *    balanceArray  -  array  of  account  balances
     *    numAccts  -  number  of  active  accounts
     * output - reference to the output file
     * input - reference to the "test cases" input file
     *  Process:
     *    Prompts  for  the  requested  account
     *    Calls  findacct()  to  see  if  the  account  exists
     *    If  the  account  exists,  prompts  for  the  amount  to  deposit
     *    If  the  amount  is  valid,  it  makes  the  deposit  and  prints  the  new  balance
     *    Otherwise,  an  error  message  is  printed
     *  Output:
     * For a valid deposit, the deposit transaction is printed
     *    Otherwise,  an  error  message  is  printed
     */


    public static void deposit(BankAccount [] account,double[] balanceArray, int num_accts, Scanner input,
                               Stack s) {
        int requestedAccount;
        int index;
        double amountToDeposit;

        System.out.println();
        System.out.print("Enter the account number: ");   //prompt for account number
        requestedAccount = input.nextInt();      //read-in the account number

        //call findAcct to search if requestedAccount exists
        index = findAcct(account, num_accts , requestedAccount,input);

        if (index == -1)                                        //invalid account
        {
            s.push("Transaction Requested: Deposit");
            s.push("Error: Account number " + requestedAccount + " does not exist");
        } else                                                    //valid account
        {
            System.out.print("Enter amount to deposit: ");  //prompt for amount to deposit
            amountToDeposit = input.nextDouble();      //read-in the amount to deposit
            if( amountToDeposit <0 ){
                s.push("Transaction Requested: Deposit");
                s.push("Account Number: " + requestedAccount);
                s.push("Error: "+amountToDeposit+" invalid amount to deposit");
            }else{

                s.push("Transaction Requested: Deposit");
                s.push("Account Number: " + requestedAccount);
                s.push("Amount to Deposit: $" + amountToDeposit);
                balanceArray[index] += amountToDeposit;
                if(amountToDeposit>=500){
                    balanceArray[index] += balanceArray[index]*0.1;
                    System.out.println("You have 10% bounces");
                }
                s.push("\n");
            }
        }

    }

    /*  Method  newAcct:
     * Input:
     *    acctNumArray  -  array  of  account  numbers
     *    balanceArray  -  array  of  account  balances
     *    numAccts  -  number  of  active  accounts
     * output - reference to the output file
     * input - reference to the "test cases" input file
     *  Process:
     *    Prompts  for  the  requested  account
     *    Calls  findacct()  to  see  if  the  account  exists
     * If the account exist, it prints a message
     * If the account does not exist, it creates a new account
     *  Output:
     *    For  a  valid  newAcct,  a  new  account  number  is  created
     *    Otherwise,  an  error  message  is  printed
     */


    public static int newAcct(BankAccount [] account, int num_accts, Scanner input,
                              PrintWriter output) {
        int index,newaccount;
        System.out.println("Enter your new account number: ");
        newaccount=input.nextInt();
        index= findAcct(account, num_accts, newaccount,input);

        if (index == -1) {
            Scanner inp = new Scanner(System.in);
            System.out.print("Enter First Name: ");
            String firstName = inp.nextLine();

            System.out.print("Enter Last Name: ");
            String lastName = inp.nextLine();

            System.out.print("Enter Social Security Number: ");
            String secNum = inp.nextLine();

            System.out.println("Enter Account Type :");
            System.out.println("C - Checking");
            System.out.println("S - Saving");
            System.out.println("D - CD ");
            System.out.print("Enter your choice: ");
            String accType = inp.nextLine();

            System.out.print("Initial Account Balance: ");
            double balance = inp.nextDouble();
            Name name = new Name(firstName,lastName);
            Depositor depositor = new Depositor(name,secNum);
            BankAccount account1 = new BankAccount(depositor,newaccount,accType,balance);


            account[num_accts] = account1;
            num_accts++;
            output.println("Account Creation Successful \n"+firstName+"\t"
                    +lastName+"\t"+secNum+"\t"+newaccount+"\t"+accType+"\t"+balance);
            output.println();
            inp.close();
        }
        return num_accts;
    }

    /*  Method  deleteAcct:
     * Input:
     *    acctNumArray  -  array  of  account  numbers
     *    balanceArray  -  array  of  account  balances
     *    numAccts  -  number  of  active  accounts
     * output - reference to the output file
     * input - reference to the "test cases" input file
     *  Process:
     *    Prompts  for  the  requested  account
     *    Calls  findacct()  to  see  if  the  account  exists
     * If the account exists and has zero balance, it deletes account
     * If the account exists but non-zero balance, it prints an error message
     *    Otherwise,  an  error  message  is  printed
     *  Output:
     *    For  a  valid  account,  deletion  succeed
     *    Otherwise,  an  error  message  is  printed
     */



    public static int deleteAcct(BankAccount [] account, int num_accts, Scanner input,
                                 PrintWriter output) {
        int index, delaccount;
        System.out.println("Enter number account you need to delete");
        delaccount=input.nextInt();
        index=findAcct(account, num_accts, delaccount,input);

        if (index == -1) {
            output.println(delaccount+"Account number does not exist");
            output.println();
        }else if (index != -1 && account[index].getBalance() >0) {
            output.println(delaccount+"have money left,account cannot delete");
            output.println();
        }else {
            for(int i=index; i < num_accts; i++) {
                account[i]=account[i+1];
            }
            output.println("Transaction Requested: Deletion");
            output.printf("Deletion successes");
            output.println();
            num_accts--;
            System.out.println("Completed");
            System.out.println("Next following transactions:");
        }
        return num_accts;
    }


    /*  Method  balance:
     * Input:
     *    acctNumArray  -  array  of  account  numbers
     *    balanceArray  -  array  of  account  balances
     *    numAccts  -  number  of  active  accounts
     * output - reference to output file
     * input - reference to the "test cases" input file
     *  Process:
     *    Prompts  for  the  requested  account
     *    Calls  findAcct()  to  see  if  the  account  exists
     *  If the account exists, the balance is  printed
     *    Otherwise,  an  error  message  is  printed
     *  Output:
     * If the account exists, the balance is printed
     *    Otherwise,  an  error  message  is  printed
     */


    public static void balance(BankAccount [] account, int num_accts, Scanner input,
                               Stack s) {
        int acctnum;
        //prompt and read an account number
        System.out.print("\nEnter account number: ");
        acctnum = input.nextInt();
        //check if account exits
        int index = findAcct(account, num_accts, acctnum,input);
        //display error message if account does not exits

        if(index == -1) {
            s.push("Transaction Requested: Balance Inquiry");
            s.push(acctnum+" Account does not exist.\n");
            s.push("\n");
        }else{
            s.push("Transaction Requested: Balance Inquiry");
            s.push("Account Person and Number: "+ account[index].getDepositor());
            s.push("Balance: " + account[index].getBalance());
            s.push("Account Type: " + account[index].getAccType());
            s.push("\n");
        }
    }

    public static void accountInfo(BankAccount [] account, int num_accts, Scanner input,
                                   PrintWriter output) {
        String ssn = input.nextLine();
        for(int i = 0;i<curAccts;i++) {
            if(ssn.equals(accounts[i].getDepositor().getSecNum())) {
                output.println(accounts[i]);
            }
        }
    }

    public static void printAccts(BankAccount [] account, double [] balanceArray, int numAccts,
                                  PrintWriter output, HashMap<Integer, String> name, Queue<String> q) {

        output.println("First\tLastName\tSSN\t\t\tAccount number\tAccount type\tBalance");
        System.out.print("Welcome to JZ Bank! There are some suggestions for follow customers\n\n");

        double newBalance = 0;
        String pulled;
        for(int i = 0;i<numAccts;i++)
        {

            output.format("%15s",account[i].getDepositor());


            output.format("%15s",account[i].getAccNum());


            output.format("%15s",account[i].getAccType());

            newBalance = account[i].getBalance() + balanceArray[i];

            output.format("%13.2f",newBalance);

            if(newBalance >= 1000){
                name.put(account[i].getAccNum(), "Welcome back! our honored customer!");
                pulled = name.get(account[i].getAccNum());
                System.out.print("Account #"+account[i].getAccNum() + ", " + pulled);
            }
            if(newBalance <= 0){
                name.put(account[i].getAccNum(), "Save more money or current account will freeze in next 14 days!");
                pulled = name.get(account[i].getAccNum());
                System.out.print("Account #"+account[i].getAccNum() + ", " + pulled);
            }
            if(newBalance >0 && newBalance <1000){
                name.put(account[i].getAccNum(), "Save more than $500 for having up to 10% cash back in transaction!");
                pulled = name.get(account[i].getAccNum());
                System.out.print("Account #"+account[i].getAccNum() + ", " + pulled);
            }
            System.out.println();
            output.println();

        }
        output.println();

    }

}