import javax.management.QueryEval;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Queue;


public class RecursiveDescent {
    private HashMap<String, Integer> identValueTable;
    private final LexicalAnalyzer lexicalAnalyzer;
    private final ArrayList<Queue<Token>> tokenStream;
    private Queue<Token> tokenLine;
    private static final String WARN = "[WARN]: ";
    private static final String ERROR = "[WARN]: ";
    private static final String OK = "[OK]";
    private static final String UNDEF_ERROR = "정의되지 않은 변수(operand1)가 참조됨";
    private static boolean isOk = true;
    private static int ID_COUNT, OP_COUNT, CONST_COUNT;

    public RecursiveDescent(String path) {
        this.identValueTable = new HashMap<>();
        this.lexicalAnalyzer = new LexicalAnalyzer();
        this.lexicalAnalyzer.setFilePath(path);
        this.tokenStream = this.lexicalAnalyzer.Lexical();
    }

    public void Run() {
        StringBuilder sb = new StringBuilder();

        Queue<Token> stream = tokenStream.get(0);
        for (Queue<Token> line :
                tokenStream) {
            ID_COUNT = 0;
            OP_COUNT = 0;
            CONST_COUNT = 0;
            printLine(line);
            tokenLine = line;
            String count = printCount();
            isOk = true;
            parseStatement();
            if (isOk)
                System.out.println(OK);
            System.out.println(count);
            System.out.println("---------------------");
        }
        printResult();
    }

    private void parseStatement() {
        Token thisToken = tokenLine.poll();
        int result = 0;
        if (thisToken.getToken_type() != NextToken.IDENT) {
            System.out.println(ERROR + "statement must start with id!!");
            isOk = false;
            return;
        } else if (!tokenLine.isEmpty()) {
            Token nextToken = tokenLine.poll();
            if (nextToken.getToken_type() != NextToken.ASSIGNMENT_OP) {
                System.out.println(ERROR + "not assignment opperation!!");
                isOk = false;
                return;
            } else {
                result = parseExpression();
                if (result <= -2000000000 || result >= 2000000000)
                    identValueTable.put(thisToken.getToken_value(), Integer.MAX_VALUE);
                else
                    identValueTable.put(thisToken.getToken_value(), result);
            }
        }
    }

    private int parseExpression() {
        int termResult = parseTerm();
        int termTailResult = parseTermTail();
        if (termResult == Integer.MAX_VALUE || termTailResult == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        else
            return termResult + termTailResult;
    }

    private int parseTerm() {
        int factorResult = parseFactor();
        if (factorResult != Integer.MAX_VALUE) {
            if (!tokenLine.isEmpty() && tokenLine.peek().getToken_type() == NextToken.MULT_OPERATIOR) {
                Token nextToken = tokenLine.peek();
                int factorTail = parseFactorTail();
                if (factorTail != Integer.MAX_VALUE) {
                    if (Objects.equals(nextToken.getToken_value(), "*")) {
                        factorResult *= factorTail;
                    } else if (Objects.equals(nextToken.getToken_value(), "/")) {
                        factorResult /= factorTail;
                    }
                }
            }
        }
        return factorResult;
    }

    private int parseTermTail() {
        int result = 0;
        if (!tokenLine.isEmpty() && tokenLine.peek().getToken_type() == NextToken.ADD_OPERATOR) {
            Token thisToken = tokenLine.poll();
            int termResult = parseTerm();
            int termTailResult = parseTermTail();
            if (termResult == Integer.MAX_VALUE || termTailResult == Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            } else {
                if (Objects.equals(thisToken.getToken_value(), "+")) {
                    result += termResult + termTailResult;
                } else if(Objects.equals(thisToken.getToken_value(), "-")){
                    result -= termResult + termTailResult;
                }
            }
            return result;
        }
        return 0;
    }

    private int parseFactor() {
        int result = 0;
        if (tokenLine.isEmpty()) {
            System.out.println(ERROR + "no factor found!");
            isOk = false;
            return 0;
        } else {
            Token thisToken = tokenLine.poll();
            if (thisToken.getToken_type() == NextToken.LEFT_PAREN) {
                result += parseExpression();
                if (tokenLine.isEmpty() || tokenLine.poll().getToken_type() != NextToken.RIGHT_PAREN) {
                    System.out.println(WARN + "there must be Right Paren");
                    isOk = false;
                }
                return result;
            } else if (thisToken.getToken_type() == NextToken.IDENT) {
                ID_COUNT++;
                result = identValueTable.getOrDefault(thisToken.getToken_value(), Integer.MAX_VALUE);
                if (result == Integer.MAX_VALUE) {
                    printWarning(thisToken.getToken_value());
                    identValueTable.put(thisToken.getToken_value(), Integer.MAX_VALUE);
                    isOk = false;
                    return Integer.MAX_VALUE;
                } else
                    return result;
            } else if (thisToken.getToken_type() == NextToken.CONST) {
                return Integer.parseInt(thisToken.getToken_value());
            }
        }
        return 0;
    }

    private int parseFactorTail() { // 여기서 문제!
        int factorResult = 0;
        if (!tokenLine.isEmpty() && tokenLine.peek().getToken_type() == NextToken.MULT_OPERATIOR) {
            tokenLine.poll();
            factorResult = parseFactor();
            if (Objects.equals(tokenLine.peek().getToken_value(), "*")) {
                factorResult *= parseFactorTail();
            } else if (Objects.equals(tokenLine.peek().getToken_value(), "/")) {
                factorResult /= parseFactorTail();
            }
        } else {
            return Integer.MAX_VALUE;
        }
        return factorResult;
    }

    private void printLine(Queue<Token> tokenLine) {
        StringBuilder sb = new StringBuilder();
        for (Token T :
                tokenLine) {
            sb.append(T.getToken_value());
        }
        System.out.println(sb.toString());
    }

    private void printResult() {
        StringBuilder sb = new StringBuilder();
        sb.append("Result ==> ");
        for (String key :
                identValueTable.keySet()) {
            int value = identValueTable.getOrDefault(key, Integer.MAX_VALUE);
            sb.append(key).append(": ");
            if (value <= -2000000000 || value >= 2000000000) {
                sb.append("Unknown ");
            } else
                sb.append(value).append(" ");
        }
        sb.append("\n");
        System.out.println(sb);
    }

    private String printCount() {
        for (Token T :
                tokenLine) {
            if (T.getToken_type() == NextToken.IDENT) {
                ID_COUNT++;
            } else if (T.getToken_type() == NextToken.ADD_OPERATOR || T.getToken_type() == NextToken.MULT_OPERATIOR) {
                OP_COUNT++;
            } else if (T.getToken_type() == NextToken.CONST) {
                CONST_COUNT++;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(ID_COUNT).append("; ");
        sb.append("CONST: ").append(CONST_COUNT).append("; ");
        sb.append("OP: ").append(OP_COUNT).append("; ");
        return sb.toString();
    }


    private void printWarning(String ident) {
        StringBuilder sb = new StringBuilder();
        sb.append(WARN).append("정의되지 않은 변수 ").append(ident).append("가 참조됨");
        System.out.println(sb);
    }
}
