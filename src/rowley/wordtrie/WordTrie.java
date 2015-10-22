package rowley.wordtrie;

import java.io.Serializable;
import java.util.List;

/**
 * Created by joe on 10/20/15.
 */
public class WordTrie implements Serializable {
    private TrieNode[] childNodes = new TrieNode[26];
    private int wordCount = 0;

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
}
