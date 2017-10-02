package com.example.dell.auto;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
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
   // private TextView final_text=(TextView)findViewById(R.id.Welcome);
    private TextView edit_text;

    private TextView[] textView_arr;

    private ArrayList<WordClass> Ans=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);
        edit_text=(TextView) findViewById(R.id.text);
        edit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            }
        });
        textView_arr=new TextView[]{(TextView)findViewById(R.id.Text1),(TextView)findViewById(R.id.Text2),
                (TextView)findViewById(R.id.Text3),(TextView)findViewById(R.id.Text4),
                (TextView)findViewById(R.id.Text5)};
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

        //Ans=tree.getPossibleWords("th");

    }
    @Override
    public boolean onKeyUp(int Keycode, KeyEvent event)
    {
        if(Keycode>=KeyEvent.KEYCODE_A && Keycode<=KeyEvent.KEYCODE_Z)
        {
            String input=edit_text.getText().toString();
            input += (char)event.getUnicodeChar();
            edit_text.setText(input);
            Ans=tree.getPossibleWords(input);
            if(Ans !=null) {
                Collections.sort(Ans, new Comparator<WordClass>() {
                    @Override
                    public int compare(WordClass wordClass, WordClass t1) {

                        return wordClass.freq.compareTo(t1.freq)*-1;
                    }
                });
                int print_size = Ans.size();
                for (int i=0;i<5;i++)
                {
                    if(i<Ans.size())
                        textView_arr[i].setText(Ans.get(i).string);
                    else
                        textView_arr[i].setText("");
                }


            }
            else{
                for (int i=0;i<5;i++)
                {
                    textView_arr[i].setText("");
                }
            }
        }
       // final_text.setText("Hello");
        return super.onKeyUp(Keycode,event);
    }
}
