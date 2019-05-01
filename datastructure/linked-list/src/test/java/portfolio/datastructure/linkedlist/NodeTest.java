package portfolio.datastructure.linkedlist;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import portfolio.datastructure.linkedlist.Node;

/**
 * Unit test for simple App.
 */
public class NodeTest {
    /**
     * Tests the assumptions created by construction.
     */
    @Test
    public void test_Constructor_isTail_isNull() {
    	Integer element = 1;
    	Node head = new Node(element);
    	
        assertTrue(Node.NULL.isNull(), "Expected head to be Node.NULL but it was not." );
        assertTrue(head.isTail(), "Expected head to be the tail, but it is not." );
        assertFalse(Node.NULL.isTail());
    }
    
    @Test
    public void test_Element() {
    	Integer element = 1;
    	Node head = new Node(element);
    	
        assertEquals(element, head.element());
        assertEquals(head.next(), Node.NULL);
    }
    
    @Test
    public void test_Next() {
    	Integer element = 1;
    	Node head = new Node(element);
    	
        assertTrue(head.next() == Node.NULL);
    }
    
    @Test
    public void test_setNext() {
    	Integer element_1 = 1;
    	Integer element_2 = 2;
    	Node tail = new Node(element_1);
    	Node head = new Node(element_2);
    	
    	head.setNext(tail);
    	
    	assertEquals(element_2, head.element());
    	assertEquals(element_1, head.next().element());
    	
//    	assertThrows(RuntimeException.class, () -> tail.setNext(Node.NULL));
    }
}
