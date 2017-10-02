package com.example.dell.auto;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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

    public ArrayList<WordClass> getPossibleWords(String prefix){

        ArrayList<WordClass> result = new ArrayList<>();

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
                return null;
        }
        result = curr_node.getSuggestions();
        for(int j=0;j<result.size();j++)
            result.get(j).insertBeg(prefix);

        return result;
    }

    public ArrayList<WordClass>  getSuggestions(){
        Character[] key_set = (Character[])this.children.keySet().toArray();
        ArrayList<WordClass> new_list= new ArrayList<>();

        if(key_set.length > 0){
            int total_keys = key_set.length;

            for(int i=0;i<total_keys;i++) {
                ArrayList<WordClass> child_list = this.children.get(key_set[i]).getSuggestions();
                for(int j=0;j<child_list.size();j++)
                    child_list.get(j).insertBeg(key_set[j]);

                new_list.addAll(child_list);
            }

            return new_list;
        }
        new_list.add(new WordClass("",this.freq));
        return new_list;
    }
}
