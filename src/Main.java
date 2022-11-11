import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        for (String arg :
                args) {
            RecursiveDescent recursiveDescent = new RecursiveDescent(arg); // 여러개 들어올 수 있음.
            recursiveDescent.Run();
        }
    }
}
