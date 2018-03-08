package com.example.eeach.game2048;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by eeach on 2017/4/21.
 */
public class Card extends FrameLayout {
    public Card(Context context) {
        super(context);
        label=new TextView(getContext());
        label.setTextSize(32);
        label.setGravity(Gravity.CENTER);
        LayoutParams lp=new LayoutParams(-1,-1);
        lp.setMargins(15,15,0,0);
        addView(label, lp);
        setNum(0);
    }
    private int num=0;
    public int getNum() {
        return num;
    }

    public TextView getLabel(){
        return label;
    }
    public void setNum(int num) {
        this.num = num;
        if(num<=0){
            label.setText("");
        }else{
        label.setText(num+"");}
        switch (num){
            case 0:
                label.setBackgroundColor(0x72eff3d1);break;
            case 2:
                label.setBackgroundColor(0xffeee4da);break;
            case 4:
                label.setBackgroundColor(0xffede0c8);break;
            case 8:
                label.setBackgroundColor(0xfff2b179);break;
            case 16:
                label.setBackgroundColor(0xfff59563);break;
            case 32:
                label.setBackgroundColor(0xfff67c5f);break;
            case 64:
                label.setBackgroundColor(0xfff65e3b);break;
            case 128:
                label.setBackgroundColor(0xffedcf72);break;
            case 256:
                label.setBackgroundColor(0xffff60c0);break;
            case 512:
                label.setBackgroundColor(0xff606020);break;
            case 1024:
                label.setBackgroundColor(0xff66ff66);break;
            case 2048:
                label.setBackgroundColor(0xff3366ff);break;
            case 4096:
                label.setBackgroundColor(0xff9933ff);break;
                default:
                    label.setBackgroundColor(0xff1a45fe);break;
        }
    }
    public boolean equals(Card o) {
        return getNum()==o.getNum();
    }

    @Override
    public int hashCode() {
        int result = num;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        return result;
    }

    private TextView label;
}
