package portfolio.datastructure.linkedlist;

public class Node {
	public final static Node NULL = new Node(0, null);
	
	private Integer data;
	private Node next;
	
	public Node(Integer data) {
		this.data = data;
		this.next = Node.NULL;
	}
	
	// for use by internal code only
	private Node(Integer data, Node next) {
		this.data = data;
		this.next = next;
	}
	
	public final Integer element() {
		return this.data;
	}
	
	public final Node next() {
		return this.next;
	}
	
	public final void setNext(Node next) {
//		if (next.isNull()) {
//			throw new RuntimeException("You can not use Node.NULL for the next node!");
//		}
		
		this.next = next;
	}
	
	public final boolean isTail() {
		return this.next == Node.NULL;
	}
	
	public final boolean isNull() {
		return this == Node.NULL;
	}
}
