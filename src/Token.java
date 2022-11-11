public class Token { // Token class. contains type and value;
    private final NextToken token_type;
    private final String value;

    public Token(NextToken token_type, String value) {
        this.token_type = token_type;
        this.value = value;
    }

    public NextToken getToken_type() {
        return this.token_type;
    }

    public String getToken_value() {
        return this.value;
    }

    public void out() {
        System.out.println(this.token_type);
        System.out.println(this.value);
    }
}
