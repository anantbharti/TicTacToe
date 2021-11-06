package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    ImageView singleIcon,duoIcon;
    TextView singleText,duoText;
    LinearLayout singleLL,duoLL;
    Button startSingle,startDuo,cancel;
    EditText singleName,playerA,playerB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        myDb=new DatabaseHelper(this);
        setCancel();

        singleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleLL.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                singleIcon.setVisibility(View.GONE);
                singleText.setVisibility(View.GONE);
                duoIcon.setVisibility(View.GONE);
                duoText.setVisibility(View.GONE);
            }
        });

        duoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duoLL.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                singleIcon.setVisibility(View.GONE);
                singleText.setVisibility(View.GONE);
                duoIcon.setVisibility(View.GONE);
                duoText.setVisibility(View.GONE);
//                startActivity(new Intent(MainActivity.this,DuoGame.class));
            }
        });

        startSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=singleName.getText().toString();
                if(name.isEmpty())
                    name="Player";
                final String[] levels={"Newbie","Pupil","Specialist","Expert"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select level");
                String finalName = name;
                builder.setSingleChoiceItems(levels, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String lev=levels[i];
                        Intent intent=new Intent(MainActivity.this,Game.class);
                        intent.putExtra("Level",lev);
                        intent.putExtra("Name", finalName);
                        startActivity(intent);
                        setCancel();
                    }
                });
                builder.setCancelable(true);
                builder.show();
            }
        });

        startDuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p1=playerA.getText().toString();
                String p2=playerB.getText().toString();
                if(p1.isEmpty())
                    p1="Player1";
                if(p2.isEmpty())
                    p2="Player2";

                Intent intent=new Intent(MainActivity.this,DuoGame.class);
                intent.putExtra("Player1",p1);
                intent.putExtra("Player2", p2);
                startActivity(intent);
                setCancel();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCancel();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.single_scores:{
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select level");
                final String[] levels={"Newbie","Pupil","Specialist","Expert"};
                builder.setSingleChoiceItems(levels, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String lev=levels[i];
                        Intent intent=new Intent(MainActivity.this,Scores.class);
                        intent.putExtra("Level",lev);
                        startActivity(intent);
                        setCancel();
                    }
                });
                builder.setCancelable(true);
                builder.show();
//                startActivity(new Intent(MainActivity.this,Scores.class));
                break;
            }
            case R.id.duo_scores:{
                Cursor cursor = myDb.getAllData("DUO");
                if(cursor.getCount()==0)
                    Toast.makeText(MainActivity.this,"No matches played!",Toast.LENGTH_SHORT).show();
                else{
                    StringBuffer buffer = new StringBuffer();
                    while(cursor.moveToNext()){
                        buffer.append(cursor.getString(6)+"\n");
                        buffer.append(cursor.getString(1)+": "+cursor.getString(3)+"  ");
                        buffer.append(cursor.getString(2)+": "+cursor.getString(4)+"  ");
                        buffer.append("Tie: "+cursor.getString(5)+"\n\n");
                    }
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Duo Scoreboard");
                    builder.setMessage(buffer.toString());
                    builder.show();
                }
                break;
            }
            case R.id.reset_scores:{
                new AlertDialog.Builder(this)
                        .setMessage("Do you want to reset all the scores ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myDb.deleteAllData();
                                Toast.makeText(MainActivity.this,"Scores deleted",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setCancel(){
        // show
        singleIcon.setVisibility(View.VISIBLE);
        duoIcon.setVisibility(View.VISIBLE);
        singleText.setVisibility(View.VISIBLE);
        duoText.setVisibility(View.VISIBLE);

        // hide
        cancel.setVisibility(View.GONE);
        singleLL.setVisibility(View.GONE);
        duoLL.setVisibility(View.GONE);
    }

    void setView(){
        singleIcon=findViewById(R.id.single_icon);
        duoIcon=findViewById(R.id.duo_icon);
        duoIcon=findViewById(R.id.duo_icon);
        duoLL=findViewById(R.id.duo_ll);
        singleLL=findViewById(R.id.single_ll);
        startDuo=findViewById(R.id.start_duo);
        startSingle=findViewById(R.id.start_single);
        cancel=findViewById(R.id.cancel);
        singleName=findViewById(R.id.single_name);
        playerA=findViewById(R.id.player_A);
        playerB=findViewById(R.id.player_B);
        singleText=findViewById(R.id.single_text);
        duoText=findViewById(R.id.duo_text);
    }
}