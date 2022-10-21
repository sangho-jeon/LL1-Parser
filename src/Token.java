public class Token<T> { // Token class. contains type and value;
    private NextToken token_type;
    private T value;
    public Token(NextToken token_type, T value){
        this.token_type = token_type;
        this.value = value;
    }
    public Token(NextToken token_type){
        this.token_type = token_type;
    }

    public NextToken getToken_type(){
        return this.token_type;
    }

    public T getToken_value(){

        return this.value;
    }
    public void out(){
        System.out.println(this.token_type);
        System.out.println(this.value);
    }
}
