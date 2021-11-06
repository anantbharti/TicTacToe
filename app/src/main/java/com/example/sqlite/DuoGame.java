package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DuoGame extends AppCompatActivity {

    TextView player1,player2,score1,score2,tieText,turnText,winnerText;
    ImageView[][] img= new ImageView[3][3];
    Button replay;
    char[][] a= new char[3][3];
    DatabaseHelper myDb;
    int id=-1;
    int turns;
    int scoreA=0,scoreB=0,tie=0;
    String A="Anant";
    String B="Happy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duo_game);
        setViews();
        myDb=new DatabaseHelper(this);
        replay.setVisibility(View.GONE);
        turns=0;
        A=getIntent().getExtras().getString("Player1");
        B=getIntent().getExtras().getString("Player2");
        player1.setText(A);
        player2.setText(B);
        turnText.setText(A+"'s turn");
        winnerText.setVisibility(View.GONE);

        // 1   00
        img[0][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTurn(0,0);
            }
        });
        // 2   01
        img[0][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTurn(0,1);
            }
        });
        // 3   02
        img[0][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTurn(0,2);
            }
        });
        // 4   10
        img[1][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTurn(1,0);
            }
        });
        // 5   11
        img[1][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTurn(1,1);
            }
        });
        // 6   12
        img[1][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTurn(1,2);
            }
        });
        // 7  20
        img[2][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTurn(2,0);
            }
        });
        // 8   21
        img[2][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTurn(2,1);
            }
        });
        // 9   22
        img[2][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTurn(2,2);
            }
        });

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setReplay();
            }
        });

        // code here

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Do you want to end the game ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DuoGame.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void makeTurn(int i,int j){
        turns++;
        if(turns%2==1) {
            a[i][j] = 'X';
            img[i][j].setImageResource(R.mipmap.x_icon);
            turnText.setText(B+"'s turn");
        }
        else{
            a[i][j]='O';
            img[i][j].setImageResource(R.mipmap.o_emoji);
            turnText.setText(A+"'s turn");
        }
        img[i][j].setClickable(false);
        proceed();
    }

    private void setReplay(){
        a=new char[3][3];
        replay.setVisibility(View.GONE);
        winnerText.setVisibility(View.GONE);
        turns=0;
        turnText.setText(A+"'s turn");
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++){
                img[i][j].setImageResource(0);
                img[i][j].setClickable(true);
            }
    }

    private void proceed(){
        int winner=getWinner();
//        Toast.makeText(Game.this,"winner = "+winner,Toast.LENGTH_SHORT).show();
        if(winner!=-1){
            endMatch(winner);
            return;
        }
    }

    private int getWinner(){
        // case 1 => 00=01=02
        if(a[0][1]==a[0][2]&&a[0][1]==a[0][0]&&a[0][1]!=0)
        {
            if(a[0][0]=='X')
                return 1;
            else if(a[0][0]=='O')
                return 2;
        }
        // case 2 => 10=11=12
        if(a[1][0]==a[1][1]&&a[1][1]==a[1][2]&&a[1][0]!=0)
        {
            if(a[1][0]=='X')
                return 1;
            else if(a[1][0]=='O')
                return 2;
        }
        // case 3 => 20=21=22
        if(a[2][0]==a[2][1]&&a[2][1]==a[2][2]&&a[2][0]!=0)
        {
            if(a[2][0]=='X')
                return 1;
            else if(a[2][0]=='O')
                return 2;
        }
        // case 4 => 00=10=20
        if(a[0][0]==a[1][0]&&a[1][0]==a[2][0]&&a[0][0]!=0)
        {
            if(a[0][0]=='X')
                return 1;
            else if(a[0][0]=='O')
                return 2;
        }
        // case 5 => 01=11=21
        if(a[0][1]==a[1][1]&&a[1][1]==a[2][1]&&a[0][1]!=0)
        {
            if(a[0][1]=='X')
                return 1;
            else if(a[0][1]=='O')
                return 2;
        }
        // case 6 => 02=12=22
        if(a[0][2]==a[1][2]&&a[1][2]==a[2][2]&&a[0][2]!=0)
        {
            if(a[0][2]=='X')
                return 1;
            else if(a[0][2]=='O')
                return 2;
        }
        // case 7 => 00=11=22
        if(a[0][0]==a[1][1]&&a[2][2]==a[1][1]&&a[0][0]!=0)
        {
            if(a[0][0]=='X')
                return 1;
            else if(a[0][0]=='O')
                return 2;
        }
        // case 8 => 02=11=20
        if(a[0][2]==a[1][1]&&a[1][1]==a[2][0]&&a[0][2]!=0)
        {
            if(a[0][2]=='X')
                return 1;
            else if(a[0][2]=='O')
                return 2;
        }
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(a[i][j]==0)
                    return -1;
        return 0;
    }

    private void endMatch(int winner){
        if(winner==1){
            // player A won
            scoreA++;
//            Toast.makeText(DuoGame.this,A+" won",Toast.LENGTH_SHORT).show();
            winnerText.setText(A+" won");
        }
        else if(winner==2){
            // player B won
            scoreB++;
//            Toast.makeText(DuoGame.this,B+" won",Toast.LENGTH_SHORT).show();
            winnerText.setText(B+" won");
        }
        else{
            // match tied
            tie++;
//            Toast.makeText(DuoGame.this,"Match draw",Toast.LENGTH_SHORT).show();
            winnerText.setText("match draw");
        }
        score1.setText(String.valueOf(scoreA));
        score2.setText(String.valueOf(scoreB));
        tieText.setText(String.valueOf(tie));
        saveScore();
        winnerText.setVisibility(View.VISIBLE);
        replay.setVisibility(View.VISIBLE);
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                img[i][j].setClickable(false);
    }

    private  void saveScore(){
        if(id==-1) {
            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//            Toast.makeText(DuoGame.this,date,Toast.LENGTH_SHORT).show();
            int inserted=myDb.insertDuoData(A,B,scoreA,scoreB,tie,date);
            if(inserted==-1)
                Toast.makeText(DuoGame.this,"Error in saving score!",Toast.LENGTH_SHORT).show();
            else
                id=inserted;
//            Toast.makeText(Game.this,"id="+id,Toast.LENGTH_SHORT).show();
        }
        else{
            boolean updated=myDb.updateDuoData(String.valueOf(id),scoreA,scoreB,tie);
//            if(updated)
//                Toast.makeText(Game.this,"Updated",Toast.LENGTH_SHORT).show();
//            else
//                Toast.makeText(Game.this,"Error!",Toast.LENGTH_SHORT).show();
        }
    }

    private void setViews(){
        img[0][0]=findViewById(R.id.img_1_1);
        img[0][1]=findViewById(R.id.img_1_2);
        img[0][2]=findViewById(R.id.img_1_3);
        img[1][0]=findViewById(R.id.img_2_1);
        img[1][1]=findViewById(R.id.img_2_2);
        img[1][2]=findViewById(R.id.img_2_3);
        img[2][0]=findViewById(R.id.img_3_1);
        img[2][1]=findViewById(R.id.img_3_2);
        img[2][2]=findViewById(R.id.img_3_3);
        replay=findViewById(R.id.replay_btn);
        player1=findViewById(R.id.player_1);
        player2=findViewById(R.id.player_2);
        score1=findViewById(R.id.score_1);
        score2=findViewById(R.id.score_2);
        tieText=findViewById(R.id.draw_count);
        turnText=findViewById(R.id.turn);
        winnerText=findViewById(R.id.winner_text);
    }
}