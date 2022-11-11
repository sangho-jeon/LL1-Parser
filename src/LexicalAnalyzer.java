import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class LexicalAnalyzer { // lexical analyzing class.

    private Path file;

    public LexicalAnalyzer() {

    }

    public void setFilePath(String path) {
        this.file = Paths.get(path);
    }

    public ArrayList<Queue<Token>> Lexical() { // returns Lexical analyzed tokens;
        try (BufferedReader reader = Files.newBufferedReader(this.file)) {
            ArrayList<Queue<Token>> lexims = new ArrayList<>();
            String line;
            Queue<Token> tokensOfLine = new LinkedList<>();

            while ((line = reader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ";| +-*/()", true);
                while (st.hasMoreElements()) {
                    String token = st.nextToken();
                    if (token.isBlank()) continue;

                    NextToken type;
                    if (isNumeric(token)) {
                        tokensOfLine.add(new Token(NextToken.CONST, token));
                    } else {
                        type = getTokenType(token);
                        tokensOfLine.add(new Token(type, token));
                    }

                    if (Objects.equals(token, ";")) {
                        lexims.add(tokensOfLine);
                        tokensOfLine = new LinkedList<>();
                    }
                }
            }
            if (!tokensOfLine.isEmpty()) {
                Token semiColon = new Token(NextToken.SEMI_COLON, ";");
                if(!tokensOfLine.contains(semiColon)){
                    tokensOfLine.add(semiColon);
                }
                lexims.add(tokensOfLine);
            }
            return lexims;
        } catch (IOException e) {
            System.out.println("File not Found");
            e.printStackTrace();
        }
        return null;
    }

    private boolean isNumeric(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private NextToken getTokenType(String token) {
        switch (token) {
            case ":=":
                return NextToken.ASSIGNMENT_OP;
            case "+":
            case "-":
                return NextToken.ADD_OPERATOR;
            case "*":
            case "/":
                return NextToken.MULT_OPERATIOR;
            case "(":
                return NextToken.LEFT_PAREN;
            case ")":
                return NextToken.RIGHT_PAREN;
            case ";":
                return NextToken.SEMI_COLON;
            default:
                if (token.matches("^[a-zA-Z0-9]*$")) {
                    return NextToken.IDENT;
                }
                return NextToken.NOT_FOUND;
        }
    }
}
