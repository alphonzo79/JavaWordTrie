package rowley.wordtrie;

import java.io.*;
import java.util.List;

/**
 * Created by joe on 10/20/15.
 */
public class WordTrie implements Serializable {
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
        int nodeIndex = getIndexOfChar(wordChars[0]);
        if(nodeIndex == -1) {
            return;
        }

        if(childNodes[nodeIndex] == null) {
            childNodes[nodeIndex] = new TrieNode(wordChars[0]);
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
        int nodeIndex = getIndexOfChar(wordChars[0]);
        return nodeIndex > -1 && childNodes[nodeIndex] != null && childNodes[nodeIndex].isKnownWord(wordChars, 0);
    }

    public int getWordCount() {
        return wordCount;
    }

    private void initializeSowpodWords(int maxWordLength) {
        readInResource("sowpods.txt", maxWordLength);
    }

    private void initializeEnableWords(int maxWordLength) {
        readInResource("enable1.txt", maxWordLength);
    }

    private void readInResource(String filename, int maxWordLength) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String word;
        try {
            while((word = bufferedReader.readLine()) != null) {
                if(word.length() <= maxWordLength) {
                    addWord(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int getIndexOfChar(char character) {
        switch(character) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'j':
                return 9;
            case 'k':
                return 10;
            case 'l':
                return 11;
            case 'm':
                return 12;
            case 'n':
                return 13;
            case 'o':
                return 14;
            case 'p':
                return 15;
            case 'q':
                return 16;
            case 'r':
                return 17;
            case 's':
                return 18;
            case 't':
                return 19;
            case 'u':
                return 20;
            case 'v':
                return 21;
            case 'w':
                return 22;
            case 'x':
                return 23;
            case 'y':
                return 24;
            case 'z':
                return 25;
            default:
                return -1;
        }
    }

    public static class WordTrieBuilder {
        private WordTrie trie;

        public WordTrieBuilder() {
            trie = new WordTrie();
        }

        public WordTrieBuilder addSowpodWords() {
            trie.initializeSowpodWords(Integer.MAX_VALUE);
            return this;
        }

        public WordTrieBuilder addSowpodWords(int maxWordLength) {
            trie.initializeSowpodWords(maxWordLength);
            return this;
        }

        public WordTrieBuilder addEnableWords() {
            trie.initializeEnableWords(Integer.MAX_VALUE);
            return this;
        }

        public WordTrieBuilder addEnableWords(int maxWordLength) {
            trie.initializeEnableWords(maxWordLength);
            return this;
        }

        public WordTrie build() {
            return trie;
        }
    }
}
