package bf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * A brainfuck interpreter for Java.
 * http://en.wikipedia.org/wiki/Brainfuck
 * @author Bill Cruise
 *
 */
public class BF {
	
	/* The brainfuck virtual machine consists of a data array and data pointer,
	 * an instruction array and instruction pointer, and input and output streams.
	 */
	private byte[] data;
	private int dataPtr = 0;
	
	private char[] instructions;
	
	private InputStream inStream;
	private OutputStream outStream;
	
	public BF(char[] instructions) {
		data = new byte[30000];
		this.instructions = instructions;
		inStream = System.in;
		outStream = System.out;
	}
	
	public void execute() throws IOException {
		for (int instrPtr = 0; instrPtr < instructions.length; instrPtr++) {
			char instr = instructions[instrPtr];
			
			switch(instr) {
				case '>': // increment the data pointer (to point to the next cell to the right).
					dataPtr++;
					break;
				case '<': // decrement the data pointer (to point to the next cell to the left).
					dataPtr--;
					break;
				case '+': // increment the byte at the data pointer.
					data[dataPtr]++;
					break;
				case '-': // decrement the byte at the data pointer.
					data[dataPtr]--;
					break;
				case '.': // output the byte at the data pointer as an ASCII encoded character.
					outStream.write((char)data[dataPtr]);
					break;
				case ',': // accept one byte of input, storing its value in the byte at the data pointer.
					data[dataPtr] = (byte) inStream.read();
					break;
				case '[': // Starts loop, flag under pointer 
					// if the byte at the data pointer is zero, then instead of moving the instruction pointer 
					// forward to the next command, jump it forward to the command after the  matching ] command*.
					if (data[dataPtr] == 0) {
						int bCtr = 1;
						while (bCtr > 0) {
							char c = instructions[++instrPtr];
							if (c == '[') {
								bCtr++;
							}
							else if (c == ']') {
								bCtr--;
							}
						}
					}
					break;
				case ']': //Indicates end of loop
					// if the byte at the data pointer is nonzero, then instead of moving the instruction pointer 
					// forward to the next command, jump it back to the command after the matching [ command*.
					int bCtr = 1;
					while (bCtr > 0) {
						char c = instructions[--instrPtr];
						if (c == '[') {
							bCtr--;
						}
						else if (c == ']') {
							bCtr++;
						}
					}
					instrPtr--;
					break;
				default: // do nothing; all non-instruction characters are ignored in brainfuck
			}
		}
	}
	

	/**
	 * A command-line brainfuck interpreter.
	 * Takes a .bf program file name as the only argument.
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("You must specify a program file name.");
			System.out.println("Usage: java BF program.bf");
			return;
		}
		
		File programFile = new File(args[0]);

		try {
			Scanner scanner = new Scanner(programFile);
			StringBuilder sb = new StringBuilder();
			while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                sb.append(line);
            }
			scanner.close();
			
			BF bf = new BF(sb.toString().toCharArray());
			bf.execute();
		}
		catch (FileNotFoundException fnfe) {
			System.out.println("The program file specified could not be found.");
			System.out.println("Usage: java BF program.bf");
		}
		catch(IOException ioe) {
			System.out.println("There was a problem reading or writing to the I/O stream.");
		}
	}
}
