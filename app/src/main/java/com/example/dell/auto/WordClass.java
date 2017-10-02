package com.example.dell.auto;

/**
 * Created by coldball on 2/10/17.
 */

public class WordClass {
    String string;
    Long freq;

    public WordClass(){
        string = null;
        freq = Long.valueOf(0);
    }

    public WordClass(String new_str, Long freq){
        string = new_str;
        this.freq = freq;
    }

    public void insertBeg(Character c){
        string = c.toString() + string;
    }

    public void insertBeg(String s){
        string = s + string;
    }
}
