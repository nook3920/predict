package com.nook.predict;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//TODO: add database user
//TODO: add recycleView

public class MainActivity extends AppCompatActivity {
    private EditText inputText;
    private TextView outputText;
    private android.support.v7.widget.GridLayout gbt;
    private static ArrayList<String> result;

    static {
        result = new ArrayList<>();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        inputText = findViewById(R.id.inputText);
        outputText = findViewById(R.id.textView5);
        inputText.addTextChangedListener(myTextWatcher);
        gbt = findViewById(R.id.gridcontainBT);

        inputText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER){
                    result.add(inputText.getText().toString());
                    String a = "";
                    for(String b : result)
                        a += b;
                    outputText.setText(a);
                    inputText.setText("");
                    return true;
                }
                return false;
            }
        });

    }

    private TextWatcher myTextWatcher;
    {
        myTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (inputText.length() > 0) {
                    CompleteDbAccess aa = new CompleteDbAccess(getApplicationContext());
                    aa.open();
                    ArrayList<String> ss = aa.getComplete(inputText.getText().toString());
                    gbt.removeAllViews();
                    MakeButton(ss);

                }
                else if((result.size() >= 1 && inputText.getText().length() == 0)){
                    PredictDbAccess bb = new PredictDbAccess(getApplicationContext());
                    bb.open();
                    String a = result.get(result.size()-1);

                    ArrayList<String> zz = bb.getPredict(a);

                    gbt.removeAllViews();
                    MakeButton(zz);
                }
                else{
                    gbt.removeAllViews();
                }
            }
        };
    }

    private void MakeButton(ArrayList<String> completeWord){
        for(String s : completeWord){
            final Button bt = new Button(this);
            bt.setText(s);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    updateText(bt.getText().toString());
                    Toast.makeText(MainActivity.this,bt.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });

            bt.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    MyTTS.getInstance(getApplicationContext()).speak(bt.getText().toString());
                    Toast.makeText(MainActivity.this,"Speak!!!",Toast.LENGTH_SHORT);
                    return true;
                }
            });
            gbt.addView(bt);
        }
    }

    private void updateText(String s){
        result.add(s);
        String a = "";
        for(String b : result)
            a += b;
        outputText.setText(a);
        gbt.removeAllViews();
        inputText.setText("");
    }

    public void delWord(View view){
        if(result.size() > 0){
            result.remove(result.size()-1);
            String a = "";
            for(String b : result)
                a += b;
            outputText.setText(a);
            final Animation myAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
            view.startAnimation(myAnim);
            gbt.removeAllViews();
        }
        if((result.size() >= 1 && inputText.getText().length() == 0)){
            PredictDbAccess bb = new PredictDbAccess(getApplicationContext());
            bb.open();
            String a = result.get(result.size()-1);

            ArrayList<String> zz = bb.getPredict(a);

            gbt.removeAllViews();
            MakeButton(zz);
        }

    }

    public void speak(View view){

        if(result.size() > 0){
            String a = "";
            for(String b : result)
                a += b;
            MyTTS.getInstance(getApplicationContext()).speak(a);
        }
    }

}
