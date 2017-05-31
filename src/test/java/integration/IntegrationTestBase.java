package integration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.*;


public abstract class IntegrationTestBase {
  private static final String DIR = "src/test/resources/integration/";


  private static class ProcessResult {
    String out = "";
    String err = "";
  }


  private static String slurp(String file) throws IOException {
    return new String(Files.readAllBytes(Paths.get(file)), UTF_8);
  }

  private static String slurpStream(InputStream is) throws IOException {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(is, UTF_8))) {
      return br.lines().collect(Collectors.joining("\n"));
    }
  }


  private static String findWantedOutput(String content, String marker) {
    Pattern p = Pattern.compile("^\\s*\\*" + marker + " (.*)$",
                                Pattern.MULTILINE);

    Matcher       m  = p.matcher(content);
    StringBuilder sb = new StringBuilder();

    while (m.find()) {
      sb.append(m.group(1));
      sb.append('\n');
    }

    return sb.toString();
  }


  private static int run(ProcessResult res, String... args)
      throws IOException, InterruptedException {
    Process proc = new ProcessBuilder(args).start();
    int     exit = proc.waitFor();

    res.out += slurpStream(proc.getInputStream());
    res.err += slurpStream(proc.getErrorStream());

    return exit;
  }


  private static ProcessResult compileAndRun(String name, String file)
      throws IOException, InterruptedException {
    ProcessResult res = new ProcessResult();

    if (run(res, "java", "-jar", "jastics.jar", file, "-cp", "jastics.jar") != 0) {
      return res;
    }

    run(res, "java", "-cp", DIR + ":jastics.jar", name);

    return res;
  }


  protected void checkIntegration(String name) {
    try {
      String file    = DIR + name + ".java";
      String content = slurp(file);
      String wantOut = findWantedOutput(content, ">");
      String wantErr = findWantedOutput(content, "!");

      ProcessResult res = compileAndRun(name, file);
      assertEquals("stdout:\n\n" + res.out + "\nmatches:\n\n" + wantOut, wantOut.trim(), res.out.trim());
      assertEquals("stderr matches", wantErr.trim(), res.err.trim());
    }
    catch (IOException|InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
