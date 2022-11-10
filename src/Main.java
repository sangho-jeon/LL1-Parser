import java.io.IOException;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        RecursiveDescent recursiveDescent = new RecursiveDescent(args[0]); // 여러개 들어올 수 있음.

        recursiveDescent.Run();

    }
}
