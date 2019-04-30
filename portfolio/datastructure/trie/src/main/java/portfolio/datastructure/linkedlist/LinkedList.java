package portfolio.datastructure.linkedlist;

/**
 * The Linked List implementation will not leak the list, or any subset of the list
 * outside of the Linked List composition. This class is solely responsible for life-cycle
 * management of Nodes in the list.
 * 
 * @author jerryjasperson
 *
 */
public class LinkedList {
	private Node head;
	int length;
	
	public LinkedList() {
		this.head = Node.NULL;
		this.length = 0;
	}
	
	public int length() {
		return this.length;
	}
	
	public void insert(Integer data) {
		Node newNode = new Node(data);
		newNode.setNext(this.head);
		this.head = newNode;
		++this.length;
	}
	
	public boolean delete() {
		boolean retval = false;
		
		if (!this.head.isNull()) {
			this.head = head.next();
			--this.length;
			retval = true;
		}
		
		return retval;
	}
	
	public boolean delete(Integer key) {
		boolean retval = false;
		Node candidate = this.searchPreNode(key);
		
		if (!candidate.isNull()) {
			candidate.setNext(candidate.next().next());
			--this.length;
			retval = true;
		}
		
		return retval;
	}
	
	public Node search(Integer key) {
		if (this.length < 1)
			return Node.NULL;
		
		Node search = this.searchPreNode(key);		
		return (search.isNull()) ? Node.NULL : new Node(search.next().element());
	}
	
	public Node[] toArray() {
		Node[] retval = new Node[this.length];
		
		Node ptr = this.head;
		int count = 0;
		while (!ptr.isNull()) {
			retval[count++] = ptr;
			ptr = ptr.next();
		}
		
		return retval;
	}
	
	private Node searchPreNode(Integer key) {
		if (this.length < 1)
			return Node.NULL;
		
		Node current = this.head;
		Node next = Node.NULL;
		boolean foundKey = (!current.isNull() && current.element().equals(key));
		
		while (!current.isTail() && !foundKey) {
			next = current.next();
			
			if (next.element() == key) {
				foundKey = true;
			}
			else {
				current = next;
			}
		}
		
		return (foundKey) ? current : next;
	}
}
