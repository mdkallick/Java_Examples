/* written by Mathias Dyssegaard Kallick
 * defines a class romWriter that holds a group of methods that will make up a pldrom
 * file for use with pld2.vhd -- (will write to a file)
 */

 //import statements
import java.nio.charset.Charset;
import java.nio.file.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Console;

public class romWriter {

	private Charset charset;
	private Path path; // initializing the file path
	private int counter; // counts the number of the command so the addr can be assigned to the right data
	// private BufferedWriter writer;
	
	public romWriter() {
		this.charset = Charset.forName("US-ASCII");
		this.path = FileSystems.getDefault().getPath("files", "pldrom.vhd"); // writes the file to pldrom.vhd in the folder files (inside of the folder you run the code from)
		this.counter = 0;
	}
	
	/* method initialize()
	 * writes all of the beginning stuff into the file, such that it works with the pld2.vhd file.
	 */
	public void initialize() {
		String line1 = "-- Quartus II VHDL Template";
		String line2 = "-- pldROM";
		String line3 = "library ieee;";
		String line4 = "use ieee.std_logic_1164.all;";
		String line5 = "use ieee.numeric_std.all;";
		String line6 = "entity pldrom is";
		String line7 = "    port";
		String line8 = "    (";
		String line9 = "        addr : in std_logic_vector(3 downto 0);";
		String line10 = "        data : out std_logic_vector(9 downto 0)";
		String line11 = "    );";
		String line12 = "end entity;";
		String line13 = "architecture rtl of pldrom is";
		String line14 = "begin";
		String line15 = "  data <= ";
		String[] lines = {line1,line2,line3,line4,line5,line6,line7,line8,line9,line10,line11,line12,line13,line14,line15};
		
		try (BufferedWriter writer = Files.newBufferedWriter(this.path, this.charset)) { // this command erases the previous file, so if you want to make more than one, either copy the old file or change the path
			for (int i = 0; i < 15; i++) {
				String tempstring = "line" + Integer.toString(i);
				writer.write(lines[i], 0, lines[i].length());
				writer.newLine();
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}
	
	/* method Move
	 * this method writes a load command to the rom, defined by the inputs source, destination, and binaryValue
	 * the 	parsing if statements change the string inputs to the binary values used by the pld2 IR
	 */
	public void Move( String source, String destination, String binaryValue) {
		String data = "	\"00";
		String parseddestination = "";
		String parsedsource = "";
		//parsing ifs for source:
		if (source.equals("ACC")) {
			parsedsource = "00";
		}
		else if (source.equals("LR")) {
			parsedsource = "01";
		}
		else if (source.equals("IR")) {
			parsedsource = "10";
		}
		else if (source.equals("11")) {
			parsedsource = "11";
		}
		else {
			System.out.println("ERROR WITH SOURCE: assuming you wanted it to be all ones");
			parsedsource = "11";
		}
		//parsing ifs for destination:
		if (destination.equals("ACC")) {
			parseddestination = "00";
		}
		else if (destination.equals("LR")) {
			parseddestination = "01";
		}
		else if (destination.equals("ACC low")) {
			parseddestination = "10";
		}
		else if (destination.equals("ACC high")) {
			parseddestination = "11";
		}
		else {
			System.out.println("ERROR WITH DESTINATION: assuming you wanted it to be ACC high");
			parseddestination = "11";
		}
		
		//puts all of the data into the IR in the right order
		data = data + parseddestination;
		data = data + parsedsource;
		data = data + binaryValue;
		// System.out.println(data);
		if (counter < 15) {
			try (BufferedWriter writer = Files.newBufferedWriter(this.path, this.charset, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
					writer.write(data, 0, data.length());
					writer.write("\" when addr = \"", 0, 15);
					String binaryCount = Integer.toBinaryString(this.counter); //turns counter into a binary number
					// this while loop adds enough 0's to the left of the binary counter so it is 4 bit.
					while (binaryCount.length() < 4) {
						// System.out.println(binaryCount);
						binaryCount = "0".concat(binaryCount);
					}
					writer.write(binaryCount, 0, binaryCount.length());
					writer.write("\" else", 0, 6);
					writer.newLine();
			} catch (IOException x) {
				System.err.format("IOException: %s%n", x);
			}
		}
		else if (counter == 16) { // makes sure there are only 16 commands, and that the last command finishes the else statement
			try (BufferedWriter writer = Files.newBufferedWriter(this.path, this.charset, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
					writer.write(data, 0, data.length());
					writer.write("\";", 0, 2);
					writer.newLine();
			} catch (IOException x) {
				System.err.format("IOException: %s%n", x);
			}
		}
		else {
			System.out.println(counter);
		}
		this.counter+=1;
	}
	/* method Binary
	 * this method writes a binary operation command to the rom, defined by the inputs source, destination, operation, and binaryValue
	 * the 	parsing if statements change the string inputs to the binary values used by the pld2 IR
	 */
	public void Binary(String source, String destination, String operation, String binaryValue) {
		String data = "	\"01";
		String parseddestination = "";
		String parsedsource = "";
		String parsedoperation = "";
		//parsing ifs for source:
		if (source.equals("ACC")) {
			parsedsource = "00";
		}
		else if (source.equals("LR")) {
			parsedsource = "01";
		}
		else if (source.equals("IR")) {
			parsedsource = "10";
		}
		else if (source.equals("11")) {
			parsedsource = "11";
		}
		else {
			System.out.println("ERROR WITH SOURCE: assuming you wanted it to be all ones");
			parsedsource = "11";
		}
		//parsing ifs for destination:
		if (destination.equals("ACC")) {
			parseddestination = "0";
		}
		else if (destination.equals("LR")) {
			parseddestination = "1";
		}
		else {
			System.out.println("ERROR WITH DESTINATION: assuming you wanted it to be LR");
			parseddestination = "1";
		}
		//parsing ifs for operation:
		if (operation.equals("add")) {
			parsedoperation = "000";
		}
		else if (operation.equals("sub")) {
			parsedoperation = "001";
		}
		else if (operation.equals("shift left")) {
			parsedoperation = "010";
		}
		else if (operation.equals("shift right")) {
			parsedoperation = "011";
		}
		else if (operation.equals("xor")) {
			parsedoperation = "100";
		}
		else if (operation.equals("and")) {
			parsedoperation = "101";
		}
		else if (operation.equals("rotate left")) {
			parsedoperation = "110";
		}
		else if (operation.equals("rotate right")) {
			parsedoperation = "111";
		}
		else {
			System.out.println("ERROR WITH OPERATION: assuming you wanted to rotate right");
			parsedoperation = "111";
		}
		// putting the IR together
		data = data + parsedoperation;
		data = data + parsedsource;
		data = data + parseddestination;
		data = data + binaryValue;
		// System.out.println(data);
		if (counter < 15) {
			try (BufferedWriter writer = Files.newBufferedWriter(this.path, this.charset, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
					writer.write(data, 0, data.length());
					writer.write("\" when addr = \"", 0, 15);
					String binaryCount = Integer.toBinaryString(this.counter);
					while (binaryCount.length() < 4) { //making the binary counter 4 bits
						// System.out.println(binaryCount);
						binaryCount = "0".concat(binaryCount);
					}
					writer.write(binaryCount, 0, binaryCount.length());
					writer.write("\" else", 0, 6);
					writer.newLine();
			} catch (IOException x) {
				System.err.format("IOException: %s%n", x);
			}
		}
		else if (counter == 16) { // makes sure there aren't more than 15 commands and 1 else statement
			try (BufferedWriter writer = Files.newBufferedWriter(this.path, this.charset, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
					writer.write(data, 0, data.length());
					writer.write("\";", 0, 2);
					writer.newLine();
			} catch (IOException x) {
				System.err.format("IOException: %s%n", x);
			}
		}
		else {
			System.out.println(counter);
		}
		this.counter+=1;
	}
	/* method Branch
	 * this method writes a branch command to the rom, defined by the input address
	 * just adds the address to what is mostly 0's
	 */
	public void Branch(String Address) {
		String data = "	\"100000";
		data = data + Address;
		// System.out.println(data);
		if (counter < 15) {
			try (BufferedWriter writer = Files.newBufferedWriter(this.path, this.charset, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
					writer.write(data, 0, data.length());
					writer.write("\" when addr = \"", 0, 15);
					String binaryCount = Integer.toBinaryString(this.counter);
					while (binaryCount.length() < 4) {
						System.out.println(binaryCount);
						binaryCount = "0".concat(binaryCount);
					}
					writer.write(binaryCount, 0, binaryCount.length());
					writer.write("\" else", 0, 6);
					writer.newLine();
			} catch (IOException x) {
				System.err.format("IOException: %s%n", x);
			}
		}
		else if (counter == 16) {
			try (BufferedWriter writer = Files.newBufferedWriter(this.path, this.charset, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
					writer.write(data, 0, data.length());
					writer.write("\";", 0, 2);
					writer.newLine();
			} catch (IOException x) {
				System.err.format("IOException: %s%n", x);
			}
		}
		else {
			System.out.println(counter);
		}
		this.counter+=1;
	}
	/* method CondBranch
	 * this method writes a conditional branch command to the rom, defined by the inputs source and address
	 * the source is what is checked to be 0, the address is where to branch to.
	 */
	public void CondBranch(String source, String Address) {
		String data = "	\"11";
		String parsedsource = "";
		//parsing ifs for source:
		if (source.equals("ACC")) {
			parsedsource = "0";
		}
		else if (source.equals("LR")) {
			parsedsource = "1";
		}
		else {
			System.out.println("ERROR WITH SOURCE: assuming that you wanted LR");
			parsedsource = "1";
		}
		
		data = data + parsedsource;
		data = data + "000";
		data = data + Address;
		// System.out.println(data);
		if (counter < 15) {
			try (BufferedWriter writer = Files.newBufferedWriter(this.path, this.charset, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
					writer.write(data, 0, data.length());
					writer.write("\" when addr = \"", 0, 15);
					String binaryCount = Integer.toBinaryString(this.counter);
					while (binaryCount.length() < 4) {
						System.out.println(binaryCount);
						binaryCount = "0".concat(binaryCount);
					}
					writer.write(binaryCount, 0, binaryCount.length());
					writer.write("\" else", 0, 6);
					writer.newLine();
			} catch (IOException x) {
				System.err.format("IOException: %s%n", x);
			}
		}
		else if (counter == 16) {
			try (BufferedWriter writer = Files.newBufferedWriter(this.path, this.charset, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
					writer.write(data, 0, data.length());
					writer.write("\";", 0, 2);
					writer.newLine();
			} catch (IOException x) {
				System.err.format("IOException: %s%n", x);
			}
		}
		else {
			System.out.println(counter);
		}
		this.counter+=1;
	}
	/* method close
	 * this method closes off the file, which is basically just writing end rtl;
	 */
	public void close() {
		String endline = "end rtl;";
		try (BufferedWriter writer = Files.newBufferedWriter(this.path, this.charset, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
				if (counter <= 16) {
					writer.write("	\"1111111111\";", 0, 14);
					writer.newLine();
				}
				writer.write(endline, 0, endline.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}
	/* this main method puts together a series of terminal readLine statements, and reacts to those in a tree of options,
	 * such that you can specify which commands you want. You can only specify 16 commands, and you can only specify them in
	 * order. If the program isn't 16 lines long, it adds a last line - "1111111111" - for the else statement.
	 */
	public static void main(String args[]) {
		romWriter test = new romWriter();
		test.initialize();
		int whileCount = 0;
		Console c = System.console();
		while (whileCount < 16) {
			String command = c.readLine("Enter the command type you want (Move, Binary, Branch, CondBranch (or Exit if you are done) ): ");
			System.out.println(command);
			if (command.equals("Move") || command.equals("move")) {
				String source = c.readLine("Enter the source to move from (ACC, LR, IR, 11): ");
				String destination = c.readLine("Enter the destination to move to (ACC, LR, ACC low, ACC high): ");
				String binaryValue = c.readLine("Enter the IR value you want (4 bits): ");
				// String correct = c.readLine("Is this what you wanted? (y/n): ");
				// if (correct.equals("n")) {
					// return;
				// }
				test.Move(source, destination, binaryValue);
				whileCount+=1;
			}
			else if (command.equals("Binary") || command.equals("binary")) {
				String source = c.readLine("Enter the source to move from (ACC, LR, IR, 11): ");
				String destination = c.readLine("Enter the destination to move to (ACC, LR): ");
				String operation = c.readLine("Enter the operation to enact (add, sub, shift left, shift right, xor, and, rotate left, rotate right): ");
				String binaryValue = c.readLine("Enter the IR value you want (2 bits): ");
				// String correct = c.readLine("Is this what you wanted? (y/n): ");
				// if (correct.equals("n")) {
					// return;
				// }
				test.Binary(source, destination, operation, binaryValue);
				whileCount+=1;
			}
			else if (command.equals("Branch") || command.equals("branch")) {
				String address = c.readLine("Enter the address you want to branch to (4 bits): ");
				// String correct = c.readLine("Is this what you wanted? (y/n): ");
				// if (correct.equals("n")) {
					// return;
				// }
				test.Branch(address);
				whileCount+=1;
			}
			else if (command.equals("CondBranch") || command.equals("Condbranch") || command.equals("condbranch")) {
				String source = c.readLine("Enter the source you want to check the condition from (ACC, LR): ");
				String address = c.readLine("Enter the address you want to branch to (4 bits): ");
				// String correct = c.readLine("Is this what you wanted? (y/n): ");
				// if (correct.equals("n")) {
					// return;
				// }
				test.CondBranch(source, address);
				whileCount+=1;
			}
			else if (command.equals("Exit") || command.equals("exit")) {
				whileCount = 16;
			}
			else {
				System.out.println("That's not a command");
			}
		}
		// test.Move("LR","ACC low", "0111");
		// test.Binary("ACC", "LR", "add", "11");
		// test.Branch("0110");
		// test.CondBranch("LR", "1110");
		test.close();
	}
}