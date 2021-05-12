class Name {
    private String first;
    private String last;
    Name(String first, String last){
        this.first = first;
        this.last = last;
    }

    //setters
    public void settFirst(String first){
        this.first = first;
    }
    public void setLast (String last){
        this.last = last;
    }
    //getters
    public String getFirst(){
        return this.first;
    }
    public String getLast(){
        return this.last;
    }
    public String toString(){
        return this.first+"\t"+this.last+"\t";
    }
}
