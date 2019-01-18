package P5;

public class TestComputer {
	public static void main(String[] args) {
		Computer comp =  new Computer();
		comp.compile("program.asm");
		comp.run("program.ex");
	}
}
