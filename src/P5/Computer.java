package P5;
	import java.io.*;
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.Scanner;
	
	public class Computer {
		private Memory ram;
		private PC pc;
		private IR ir;
		private ACC acc;
		ArrayList<String> instruction = new ArrayList<>(Arrays.asList("STOP","LD","STO","ADD","SUB","MPY","DIV","IN","OUT","B","BGTR","BZ"));
		public Computer() {
			ram = new Memory(100);
			pc = new PC(0);
			ir = new IR(0);
			acc = new ACC(0);
		}
		private void fetch() { 		// IR = Memory[PC]
			ir.setValue(ram.getValue(pc.getValue()));
		}
		private void incrementPC() {
			pc.increment();
		}
		private boolean execute() { // decode IR and execute based on OpCode and Operand
			boolean stop = false;
			switch (ir.getOpCode()) {
			case 0: // Stop
				stop = true;
				break;
			case 1: // Load - ACC = Memory[Operand]
				acc.setValue(ram.getValue(ir.getOperand()));
				break;
			case 2: // Store - Memory[Operand] = ACC;
				ram.setValue(ir.getOperand(), acc.getValue());
				break;
			case 3: // Add - ACC = ACC + Memory[Operand]
				acc.add(ram.getValue(ir.getOperand()));
				break;
			case 4: // Subtract - ACC = ACC - Memory[Operand]
				acc.sub(ram.getValue(ir.getOperand()));
				break;
			case 5: // Multiply - ACC = ACC * Memory[Operand]
				acc.mult(ram.getValue(ir.getOperand()));
				break;
			case 6: // Divide - ACC = ACC / Memory[Operand]
				acc.div(ram.getValue(ir.getOperand()));
				break;
			case 7: // Input - Memory[Operand] = input
				ram.setValue(ir.getOperand(), Terminal.input());
				break;
			case 8: // Output - output = Memory[Operand]
				Terminal.output(ram.getValue(ir.getOperand()));
				break;
			case 9: // Unconditional Branch - PC = Operand
				pc.setValue(ir.getOperand());
				break;
			case 10: // Branch greater
				if (acc.getValue() > 0)
					pc.setValue(ir.getOperand());
				break;
			case 11: // Branch equal
				if (acc.getValue() == 0)
					pc.setValue(ir.getOperand());
				break;
			default: // Not an instruction - could throw their own exception
				Terminal.output("Error - not an instruction\n");
			} 
			return stop;
		}
		private void cycle() {
			boolean stop = false;
			while (!stop) {
				this.fetch();
				this.incrementPC();
				stop = this.execute(); // if opcode = stop instruction, will become true
			}
		}
		public void run() {
			ram.loadProgram();
			pc.setValue(0);
			this.cycle();
		}
		public void run(String filename) {
			ram.loadProgram(filename);
			pc.setValue(0);
			this.cycle();
		}
		public void compile (String filename) {
			SymbolList symbolTable = firstPass(filename);
			secondPass(symbolTable, filename);	
		}
		public SymbolList firstPass(String filename) {	
			int pc = 0;
			int dc = 99;
			String Line = "";
			String firstWord = "";
			String secondWord = "";
			String thirdWord = "";
			SymbolList list = new SymbolList();
		try {
			File readFile = new File(filename);
			Scanner in = new Scanner(readFile);
				while (in.hasNextLine()) {
					Line = in.nextLine();
					Scanner LineScan = new Scanner(Line);
					firstWord = LineScan.next();
					if (firstWord.equals("REM")) {}
					else if (instruction.contains(firstWord)) {
					//Do nothing
					pc++;
				} 
			else {
				secondWord = LineScan.next();
				if (instruction.contains(secondWord)) { 
					list.add(new Symbol(firstWord, pc, null));
						pc++;
						} else { 
						thirdWord = LineScan.next();
							list.add(new Symbol(firstWord, dc, (int) Integer
									.parseInt(thirdWord)));
							dc--;
						}
					}
					LineScan.close();
				}
				in.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			return list;
		}
		public void secondPass(SymbolList symbolTable, String filename) {
			String FileOut = filename.substring(0,(filename.length() - 3)) + "ex";
			int count = 0;
			int dc = 99;
			String Line = "";
			String FirstWord = "";
			String SecondWord = "";
			String ThirdWord = "";
			String OutLine = "";
			String tempLocation = "";
			Symbol tempSymbol = null;
			try {
				File asm = new File(filename);
				File ex = new File(FileOut);
				
				
				Scanner in = new Scanner(asm);
				DataOutputStream out = new DataOutputStream(new FileOutputStream(ex));
				while (in.hasNextLine()) {
					Line = in.nextLine();
					Scanner LineScan = new Scanner(Line);

					SecondWord = "";
					ThirdWord = "";

					FirstWord = LineScan.next();
					if (LineScan.hasNext()) {
						SecondWord = LineScan.next();
						SecondWord = SecondWord + ":";
					}
					if (LineScan.hasNext()) {
						ThirdWord = LineScan.next();
						ThirdWord = ThirdWord + ":";
					}
					if (instruction.contains(FirstWord)) { 
						if (("" + instruction.indexOf(FirstWord)).length() == 1)
							OutLine = "0" + instruction.indexOf(FirstWord);
						else
							OutLine = "" + instruction.indexOf(FirstWord);
						for (int i = 0; i < symbolTable.size(); i++) {
							tempSymbol = symbolTable.get(i);
							if ((tempSymbol.getName()).equalsIgnoreCase(SecondWord)) {
								tempLocation = "" + tempSymbol.getLocation();
								if (tempLocation.length() == 1)
									OutLine = OutLine + "0" + tempLocation;
								else
									OutLine = OutLine + tempLocation;
								break;
							}
						}
						count++;
						int a = Integer.parseInt(OutLine);
						out.writeInt(a);
					} else if (FirstWord.equals("REM")) {
					} else { 
						SecondWord = SecondWord.substring(0,
								(SecondWord.length() - 1));
						if (instruction.contains(SecondWord)) { 
							count++;
							if (("" + instruction.indexOf(SecondWord)).length() == 1)
								OutLine = "0" + instruction.indexOf(SecondWord);
							else
								OutLine = "" + instruction.indexOf(SecondWord);
							for (int i = 0; i < symbolTable.size(); i++) {
								tempSymbol = symbolTable.get(i);
								if ((tempSymbol.getName())
										.equalsIgnoreCase(ThirdWord)) {
									tempLocation = "" + tempSymbol.getLocation();
									if (tempLocation.length() == 1)
										OutLine = OutLine + "0" + tempLocation;
									else
										OutLine = OutLine + tempLocation;
									break;
								}
							}
							if (OutLine.length() == 2)
								OutLine = OutLine + "00";
							int b = Integer.parseInt(OutLine);
							out.writeInt(b);
						} else if (SecondWord.equalsIgnoreCase("DC")) {
							dc--;
						}
					}
					LineScan.close();
				}
				dc++;
				for (int i = 0; i < (dc - count); i++) {
					int fl = Integer.parseInt("0");
					out.writeInt(fl);					
				}
				while (dc <= 99) {
					for (int i = 0; i < symbolTable.size(); i++) {
						tempSymbol = symbolTable.get(i);
						if (tempSymbol.getLocation() == dc) {
							OutLine = "" + tempSymbol.getValue();
							dc++;
							int c = Integer.parseInt(OutLine);
							out.writeInt(c);
							break;
						}
					}
				}
				out.close();
				in.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
}
