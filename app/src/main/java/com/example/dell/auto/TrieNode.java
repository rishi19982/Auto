package com.example.dell.auto;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by coldball on 2/10/17.
 */

public class TrieNode {

    private HashMap<Character,TrieNode> children;
    private boolean isWord;
    private Long freq;
    private String suggested_str;

    public TrieNode(){
        children = new HashMap<>();
        suggested_str = "";
        isWord = false;
        freq = new Long(0);
    }

    public void addWord(String new_word, Long freq){
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

    public ArrayList<HashMap<String,Long>> getPossibleWords(String prefix){

        ArrayList<HashMap<String,Long>> result = new ArrayList<>();

        String valid_word = "";
        TrieNode curr_node = this;
        int len = prefix.length();

        for(int i=0;i<len;i++) {
            Character curr_key = new Character(prefix.charAt(i));
            if (curr_node.children.containsKey(curr_key)){
                curr_node = curr_node.children.get(curr_key);
                valid_word += curr_key.toString();

            }
            else
                return result;
        }


        return result;
    }

    public ArrayList<HashMap<String,Long>>  getSuggestions(){

        return null;
    }
}
