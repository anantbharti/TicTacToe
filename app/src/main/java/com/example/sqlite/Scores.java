package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Scores extends AppCompatActivity {

    DatabaseHelper myDb;
    TextView scoreText;
    String level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        myDb=new DatabaseHelper(this);
        scoreText=findViewById(R.id.score_text);
        level=getIntent().getExtras().getString("Level");
        viewScores(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.score_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_by_date:{
                viewScores(1);
                break;
            }
            case R.id.sort_by_wins:{
                viewScores(2);
                break;
            }
            case R.id.sort_by_loses:{
                viewScores(3);
                break;
            }
            case R.id.sort_by_ties:{
                viewScores(4);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // sort=1 -> sort by date
    // sort=2 -> sort by won
    // sort=3 -> sort by lose
    // sort=4 -> sort by tie
    private void viewScores(int sort){
        Cursor cursor = myDb.getSortedData(level,sort);
        if(cursor.getCount()==0)
            scoreText.setText("No matches played!");
        else {
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                buffer.append(cursor.getString(5) + "   ");
                buffer.append(cursor.getString(1) + "  ");
                buffer.append("Won: " + cursor.getString(2) + "  ");
                buffer.append("Lose: " + cursor.getString(3) + "  ");
                buffer.append("Tie: " + cursor.getString(4) + "\n\n");
            }
            scoreText.setText(buffer.toString());
        }
    }
}