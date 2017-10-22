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
import android.widget.Button;
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
import java.util.Stack;

public class Auto extends AppCompatActivity {
    TrieNode tree=new TrieNode();
   // private TextView final_text=(TextView)findViewById(R.id.Welcome);
    private String e_text;
    private String p_text;

    private TextView edit_text;
    private TextView final_text;
    private TextView[] textView_arr;
    private Button keyboard_button;
    private Stack <MemoryState> text_state;
    private ArrayList<WordClass> Ans=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        final_text = (TextView)findViewById(R.id.Welcome);

        edit_text=(TextView) findViewById(R.id.text);

        e_text = new String();
        p_text = new String();

        text_state = new Stack<>();

        keyboard_button = (Button)findViewById(R.id.KeyboardButton);
        keyboard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            }
        });

        textView_arr=new TextView[]{
                (TextView)findViewById(R.id.Text1),(TextView)findViewById(R.id.Text2),
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
        if(Keycode == KeyEvent.KEYCODE_SPACE || Keycode == KeyEvent.KEYCODE_ENTER ) {

            char ws = (char) event.getUnicodeChar();
            String curr_state = p_text;

            text_state.push(new MemoryState(p_text,e_text));
            final_text.setText(p_text + e_text + String.valueOf(ws));

            p_text += (e_text + String.valueOf(ws));
            e_text = "";

            //edit_text.setText("");
            for (int i=0;i<5;i++)
            {
                textView_arr[i].setText("");
            }

        }

        else if(Keycode == KeyEvent.KEYCODE_DEL){


            if(e_text != null && e_text.length() > 0) {
                e_text = e_text.substring(0, e_text.length() - 1);
                final_text.setText(p_text + e_text);
                //edit_text.setText(e_text);

                Ans=tree.getPossibleWords(e_text);
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
            else
            {
                if(!text_state.empty()) {
                    MemoryState prev_state = text_state.pop();
                    final_text.setText(prev_state.final_string + prev_state.editing_string);
                    e_text = prev_state.editing_string;
                    p_text = prev_state.final_string;

                    Ans=tree.getPossibleWords(e_text);
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
                    else {

                        for (int i = 0; i < 5; i++) {
                            textView_arr[i].setText("");
                        }
                    }
                }
            }

        }
       else {

            //String input=edit_text.getText().toString();
            e_text += String.valueOf((char)event.getUnicodeChar());
            //edit_text.setText(input);
            final_text.setText(p_text + e_text);
            Ans=tree.getPossibleWords(e_text);
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

    public void setText(View view){
        TextView selected_view = (TextView)view;

        String curr_state = p_text;
        String select_txt = selected_view.getText().toString();

        text_state.push(new MemoryState(curr_state,select_txt));

        final_text.setText( curr_state + select_txt + " " );

        p_text += select_txt;
        e_text = "";
        //edit_text.setText("");

        for (int i=0;i<5;i++)
        {
            textView_arr[i].setText("");
        }

    }
}
