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

	/*
    Basic idea of heapsort-algorithm:
    Arrange elements in binary tree structure.
    Every node has to fulfill the heap-requirements (Max-heap: Every parent node has to be larger than the child-nodes).
    Now check heap condition for every node an swap nodes if it's necessary.
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

    /**
     * MergeSort is a divide & conquer algorithm. It splits an array into halves recursively until the trivial
     * size of 1 is reached. Then the results get merged by sorting the subarrays step by step until the entire
     * array is sorted.
     *
     * In this implementation we split arrays by indices. This allows us to directly sort in the given array.
     * Separate arrays (out of place) are only used during the merging process.
     *
     * @param data array to be sorted
     */
    @Override
    public void mergesort(int[] data) {
        if(data == null)
            return;
        mSort(data, 0, data.length-1);
    }

    /**
     * Actual sorting method.
     *
     * @param data array to be sorted
     * @param start start index of subarray
     * @param end end index of subarray
     */
    private void mSort(int[] data, int start, int end) {
        // only one element left - definitely sorted - recursion stops
        if(start >= end)
            return;

        // split into lower and upper subarrays
        int length = (end - start) + 1;
        int hiStart = start + length / 2;
        int loEnd = hiStart - 1;

        // recursive calls to split array and subsequently merge the results
        mSort(data, start, loEnd);
        mSort(data, hiStart, end);
        merge(data, start, loEnd, hiStart, end);
    }

    /**
     * The merging process.
     *
     * @param data array to be sorted
     * @param start start index of lower subarray
     * @param loEnd end index of lower subarray
     * @param hiStart start index of upper subarray
     * @param end end index of upper subarray
     */
    private void merge(int[] data, int start, int loEnd, int hiStart, int end) {
        int loLength = (loEnd - start) + 1;
        int hiLength = (end - hiStart) + 1;

        // the two subarrays to be sorted
        int[] lo = extractArray(data, start, loEnd);
        int[] hi = extractArray(data, hiStart, end);

        int i = start;
        int loIndex = 0;
        int hiIndex = 0;
        while(i <= end) {
            // if lower subarray is done, fill result with upper subarray
            if(loIndex >= loLength) {
                while(hiIndex < hiLength) {
                    data[i] = hi[hiIndex];
                    hiIndex++;
                    i++;
                }
            }
            // if upper subarray is done, fill result with lower subarray
            else if(hiIndex >= hiLength) {
                while(loIndex < loLength) {
                    data[i] = lo[loIndex];
                    loIndex++;
                    i++;
                }
            }
            else {
                // compare elements of lower and upper subarray - add the lower elements to result
                if(lo[loIndex] <= hi[hiIndex]) {
                    data[i] = lo[loIndex];
                    loIndex++;
                }
                else {
                    data[i] = hi[hiIndex];
                    hiIndex++;
                }
                i++;
            }
        }
    }

    /**
     * Helper method to extract an array that represents a section of another array. The section is defined
     * by indices.
     *
     * @param data source array
     * @param start start index of section
     * @param end end index of section
     * @return an array that represents a section of the given array
     */
    private int[] extractArray(int[] data, int start, int end) {
        int length = (end - start) + 1;
        int[] array = new int[length];

        for(int i = 0; i < length; i++) {
            array[i] = data[start + i];
        }

        return array;
    }
}
