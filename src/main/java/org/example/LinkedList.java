package org.example;

import java.util.ArrayList;
import java.util.List;

public class LinkedList {
    LinkedListNode head;
    private int size;
    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void insert(Document document) {
        LinkedListNode newNode = new LinkedListNode(document);
        if (head == null) {
            head = newNode;
        } else {
            LinkedListNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public void remove(String title) {
        if (head == null) {
            return;
        }
        if (head.document.title.equals(title)) {
            head = head.next;
            size--;
            return;
        }
        LinkedListNode current = head;
        while (current.next != null) {
            if (current.next.document.title.equals(title)) {
                current.next = current.next.next;
                size--;
                return;
            }
            current = current.next;
        }
    }

    public List<Document> search(String query) {
        List<Document> results = new ArrayList<>();
        List<Document> searchResults = new ArrayList<>();
        if (head == null) {
            return results;
        }

        String[] terms = query.split(" ");
        boolean andOperator = true;

        for (String term : terms) {
            System.out.println("Searching for term: " + term);
            List<Document> tempResults = new ArrayList<>();

            for (LinkedListNode current = head; current != null; current = current.next) {
                if (current.document.title.contains(term)) {
                    tempResults.add(current.document);
                    System.out.println("Found matching document: " + current.document.title);
                }
            }

            if (!tempResults.isEmpty() && andOperator) {
                if (results.isEmpty()) {
                    results.addAll(tempResults);
                } else {
                    results.retainAll(tempResults);
                }
            } else if (!tempResults.isEmpty()) {
                results.addAll(tempResults);
            }
        }
        searchResults = results;
        return results;
    }

    public int size() {
        return size;
    }
    public void clearList() {
        head = null;
    }
}
