import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class RecursiveDescent {
    public static HashMap<String, Integer> identValueTable = new HashMap<>();

    private enum STATE {
        IDLE_STATE,
        ASSIGN_STATE,
        OPERATION_STATE;
    }


    private LexicalAnalyzer lexicalAnalyzer;
    private ArrayList<ArrayList<Token>> tokenStream;

    public RecursiveDescent(String path) {
        this.lexicalAnalyzer = new LexicalAnalyzer();
        this.lexicalAnalyzer.setFilePath(path);
    }

    public void Run() {
        tokenStream = this.lexicalAnalyzer.Lexical();
        StringBuilder sb = new StringBuilder();
        int ID_COUNT, OP_COUNT, CONST_COUNT = 0;
        for (ArrayList<Token> line :
                tokenStream) {
            STATE state = STATE.IDLE_STATE;

        }
    }
}
