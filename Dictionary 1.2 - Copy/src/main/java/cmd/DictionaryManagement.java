package cmd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DictionaryManagement {
    private static final String url = "jdbc:sqlite:./src/main/resources/data/database/dictionary_database.db";

    // Basic Trie implementation
    public static class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;
        String html;

        public TrieNode() {
            this.children = new HashMap<>();
            this.isEndOfWord = false;
            this.html = null;
        }
    }

    private static TrieNode root = new TrieNode();

    private static void insertWordToTrie(String word, String html) {
        TrieNode node = root;
        for (char ch : word.toLowerCase().toCharArray()) {
            node.children.putIfAbsent(ch, new TrieNode());
            node = node.children.get(ch);
        }
        node.isEndOfWord = true;
        node.html = html;
    }

    public static String searchWordInTrie(TrieNode trie, String word) {
        TrieNode node = root;
        if (word == null) {
            return null;
        }
        for (char ch : word.toLowerCase().toCharArray()) {
            if (!node.children.containsKey(ch)) {
                return null;
            }
            node = node.children.get(ch);
        }
        return node.html;
    }

    public static TrieNode buildTrie(String datatable) {
        TrieNode root = new TrieNode();
        try (Connection c = DriverManager.getConnection(url);
             Statement stmt = c.createStatement()) {

            String sql = "SELECT * FROM " + datatable;
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String word = rs.getString("word");
                    String html = rs.getString("html");
                    insertWordToTrie(word, html);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error building trie: " + e.getMessage());
            e.printStackTrace();
        }
        return root;
    }

    public static ObservableList<Word> searchWordsInTrie(TrieNode trie, String searchTerm) {
        ObservableList<Word> resultList = FXCollections.observableArrayList();
        String htmlContent = searchWordInTrie(trie, searchTerm.toLowerCase().trim());

        if (htmlContent != null && !htmlContent.isEmpty()) {
            resultList.add(new Word(searchTerm, htmlContent));
        }

        return resultList;
    }

    public static ObservableList<Word> dbSearchWord(String keyWord, String tableName) {
        ObservableList<Word> list = FXCollections.observableArrayList();
        try (Connection c = DriverManager.getConnection(url);
             PreparedStatement pstmt = c.prepareStatement("SELECT * FROM " + tableName + " WHERE word LIKE ? ORDER BY word")) {
            pstmt.setString(1, keyWord + "%");
            System.out.println("Executing SQL query: " + pstmt.toString());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Word word = new Word(
                            rs.getString("word"),
                            rs.getString("html")
                    );
                    insertWordToTrie(word.getWordTarget(), word.getHtml());
                    list.add(word);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}
