package rowley.wordtrie;

import java.io.*;

/**
 * Created by joe on 10/20/15.
 */
public class BuildTrie {
    public static void main(String[] args) {
        WordTrie sowpodsTrie = new WordTrie();
        WordTrie enableTrie = new WordTrie();
        WordTrie bothTrie = new WordTrie();

        File sowpods = new File("./assets/sowpods.txt");
        File enable = new File("./assets/enable1.txt");
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(sowpods);
            bufferedReader = new BufferedReader(fileReader);

            //Two lines at the beginning
            bufferedReader.readLine();
            bufferedReader.readLine();
            String word;
            while((word = bufferedReader.readLine()) != null) {
                sowpodsTrie.addWord(word);
                bothTrie.addWord(word);
            }
//            System.out.println("Sowpods: " + sowpodsTrie.getWordCount());

            fileReader = new FileReader(enable);
            bufferedReader = new BufferedReader(fileReader);
            while((word = bufferedReader.readLine()) != null) {
                enableTrie.addWord(word);
                bothTrie.addWord(word);
            }
//            System.out.println("Enable: " + enableTrie.getWordCount());
//            System.out.println("Both: " + bothTrie.getWordCount());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileReader != null) {
                try {
                    fileReader.close();
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

        File serializedSowpods = new File("./resources/sowpods.trie");
        File serializedEnable = new File("./resources/enable.trie");
        File serializedBoth = new File("./resources/both.trie");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            serializedSowpods.createNewFile();
            serializedEnable.createNewFile();
            serializedBoth.createNewFile();

            fos = new FileOutputStream(serializedSowpods);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(sowpodsTrie);

            fos = new FileOutputStream(serializedEnable);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(enableTrie);

            fos = new FileOutputStream(serializedBoth);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(bothTrie);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
            if(oos != null) {
                try {
                    oos.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
