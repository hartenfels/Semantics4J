package syntax;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.extendj.JavaChecker;
import org.extendj.parser.JavaParser;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.*;


public abstract class SyntaxTestBase {
  private static final String DIR = "src/test/resources/syntax/";

  private static class Checker extends JavaChecker {
    private String runChecker(String file) throws IOException {
      int ret = run(new String[]{"-classpath", "build/classes/main", file});
      if (!file.contains("UNCHECKED")) {
        assertEquals(String.format("static checking of '%s'", file), 0, ret);
      }
      return program.structuredPrettyPrint();
    }
  }

  private static String slurp(String file) throws IOException {
    return new String(Files.readAllBytes(Paths.get(file)), UTF_8);
  }

  protected void checkSyntax(String name) {
    String actual, expected;
    try {
      actual   = new Checker().runChecker(DIR + name + ".java");
      expected = slurp(DIR + name + ".expected");
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(expected.trim(), actual.trim());
  }
}
