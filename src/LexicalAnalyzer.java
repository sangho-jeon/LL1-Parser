import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class LexicalAnalyzer { // singletone class for reading grammer and for getting grammer table;

    private static LexicalAnalyzer lexicalAnalyzer = null;
    private HashMap<String, LinkedList<String>> first = new HashMap<>();
    private String path;
    private Path file;
    public LexicalAnalyzer() throws IOException {

    }
    public void setFilePath(String path){
        this.path = path;
        this.file = Paths.get(path);
    }

    public ArrayList<ArrayList<Token>> Lexical() throws IOException { // returns Lexical analyzed tokens;
        try (BufferedReader reader = Files.newBufferedReader(this.file)) {
            ArrayList<ArrayList<Token>> lexims = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                ArrayList<Token> tokensOfLine = new ArrayList<>();
                StringTokenizer st = new StringTokenizer(line, ";| ", true);
                while(st.hasMoreElements()){
                    String token  = st.nextToken();
                    //toDo -> token 식별 및 저장
                    tokensOfLine.add(new Token(NextToken.IDENT, 0));
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
}
