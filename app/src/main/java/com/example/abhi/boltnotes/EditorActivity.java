package com.example.abhi.boltnotes;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.security.PrivilegedAction;

public class EditorActivity extends AppCompatActivity {

    SharedPreferences settingss;
    private String action ;
    private EditText editor ;
    private String noteFilter;
    private  String oldText;
    ImageView imgg;//yellow
    ImageView img;//red
    ImageView purple;
    ImageView green;
    ImageView blue;
    String colorCheck="black";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        editor =(EditText)findViewById(R.id.editText);

        img = (ImageView)findViewById(R.id.imagev);
        imgg = (ImageView)findViewById(R.id.imageView);
        green = (ImageView)findViewById(R.id.green);
        purple = (ImageView)findViewById(R.id.purple);
        blue = (ImageView)findViewById(R.id.blue);

        settingss = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        colorCheck= settingss.getString("tokenn",colorCheck);
        if(colorCheck.equals("black"))
        editor.setTextColor(getResources().getColor(R.color.black));
        if(colorCheck.equals("red"))
            editor.setTextColor(getResources().getColor(R.color.red));
        if(colorCheck.equals("myYellow"))
            editor.setTextColor(getResources().getColor(R.color.myYellow));
        if(colorCheck.equals("blue"))
            editor.setTextColor(getResources().getColor(R.color.blue));
        if(colorCheck.equals("purple"))
            editor.setTextColor(getResources().getColor(R.color.purple));
        if(colorCheck.equals("green"))
            editor.setTextColor(getResources().getColor(R.color.green));


        Intent intent = getIntent();
        Uri uri =intent.getParcelableExtra(NotesProvider.CONTENT_ITEM_TYPE);
        if(uri == null) {
            action = Intent.ACTION_INSERT;
            setTitle(getString(R.string.new_note));
        }
        else
        {
            action=Intent.ACTION_EDIT;
            noteFilter = DBOpenHelper.NOTE_ID + "=" +uri.getLastPathSegment();

            Cursor cursor = getContentResolver().query(uri,DBOpenHelper.ALL_COLUMNS,noteFilter,null,null);
            cursor.moveToFirst();
            oldText =cursor.getString(cursor.getColumnIndex(DBOpenHelper.NOTE_TEXT));
            editor.setText(oldText);
            editor.requestFocus();
        }

    }
    private void finishedEditing(){
        String newText = editor.getText().toString().trim();
        switch (action)
        {
            case Intent.ACTION_INSERT:
                if(newText.length()==0){
                    setResult(RESULT_CANCELED);
        }else
        {
            insertNote(newText);
        }
                break;
            case Intent.ACTION_EDIT:
                if(newText.length() == 0)
                {
                    deleteNote();
                }else if(oldText.equals(newText))
                {
                    setResult(RESULT_CANCELED);
                }
                else
                {
                    updateNote(newText);
                }

        }finish();

    }

    private void updateNote(String noteText) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.NOTE_TEXT, noteText);
        getContentResolver().update(NotesProvider.CONTENT_URI,values,noteFilter,null);
        Toast.makeText(getApplicationContext(), R.string.note_updated,Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
    }

    private void deleteNote() {
        getContentResolver().delete(NotesProvider.CONTENT_URI,noteFilter,null);
        Toast.makeText(this, R.string.note_deleted,Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(action.equals(Intent.ACTION_EDIT)){
        getMenuInflater().inflate(R.menu.menu_editor, menu);}
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()){
            case android.R.id.home:
                finishedEditing();
                break;
            case R.id.action_delete:
                deleteNote();
        }

        return true;
    }


    private void insertNote(String noteText) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.NOTE_TEXT, noteText);
        getContentResolver().insert(NotesProvider.CONTENT_URI,values);
        setResult(RESULT_OK);
    }
    @Override
    public void onBackPressed(){finishedEditing();}

    public void colorChange(View view) {
        String tagg =view.getTag().toString();
        if(tagg.equals("red"))
        {
            editor.setTextColor(getResources().getColor(R.color.red));
            settingss = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = settingss.edit();
            editor.putString("tokenn", "red");
            editor.apply();
        }
        if(tagg.equals("yellow"))
        {
            editor.setTextColor(getResources().getColor(R.color.myYellow));
            settingss = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = settingss.edit();
            editor.putString("tokenn", "myYellow");
            editor.apply();
        }
        if(tagg.equals("blue"))
        {
            editor.setTextColor(getResources().getColor(R.color.blue));
            settingss = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = settingss.edit();
            editor.putString("tokenn", "blue");
            editor.apply();
        }
        if(tagg.equals("green"))
        {
            editor.setTextColor(getResources().getColor(R.color.green));
            settingss = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = settingss.edit();
            editor.putString("tokenn", "green");
            editor.apply();
        }
        if(tagg.equals("purple"))
        {
            editor.setTextColor(getResources().getColor(R.color.purple));
            settingss = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = settingss.edit();
            editor.putString("tokenn", "purple");
            editor.apply();
        }
    }
}
