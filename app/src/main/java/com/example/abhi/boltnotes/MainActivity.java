package com.example.abhi.boltnotes;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>

{
   // SharedPreferences mPrefs;
    SharedPreferences settings;
    private static final int EDITOR_REQUEST_CODE =1001;
    private CursorAdapter cursorAdapter;
    boolean tokenPassword =false;

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //Retrieve data in the intent
//        tokenPassword= intent.getStringExtra("valueId");
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] from = {DBOpenHelper.NOTE_TEXT};
        int[] to = {R.id.tvNote};
        cursorAdapter = new NotesCursorAdapter(this, null, 0);

        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,EditorActivity.class);
                Uri uri =Uri.parse(NotesProvider.CONTENT_URI + "/" + id);
                intent.putExtra(NotesProvider.CONTENT_ITEM_TYPE,uri);
                startActivityForResult(intent,EDITOR_REQUEST_CODE);
            }
        });

        getLoaderManager().initLoader(0, null, this);

        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        tokenPassword= settings.getBoolean("token",tokenPassword);
        if(tokenPassword) {

            Intent intt = new Intent(this,PasswordPage.class);
            startActivity(intt);
        }

    }

    private void insertNote(String noteText) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.NOTE_TEXT, noteText);
        Uri noteUri = getContentResolver().insert(NotesProvider.CONTENT_URI,
                values);
        Log.d("MainActivity", "Inserted note " + noteUri.getLastPathSegment());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_create_sample_data:
                insertSampleData();
                break;
            case R.id.action_delete_all:
                deleteAllSamples();
                break;
            case R.id.action_password:
                password();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void password() {
        Intent intent = new Intent(this,ChangingPassword.class);
        startActivityForResult(intent, 1000);
    }

    private void deleteAllSamples() {
        DialogInterface.OnClickListener dialogclicklistener= new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int button) {
                if(button==DialogInterface.BUTTON_POSITIVE){
                    getContentResolver().delete(NotesProvider.CONTENT_URI,null,null);
                    restartLoader();
                    Toast.makeText(getApplicationContext(),getString(R.string.all_deleted),Toast.LENGTH_LONG).show();
                }

            }
        };
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.are_you_sure))
                .setPositiveButton(getString(android.R.string.yes),dialogclicklistener)
                .setNegativeButton(getString(android.R.string.no),dialogclicklistener)
                .show();
    }

    private void insertSampleData() {
        insertNote("simple note");
        insertNote("Multi-line\nnote");
        insertNote("Very long note with a lot of text  exceeding the width of the screen");
        restartLoader();
    }

    private Loader<Cursor> restartLoader() {
        return getLoaderManager().restartLoader(0,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, NotesProvider.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    public void openEditorForNewNote(View view) {
        Intent intent =new Intent(this,EditorActivity.class);
        startActivityForResult(intent,EDITOR_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==EDITOR_REQUEST_CODE&&resultCode ==RESULT_OK)
        {
            restartLoader();
        }
        if (requestCode == 1000) {
            if(resultCode == MainActivity.RESULT_OK){
                settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("token",true);
                editor.commit();
            }
            if (resultCode == MainActivity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
    }
}}
