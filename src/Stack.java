public class Stack {
    String element [] = new String [10];
    int top = 0;


    public void push(String x){
        element[top] = x;
        top++;
    }

    public String pop() {
        String data;
        data = element[top++];
        return data;
    }

    public String peek() {
        String data;
        data = element[top-1];
        return data;
    }
}