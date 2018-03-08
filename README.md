# 2048游戏app



### 一、项目说明

- 2048简易app


### 二、思路和核心算法

- 简单思路
  - 做一个最基本的Activity，就是游戏的主界面，只要做好布局、触摸滑动事件的监听和相应的处理办法就能完成简单的游戏设计


- 布局


![layout](https://raw.githubusercontent.com/eeach520/Game2048/master/app/src/main/res/drawable/1.jpg)

- 整体是一个垂直的线性布局

  ```java
  <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:tools="http://schemas.android.com/tools"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:layout_marginBottom="10dp"
      android:layout_marginTop="10dp"
      android:orientation="vertical"
      tools:context=".MainActivity">
  ```

- 中间是一个网格布局(直接引入了一个类)

  ```java
   <FrameLayout
          android:id="@+id/gameContaner"
          android:layout_width="fill_parent"
          android:layout_height="0dp"
          android:layout_weight="1"
          android:background="@drawable/bj">

      <com.example.eeach.game2048.GameView
              android:id="@+id/gameView"
              android:layout_width="fill_parent"
              android:layout_height="fill_parent"


              ></com.example.eeach.game2048.GameView>

          <com.example.eeach.game2048.AnimLayer
              android:id="@+id/animation"
              android:layout_width="fill_parent"
              android:layout_height="fill_parent"></com.example.eeach.game2048.AnimLayer>
      </FrameLayout>
  ```

- 触摸事件的监听

  ```java
   btn2.setOnClickListener(new View.OnClickListener(){
              /**
               * Called when a view has been clicked.
               *
               * @param v The view that was clicked.
               */
              @Override
              public void onClick(View v) {

                  new AlertDialog.Builder(mainActivity).setTitle("你好").setMessage("确定要退出吗？").setPositiveButton("确定",new DialogInterface.OnClickListener() {
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
  ```

- 滑动事件的监听

  ```java
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
  ```

- 数字消除的处理办法（包括判断是否游戏结束）

  ```java
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
  ```

- 游戏刚开始以及每次滑动随机添加两张卡片

  ```java
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
  ```

- 游戏相关截图

  ![游戏截图](https://raw.githubusercontent.com/eeach520/Game2048/master/app/src/main/res/drawable/2.jpg)



### 三、体验办法



- 安卓手机下载`Game2048/app/app-release.apk`即可使用