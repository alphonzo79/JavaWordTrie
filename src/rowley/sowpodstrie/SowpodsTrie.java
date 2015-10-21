package rowley.sowpodstrie;

/**
 * Created by joe on 10/20/15.
 */
public class SowpodsTrie {
    private TrieNode[] childNodes = new TrieNode[26];
    private int wordCount = 0;

    void addWord(String word) {
        if(word.length() < 1) {
            return;
        }

        char[] wordChars = word.toLowerCase().toCharArray();
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
        int nodeIndex = TrieNode.getIndexOfChar(wordChars[0]);
        return nodeIndex > -1 && childNodes[nodeIndex] != null && childNodes[nodeIndex].isKnownWord(wordChars, 0);
    }

    public int getWordCount() {
        return wordCount;
    }
}
