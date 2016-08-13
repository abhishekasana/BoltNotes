package com.example.abhi.boltnotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class PasswordPage extends AppCompatActivity {
    String passw;
    StringBuilder myName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_page);
    }
    public void setPassword(View view)
    {
        myName = new StringBuilder(passw);
        passw="0123";
        String tag="";
        int n=0;
        tag = view.getTag().toString();
        if(tag.equals("0"))
        {
            myName.setCharAt(n, '0');
            ++n;
        }
        if(tag.equals("1"))
        {
            myName.setCharAt(n, '1');
            ++n;
        }
        if(tag.equals("2"))
        {
            myName.setCharAt(n, '2');
            ++n;
        }
        if(tag.equals("3"))
        {
            myName.setCharAt(n, '3');
            ++n;
        }
        if(tag.equals("4"))
        {
            myName.setCharAt(n, '4');
            ++n;
        }
        if(tag.equals("5"))
        {
            myName.setCharAt(n, '5');
            ++n;
        }
        if(tag.equals("6"))
        {
            myName.setCharAt(n, '6');
            ++n;
        }
        if(tag.equals("7"))
        {
            myName.setCharAt(n, '7');
            ++n;
        }
        if(tag.equals("8"))
        {
            myName.setCharAt(n, '8');
            ++n;
        }
        if(tag.equals("9"))
        {
            myName.setCharAt(n, '9');
            ++n;
        }
        Log.i("PLEASEEEE",tag);
        System.out.println(myName);
//        if(myName.length()==4) {
//            Intent intet = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intet);
//        }
    }


}
