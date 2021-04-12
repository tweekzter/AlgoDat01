package ab1.impl.SimonSoellnbauer;

import ab1.Ab1;

/**
 * Algorithmen & Datenstrukturen
 * ABGABE 1
 * <p>
 * Peter Söllnbauer - #11904589
 * Manuel Simon - #00326348
 */
public class Ab1Impl implements Ab1 {

	/*
    Basic idea of heapsort-algorithm:
    Arrange elements in binary tree structure.
    Every node has to fulfill the heap-requirements (Max-heap: Every parent node has to be larger than the child-nodes).
    Now check heap condition for every node an swap nodes if it's neccessary.
    This has to be done recursively until the largest element is at the top of the tree.

    In this case: Recursive implementation
    */

    // Variables: data[]: random array, int j: lenght of array, i: counter variable for pointer


    @Override
    public boolean isHeap(int i, int j, int[] data) {

        //check heap conditions i <= k <= j
        for (i = 0; i < (j - 2) / 2; i++) {

            //a[k] >= a[2k], in case of 2k <= j --> false
            if (data[2 * i + 1] > data[i]) {
                return false;
            }

            //a[k] >= a[2k + 1], in case of 2k + 1 <= j --> false
            if (2 * i + 2 < j && data[2 * i + 2] > data[i]) {
                return false;
            }
        }
        //both heap-conditions true
        return true;
    }

    @Override
    public void toHeap(int[] data) {

        int length = data.length;
        int index = (length / 2) - 1;

        for (int i = index; i >= 0; i--) {
            //call helper-method to build max-heap
            heapify(data, length, i);
        }
    }

    // helper-method to build max-heap
    static void heapify(int[] data, int length, int i) {

        int root = i; //largest element (parent node)
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        //left child node is lager than parent node
        if (left < length && data[left] > data[root]) {
            root = left;
        }

        // right child node is current largest element
        if (right < length && data[right] > data[root]) {
            root = right;
        }

        // largest element is not root element
        if (root != i) {

            // so called "Dreieckstausch"
            int temp = data[i];
            data[i] = data[root];
            data[root] = temp;

            // therefore, recursively start the changing procedure again
            heapify(data, length, root);
        }
    }

    @Override
    public void heapsort(int[] data) {

        int length = data.length;

        // build heap with previous heapify method
        for (int i = length / 2 - 1; i >= 0; i--) {
            heapify(data, length, i);
        }

        // extract heap elements (from tree structure, e.g. VO-Folien)
        for (int i = length - 1; i >= 0; i--) {

            //swap elements (current root to the end)
            // so called "Dreieckstausch"
            int temp = data[0];
            data[0] = data[i];
            data[i] = temp;

            // call heapify method on the rearranged array
            heapify(data, i, 0);
        }
    }

    @Override
    public ListNode insert(ListNode head, int value) {
        ListNode newNode = new ListNode(value);
        ListNode actual = head;
        ListNode prev = null;

        // empty list
        if (head == null)
            return newNode;

        // find correct spot in sorted list
        while (actual != null && actual.value < newNode.value) {
            prev = actual;
            actual = actual.next;
        }

        // if new element is at the beginning of list
        if (prev == null) {
            newNode.next = head;
            return newNode;
        }

        // otherwise insert new element between preceding
        // and subsequent element (or null)
        prev.next = newNode;
        newNode.next = actual;

        return head;
    }

    @Override
    public ListNode search(ListNode head, int value) {
        while (head != null && head.value != value) {
            head = head.next;
        }

        return head;
    }

    @Override
    public ListNode minimum(ListNode head) {
        // as the pre condition is a sorted ascending list, the first element is also the smallest
        return head;
    }

    @Override
    public void mergesort(int[] data) {
        if (data == null)
            return;

        int[] result = mSort(data);
        for (int i = 0; i < result.length; i++) {
            data[i] = result[i];
            System.out.print(result[i]);
        }
    }

    /**
     * Actual MergeSort algorithm.
     * <p>
     * The basic idea is to divide an array into trivial parts of size 1 and therefore sorted subarrays.
     * Then the recursion stops and the subarrays are sorted by merging all pairs of neighbouring subarrays.
     *
     * @param data Array to sort
     * @return sorted array or respective subarray
     */
    private int[] mSort(int[] data) {
        // subarrays at size 1 -> subarrays are definitely sorted - end recursion
        if (data.length <= 1)
            return data;

        // divide arrays
        int loLength = data.length / 2;
        int hiLength = data.length - loLength;

        int[] lo = new int[loLength]; // lower subarray
        int[] hi = new int[hiLength]; // upper subarray

        // populate subarrays
        for (int i = 0; i < loLength; i++) {
            lo[i] = data[i];
        }
        for (int i = 0; i < hiLength; i++) {
            hi[i] = data[loLength + i];
        }

        // start recursive division of arrays
        lo = mSort(lo);
        hi = mSort(hi);
        int[] result = merge(lo, hi);

        return result;
    }

    /**
     * helper method to merge (sort) two subarrays
     *
     * @param lo lower subarray
     * @param hi upper subarray
     * @return merged sorted array
     */
    private int[] merge(int[] lo, int[] hi) {
        int[] result = new int[lo.length + hi.length];

        // i ... lower subarray index
        // j ... upper subarray index
        // k ... result array index
        int i = 0, j = 0, k = 0;
        while (k < result.length) {
            // if one of the subarrays is done - add the rest of the other array
            if (i >= lo.length) {
                while (j < hi.length) {
                    result[k] = hi[j];
                    j++;
                    k++;
                }
                break;
            } else if (j >= hi.length) {
                while (i < lo.length) {
                    result[k] = lo[i];
                    i++;
                    k++;
                }
                break;
            }

            // if there are still elements in both subarrays
            // compare elements step by step and always add the lower one to the result
            if (lo[i] <= hi[j]) {
                result[k] = lo[i];
                i++;
                k++;
            } else {
                result[k] = hi[j];
                j++;
                k++;
            }
        }

        return result;
    }
}
