package com.example.abhi.boltnotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ChangingPassword extends AppCompatActivity {
    TextView password;
    String passwordd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changing_password);

        password = (EditText)findViewById(R.id.password);

    }
//    Intent intentt;
    public void setPassword(View view) {

        String pass = password.getText().toString();
        SharedPreferences.Editor e = this.getPreferences(Context.MODE_PRIVATE).edit();
        e.putString(pass, passwordd);
        e.commit();
//        intentt = new Intent(this,MainActivity.class);
//        intentt.putExtra("pass",passwordd);
//        intentt.putExtra("token",true);
//        startActivityForResult(intentt,0);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("token",true);
        setResult(MainActivity.RESULT_OK,returnIntent);
        finish();
    }
}
