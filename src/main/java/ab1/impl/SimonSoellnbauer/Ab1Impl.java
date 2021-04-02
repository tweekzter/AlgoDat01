package ab1.impl.SimonSoellnbauer;

import ab1.Ab1;

/**
 * Algorithmen & Datenstrukturen
 * ABGABE 1
 *
 * Peter Söllnbauer - #11904589
 * Manuel Simon - #00326348
 */
public class Ab1Impl implements Ab1 {

	@Override
	public boolean isHeap(int i, int j, int[] data)
	{
		// YOUR CODE HERE
		return false;
	}

	@Override
	public void toHeap(int[] data)
	{
		// YOUR CODE HERE
	}

	@Override
	public void heapsort(int[] data)
	{
		// YOUR CODE HERE
	}

	@Override
	public ListNode insert(ListNode head, int value)
	{
		ListNode newNode = new ListNode(value);
		ListNode actual = head;
		ListNode prev = null;

		if(head == null)
			return newNode;

		// find correct spot in sorted list for new element
		while(actual != null && actual.value < newNode.value) {
			prev = actual;
			actual = actual.next;
		}

		// if new element is at the beginning of list
		if(prev == null) {
			newNode.next = head;
			return newNode;
		}

		// otherwise insert new element between preceding smaller element
		// and subsequent equal or greater element (or null)
		prev.next = newNode;
		newNode.next = actual;

		return head;
	}

	@Override
	public ListNode search(ListNode head, int value)
	{
		while(head != null && head.value != value) {
			head = head.next;
		}

		return head;
	}

	@Override
	public ListNode minimum(ListNode head)
	{
		// as the pre condition is a sorted ascending list, the first element is also the smallest
		return head;
	}

	@Override
	public void mergesort(int[] data)
	{
		// YOUR CODE HERE
	}
}
