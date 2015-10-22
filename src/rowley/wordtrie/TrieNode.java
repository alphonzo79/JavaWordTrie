package rowley.wordtrie;

import java.io.Serializable;

/**
 * Created by joe on 10/20/15.
 */
class TrieNode implements Serializable {
    private TrieNode[] childNodes;
    private boolean isWordEnd = false;
    private char character;

    TrieNode(char character) {
        this.character = character;
        childNodes = new TrieNode[2];
    }

    boolean addWord(char[] word, int index) {
        if(++index == word.length) {
            if(!isWordEnd) {
                return isWordEnd = true;
            } else {
                return false;
            }
        } else {
            if(word[index] < 'a' || word[index] > 'z') {
                return false;
            }

            int nodeIndex = 0;
            for(; nodeIndex < childNodes.length; nodeIndex++) {
                if(childNodes[nodeIndex] == null) {
                    break;
                }
                if(childNodes[nodeIndex].getCharacter() == word[index]) {
                    return childNodes[nodeIndex].addWord(word, index);
                }
            }

            //If we're here we did not have an existing node to add to and we need to create
            //one at the index we have been left with
            if(nodeIndex == childNodes.length) {
                resizeChildNodes();
            }
            childNodes[nodeIndex] = new TrieNode(word[index]);
            return childNodes[nodeIndex].addWord(word, index);
        }
    }

    private void resizeChildNodes() {
        //Only increase by 2 because we know we will never work with anything larger than 26
        //Also, building the trie is prep work, ideally done only once, and we want to limit
        //our memory footprint because it can get out of control real quick. So we will only increase
        //the size of the array by 2 each time
        TrieNode[] temp = new TrieNode[childNodes.length + 2];
        for(int i = 0; i < childNodes.length; i++) {
            temp[i] = childNodes[i];
        }
        childNodes = temp;
    }

    boolean isKnownWord(char[] word, int index) {
        if(++index == word.length) {
            return isWordEnd;
        } else {
            for(TrieNode node : childNodes) {
                if(node != null && node.getCharacter() == word[index]) {
                    return node.isKnownWord(word, index);
                }
            }
            return false;
        }
    }

    public char getCharacter() {
        return character;
    }
}
