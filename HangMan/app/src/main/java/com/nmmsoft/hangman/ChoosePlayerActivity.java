package com.nmmsoft.hangman;
///////////////////////////
//  Designed and Creared //
//     Naser Elziadna    //
//         Thanks♥       //
///////////////////////////

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.nmmsoft.hangman.logic.DatabaseHelper;
import com.nmmsoft.hangman.logic.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class ChoosePlayerActivity extends AppCompatActivity {
    DatabaseHelper db;

    Button add_data;
    Button next;
    EditText add_name;
    ListView userlist;
    String clicked_p,clicked_s;
    HashMap<Integer,Player> players=new HashMap<>();

    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player);

        db=new DatabaseHelper(ChoosePlayerActivity.this);

        listItem = new ArrayList<>();

        add_data=findViewById(R.id.add_data);
        add_name=findViewById(R.id.add_name);
        userlist=findViewById(R.id.users_list);
        next=findViewById(R.id.next);

        viewData();

        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                final String text = userlist.getItemAtPosition(position).toString();
                Toast.makeText(ChoosePlayerActivity.this,""+text,Toast.LENGTH_SHORT).show();
                String[] a=text.split(" ");
                clicked_p=a[0];
                clicked_s=a[1];
//                current_Player=new Player(clicked_p,Integer.parseInt(clicked_s));
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(ChoosePlayerActivity.this, MainActivity.class);
                        intent.putExtra("Player", clicked_p);
                        intent.putExtra("Score", clicked_s);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db==null){Toast.makeText(ChoosePlayerActivity.this,"null data",Toast.LENGTH_LONG).show();}
                String name = add_name.getText().toString();
                if (!name.equals("")&& db.insertData(name)){
                    Toast.makeText(ChoosePlayerActivity.this,"Data Added",Toast.LENGTH_SHORT).show();
                    add_name.setText("");
                    listItem.clear();
                    viewData();
                }else {
                    Toast.makeText(ChoosePlayerActivity.this,"Data not Added,\nCheck if player name already exits",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void viewData() {
        Cursor cursor =db.viewData();
        if (cursor.getCount()==0){
            Toast.makeText(ChoosePlayerActivity.this,"No Data to Show ",Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                listItem.add(cursor.getString(1)+" "+cursor.getString(2));
                players.put(cursor.getInt(0),new Player(cursor.getString(1),cursor.getInt(2)));
            }
            adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listItem);
            userlist.setAdapter(adapter);
        }
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu,menu);
//
//        MenuItem searchItem =menu.findItem(R.id.item_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                ArrayList<String> userlist = new ArrayList<>();
//                for (String user: listItem) {
//                    if (user.toLowerCase().contains(newText.toLowerCase())){
//                        userlist.add(user);
//                    }
//                }
//                ArrayAdapter<String> adapter=new ArrayAdapter<String>(ChoosePlayerActivity.this,android.R.layout.simple_list_item_1,userlist);
//                ListView lv=findViewById( R.id.users_list);
//                lv.setAdapter(adapter);
//                return true;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
}