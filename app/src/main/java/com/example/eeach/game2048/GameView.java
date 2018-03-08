package com.example.eeach.game2048;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eeach on 2017/4/21.
 */
public class GameView extends GridLayout {

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initGameView();
    }

    public GameView(Context context) {

        super(context);
        initGameView();
    }
    private void initGameView(){

        setColumnCount(4);
        setOnTouchListener(new View.OnTouchListener(){
            /**
             * Called when a touch event is dispatched to a view. This allows listeners to
             * get a chance to respond before the target view.
             *
             * @param v     The view the touch event has been dispatched to.
             * @param event The MotionEvent object containing full information about
             *              the event.
             * @return True if the listener has consumed the event, false otherwise.
             */
            private float startX,startY,offsetX,offsetY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX=event.getX();
                        startY=event.getY();
                    case MotionEvent.ACTION_UP:
                        offsetX=event.getX()-startX;
                        offsetY=event.getY()-startY;

                        if(Math.abs(offsetX)>Math.abs(offsetY)){
                            if(offsetX<-5){
                                swipeLeft();
                            }else if(offsetX>5){
                                swipeRight();
                            }
                        }
                        else{
                            if(offsetY<-5){
                                swipeUp();
                            }else if(offsetY>5){
                                swipeDown();
                            }
                        }
                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth=(Math.min(w,h)-10)/4;
        Config.CARD_WIDTH=cardWidth=(Math.min(w,h)-10)/4;
        addCard(cardWidth,cardWidth);
        startGame();
    }

    private void addCard(int cardWidth,int cardHeight){
        Card c;
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
            c=new Card(getContext());
                c.setNum(0);
                addView(c,cardWidth,cardHeight);
                cardsMap[x][y]=c;
        }}
    }

    public void startGame(){
        MainActivity.getMainActivity().clearScore();
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                cardsMap[x][y].setNum(0);
            }
    }
        addRandomNum();
        addRandomNum();
    }
    private void addRandomNum(){

        emptypoint.clear();
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
               if(cardsMap[x][y].getNum()<=0){
                   emptypoint.add(new Point(x,y));
               }}}
        if(emptypoint.size()>0){
                Point p=emptypoint.remove((int)(Math.random()*emptypoint.size()));
                cardsMap[p.x][p.y].setNum((Math.random()>0.1? 2:4));
            MainActivity.getMainActivity().getAnimation().createScaleTo1(cardsMap[p.x][p.y]);}

    }

    private void swipeLeft(){
        boolean merge=false;
        boolean merge1=false,merge2=false;
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                for(int x1=x+1;x1<4;x1++){
                    if(cardsMap[x1][y].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            MainActivity.getMainActivity().getAnimation().createMoveAnim(cardsMap[x1][y],cardsMap[x][y], x1, x, y, y);
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            merge1=true;
                            x--;
                          merge=true;
                        }
                        else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                            MainActivity.getMainActivity().getAnimation().createMoveAnim(cardsMap[x1][y],cardsMap[x][y], x1, x, y, y);
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                          merge=true;
                            merge2=true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge==true){
            addRandomNum();
            checkCompelete();
        }
        if(merge2==true){
            MainActivity.getMainActivity().startBmusic();
        }
        else if(merge1==true){
            MainActivity.getMainActivity().startCmusic();
        }
    }
    private void swipeRight(){
        boolean merge =false;
        boolean merge1=false,merge2=false;
        for(int y=0;y<4;y++){
            for(int x=3;x>=0;x--){
                for(int x1=x-1;x1>=0;x1--){
                    if(cardsMap[x1][y].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            MainActivity.getMainActivity().getAnimation().createMoveAnim(cardsMap[x1][y],cardsMap[x][y], x1, x, y, y);

                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            merge1=true;
                            x++;
                           merge=true;
                        }
                        else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                            MainActivity.getMainActivity().getAnimation().createMoveAnim(cardsMap[x1][y],cardsMap[x][y], x1, x, y, y);
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge=true;
                            merge2=true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge==true){
            addRandomNum();
            checkCompelete();
        }
        if(merge2==true){
            MainActivity.getMainActivity().startBmusic();
        }
        else if(merge1==true){
            MainActivity.getMainActivity().startCmusic();
        }
    }
    private void swipeUp(){
        boolean merge1=false,merge2=false;
        boolean merge=false;
        for(int x=0;x<4;x++){
            for(int y=0;y<4;y++){
                for(int y1=y+1;y1<4;y1++){
                    if(cardsMap[x][y1].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            MainActivity.getMainActivity().getAnimation().createMoveAnim(cardsMap[x][y1],cardsMap[x][y], x, x, y1, y);
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                           merge1=true;
                            y--;
                            merge=true;
                        }
                        else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                            MainActivity.getMainActivity().getAnimation().createMoveAnim(cardsMap[x][y1],cardsMap[x][y], x, x, y1, y);
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge2=true;
                            merge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge==true){
            addRandomNum();
            checkCompelete();
        }
        if(merge2==true){
            MainActivity.getMainActivity().startBmusic();
        }
        else if(merge1==true){
            MainActivity.getMainActivity().startCmusic();
        }
    }
    private void swipeDown(){
        boolean merge1=false,merge2=false;
        boolean merge=false;
        for(int x=0;x<4;x++){
            for(int y=3;y>=0;y--){
                for(int y1=y-1;y1>=0;y1--){
                    if(cardsMap[x][y1].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            MainActivity.getMainActivity().getAnimation().createMoveAnim(cardsMap[x][y1],cardsMap[x][y], x, x, y1, y);
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            merge1=true;
                            y++;
                            merge=true;
                        }
                        else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                            MainActivity.getMainActivity().getAnimation().createMoveAnim(cardsMap[x][y1],cardsMap[x][y], x, x, y1, y);
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge2=true;
                            merge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge==true){
            addRandomNum();
            checkCompelete();
        }
        if(merge2==true){
            MainActivity.getMainActivity().startBmusic();
        }
        else if(merge1==true){
            MainActivity.getMainActivity().startCmusic();
        }
    }
    private void checkCompelete(){
        boolean compelete=true;
        ALL:
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                if(cardsMap[x][y].getNum()==0||(x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
                        (x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
                        (y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
                        (y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))){
                    compelete=false;
                    break ALL;
                }
            }
        }
        if(compelete){
            MainActivity.getMainActivity().startDmusic();
            new AlertDialog.Builder(getContext()).setTitle("你好,么么哒").
                    setMessage("游戏结束了，哈哈").setPositiveButton("重新开始",new DialogInterface.OnClickListener(){
                /**
                 * This method will be invoked when a button in the dialog is clicked.
                 *
                 * @param dialog The dialog that received the click.
                 * @param which  The button that was clicked (e.g.
                 *               {@link android.content.DialogInterface#BUTTON1}) or the position
                 */
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                    MainActivity.getMainActivity().startAmusic();
                }
            }).show();
        }
    }
    private Card[][] cardsMap=new Card[4][4];
    private List<Point> emptypoint=new ArrayList<>();
    private static GameView gameView=null;
    public static GameView getGameView(){
        return gameView;
    }


}
