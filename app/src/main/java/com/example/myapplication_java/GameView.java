package com.example.myapplication_java;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.*;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

public class GameView  extends GridLayout {

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }


    private void initGameView() {
        setColumnCount(4);
        setBackgroundColor(0xffb5e6e6);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int carwidth= (int) (72 * scale + 0.5f);

        TheDataDBHelper theDataDBHelper=TheDataDBHelper.getInstance(getContext(),1);
        SQLiteDatabase database=theDataDBHelper.openWriteLink();
        theDataDBHelper.onCreate(database);
        theDataDBHelper.openReadLink();
        System.out.println("-----2");
        String[] all=theDataDBHelper.queryByString().split("_");
        String top=all[1];
        top_score=Integer.parseInt(top);
        top_score_last=Integer.parseInt(top);
        now_scores=all[0];
        String cards=all[2];
        cardString=cards.split(",");
        put_count=Integer.parseInt(all[3]);
        System.out.println(put_count);
        user_id=all[4];
        System.out.println(user_id +"   "+"abb");
        if (all[5].equals("sssssss")){
            user_name="";
        }
        else
            user_name=all[5];
        remain_time=Integer.parseInt(all[6]);
        System.out.println(user_name +"   "+"abb");

        addCards(carwidth,carwidth);

        System.out.println("qqqq");

        setOnTouchListener(new View.OnTouchListener() {

            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        if (event.getX()==startX&&switch_is_cheat!=0){
                            System.out.println("-----------------------------");
                            System.out.println((int)(event.getX()/ scale + 0.5f)/70);
                            System.out.println((int)(event.getY()/ scale + 0.5f)/70);
                            int map_x=(int)(event.getX()/ scale + 0.5f)/70;
                            int map_y=(int)(event.getY()/ scale + 0.5f)/70;
                            if(card_map_x!=-1){
                                cardsMap[card_map_x][card_map_y].setNum( cardsMap[card_map_x][card_map_y].getNum());
                            }
                            if (card_map_x==map_x&&card_map_y==map_y){
                                cardsMap[card_map_x][card_map_y].setNum( cardsMap[card_map_x][card_map_y].getNum());
                                card_map_x=-1;
                                card_map_y=-1;
                                cardMap_change=cardMap_change_last;
                            }
                            else {
//                                System.out.println(cardsMap[map_x][map_y].getNum());
                                card_map_x=map_x;
                                card_map_y=map_y;
                                cardMap_change=cardsMap[map_x][map_y];
                                cardsMap[map_x][map_y].chosen_change();
                            }
                        }
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                swipeLeft();
                            } else if (offsetX > 5) {
                                swipeRight();
                            }
                        } else {
                            if (offsetY < -5) {
                                swipeUp();
                            } else if (offsetY > 5) {
                                swipeDown();
                            }
                        }

                        break;
                }
                return true;
            }
        });
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int CardWeight=Math.min(w,h)/4;
//        addCards(CardWeight,CardWeight);
        startGame();
        loadGame();
    }

    private void addCards(int cardWidth,int cardHeight){
        Card c;

        for (int y = 0; y< 4; y++) {
            for (int x = 0; x < 4; x++) {
                c=new Card(getContext());
                addView(c,cardWidth,cardHeight);
                cardsMap[x][y]=c;
                cardsMap_last[x][y]=new Card(getContext());
            }
        }
    }

    public void division_2(){
        System.out.println("");
        if (remain_time>0&&cardMap_change.getNum()>2){
            cardMap_change.setNum(cardMap_change.getNum()/2);
            remain_time--;
            MainActivity.getMainActivity().show_remain_times(remain_time);
        }
    }

    public void mult_2(){
        System.out.println("mult2-----------------");
        if (remain_time>0&&cardMap_change.getNum()>=2){
            int a=cardMap_change.getNum();
            cardMap_change.setNum(a*2);
            remain_time--;
            MainActivity.getMainActivity().show_remain_times(remain_time);
            System.out.println(cardMap_change.getNum());
        }
    }

    public void setSwitch_is_cheat(int num){
        switch_is_cheat=num;
        if (num==0){
            cardMap_change.setNum(cardMap_change.getNum());
        }
    }


    public void get_rank(){
        boolean is_true=false;
        new Thread(() -> {
            try {
                //代码块，调用connection连接或者增删改查都写这里面
                 RankDao rankDao =new RankDao();
                 List<Rank> rank_list = rankDao.get_rank_list();
                 ArrayList<TextView> textViews =new ArrayList<>();
                 textViews.add(MainActivity.getMainActivity().rank_1);
                 textViews.add(MainActivity.getMainActivity().rank_2);
                 textViews.add(MainActivity.getMainActivity().rank_3);
                 textViews.add(MainActivity.getMainActivity().rank_4);
                 textViews.add(MainActivity.getMainActivity().rank_5);
                 textViews.add(MainActivity.getMainActivity().rank_6);
                 textViews.add(MainActivity.getMainActivity().rank_7);
                if (rank_list.size()!=0){
                    for (int i = 0; i < rank_list.size(); i++) {
                        Rank rank =rank_list.get(i);
                        String id=String.valueOf(i+1);
                        String name=rank.getRankName();
                        String score=String.valueOf(rank.getRankScore());
                        System.out.println(score);
                        int name_length=0;
                        float a=0;
                        if (name.length()>0){
                            String[] names=name.split("");
                            Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5]");
                            for (int j = 0; j < names.length; j++) {
                                Matcher matcher=pattern.matcher(names[j]);
                                if (matcher.matches()){
                                    a+=2;
                                }
                                else
                                    a+=1;
                            }
                        }
                       name_length=(int) a;
                        for (int j = id.length(); j < 6;) {
                            id=" "+id;
                            if (id.length()==6){
                                break;
                            }
                            id=id+" ";
                            j=id.length();
                        }
                        for (int j = name_length; j < 9;) {
                            if (j==0){
                                name=" "+name;
                            }
                            name=" "+name;
                            name=name+" ";
                            j=j+1;
                        }
                        for (int j =score.length(); j < 6;) {
                            score=" "+score;
                            score=" "+score;

                            score=score+" ";
                            j=j+1;
                        }
                        System.out.println(id.length()+" "+name.length()+" "+score.length());
                        textViews.get(i).setText(id+"           "+name+score);
                    }

                }
                System.out.println("22222");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        MainActivity.getMainActivity().main.setVisibility(View.GONE);
        MainActivity.getMainActivity().rank_list.setVisibility(View.VISIBLE);
        System.out.println("33333");
    }


    public void post_rank(){
        int i = Math.max(MainActivity.getMainActivity().getScore(), top_score_last);
        new Thread(() -> {
            try {
                //代码块，调用connection连接或者增删改查都写这里面
                RankDao rankDao =new RankDao();
                System.out.println(user_name+" "+i+" "+put_count+" "+user_id);
                boolean b = rankDao.add_rank_list(user_name, String.valueOf(i),put_count,user_id);
                put_count+=1;
                if (user_id.equals("first")){
                    user_id=rankDao.get_user_id();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void post_name(String name){
        int i = Math.max(MainActivity.getMainActivity().getScore(), top_score_last);
        user_name=name;
        new Thread(() -> {
            try {
                //代码块，调用connection连接或者增删改查都写这里面
                RankDao rankDao =new RankDao();
                rankDao.post_name(user_name, String.valueOf(i),put_count,user_id);
                put_count+=1;
                if (user_id.equals("first")){
                    user_id=rankDao.get_user_id();
                    System.out.println(user_id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void startGame(){
//        MainActivity.getMainActivity().showScore();
       // MainActivity.getMainActivity().setScore(0);


        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0);
            }
        }


        addRandomNum();
        addRandomNum();
        saveGame();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap_last[x][y].setNum(cardsMap[x][y].getNum());
            }
        }
        MainActivity.getMainActivity().setScore_last(0);
        MainActivity.getMainActivity().showTopScore(top_score_last);
        MainActivity.getMainActivity().clearScore();
        remain_time=5;
        MainActivity.getMainActivity().show_remain_times(remain_time);
//        TheDataDBHelper theDataDBHelper=TheDataDBHelper.getInstance(getContext(),1);
//        SQLiteDatabase database=theDataDBHelper.openWriteLink();
//        theDataDBHelper.onCreate(database);
//        System.out.println(theDataDBHelper.insert("555","2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2"));

    }

    private void loadGame(){
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardString[x+4*y].equals("")){
                    cardsMap[x][y].setNum(0);
                }else
                    cardsMap[x][y].setNum(Integer.parseInt(cardString[x+4*y]));
            }
        }
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap_last[x][y].setNum(cardsMap[x][y].getNum());
            }
        }
        MainActivity.getMainActivity().setScore_last(Integer.parseInt(now_scores));
        MainActivity.getMainActivity().setScore(Integer.parseInt(now_scores));
        MainActivity.getMainActivity().showScore();
    }

    public void saveGame(){
        top_score=MainActivity.getMainActivity().getScore();
        String top_scores=String.valueOf(top_score);
        String excel="";
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (x+4*y<15){
                    excel=excel+String.valueOf(cardsMap[x][y].getNum())+",";}
                else if (x+4*y==15){
                    excel=excel+String.valueOf(cardsMap[x][y].getNum());
                }
            }
        }
        TheDataDBHelper theDataDBHelper=TheDataDBHelper.getInstance(getContext(),1);
        theDataDBHelper.openWriteLink();
        theDataDBHelper.delet(""+top_score_last);
        System.out.println("111111111111111111111111111111111");
        System.out.println(top_score_last);
        System.out.println(MainActivity.getMainActivity().getScore());
        String user_name_final;
        if (user_name.equals("")){
            user_name_final="sssssss";
        }
        else
            user_name_final=user_name;
        if (MainActivity.getMainActivity().getScore()>top_score_last){
            theDataDBHelper.insert(top_scores,String.valueOf(MainActivity.getMainActivity().getScore()),
                    excel,put_count,user_id,user_name_final, String.valueOf(remain_time));
            top_score_last=MainActivity.getMainActivity().getScore();
        }
        else
            theDataDBHelper.insert(top_scores,String.valueOf(top_score_last),
                    excel,put_count,user_id,user_name_final, String.valueOf(remain_time));
        theDataDBHelper.closeLink();

    }

    private void addRandomNum(){
        emptyPoints.clear();
        for (int y = 0; y< 4; y++) {
            for (int x = 0; x < 4; x++) {
                if(cardsMap[x][y].getNum()<=0){
                    emptyPoints.add(new Point(x,y));
                }
            }
        }
        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.scaleanim);
        cardsMap[p.x][p.y].startAnimation(animation);
    }


    public void gameback(){
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(cardsMap_last[x][y].getNum());
            }
        }
        MainActivity.getMainActivity().setScore(MainActivity.getMainActivity().getScore_last());
        MainActivity.getMainActivity().showScore();
    }


    private void checkComplete(){

        boolean complete=true;

        ALL:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if(cardsMap[x][y].getNum()==0||(x>0&&cardsMap[x][y].equals(cardsMap[x-1][y])||
                        (x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
                        y>0&&cardsMap[x][y].equals(cardsMap[x][y-1])||
                        y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))){
                    complete=false;
                    break ALL;
                }
            }
        }
        if (complete){
            FrameLayout in=MainActivity.getMainActivity().in;
            FrameLayout out=MainActivity.getMainActivity().out;
            in.setVisibility(View.VISIBLE);
            out.setVisibility(View.GONE);
        }
    }


    private void swipeLeft(){

        boolean merge=false;

        MainActivity.getMainActivity().setScore_last(MainActivity.getMainActivity().getScore());

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap_last[x][y].setNum(cardsMap[x][y].getNum());
            }
        }


        for (int y = 0; y< 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x+1; x1 < 4; x1++) {
                    if (cardsMap[x1][y].getNum()>0){
                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
//                            int distance= (int) (cardsMap[x1][y].getX()-cardsMap[x][y].getX());
//                            TranslateAnimation transformation = new TranslateAnimation(0,distance,0,0);
//                            transformation.setDuration(50);
//                            cardsMap[x][y].startAnimation(transformation);
                            cardsMap[x1][y].setNum(0);
                            x--;
                            merge=true;
                        }else if ((cardsMap[x][y].equals(cardsMap[x1][y]))){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.scaleanim);
                            animation.setDuration(50);
                            cardsMap[x][y].startAnimation(animation);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge=true;
                        }
                        break;
                    }
                }
            }
        }
        if (MainActivity.getMainActivity().getScore()>top_score_last){
            MainActivity.getMainActivity().showTopScore(MainActivity.getMainActivity().getScore());
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeRight(){

        boolean merge=false;

        MainActivity.getMainActivity().setScore_last(MainActivity.getMainActivity().getScore());

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap_last[x][y].setNum(cardsMap[x][y].getNum());
            }
        }

        for (int y = 0; y< 4; y++) {
            for (int x = 3; x >=0; x--) {
                for (int x1 = x-1; x1 >=0; x1--) {
                    if (cardsMap[x1][y].getNum()>0){
                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;
                            merge=true;
                        }else if ((cardsMap[x][y].equals(cardsMap[x1][y]))){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.scaleanim);
                            animation.setDuration(50);
                            cardsMap[x][y].startAnimation(animation);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge=true;
                        }
                        break;
                    }
                }
            }
        }
        if (MainActivity.getMainActivity().getScore()>top_score_last){
            MainActivity.getMainActivity().showTopScore(MainActivity.getMainActivity().getScore());
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeUp(){

        boolean merge=false;

        MainActivity.getMainActivity().setScore_last(MainActivity.getMainActivity().getScore());

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap_last[x][y].setNum(cardsMap[x][y].getNum());
            }
        }

        for (int x = 0; x< 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y+1; y1 < 4; y1++) {
                    if (cardsMap[x][y1].getNum()>0){
                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
//                            int distance= (int) (cardsMap[x][y1].getX()-cardsMap[x][y].getX());
//                            TranslateAnimation transformation = new TranslateAnimation(0,0,0,distance);
//                            transformation.setDuration(0);
//                            cardsMap[x][y].startAnimation(transformation);
                            cardsMap[x][y1].setNum(0);
                            y--;
                            merge=true;
                        }else if ((cardsMap[x][y].equals(cardsMap[x][y1]))){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.scaleanim);
                            animation.setDuration(50);
                            cardsMap[x][y].startAnimation(animation);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge=true;
                        }
                        break;
                    }
                }
            }
        }
        if (MainActivity.getMainActivity().getScore()>top_score_last){
            MainActivity.getMainActivity().showTopScore(MainActivity.getMainActivity().getScore());
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }


    private void swipeDown(){
        boolean merge=false;

        MainActivity.getMainActivity().setScore_last(MainActivity.getMainActivity().getScore());

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap_last[x][y].setNum(cardsMap[x][y].getNum());
            }
        }
        for (int x = 0; x< 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y-1; y1 >= 0; y1--) {
                    if (cardsMap[x][y1].getNum()>0){
                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
//                            int distance= (int) (cardsMap[x][y].getX()-cardsMap[x][y1].getX());
//                            TranslateAnimation transformation = new TranslateAnimation(0,0,0,distance);
//                            transformation.setDuration(0);
//                            cardsMap[x][y].startAnimation(transformation);
                            cardsMap[x][y1].setNum(0);
                            y++;
                            merge=true;
                        }else if ((cardsMap[x][y].equals(cardsMap[x][y1]))){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.scaleanim);
                            animation.setDuration(50);
                            cardsMap[x][y].startAnimation(animation);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());

                            merge=true;
                        }
                        break;
                    }
                }
            }
        }
        if (MainActivity.getMainActivity().getScore()>top_score_last){
            MainActivity.getMainActivity().showTopScore(MainActivity.getMainActivity().getScore());
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }

    private Card[][] cardsMap= new Card[4][4];
    private Card[][] cardsMap_last = new Card[4][4];
    private Card cardMap_change=new Card(getContext());
    private Card cardMap_change_last=new Card(getContext());
    private int card_map_x=-1;
    private int card_map_y=-1;
    private List<Point> emptyPoints= new ArrayList<>();
    public int top_score=0;
    public int top_score_last;
    private String[] cardString=new String[16];
    private String now_scores;
    public String user_id;
    public int put_count;
    public String user_name;
    public int remain_time;
    public int switch_is_cheat=0;
}
