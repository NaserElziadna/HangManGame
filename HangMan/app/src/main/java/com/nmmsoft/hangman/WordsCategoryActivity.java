package com.nmmsoft.hangman;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.nmmsoft.hangman.logic.CustomGridAdapter;

public class WordsCategoryActivity extends AppCompatActivity {
    GridView gridView;

    // This Data show in grid ( Used by adapter )

    static String[] GRID_DATA;
    String [] category_KeyWord;
    String clicked_p;
    String clicked_s;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_words_category);

        GRID_DATA=getResources().getStringArray(R.array.category);

        Intent intent=getIntent();
        clicked_p=intent.getStringExtra("Player");
        clicked_s=intent.getStringExtra("Score");

        gridView = (GridView) findViewById(R.id.gridView1);

        // Set custom adapter (GridAdapter) to gridview

        gridView.setAdapter(  new CustomGridAdapter( this, GRID_DATA ) );

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (((TextView) view.findViewById( R.id.grid_item_label )).getText()=="java")
                    category_KeyWord=getResources().getStringArray(R.array.javaKeyWord);
                else if (((TextView) view.findViewById( R.id.grid_item_label )).getText()=="animals")
                    category_KeyWord=getResources().getStringArray(R.array.animalsKeyWord);
                else if (((TextView) view.findViewById( R.id.grid_item_label )).getText()=="names")
                    category_KeyWord=getResources().getStringArray(R.array.namesKeyWord);
                else if (((TextView) view.findViewById( R.id.grid_item_label )).getText()=="verbs")
                    category_KeyWord=getResources().getStringArray(R.array.verbsKeyWord);

                Intent intent = new Intent(WordsCategoryActivity.this,MainActivity.class);
                intent.putExtra("WordsCat",category_KeyWord);
                intent.putExtra("Player", clicked_p);
                intent.putExtra("Score", clicked_s);
                startActivity(intent);
                finish();
                return false;
            }
        });
    }
}