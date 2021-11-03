package com.example.sqlite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity {

    String level;
    ImageView[][] img= new ImageView[3][3];
    Button replay;
    char[][] a= new char[3][3];
    int turns;
    boolean mid,corner;
//    boolean botFirst;
    TextView gameLevel,playerName,yourScore,botScore,tieText;
    int won=0,lose=0,tie=0;
    String name="Anant";
    DatabaseHelper myDb;
    int id=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
//        ActionBar actionBar=getSupportActionBar();
//        actionBar.hide();
        myDb=new DatabaseHelper(this);

        setViews();
        level=getIntent().getExtras().getString("Level");
        name=getIntent().getExtras().getString("Name");
        gameLevel.setText(level);
        playerName.setText(name);
//        botFirst=false;

        turns=0;
        mid=false;
        corner=false;
        replay.setVisibility(View.GONE);

        // 1   00
        img[0][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turns++;
                a[0][0]='X';
                img[0][0].setImageResource(R.mipmap.x_icon);
//                ImageViewAnimatedChange(Game.this,img[0][0],R.mipmap.x_icon);
                img[0][0].setClickable(false);
                proceed();
            }
        });
        // 2   01
        img[0][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turns++;
                a[0][1]='X';
                img[0][1].setImageResource(R.mipmap.x_icon);
//                ImageViewAnimatedChange(Game.this,img[0][1],R.mipmap.x_icon);
                img[0][1].setClickable(false);
                proceed();
            }
        });
        // 3   02
        img[0][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turns++;
                a[0][2]='X';
                img[0][2].setImageResource(R.mipmap.x_icon);
//                ImageViewAnimatedChange(Game.this,img[0][2],R.mipmap.x_icon);
                img[0][2].setClickable(false);
                proceed();
            }
        });
        // 4   10
        img[1][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turns++;
                a[1][0]='X';
                img[1][0].setImageResource(R.mipmap.x_icon);
//                ImageViewAnimatedChange(Game.this,img[1][0],R.mipmap.x_icon);
                img[1][0].setClickable(false);
                proceed();
            }
        });
        // 5   11
        img[1][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turns++;
                a[1][1]='X';
                img[1][1].setImageResource(R.mipmap.x_icon);
//                ImageViewAnimatedChange(Game.this,img[1][1],R.mipmap.x_icon);
                img[1][1].setClickable(false);
                proceed();
            }
        });
        // 6   12
        img[1][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turns++;
                a[1][2]='X';
                img[1][2].setImageResource(R.mipmap.x_icon);
//                ImageViewAnimatedChange(Game.this,img[1][2],R.mipmap.x_icon);
                img[1][2].setClickable(false);
                proceed();
            }
        });
        // 7  20
        img[2][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turns++;
                a[2][0]='X';
                img[2][0].setImageResource(R.mipmap.x_icon);
//                ImageViewAnimatedChange(Game.this,img[2][0],R.mipmap.x_icon);
                img[2][0].setClickable(false);
                proceed();
            }
        });
        // 8   21
        img[2][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turns++;
                a[2][1]='X';
                img[2][1].setImageResource(R.mipmap.x_icon);
                img[2][1].setClickable(false);
//                ImageViewAnimatedChange(Game.this,img[2][1],R.mipmap.x_icon);
                proceed();
            }
        });
        // 9   22
        img[2][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turns++;
                a[2][2]='X';
                img[2][2].setImageResource(R.mipmap.x_icon);
//                ImageViewAnimatedChange(Game.this,img[2][2],R.mipmap.x_icon);
                img[2][2].setClickable(false);
                proceed();
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
                        Game.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    private void proceed(){
        int winner=getWinner();
//        Toast.makeText(Game.this,"winner = "+winner,Toast.LENGTH_SHORT).show();
        if(winner!=-1){
            endMatch(winner);
            return;
        }
        else {
            if(level.equals("Newbie"))
               easy();
            else if(level.equals("Pupil"))
               medium();
            else
               hard();
            turns++;
            winner = getWinner();
            if (winner != -1) {
                endMatch(winner);
                return;
            }
        }
    }

    private void hard(){
//        if(botFirst==0){
//            playerFirstTurn();
//        }
        playerFirstTurn();
    }

    private void playerFirstTurn(){
        if(turns==1){
            mid=false;corner=false;
            if(a[1][1]=='X')mid=true;
            else if(a[0][0]=='X'||a[0][2]=='X'||a[2][0]=='X'||a[2][2]=='X') corner=true;
            if(mid){
                a[2][0]='O';
//                img[2][0].setImageResource(R.mipmap.o_emoji);
                ImageViewAnimatedChange(Game.this,img[2][0],R.mipmap.o_emoji);
                img[2][0].setClickable(false);
            }
            else if(corner){
                a[1][1]='O';
//                img[1][1].setImageResource(R.mipmap.o_emoji);
                ImageViewAnimatedChange(Game.this,img[1][1],R.mipmap.o_emoji);
                img[1][1].setClickable(false);
            }
            else{
                if(level.equals("Expert")){
                    if(a[0][1]=='X'||a[1][2]=='X'){
                        a[0][2]='O';
//                        img[0][2].setImageResource(R.mipmap.o_emoji);
                        ImageViewAnimatedChange(Game.this,img[0][2],R.mipmap.o_emoji);
                        img[0][2].setClickable(false);
                    }
                    else{
                        a[2][0]='O';
//                        img[2][0].setImageResource(R.mipmap.o_emoji);
                        ImageViewAnimatedChange(Game.this,img[2][0],R.mipmap.o_emoji);
                        img[2][0].setClickable(false);
                    }
                }
                else
                    easy();
            }
        }
        else if(turns==3){
            if(mid){
                if(a[0][2]=='X'){
                    a[2][2]='O';
//                    img[2][2].setImageResource(R.mipmap.o_emoji);
                    ImageViewAnimatedChange(Game.this,img[2][2],R.mipmap.o_emoji);
                    img[2][2].setClickable(false);
                }
                else
                    medium();
            }
            else if(corner){
                if(a[0][0]==a[2][2]&&a[0][0]=='X'){
                    a[1][2]='O';
//                    img[1][2].setImageResource(R.mipmap.o_emoji);
                    ImageViewAnimatedChange(Game.this,img[1][2],R.mipmap.o_emoji);
                    img[1][2].setClickable(false);
                }
                else if(a[0][2]==a[2][0]&&a[0][2]=='X'){
                    a[1][0]='O';
//                    img[1][0].setImageResource(R.mipmap.o_emoji);
                    ImageViewAnimatedChange(Game.this,img[1][0],R.mipmap.o_emoji);
                    img[1][0].setClickable(false);
                }
                else
                    medium();
            }
            else {
                if(level.equals("Expert")){
                    if((a[1][0]=='X'&&a[0][1]=='X')||(a[0][1]=='X'&&a[1][2]=='X')||(a[1][2]=='X'&&a[2][1]=='X')||(a[2][1]=='X'&&a[1][0]=='X')){
                        a[1][1]='O';
//                        img[1][1].setImageResource(R.mipmap.o_emoji);
                        ImageViewAnimatedChange(Game.this,img[1][1],R.mipmap.o_emoji);
                        img[1][1].setClickable(false);
                    }
                    else
                        medium();
                }
                else
                medium();
            }
        }
        else
            medium();
    }

    private void medium(){
         boolean done = getPos('O');
         if(done==false){
             done = getPos('X');
         }
         if(done==false)
             easy();
    }

    private void easy(){
        Random rand = new Random();
        // Generate random integers in range 0 to 2
        int x = rand.nextInt(3);
        int y = rand.nextInt(3);
        if(a[x][y]==0){
            // make a random turn
            a[x][y]='O';
//            img[x][y].setImageResource(R.mipmap.o_emoji);
            ImageViewAnimatedChange(Game.this,img[x][y],R.mipmap.o_emoji);
            img[x][y].setClickable(false);
        }
        else {
            // make turn to next empty box
            boolean done = false;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (a[i][j] == 0) {
                        a[i][j] = 'O';
//                        img[i][j].setImageResource(R.mipmap.o_emoji);
                        ImageViewAnimatedChange(Game.this,img[i][j],R.mipmap.o_emoji);
                        img[i][j].setClickable(false);
                        done = true;
                        break;
                    }
                }
                if (done)
                    break;
            }
        }
    }

    private boolean getPos(char c){
        boolean gotPos=false;
        int x=-1,y=-1;
        int req=0;
        for(int i=0;i<3;i++){
            req=0;
            x=-1;y=-1;
            for(int j=0;j<3;j++){
                if(a[i][j]==c)
                    req++;
                else if(a[i][j]==0){
                    x=i;y=j;
                }
            }
            //check if got pos
            if(req==2&&x!=-1&&y!=-1){
                gotPos=true;
                break;
            }
            req=0;
            x=-1;y=-1;
            for(int j=0;j<3;j++){
                if(a[j][i]==c)
                    req++;
                else if(a[j][i]==0){
                    x=j;y=i;
                }
            }
            // check if got pos
            if(req==2&&x!=-1&&y!=-1){
                gotPos=true;
                break;
            }
        }
        if(gotPos==true){
            a[x][y]='O';
//            img[x][y].setImageResource(R.mipmap.o_emoji);
            ImageViewAnimatedChange(Game.this,img[x][y],R.mipmap.o_emoji);
            img[x][y].setClickable(false);
        }
        else {
            req=0;
            x=-1;y=-1;
            if(a[0][0]==c)
                req++;
            else if(a[0][0]==0){
                x=0;y=0;
            }
            if(a[1][1]==c)
                req++;
            else if(a[1][1]==0){
                x=1;y=1;
            }
            if(a[2][2]==c)
                req++;
            else if(a[2][2]==0){
                x=2;y=2;
            }
            // check if got pos
            if(req==2&&x!=-1&&y!=-1){
                gotPos=true;
                a[x][y]='O';
//                img[x][y].setImageResource(R.mipmap.o_emoji);
                ImageViewAnimatedChange(Game.this,img[x][y],R.mipmap.o_emoji);
                img[x][y].setClickable(false);
            }

            if(gotPos==false){
                req=0;
                x=-1;y=-1;
                if(a[0][2]==c)
                    req++;
                else if(a[0][2]==0){
                    x=0;y=2;
                }
                if(a[1][1]==c)
                    req++;
                else if(a[1][1]==0){
                    x=1;y=1;
                }
                if(a[2][0]==c)
                    req++;
                else if(a[2][0]==0){
                    x=2;y=0;
                }
            }

            // check if got pos
            if(req==2&&x!=-1&&y!=-1){
                gotPos=true;
                a[x][y]='O';
//                img[x][y].setImageResource(R.mipmap.o_emoji);
                ImageViewAnimatedChange(Game.this,img[x][y],R.mipmap.o_emoji);
                img[x][y].setClickable(false);
            }
        }
        return gotPos;
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
            // player won
            won++;
            Toast.makeText(Game.this,"You won "+won,Toast.LENGTH_SHORT).show();
        }
        else if(winner==2){
            // computer won
            lose++;
            Toast.makeText(Game.this,"You lost",Toast.LENGTH_SHORT).show();
        }
        else{
            // match tied
            tie++;
            Toast.makeText(Game.this,"Match draw",Toast.LENGTH_SHORT).show();
        }
        botScore.setText(String.valueOf(lose));
        tieText.setText(String.valueOf(tie));
        yourScore.setText(String.valueOf(won));
        saveScore();
        replay.setVisibility(View.VISIBLE);
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                img[i][j].setClickable(false);
    }

    private  void saveScore(){
        if(id==-1) {
            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            int inserted=myDb.insertData(level,name,won,lose,tie,date);
            if(inserted==-1)
                Toast.makeText(Game.this,"Error in saving score!",Toast.LENGTH_SHORT).show();
            else
                id=inserted;
//            Toast.makeText(Game.this,"id="+id,Toast.LENGTH_SHORT).show();
        }
        else{
            boolean updated=myDb.updateData(level,String.valueOf(id),won,lose,tie);
//            if(updated)
//                Toast.makeText(Game.this,"Updated",Toast.LENGTH_SHORT).show();
//            else
//                Toast.makeText(Game.this,"Error!",Toast.LENGTH_SHORT).show();
        }
    }
    public static void ImageViewAnimatedChange(Context c, final ImageView v, int mipmap) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                v.setImageResource(mipmap);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

    private void setReplay(){
        a=new char[3][3];
        turns=0;
        mid=false;
        corner=false;
        replay.setVisibility(View.GONE);
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++){
                img[i][j].setImageResource(0);
                img[i][j].setClickable(true);
            }
    }

    private void setViews(){
        img[0][0]=findViewById(R.id.img_11);
        img[0][1]=findViewById(R.id.img_12);
        img[0][2]=findViewById(R.id.img_13);
        img[1][0]=findViewById(R.id.img_21);
        img[1][1]=findViewById(R.id.img_22);
        img[1][2]=findViewById(R.id.img_23);
        img[2][0]=findViewById(R.id.img_31);
        img[2][1]=findViewById(R.id.img_32);
        img[2][2]=findViewById(R.id.img_33);
        gameLevel=findViewById(R.id.level);
        replay=findViewById(R.id.replay_btn);
        playerName=findViewById(R.id.player_2);
        botScore=findViewById(R.id.score_1);
        yourScore=findViewById(R.id.score_2);
        tieText=findViewById(R.id.draw_count);
    }
}