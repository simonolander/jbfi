import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.ByteArrayOutputStream;

public class InterpreterTest {

  private static final String HELLO_WORLD_PROGRAM = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
  private static final String ALPHABET_PROGRAM = "+++++[>+++++++++++++<-]>.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.+.";
  private static final String DIVISION_PROGRAM = ">>[-]>[-]>[-]>[-]>[-]>[-]<<<<<<<[->>+>+<<<]>>>[-<<<+>>>]<<[->>+>+<<<]>>>[-<\n" +
      "<<+>>>]<<[>[>>>>[-]<<<<<[->>+>>>+<<<<<]>>[-<<+>>]<[-<->>+<<[>>-<<[->>>+<<<]\n" +
      "]>>>[-<<<+>>>]<[-<[-]>]<]<[<[->>+>+<<<]>>>[-<<<+>>>]>>+<<<<[->>+<<]]>>[-<<+\n" +
      ">>]<]<<[->+>+<<]>[-<+>]>>>>>[-<<+<+>>>]<<<[-<->]+<[>-<[-]]>[>[-]>+<<-]<<][-\n" +
      "]>[-]>>[-<<+>>]>[-<<<<+>>>>]>[-]<[-]<[-]<[-]<<<<";

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

}
