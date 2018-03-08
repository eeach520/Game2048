package com.example.eeach.game2048;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    private Button btn1,btn2;
    private MediaPlayer mp1,mp2,mp3,mp4;
    private GameView gameView;
    private AnimLayer animation;
    public static  final String BEST_SCORE="bestScore";
    public MainActivity(){
        mainActivity=this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore=(TextView)findViewById(R.id.score);
        btn1=(Button)findViewById(R.id.again);
        btn2=(Button)findViewById(R.id.back);
        gameView=(GameView)findViewById(R.id.gameView);
        MaxScore=(TextView)findViewById(R.id.MaxScore);
        mp1=MediaPlayer.create(this,R.raw.begin);
        animation=(AnimLayer)findViewById(R.id.animation);
        startAmusic();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mainActivity).setTitle("你好,么么哒").
                        setMessage("确定要重新开始吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameView.startGame();
                        startAmusic();
                    }
                }).setNegativeButton("返回",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener(){
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(mainActivity).setTitle("你好，么么哒").setMessage("确定要退出吗？").setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                }).setNegativeButton("返回",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

    }
    private TextView tvScore,MaxScore;
    public void clearScore(){
        score=0;
        showScore();
    }
    public void showScore(){
        tvScore.setText(score+"");
    }
    public void addScore(int s){
        score+=s;
        showScore();
        int maxScore=Math.max(score,getBestScore());
        saveBestScore(maxScore);
        showBestScore(maxScore);
    }
    public AnimLayer getAnimation(){
        return animation;
    }
    public int getBestScore(){
        return getPreferences(MODE_PRIVATE).getInt(BEST_SCORE, 0);
    }
    public  void saveBestScore(int s){
        SharedPreferences.Editor e = getPreferences(MODE_PRIVATE).edit();
        e.putInt(BEST_SCORE, s);
        e.commit();
    }
    public void showBestScore(int s){
        MaxScore.setText(s+"");
    }
    private int score=0;
    private static MainActivity mainActivity=null;

    public static MainActivity getMainActivity() {
        return mainActivity;
    }
    public void onSet(){
        mp1=MediaPlayer.create(this,R.raw.begin);
        mp2=MediaPlayer.create(this,R.raw.great);
        mp3=MediaPlayer.create(this,R.raw.move);

        mp4=MediaPlayer.create(this,R.raw.over);
    }
    public void startAmusic(){
        if(mp1!=null){
            boolean is=mp1.isPlaying();
            if(is==true){
                mp1.stop();

                mp1.release(); mp1=null;}
            mp1=MediaPlayer.create(this,R.raw.begin);
        mp1.start();
        mp1.setLooping(true);}
    }
    public void startBmusic(){
        if(mp2!=null){
        mp2.stop();

        mp2.release();mp2=null;}

        mp2=MediaPlayer.create(this,R.raw.great);
        mp2.start();
    }
    public void startCmusic(){
        if(mp3!=null){
        mp3.stop();

        mp3.release();mp3=null;}
        mp3=MediaPlayer.create(this,R.raw.move);
        mp3.start();
    }
    public void startDmusic(){
        if(mp4!=null){
            mp4.stop();

        mp4.release();mp4=null;}
        mp4=MediaPlayer.create(this,R.raw.over);
        mp4.start();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
