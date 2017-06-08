package semantics;


public class JavaCompiler extends org.extendj.JavaCompiler {
  public static void main(String[] args) {
    int ret = new JavaCompiler().run(args);
    if (ret != 0) {
      System.exit(ret);
    }
  }

  @Override
  protected void initOptions() {
    super.initOptions();
    // Gradle always uses this internal javac option as a bug work around. This
    // isn't javac though and doesn't have that bug, so just accept and ignore
    // it. Making Gradle not pass this option instead is very difficult.
    program.options().addKeyOption("-XDuseUnsharedTable=true");
  }
}
