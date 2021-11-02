package com.gino.algorithm;

public class AddTwoNums {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode listNode1 = l1;
        ListNode listNode2 = l2;
        int next = 0;
        int temp;
        boolean oneOrTwo = true;
        while (l1 != null || l2 != null) {
            if (l1 != null && l2 != null) {
                temp = l1.val + l2.val + next;
                if (temp >= 10) {
                    l1.val = temp - 10;
                    l2.val = temp - 10;
                    next = 1;
                    if (l1.next == null && l2.next == null) {
                        l1.next = new ListNode(0);
                    }
                } else {
                    l1.val = temp;
                    l2.val = temp;
                    next = 0;
                }
                l1 = l1.next;
                l2 = l2.next;
            } else if (l1 == null) {
                oneOrTwo = false;
                if (next == 1) {
                    if (l2.val + next == 10) {
                        l2.val = 0;
                        if (l2.next == null) {
                            l2.next = new ListNode(0);
                        }
                    } else {
                        l2.val = l2.val + next;
                        next = 0;
                    }
                    l2 = l2.next;
                } else {
                    break;
                }
            } else {
                if (next == 1) {
                    if (l1.val + next == 10) {
                        l1.val = 0;
                        if (l1.next == null) {
                            l1.next = new ListNode(0);
                        }
                    } else {
                        l1.val = l1.val + next;
                        next = 0;
                    }
                    l1 = l1.next;
                } else {
                    break;
                }
            }
        }

        if (oneOrTwo) {
            return listNode1;
        } else {
            return listNode2;
        }
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(2, new ListNode(4, new ListNode(9)));
        ListNode listNode2 = new ListNode(5, new ListNode(6, new ListNode(4, new ListNode(9))));
        addTwoNumbers(listNode1, listNode2);
    }

}
