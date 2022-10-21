import java.io.IOException;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        RecursiveDescent recursiveDescent = new RecursiveDescent(args[0]);
        recursiveDescent.Run();

    }
}
