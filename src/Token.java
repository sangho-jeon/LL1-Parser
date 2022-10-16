public class Token {
    private NextToken token_type;
    private int value;
    public Token(NextToken token_type, int value){
        this.token_type = token_type;
        this.value = value;
    }
    public Token(NextToken token_type){
        this.token_type = token_type;
    }
}
