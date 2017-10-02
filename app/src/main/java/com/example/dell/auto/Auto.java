package com.example.dell.auto;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Auto extends AppCompatActivity {
    TrieNode tree=new TrieNode();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("word_freq.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = in.readLine()) != null) {
                //Log.d("LINE:",line);
                String word = line.trim();
                Log.d("WORD:",word);
                String[] s=word.split("\\s");
                for(String k :s)
                    Log.d("SPLIT",k);
                tree.addWord(s[0], Long.valueOf(s[1]));
                //    Log.d("Rishabh",Long.valueOf(s[1])+"");
                //    Log.d("Rishabh",s[0]+"|"+s[1]);
                  //  Log.d("Rishabh","Failed");
            }
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();

        }
        ArrayList<WordClass> Ans=new ArrayList<>();
        Ans=tree.getPossibleWords("th");
        if(Ans !=null) {
            Collections.sort(Ans, new Comparator<WordClass>() {
                @Override
                public int compare(WordClass wordClass, WordClass t1) {

                    return wordClass.freq.compareTo(t1.freq)*-1;
                }
            });
            int print_size = Ans.size();

            if (print_size > 5)
                print_size = 5;

            for (int k = 0; k < print_size; k++) {
                Log.d("Rishabh", Ans.get(k).string + " " + Ans.get(k).freq.toString());
            }
        }
    }
}
