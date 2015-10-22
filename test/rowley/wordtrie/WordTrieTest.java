package rowley.wordtrie;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by joe on 10/21/15.
 */
public class WordTrieTest {
    private String[] testWords = new String[] {"Joe", "Rowley", "Java", "Hello", "World", "Update", "ready", "license",
            "caption", "test", "hour", "pumpkin", "detective", "read", "pots", "pans", "apartment", "census", "captain",
            "question", "follow", "home", "aggressive", "inside", "lots", "chase", "corner", "entire", "charge", "momma"};

    private String[] unknownWords = new String[] {"water", "guarantee", "accessory", "1234", "jkljd.,,.;", "weight"};

    @Test
    public void testAddSingleWord() {
        WordTrie trie = new WordTrie();
        trie.addWord(testWords[0]);
        Assert.assertTrue(trie.isKnownWord(testWords[0]));
        Assert.assertFalse(trie.isKnownWord(testWords[1]));
    }

    @Test
    public void testFullListVerify() {
        WordTrie trie = new WordTrie();
        trie.addWords(testWords);
        for(String word : testWords) {
            Assert.assertTrue(word, trie.isKnownWord(word));
        }
        for(String word : unknownWords) {
            Assert.assertFalse(word, trie.isKnownWord(word));
        }
    }

    @Test
    public void testCharArraysInTrie() {
        WordTrie trie = new WordTrie();
        trie.addWord(testWords[1].toCharArray());
        Assert.assertTrue(trie.isKnownWord(testWords[1].toCharArray()));
        Assert.assertFalse(trie.isKnownWord(testWords[2].toCharArray()));
    }

    @Test
    public void testTrieIsCaseInsensitive() {
        WordTrie trie = new WordTrie();
        trie.addWord(testWords[0]);
        Assert.assertTrue(trie.isKnownWord(testWords[0].toUpperCase()));
        Assert.assertTrue(trie.isKnownWord(testWords[0].toLowerCase()));
    }

    @Test
    public void testTrieWordCount() {
        WordTrie trie = new WordTrie();
        trie.addWords(testWords);
        Assert.assertEquals(testWords.length, trie.getWordCount());
    }
}
