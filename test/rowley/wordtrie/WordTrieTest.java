package rowley.wordtrie;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.*;

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

    @Test
    public void testFullSowpodList() {
        long start = System.currentTimeMillis();
        WordTrie trie = new WordTrie.WordTrieBuilder().addSowpodWords().build();

        InputStream is = getClass().getClassLoader().getResourceAsStream("sowpods.txt");
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String word;
        try {
            while((word = bufferedReader.readLine()) != null) {
                Assert.assertTrue(trie.isKnownWord(word));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        FileOutputStream fos = null;
//        ObjectOutputStream oos = null;
//        File output = new File("./resources/sowpods.trie");
//        try {
//            fos = new FileOutputStream(output);
//            oos = new ObjectOutputStream(fos);
//
//            oos.writeObject(trie);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if(fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if(oos != null) {
//                try {
//                    oos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        Assert.assertFalse(trie.isKnownWord("gobbkleksnkoieklsnkls"));
        Assert.assertFalse(trie.isKnownWord("1234"));
        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }

    @Test
    public void testPerformance() {
        WordTrie trie = new WordTrie.WordTrieBuilder().addSowpodWords().build();
        String[] wordArray = new String[267751];
        List<String> wordList = new ArrayList<String>(267751);
        Set<String> wordHashSet = new HashSet<String>(267751);
        Set<String> wordTreeSet = new TreeSet<String>();

        InputStream is = getClass().getClassLoader().getResourceAsStream("sowpods.txt");
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String word;
        try {
            int i = 0;
            while((word = bufferedReader.readLine()) != null) {
                wordList.add(word);
                wordArray[i] = word;
                wordHashSet.add(word);
                wordTreeSet.add(word);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long start = System.currentTimeMillis();
        for(String target : wordArray) {
            trie.isKnownWord(target);
        }
        long end = System.currentTimeMillis();
        System.out.println("Trie find all: " + (end - start) + " millis");

        start = System.currentTimeMillis();
        trie.isKnownWord(wordArray[0]);
        end = System.currentTimeMillis();
        System.out.println("Trie find first: " + (end - start) + " millis");

        start = System.currentTimeMillis();
        trie.isKnownWord(wordArray[267750]);
        end = System.currentTimeMillis();
        System.out.println("Trie find last: " + (end - start) + " millis");

        start = System.currentTimeMillis();
        wordList.contains(wordArray[0]);
        end = System.currentTimeMillis();
        System.out.println("List find first: " + (end - start) + " millis");

        start = System.currentTimeMillis();
        wordList.contains(wordArray[267750]);
        end = System.currentTimeMillis();
        System.out.println("List find last: " + (end - start) + " millis");

        start = System.currentTimeMillis();
        for(String target : wordArray) {
            if(target.equals(wordList.get(0))) {
                break;
            }
        }
        end = System.currentTimeMillis();
        System.out.println("Array find first: " + (end - start) + " millis");

        start = System.currentTimeMillis();
        for(String target : wordArray) {
            if(target.equals(wordList.get(267750))) {
                break;
            }
        }
        end = System.currentTimeMillis();
        System.out.println("Array find last: " + (end - start) + " millis");

        start = System.currentTimeMillis();
        wordHashSet.contains(wordArray[0]);
        end = System.currentTimeMillis();
        System.out.println("HashSet find first: " + (end - start) + " millis");

        start = System.currentTimeMillis();
        wordHashSet.contains(wordArray[267750]);
        end = System.currentTimeMillis();
        System.out.println("HashSet find last: " + (end - start) + " millis");

        start = System.currentTimeMillis();
        for(String target : wordArray) {
            wordHashSet.contains(target);
        }
        end = System.currentTimeMillis();
        System.out.println("HashSet find all: " + (end - start) + " millis");

        start = System.currentTimeMillis();
        wordTreeSet.contains(wordArray[0]);
        end = System.currentTimeMillis();
        System.out.println("TreeSet find first: " + (end - start) + " millis");

        start = System.currentTimeMillis();
        wordTreeSet.contains(wordArray[267750]);
        end = System.currentTimeMillis();
        System.out.println("TreeSet find last: " + (end - start) + " millis");

        start = System.currentTimeMillis();
        for(String target : wordArray) {
            wordTreeSet.contains(target);
        }
        end = System.currentTimeMillis();
        System.out.println("TreeSet find all: " + (end - start) + " millis");
    }
}
