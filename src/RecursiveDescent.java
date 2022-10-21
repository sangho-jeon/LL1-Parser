import java.io.IOException;
import java.util.ArrayList;

public class RecursiveDescent {
    private LexicalAnalyzer lexicalAnalyzer;
    private ArrayList<ArrayList<Token>> tokenStream;

    public RecursiveDescent(String path) {
        this.lexicalAnalyzer = new LexicalAnalyzer();
        this.lexicalAnalyzer.setFilePath(path);
    }

    public void Run() throws IOException {
        tokenStream = this.lexicalAnalyzer.Lexical();
        for (ArrayList<Token> L:
             tokenStream) {
            for (Token T:
                 L) {
                T.out();
            }
            System.out.println("-------------------------------------");
        }
    }
}
