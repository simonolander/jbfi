import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Interpreter {
  private static final int DEFAULT_BUFFER_SIZE = 256;

  private final String program;
  private final int bufferSize;
  private final int[] buffer;
  private final InputStream in;
  private final OutputStream out;

  private int currentBufferIndex;
  private int programCounter;


  public Interpreter(String program) {
    this(
        program,
        DEFAULT_BUFFER_SIZE,
        System.in,
        System.out
    );
  }

  public Interpreter(String program, OutputStream out) {
    this(
        program,
        DEFAULT_BUFFER_SIZE,
        System.in,
        out
    );
  }

  public Interpreter(String program, InputStream in, OutputStream out) {
    this(
        program,
        DEFAULT_BUFFER_SIZE,
        in,
        out
    );
  }

  public Interpreter(String program, int bufferSize, InputStream in, OutputStream out) {
    this.program = program;
    this.bufferSize = bufferSize;
    this.buffer = new int[bufferSize];
    this.in = in;
    this.out = out;
  }

  public void run() {
    while (true) {
      if (programCounter == program.length()) {
        break;
      }

      char instruction = getCurrentInstruction();

      switch (instruction) {
        case '+':
          add();
          break;
        case '-':
          subtract();
          break;
        case '<':
          decrementBufferInder();
          break;
        case '>':
          incrementBufferIndex();
          break;
        case ',':
          read();
          break;
        case '.':
          write();
          break;
        case '[':
          beginLoop();
          break;
        case ']':
          endLoop();
          break;
        default:
          programCounter += 1;
          break;
      }
    }
  }

  private void add() {
    buffer[currentBufferIndex] += 1;
    programCounter += 1;
  }

  private void subtract() {
    buffer[currentBufferIndex] -= 1;
    programCounter += 1;
  }

  private void incrementBufferIndex() {
    currentBufferIndex += 1;
    if (currentBufferIndex < 0) {
      currentBufferIndex = bufferSize - 1;
    }
    else if (currentBufferIndex >= bufferSize) {
      currentBufferIndex = 0;
    }
    programCounter += 1;
  }

  private void decrementBufferInder() {
    currentBufferIndex -= 1;
    if (currentBufferIndex < 0) {
      currentBufferIndex = bufferSize - 1;
    }
    else if (currentBufferIndex >= bufferSize) {
      currentBufferIndex = 0;
    }
    programCounter += 1;
  }

  private void read() {
    try {
      buffer[currentBufferIndex] = in.read();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    programCounter += 1;
  }

  private void write() {
    try {
      out.write(buffer[currentBufferIndex]);
      out.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    programCounter += 1;
  }

  private void beginLoop() {
    if (buffer[currentBufferIndex] == 0) {
      int loopBeginTokens = 1;
      while (true) {
        programCounter += 1;
        char instruction = getCurrentInstruction();
        if (instruction == ']') {
          loopBeginTokens -= 1;
          if (loopBeginTokens == 0) {
            break;
          }
        }
        else if (instruction == '[') {
          loopBeginTokens += 1;
        }
      }
      programCounter += 1;
    }
    else {
      programCounter += 1;
    }
  }

  private void endLoop() {
    int loopEndTokens = 1;
    while (true) {
      programCounter -= 1;
      char instruction = getCurrentInstruction();
      if (instruction == ']') {
        loopEndTokens += 1;
      }
      else if (instruction == '[') {
        loopEndTokens -= 1;
        if (loopEndTokens == 0) {
          break;
        }
      }
    }
  }

  private char getCurrentInstruction() {
    return program.charAt(programCounter);
  }
}
