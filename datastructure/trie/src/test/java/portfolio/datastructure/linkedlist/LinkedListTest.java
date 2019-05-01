package portfolio.datastructure.linkedlist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import portfolio.datastructure.linkedlist.LinkedList;
import portfolio.datastructure.linkedlist.Node;

class LinkedListTest {

	/**
	 * Test Criteria - Construction should result in:
	 * <ul>
	 *   <li>list of length 0</li>
	 *   <li>a search on the list shall return Node.NULL</li>
	 *   <li>conversion to array should result in an array of size 0</li>
	 * </ul>
	 */
	@Test
	void testConstruction_Length_Search_ToArray() {
		LinkedList list = new LinkedList();
		
		Assertions.assertTrue(list.length() == 0);
		Assertions.assertTrue(list.search(0).isNull());
		Assertions.assertTrue(list.toArray().length == 0);
	}
	
	/**
	 * Test Criteria - Insert should result in:
	 * <ul>
	 *   <li>list of length 1</li>
	 *   <li>search of element returns correct Node</li>
	 *   <li>conversion to array should result in an array of size 1</li>
	 * </ul>
	 */
	@Test
	void testInsert_Length_Search_ToArray() {
		int element = 99;
		LinkedList list = new LinkedList();
		list.insert(element);
		
		Assertions.assertTrue(list.length() == 1);
		Assertions.assertFalse(list.search(element).isNull());
		Assertions.assertTrue(list.toArray().length == 1);
	}
	
	@Test
	void testDelete_EmptyList() {
		LinkedList list = new LinkedList();
		
		// shouldn't be able to delete an empty list
		Assertions.assertFalse(list.delete());
	}
	
	@Test
	void testDelete_SingleItemInList() {
		int element = 99;
		LinkedList list = new LinkedList();
		list.insert(element);
		
		// should successfully delete element
		Assertions.assertTrue(list.delete());
		Assertions.assertTrue(list.length() == 0);
		Assertions.assertTrue(list.search(0).isNull());
		Assertions.assertTrue(list.toArray().length == 0);
	}
	
	@Test
	void testDeleteSpecific_EmptyList() {
		int element = 99;
		LinkedList list = new LinkedList();
		
		// shouldn't be able to delete an empty list
		Assertions.assertFalse(list.delete(element));
	}
	
	@Test
	void testDeleteSpecific_NotInList() {
		int element = 99;
		int notElement = 1;
		LinkedList list = new LinkedList();
		list.insert(element);
		Node[] nodes = list.toArray();
		
		Assertions.assertFalse(list.delete(notElement));
		Assertions.assertTrue(list.length() == 1);
		Assertions.assertTrue(list.search(notElement).isNull());
		Assertions.assertTrue(nodes.length == 1);
		Assertions.assertTrue(nodes[0].element() == element);
	}
	
	@Test
	void testDeleteSpecific_SingleItemInList() {
		int element = 99;
		LinkedList list = new LinkedList();
		list.insert(element);

		// should successfully delete specific element when that element is the only one in the list
		Assertions.assertTrue(list.delete());
		Assertions.assertTrue(list.length() == 0);
		Assertions.assertTrue(list.search(0).isNull());
		Assertions.assertTrue(list.toArray().length == 0);
	}
	
	@Test
	void testDeleteSpecific_ElementIsNotTheFirstInTheList() {
		int element = 99;
		int notElement = 1;
		LinkedList list = new LinkedList();
		list.insert(element);
		list.insert(notElement);
		
		// test 4 - should successfully delete specific element when that element is not the only one in the list
		boolean test = list.delete(element);
		Node[] nodes = list.toArray();
		
		Assertions.assertTrue(test);
		Assertions.assertTrue(list.length() == 1);
		Assertions.assertTrue(list.search(0).isNull());
		Assertions.assertTrue(nodes.length == 1);
		Assertions.assertTrue(nodes[0].element() == notElement);
	}
}
