import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class InterpreterTest {

  private static final String HELLO_WORLD_PROGRAM = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
  private static final String ALPHABET_PROGRAM = "+++++[>+++++++++++++<-]>.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.";

  @Test(timeout = 1000)
  public void testHelloWorldTerminates() {
    Interpreter interpreter = new Interpreter(HELLO_WORLD_PROGRAM);
    interpreter.run();
  }

  @Test(timeout = 1000)
  public void testHelloWorld() {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Interpreter interpreter = new Interpreter(HELLO_WORLD_PROGRAM, out);
    interpreter.run();
    assertEquals("Hello World!\n", out.toString());
  }

  @Test(timeout = 1000)
  public void testAlphabet() {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Interpreter interpreter = new Interpreter(ALPHABET_PROGRAM, out);
    interpreter.run();
    assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", out.toString());
  }

  @Test(timeout = 1000)
  public void testReverseStringProgram() {
    String program = ",>,>,>,>,.<.<.<.<.";
    ByteArrayInputStream in = new ByteArrayInputStream("Simon".getBytes());
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Interpreter interpreter = new Interpreter(program, in, out);
    interpreter.run();
    assertEquals("nomiS", out.toString());
  }

}
