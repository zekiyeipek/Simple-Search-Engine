import org.example.Document;
import org.example.LinkedList;
import org.example.SearchEngine;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class SearchEngineTest {

    @Test
    public void testLoadDocuments() {
        SearchEngine searchEngine = new SearchEngine();

        // Initialize the LinkedList in the SearchEngine
        LinkedList searchEngineLinkedList = new LinkedList(); // Change the variable name
        searchEngine.setLinkedList(searchEngineLinkedList);

        // Create a test input file containing sample documents
        createInputFile("test_load_documents.txt", "doc1,Author1,2023-01-01\n" +
                "doc2,Author2,2023-01-02\n");

        // Execute the load command
        String[] args = {"load", "test_load_documents.txt"};
        searchEngine.main(args);

        // Verify that documents were loaded successfully
        LinkedList linkedList = searchEngine.getLinkedList();
        Map<String, Document> documentMap = searchEngine.getDocumentMap();

        assertEquals(2, linkedList.size());
        assertEquals(2, documentMap.size());

        Document doc1 = documentMap.get("doc1");
        Document doc2 = documentMap.get("doc2");

        assertNotNull(doc1);
        assertNotNull(doc2);

        assertEquals("doc1", doc1.getTitle());
        assertEquals("doc2", doc2.getTitle());

        deleteFile("test_load_documents.txt");
    }
    @Test
    public void testSearchDocuments() {
        SearchEngine searchEngine = new SearchEngine();

        // Create a test input file containing sample documents
        createInputFile("test_search_documents.txt", "doc1,Author1,2023-01-01\n" +
                "doc2,Author2,2023-01-02\n" +
                "doc3,Author3,2023-01-03\n");

        // Execute the load command
        String[] loadArgs = {"load", "test_search_documents.txt"};
        searchEngine.main(loadArgs);

        // Execute the search command
        String[] searchArgs = {"search", "doc"};
        searchEngine.main(searchArgs);

        // Access searchResults using the getter
        List<Document> searchResults = searchEngine.getSearchResults();

        assertNotNull(searchResults);
        assertEquals(3, searchResults.size());

        // Verify the contents of search results
        assertEquals("doc1", searchResults.get(0).getTitle());
        assertEquals("doc2", searchResults.get(1).getTitle());
        assertEquals("doc3", searchResults.get(2).getTitle());

        deleteFile("test_search_documents.txt");
    }
    @Test
    public void testRemoveDocument() {
        SearchEngine searchEngine = new SearchEngine();

        // Create a test input file containing a sample document
        createInputFile("test_remove_document.txt", "doc1,Author1,2023-01-01\n");

        // Execute the load command
        String[] loadArgs = {"load", "test_remove_document.txt"};
        searchEngine.main(loadArgs);

        // Execute the remove command
        String[] removeArgs = {"remove", "doc1"};
        searchEngine.main(removeArgs);

        // Verify that the document was removed
        LinkedList linkedList = searchEngine.getLinkedList();
        Map<String, Document> documentMap = searchEngine.getDocumentMap();

        assertEquals(0, linkedList.size());
        assertEquals(0, documentMap.size());

        deleteFile("test_remove_document.txt");
    }
    @Test
    public void testClearList() {
        SearchEngine searchEngine = new SearchEngine();

        // Create a test input file containing sample documents
        createInputFile("test_clear_list.txt", "doc1,Author1,2023-01-01\n" +
                "doc2,Author2,2023-01-02\n");

        // Execute the load command
        String[] loadArgs = {"load", "test_clear_list.txt"};
        searchEngine.main(loadArgs);

        // Execute the clear command
        String[] clearArgs = {"clear"};
        searchEngine.main(clearArgs);

        // Verify that the list is empty
        LinkedList linkedList = searchEngine.getLinkedList();
        Map<String, Document> documentMap = searchEngine.getDocumentMap();

        assertEquals(0, linkedList.size());
        assertEquals(0, documentMap.size());

        deleteFile("test_clear_list.txt");
    }
    private void createInputFile(String filePath, String content) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Utility method to delete a test file
    private void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}