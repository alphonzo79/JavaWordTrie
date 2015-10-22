package rowley.wordtrie;

import java.io.*;
import java.util.List;

/**
 * Created by joe on 10/20/15.
 */
public class WordTrie {
    private TrieNode[] childNodes = new TrieNode[26];
    private int wordCount = 0;

    //Default constructor package private to enforce use of the builder
    WordTrie() {

    }

    public void addWord(String word) {
        if(word.length() < 1) {
            return;
        }

        char[] wordChars = word.toLowerCase().toCharArray();
        addWordCharArray(wordChars);
    }

    public void addWord(char[] word) {
        if(word.length < 1) {
            return;
        }

        for(int i = 0; i < word.length; i++) {
            word[i] = Character.toLowerCase(word[i]);
        }
        addWordCharArray(word);
    }

    public void addWords(String[] words) {
        for(String word : words) {
            addWord(word);
        }
    }

    public void addWords(List<String> words) {
        for(String word : words) {
            addWord(word);
        }
    }

    private void addWordCharArray(char[] wordChars) {
        int nodeIndex = TrieNode.getIndexOfChar(wordChars[0]);
        if(nodeIndex == -1) {
            return;
        }

        if(childNodes[nodeIndex] == null) {
            childNodes[nodeIndex] = new TrieNode();
        }
        if(childNodes[nodeIndex].addWord(wordChars, 0)) {
            wordCount++;
        }
    }

    public boolean isKnownWord(String word) {
        if(word.length() < 1) {
            return false;
        }

        char[] wordChars = word.toLowerCase().toCharArray();
        return isKnownWordInternal(wordChars);
    }

    public boolean isKnownWord(char[] word) {
        if(word.length < 1) {
            return false;
        }

        for(int i = 0; i < word.length; i++) {
            word[i] = Character.toLowerCase(word[i]);
        }
        return isKnownWordInternal(word);
    }

    private boolean isKnownWordInternal(char[] wordChars) {
        int nodeIndex = TrieNode.getIndexOfChar(wordChars[0]);
        return nodeIndex > -1 && childNodes[nodeIndex] != null && childNodes[nodeIndex].isKnownWord(wordChars, 0);
    }

    public int getWordCount() {
        return wordCount;
    }

    private void initializeSowpodWords() {
        readInResource("sowpods.txt");
    }

    private void initializeEnableWords() {
        readInResource("enable1.txt");
    }

    private void readInResource(String filename) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String word;
        try {
            while((word = bufferedReader.readLine()) != null) {
                addWord(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class WordTrieBuilder {
        private WordTrie trie;

        public WordTrieBuilder() {
            trie = new WordTrie();
        }

        public WordTrieBuilder addSowpodWords() {
            trie.initializeSowpodWords();
            return this;
        }

        public WordTrieBuilder addEnableWords() {
            trie.initializeEnableWords();
            return this;
        }

        public WordTrie build() {
            return trie;
        }
    }
}
