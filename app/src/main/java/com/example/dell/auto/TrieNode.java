package com.example.dell.auto;

import java.util.HashMap;

/**
 * Created by coldball on 2/10/17.
 */

public class TrieNode {

    private HashMap<Character,TrieNode> children;
    private boolean isWord;
    private long freq;

    public TrieNode(){
        children = new HashMap<>();
        isWord = false;
        freq = 0;
    }

    public void addWord(String new_word, long freq){
        int len = new_word.length();
        TrieNode curr = this;

        for(int i=0;i<len;i++){
            Character curr_key = new Character(new_word.charAt(i));
            if(curr.children.containsKey(curr_key))
                curr = curr.children.get(curr_key);
            else{
                TrieNode new_node = new TrieNode();
                curr.children.put(curr_key,new_node);
            }

        curr.isWord = true;
        curr.freq += freq;

        }

    }
}
