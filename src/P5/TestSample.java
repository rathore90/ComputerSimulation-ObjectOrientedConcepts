package P5;

public class TestSample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SymbolList list = new SymbolList();
		list.add(new Symbol("Else", 8, null));
		list.add(new Symbol("Finish", 9, null));
		list.add(new Symbol("First", 99, 0));
		list.add(new Symbol("Second", 98, 0));
		System.out.println(list);
	}

}
