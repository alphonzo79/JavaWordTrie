package rowley.sowpodstrie;

import java.io.*;
import java.net.URL;

/**
 * Created by joe on 10/20/15.
 */
public class BuildTrie {
    public static void main(String[] args) {
        SowpodsTrie trie = new SowpodsTrie();

        File file = new File("./assets/sowpods.txt");
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            //Two lines at the beginning
            bufferedReader.readLine();
            bufferedReader.readLine();
            String word;
            while((word = bufferedReader.readLine()) != null) {
                trie.addWord(word);
            }
            System.out.println(trie.getWordCount());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
