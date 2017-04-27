import java.io.IOException;
import org.extendj.JavaChecker;


public class Main {
  private static class Checker extends JavaChecker {
    private void runSemantics(String[] args) throws IOException {
      int exitCode = run(args);

      if (exitCode != 0) {
        System.exit(exitCode);
      }

      System.out.println(program.structuredPrettyPrint());
    }
  }

  public static void main(String[] args) throws IOException {
    new Checker().runSemantics(args);
  }
}
