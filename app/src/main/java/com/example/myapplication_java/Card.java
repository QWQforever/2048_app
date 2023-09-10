package com.example.myapplication_java;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {
    public Card(Context context) {
        super(context);
        label=new TextView(getContext());
        //设置颜色
        label.setGravity(Gravity.CENTER);
        label.setTextColor(Color.WHITE);
        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(30,30,0,0);
        addView(label,lp);
        setNum(0);
    }

    private int num=0;

    public int getNum(){
        return num;
    }

    public void setNum(int num){
        this.num=num;
        if(num<=0){
            label.setText("");
        }else
            label.setText(num+"");
        switch (num){
            case 0: label.setBackgroundColor(0x5579807f); label.setTextSize(32);break;
            case 2: label.setBackgroundColor(0xED0EBAA9); label.setTextSize(32);break;
            case 4: label.setBackgroundColor(0xED10A2F1); label.setTextSize(32);break;
            case 8: label.setBackgroundColor(0xFF056F9F); label.setTextSize(32);break;
            case 16: label.setBackgroundColor(0xFF9C27B0); label.setTextSize(32);break;
            case 32: label.setBackgroundColor(0xFFFF9800); label.setTextSize(32);break;
            case 64: label.setBackgroundColor(0xFFFFC107); label.setTextSize(32);break;
            case 128: label.setBackgroundColor(0xFFCDDC39); label.setTextSize(27);;break;
            case 256: label.setBackgroundColor(0xFF8BC34A); label.setTextSize(27);break;
            case 512: label.setBackgroundColor(0xFF673AB7); label.setTextSize(27);break;
            case 1024: label.setBackgroundColor(0xFFE91E63); label.setTextSize(22);break;
            case 2048: label.setBackgroundColor(0xFFF44336); label.setTextSize(22);break;
            case 4096: label.setBackgroundColor(0xFF3F51B5); label.setTextSize(22);break;
            case 8192: label.setBackgroundColor(0xFFFFEB3B); label.setTextSize(22);break;
            case 16384: label.setBackgroundColor(0xFFCDDC39); label.setTextSize(17);break;
            case 32768: label.setBackgroundColor(0xFF8BC34A); label.setTextSize(17);break;
        }

    }


    public boolean equals(Card o){
        return getNum()==o.getNum();
    }

    public TextView getLabel() {
        return label;
    }

    public void chosen_change(){
        GradientDrawable drawable=new GradientDrawable();
        drawable.setStroke(5,Color.RED);
        drawable.setColor(Color.GREEN);
        switch (num){
            case 0: drawable.setColor(0x5579807f);break;
            case 2: drawable.setColor(0xED0EBAA9); break;
            case 4: drawable.setColor(0xED10A2F1);break;
            case 8: drawable.setColor(0xFF056F9F); break;
            case 16: drawable.setColor(0xFF9C27B0); break;
            case 32: drawable.setColor(0xFFFF9800); break;
            case 64: drawable.setColor(0xFFFFC107); break;
            case 128: drawable.setColor(0xFFCDDC39);break;
            case 256: drawable.setColor(0xFF8BC34A); break;
            case 512: drawable.setColor(0xFF673AB7); break;
            case 1024: drawable.setColor(0xFFE91E63); break;
            case 2048: drawable.setColor(0xFFF44336); break;
            case 4096: drawable.setColor(0xFF3F51B5); break;
            case 8192: drawable.setColor(0xFFFFEB3B); break;
            case 16384: drawable.setColor(0xFFCDDC39);break;
            case 32768: drawable.setColor(0xFF8BC34A); break;
        }
        label.setBackground(drawable);
    }

    private TextView label;
}
