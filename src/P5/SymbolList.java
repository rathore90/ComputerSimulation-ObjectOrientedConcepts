package P5;

public class SymbolList {
	class Node {
		Symbol element;
		Node next;
		public Node(Symbol e) {
			element = e;
		}
	}
	private int size;
	private Node head,tail;

		// Constructors
		public SymbolList() {
			head = tail = null;
		}
		 
		public void add(Symbol e) { 
			addLast(e);			 
		}
		public void addFirst(Symbol e) {		
			Node newNode = new Node(e); // Create a new node
			if (tail == null) 			// if empty list
				head = tail = newNode; 	// new node is the only node in list
			else {
				newNode.next = head; 	// link the new node with the head
				head = newNode; 		// head points to the new node
			}
			size++;
		}
		public void addLast(Symbol e) {		
			Node newNode = new Node(e); // Create a new for element e
			if (tail == null) 			// if empty list
				head = tail = newNode; 	// new node is the only node in list
			else {
				tail.next = newNode; 	// Link the new with the last node
				tail = tail.next; 		// tail now points to the last node
			}
			size++;
		}
		public void add(int index, Symbol e){
			if (index < 0 || index > size)
				throw new IndexOutOfBoundsException(); //according to Java doc.
			else if (index == 0)	addFirst(e);
			else if (index == size) addLast(e);
			else {
				Node newNode = new Node(e);
				Node current = head; 		
				for (int i = 1; i < index; i++) 
					current = current.next; 	
				newNode.next = current.next; 	
				current.next = newNode; 		
				size++;
			}
		}
		public Symbol getFirst() {
			if (size == 0)
				return null;
			else
				return head.element;
		}
		public Symbol getLast() {
			if (size == 0)
				return null;
			else
				return tail.element;
		}
		public Symbol get(int index) {		
			if (index < 0 || index >= size)
				return null;
			else if (index == 0)
				return (Symbol) getFirst();
			else if (index == size - 1)
				return (Symbol) getLast();
			else {
				Node current = head; 		
				for (int i = 0; i < index; i++)	
					current = current.next; 	
				return  (Symbol) current.element;
			}
		}
		
		public Symbol set(int index, Symbol e) {	
			if (index < 0 || index > size - 1)
				return null;
			Node current = head;
			for (int i = 0; i < index; i++)
				current = current.next;
			Symbol temp = current.element;
			current.element = e;
			return temp;
		}
	 
		public String toString() {
			StringBuilder result = new StringBuilder();
			Node current = head;
			for (int i = 0; i < size; i++) {
				result.append(current.element);
				current = current.next;
			}
			return result.toString();
		}
		public int size(){return size;}
		public boolean contains(Symbol e) {
			Node current = head;
			for (int i = 0; i < size; i++) {
				if (current.element.equals(e))
					return true;
				current = current.next;
			}
			return false;
		}
		public int indexOf(Symbol e) {
			Node current = head;
			for (int i = 0; i < size; i++) {
				if (current.element.equals(e))
					return i;
				current = current.next;
			}
			return -1;
		}
		public int lastIndexOf(Symbol e) {	
			int lastIndex = -1;
			Node current = head;
			for (int i = 0; i < size; i++) {
				if (current.element.equals(e))
					lastIndex = i;
				current = current.next;
			}
			return lastIndex;
		}		
}
