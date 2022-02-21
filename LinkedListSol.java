import java.util.HashSet;
import java.util.Stack;

public class LinkedListSol {

    public void deleteDuplicates(ListNode head) {
// Given the head of a unsorted linked list, delete all duplicates such that each element appears only once.
// Time Complexity: O(N), Space: O(N)
        HashSet<Integer> set = new HashSet();

        ListNode curr = head;
        ListNode prev = null;
        while(curr != null){
            if(set.contains(curr.val)){
                prev.next = curr.next;
            }else {
                set.add(curr.val);
                prev = curr;
            }
            curr = curr.next;
        }
    }

    public void deleteDuplicatesNoBuffer(ListNode head) {
// Time Complexity: O(N^2), Space: O(1)
        ListNode curr = head;
        ListNode runner;
        while(curr != null){
            runner = curr;
            while(runner.next != null){
                if(curr.val == runner.next.val){
                    runner.next = runner.next.next;
                }else{
                    runner = runner.next;
                }
            }
            curr = curr.next;
        }
    }

    class Index{
        int val = 0;
    }

    public ListNode findNthFromEndRecurisve(ListNode head, int n) {
        Index i = new Index();
        return recurse(head, n, i);
    }

    private ListNode recurse(ListNode curr, int n, Index index){
        if(curr == null){
            return null;
        }

        ListNode node = recurse(curr.next, n, index);
        index.val = index.val + 1;
        if(index.val == n){
            return curr;
        }
        return node;
    }

    public ListNode findNthFromEndIterative(ListNode head, int n) {
        ListNode p1 = head;
        ListNode p2 = head;

        for(int i=0; i<n; i++){
            if(p2 == null){
                return p2;
            }
            p2 = p2.next;
        }

        while(p2.next != null){
            p1 = p1.next;
            p2 = p2.next;
        }
        return p1;
    }

    public boolean deleteNode(ListNode node){
        if(node == null || node.next == null){
            return false;
        }

        node.val = node.next.val;
        node.next = node.next.next;
        return true;
    }

    public ListNode partition(ListNode head, int x) {
//  Given the head of a linked list and a value x, partition it such that all nodes less than x come before nodes
//  greater than or equal to x.
//  TimeComplexity: O(N), space: O(1)
        if(head == null) return null;

        ListNode beforeStart = null;
        ListNode beforeEnd = null;
        ListNode afterStart = null;
        ListNode afterEnd = null;

        while(head != null){
            if(head.val < x){
                if(beforeStart == null){
                    beforeStart = head;
                    beforeEnd = head;
                }else {
                    beforeEnd.next = head;
                    beforeEnd = head;
                }
            }else {
                if(afterStart == null){
                    afterStart = head;
                    afterEnd = head;
                }else {
                    afterEnd.next = head;
                    afterEnd = head;
                }
            }

            head = head.next;
        }

        if(afterStart != null){
            afterEnd.next = null;
        }

        if(beforeStart != null){
            beforeEnd.next = afterStart;
            return beforeStart;
        }
        return afterStart;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
// Two non-empty linked lists representing two digits are stored in reverse order.
// Add the two numbers and return the sum as a linked list in reverse order.
// Time & Space Complexity: O(max(m,n))
        if(l1 == null) return l2;
        if(l2 == null) return l1;

        ListNode head = new ListNode(0);
        ListNode tail = head;
        int carry = 0;

        while(l1 != null && l2 != null){
            int sum = l1.val + l2.val + carry;
            int val = sum % 10;
            carry = sum / 10;

            tail.next = new ListNode(val);
            tail = tail.next;

            l1 = l1.next;
            l2 = l2.next;
        }

        while(l1 != null){
            int sum = l1.val + carry;
            int val = sum % 10;
            carry = sum / 10;
            tail.next = new ListNode(val);
            tail = tail.next;
        }

        while(l2 != null){
            int sum = l2.val + carry;
            int val = sum % 10;
            carry = sum / 10;
            tail.next = new ListNode(val);
            tail = tail.next;
        }

        if (carry > 0){
            tail.next = new ListNode(carry);
            tail = tail.next;
        }

        return head.next;
    }

    public boolean isPalindromeWithStack(ListNode head) {
// Check if a singly linked list is palindrome
// Time: O(N), Space: O(1)
        Stack<Integer> stack = new Stack();

        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null){
            stack.push(slow.val);
            slow = slow.next;
            fast = fast.next.next;
        }

        if(fast != null){
            slow = slow.next;
        }

        while(slow != null){
            if(slow.val != stack.pop()){
                return false;
            }
            slow = slow.next;
        }
        return true;
    }

    public boolean isPalindromeWithReverse(ListNode head) {
// Time: O(N), Space: O(1)
        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        if(fast != null){
            slow = slow.next;
        }

        ListNode rev = reverse(slow);
        fast = head;

        while(rev != null){
            if(fast.val != rev.val){
                return false;
            }
            fast = fast.next;
            rev = rev.next;
        }
        return true;
    }

    private ListNode reverse(ListNode head){

        ListNode prev = null;
        while(head != null){
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }

        return prev;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
// Given the heads of two singly linked-lists headA and headB, return the node at which the two lists intersect
// Time: O(M+N), Space: O(1)
        if(headA == null || headB == null) return null;

        // Find size of both lists

        Result resultA = getTailAndSize(headA);
        Result resultB = getTailAndSize(headB);

        //Check if tail is the same
        if(resultA.tail != resultB.tail) return null;

        ListNode longer = resultA.size > resultB.size ? headA : headB;
        ListNode shorter = resultA.size > resultB.size ? headB : headA;

        longer = getKthNode(longer, Math.abs(resultA.size - resultB.size));

        //Find intersection point
        while(longer != shorter){
            longer = longer.next;
            shorter = shorter.next;
        }
        return longer;
    }

    public Result getTailAndSize(ListNode node){
        if(node == null) return null;

        int size = 1;
        ListNode head = node;
        while(head.next != null){
            if(head != null){
                size++;
                head = head.next;
            }
        }
        return new Result(head, size);
    }

    private ListNode getKthNode(ListNode curr, int k){
        ListNode node = curr;

        while(node != null && k > 0){
            node = node.next;
            k--;
        }
        return node;
    }

    public ListNode detectCycle(ListNode head) {
// There is a cycle in a linked list. Given the head of a linked list, return the node where the cycle begins.
// Time: O(N), Space: O(1)
        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null){

            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) break;
        }

        if(fast == null || fast.next == null) return null;

        slow = head;

        while(slow != fast){
            slow = slow.next;
            fast = fast.next;
        }
        return fast;
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Result{
    ListNode tail;
    int size = 0;

    Result(ListNode tail, int size){
        this.tail =tail;
        this.size = size;
    }
}