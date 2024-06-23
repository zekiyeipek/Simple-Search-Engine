package org.example;

import java.io.*;
import java.util.*;


public class SearchEngine {
    private LinkedList linkedList;
    private Map<String, Document> documentMap;
    private List<Document> searchResults;
    public LinkedList getLinkedList() {

        return linkedList;
    }
    public Map<String, Document> getDocumentMap() {
        return documentMap;
    }

    public List<Document> getSearchResults() {
        return searchResults;
    }
    public void setLinkedList(LinkedList linkedList) {

        this.linkedList = linkedList;
    }
    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        Map<String, Document> documentMap = new HashMap<>();
        List<Document> searchResults = new ArrayList<>();
        long startTime = System.nanoTime(); // Record the start time

        // Initialize searchQuery as an empty string
        String searchQuery = "";

        try {
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/org/example/commands.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] command = line.split(" ");
                    switch (command[0]) {
                        case "clear":
                            linkedList.clearList();
                            documentMap.clear();
                            break;
                        case "load":
                            String filePath = command[1];
                            try (BufferedReader fileReader = new BufferedReader(new FileReader("src/main/java/org/example/" + filePath))) {
                                String docLine;
                                while ((docLine = fileReader.readLine()) != null) {
                                    String[] docInfo = docLine.split(",");
                                    if (docInfo.length >= 3) {
                                        Document document = new Document(docInfo[0], docInfo[1], docInfo[2]);
                                        documentMap.put(docInfo[0], document);
                                    } else {
                                        System.err.println("Invalid document format: " + docLine);
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            // Sort the documents by title
                            List<Document> sortedDocuments = new ArrayList<>(documentMap.values());
                            Collections.sort(sortedDocuments, Comparator.comparing(Document::getTitle));

                            // Rebuild the linked list with sorted documents
                            linkedList.clearList();
                            for (Document document : sortedDocuments) {
                                linkedList.insert(document);
                            }
                            break;
                        case "search":
                            searchQuery = command[1];
                            searchResults = linkedList.search(searchQuery);
                            break;
                        case "remove":
                            String docTitle = command[1];
                            Document removedDoc = documentMap.get(docTitle);
                            if (removedDoc != null) {
                                linkedList.remove(docTitle);
                                documentMap.remove(docTitle);
                            } else {
                                System.err.println("Document not found: " + docTitle);
                            }
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            long endTime = System.nanoTime(); // Record the end time
            long executionTime = endTime - startTime;
            System.out.println("Execution Time: " + executionTime + " nanoseconds");

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("results.txt"))) {
                // Write the query in the output file
                bw.write("Query: " + searchQuery);
                bw.newLine();

                if (!searchResults.isEmpty()) {
                    for (Document result : searchResults) {
                        // Write the matching document titles to the output file
                        bw.write(result.title);
                        bw.newLine();
                    }
                } else {
                    bw.write("No search results for query: " + searchQuery);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}