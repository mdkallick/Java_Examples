/* Written by Mathias Dyssegaard Kallick
 * This class is my implementation of ArrayList, which overwrites Java's.
 */

public class ArrayList<T> {

	private Object[] array;
	private int size;
	private int max;

	public ArrayList() {
		this.array = new Object[10];
		this.size = 0;
		this.max = 10;
	}
	
	public boolean add(T t) {
		if (this.size >= this.max - 1){
			Object[] temp = new Object[this.size];
			temp = this.array;
			this.array = new Object[max*2];
			this.max = max * 2;
			for(int i = 0; i < this.size + 1; i++) {
				this.array[i] = temp[i];
			}
		}
		this.array[this.size] = t;
		this.size++;
		
		return true;
	}
	
	public void add(int index, T t) {
		if (index > this.max) {
			Object[] temp = this.array;
			this.array = new Object[index + max];
			this.max = index + max;
			for(int i = 0; i < this.size; i++) {
				this.array[i] = temp[i];
			}
		}
		this.size++;
		this.array[index] = t;
	}
	
	public T remove(int index) {
		if (index > this.size) {
			return null;
		}
		T tempT = (T)this.array[index];
		for(int i = index; i < this.size; i++) {
			this.array[i] = this.array[i+1];
		}
		this.size--;
		return tempT;
	}
	
	public boolean contains(T t) {
		for(int i = 0; i < this.size; i++) {
			if (this.array[i] == t) {
				return true;
			}
		}
		return false;
	}
	
	public T get(int index) {
		if (index > this.size) {
			return null;
		}
		else {
			return (T)this.array[index];
		}
	}
	
	public boolean isEmpty() {
		if (this.size == 0) {
			return true;
		}
		return false;
	}
	
	public int size() {
		return this.size;
	}
	
	public static void main(String[] args) {
		ArrayList testlist = new ArrayList();
		for (int i = 0; i < 23; i++) {
			testlist.add(i);
		}
		System.out.println(testlist.get(0));
		System.out.println(testlist.get(11));
		System.out.println(testlist.get(23));
		for (int j = 0; j < 23; j++) {
			System.out.println(testlist.remove(testlist.size()));
		}
	}
}