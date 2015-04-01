import java.util.* ;

public class LinkedList<T> implements Iterable<T> {


    public LinkedList() {
		this.head = null;
		this.counter = 0;
		this.tail = null;
    }

    public int counter;
    private Node head;
	private Node tail;
	
    public void clear() {
		this.head = null;
		this.tail.prev = null;
		this.tail = null;
		this.counter = 0;
	}
    public int size() {
		return this.counter;
    }
    public void add(T item) {
		// Node newnode = new Node(item);
		// newnode.setNext(head);
		// head = newnode;
		// head.setPrev(null);
		if (head == null && tail == null) {
			Node newnode = new Node(item);
			newnode.setNext(head);
			head = newnode;
			head.setPrev(null);
			tail = head;
		}
		else {
			Node newnode = new Node(item);
			newnode.setNext(head);
			head = newnode;
			head.getNext().setPrev(head);
		}
		counter++;
    }
    public ArrayList<T> toArrayList() {
		ArrayList<T> alist = new ArrayList<T>();
		if (this.head == null) {
			System.out.println("this.head is null in toArraylist");
			return alist;
		}
		Node loopnode = this.head;
		for(int i = 0; i < this.size(); i++) {
			if (loopnode.getThing() == null) {
				System.out.println("loopnode.getThing is null in toArrayList");
				System.out.println(loopnode);
			}
			else {System.out.println(loopnode.getThing()); }
			
			alist.add(loopnode.getThing());
			loopnode = loopnode.getNext();
		}
		return alist;
    }
    public ArrayList<T> toShuffledList() {
		ArrayList<T> alist = new ArrayList<T>();
		Node loopnode = new Node(null);
        loopnode.setNext(head);
		Random rand = new Random();
		int random = 0;
		if (loopnode == null) {
			System.out.println("loopnode is null in toShuffledList");
			return alist;
		}
		// System.out.println(this.size());
		for(int i = 1; i <= this.size(); i++) {
			if (alist.size() == 0) {
				alist.add(loopnode.getThing());
				loopnode = loopnode.getNext();
			}
			else{
				random = rand.nextInt(alist.size());
				// System.out.println(alist.size());
				if (loopnode == null) {
					System.out.println("loopnode is null at point " + i);
				}
				alist.add(random, loopnode.getThing());
				loopnode = loopnode.getNext();
			}
		}
		return alist;
    }
    public boolean remove(Object obj) {
		Node loopnode;
		if (head == null) {
			System.out.println("line 86 is the problem");
			return false;
		}
		loopnode = head;
		for(int i = 0; i < this.size(); i++) {
			if (loopnode == null) {
				System.out.println("88 is null");
			}
			else if (loopnode.getThing().equals(obj)) {
				if (this.head == null) {
					System.out.println("head is null in remove");
					return false;
				}
				if (loopnode.getNext() == null && loopnode.getPrev() != null) {
					loopnode.getPrev().setNext(null);
					tail = loopnode.getPrev();
					this.counter--;
				}
				
				else if (loopnode.getPrev() == null) {
				    if (loopnode != head) {
						System.out.println( "null prev but not head!; " + i + "; " + this.size() + "; " + this.head.getNext());
					}
					head = loopnode.getNext();
					if (head != null) {
						head.prev = null;
					}
					if ( head == null){
						System.out.println("head is null in remove 101; " + i + "; this.size = " + this.size() + "; loopnode.getThing = " + loopnode.getThing());
						tail = null;
					}
					this.counter--;
				}
				else {
					if ( loopnode.getPrev() == null) {
						System.out.println("loopnode.getprev is null (85)");
					}
					loopnode.getPrev().setNext(loopnode.getNext());
					loopnode.getNext().setPrev(loopnode.getPrev());
					counter--;
				}
				if (head == null) {
					System.out.println(this.size());
				}
				return true;
			}
			else { loopnode = loopnode.getNext(); }
		}
		return false;
    }
    public T get(int index) {
		Node current;
		current = head;
		// System.out.println(head);
		// System.out.println(this.size());
		if (index >= this.size()) { System.out.println("index is bigger than size"); }
		for(int i = 0; i <= index; i++) {
			if (i != index) {
				current = current.getNext();
			}
			else if (i == index){
			    if (current == null) {
					System.out.println("current is null" + i);
				}
				if (current.getThing() == null) {
					System.out.println("current.getThing is null");
				}
				return current.getThing();
			}
		}
		System.out.println(current.getThing());
		return current.getThing();
    }

    public Iterator<T> iterator() {
		LLIterator newit = new LLIterator(head);
		return newit;
    }	

    private class Node {

		public Node(T item) {
			next = null;
			container = item;
			prev = null;
		}

		private Node next;
		private T container;
		private Node prev;
		
		public T getThing() {
			return this.container;
		}
		public void setNext(Node n) {
			this.next = n;
		}
		
		public void setPrev(Node n) {
			this.prev = n;
		}
		
		public Node getNext() {
			if (this.next == null) {
				return null;
			}
			else {
				return this.next;
			}
		}
		
		public Node getPrev() {
			return this.prev;
		}
    }

    private class LLIterator implements Iterator<T> {
	
		private Node itnode;

		public LLIterator(Node head) {
			itnode = new Node(null);
			itnode.next = head;
		}
		public boolean hasNext() {
			if (itnode.getNext() == null) {
				return false;
			}
			else {
				return true;
			}
		}
		public T next() {
			T tempvar =  itnode.next.getThing();
			itnode = itnode.getNext();
			return tempvar;
		}
		public void remove() {
		}
	}

    public static void main(String[] argv) {
		LinkedList<Integer> llist = new LinkedList<Integer>();

		llist.add(5);
		llist.add(10);
		llist.add(20);

		System.out.printf("\nAfter setup %d\n", llist.size());
		for(Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}

		System.out.printf("\nAfter clearing %d\n", llist.size());
		llist.clear();

		
		for (Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}

		for (int i=0;i<20;i+=2) {
			llist.add( i );
		}

		System.out.printf("\nAfter setting %d\n", llist.size());
		for (Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}
		
		llist.remove(18);
		System.out.println("removed first");
		llist.remove(0);
		System.out.println("removed last");
		
		ArrayList<Integer> alist = llist.toArrayList();
		System.out.printf("\nAfter copying %d\n", alist.size());
		for(Integer item: alist) {
			System.out.printf("thing %d\n", item);
		}
	
		llist.remove(10);

		alist = llist.toShuffledList();
		System.out.printf("\nAfter copying %d\n", alist.size());
		for(Integer item: alist) {
			System.out.printf("thing %d\n", item);
		}
	}
	/*
    public static void main(String argv[]) {
		ClumpingCell newcc = new ClumpingCell(5.0, 5.0);
		LinkedList<Cell> llist = new LinkedList<Cell>();
		llist.add(newcc);
		System.out.println(newcc.toString());
	}*/
}

