package com.example.myapplication_java;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.text.Editable;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public MainActivity(){
        mainActivity=this;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore=findViewById(R.id.tvScore);
        top_score_text=findViewById(R.id.top_score);
        gameView=findViewById(R.id.gameView);
        back=findViewById(R.id.back);
        setting_back=findViewById(R.id.setting_back);
        menu=findViewById(R.id.menu);
        in=findViewById(R.id.in);
        out=findViewById(R.id.out);
        main=findViewById(R.id.main);
        rank_list=findViewById(R.id.rank_list);
        back_menu=findViewById(R.id.back_menu);
        restart=findViewById(R.id.restart);
        is_sure=findViewById(R.id.is_sure);
        is_not_sure=findViewById(R.id.is_not_sure);
        restart_shadow=findViewById(R.id.restart_shadow);
        is_not_sure_shadow=findViewById(R.id.is_not_sure_shadow);
        is_sure_shadow=findViewById(R.id.is_sure_shadow);
        rank1=findViewById(R.id.rank1);
        rank_back=findViewById(R.id.rank_back);
        rank_1=findViewById(R.id.rank_1);
        rank_2=findViewById(R.id.rank_2);
        rank_3=findViewById(R.id.rank_3);
        rank_4=findViewById(R.id.rank_4);
        rank_5=findViewById(R.id.rank_5);
        rank_6=findViewById(R.id.rank_6);
        rank_7=findViewById(R.id.rank_7);
        top_score_text.setText("Top:"+gameView.top_score);
        setting=findViewById(R.id.setting);
        setting_menu=findViewById(R.id.setting_menu);
        setting_name=findViewById(R.id.setting_name);
        put_name=findViewById(R.id.put_name);
        put_score=findViewById(R.id.put_score);
        remain_times=findViewById(R.id.remain_times);
        division_2=findViewById(R.id.division_2);
        mult_2=findViewById(R.id.mult_2);
        cheat=findViewById(R.id.cheat);
        cheatSwitch=findViewById(R.id.cheat_switch);
        music=findViewById(R.id.music);
        explain=findViewById(R.id.explain);
        explain_back=findViewById(R.id.explain_back);
        explain_show=findViewById(R.id.explain_show);

        explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                explain_show.setVisibility(View.VISIBLE);
                main.setVisibility(View.GONE);
                in.setVisibility(View.VISIBLE);
            }
        });

        explain_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                explain_show.setVisibility(View.GONE);
                main.setVisibility(View.VISIBLE);
                out.setVisibility(View.GONE);
                in.setVisibility(View.VISIBLE);
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer1==null||mediaPlayer2==null||mediaPlayer3==null||mediaPlayer4==null){
                    mediaPlayer1 = MediaPlayer.create(MainActivity.getMainActivity(),R.raw.music1);
                    mediaPlayer2 = MediaPlayer.create(MainActivity.getMainActivity(),R.raw.music02);
                    mediaPlayer3 = MediaPlayer.create(MainActivity.getMainActivity(),R.raw.music3);
                    mediaPlayer4 = MediaPlayer.create(MainActivity.getMainActivity(),R.raw.ji);
                    mediaPlayer1.start();
                    mediaPlayer1.setLooping(true);
                    music_count+=1;
                }
                if (mediaPlayer1.isPlaying()&&(music_count%8)==2){//如果当前音乐正在播放
                    mediaPlayer1.pause();
                }else if (mediaPlayer2.isPlaying()&&(music_count%8)==4){//如果当前音乐正在播放
                    mediaPlayer2.pause();}
                else if (mediaPlayer3.isPlaying()&&(music_count%8)==6){//如果当前音乐正在播放
                    mediaPlayer3.pause();}
                else if (mediaPlayer4.isPlaying()&&(music_count%8)==0){//如果当前音乐正在播放
                    mediaPlayer4.pause();}
                else if ((music_count%8)==1){
                    mediaPlayer1.start();
                    mediaPlayer1.setLooping(true);
                }
                else if ((music_count%8)==3){
                    mediaPlayer2.start();
                    mediaPlayer2.setLooping(true);
                }else if ((music_count%8)==5){
                    mediaPlayer3.start();
                    mediaPlayer3.setLooping(true);
                }else if ((music_count%8)==7){
                    mediaPlayer4.start();
                    mediaPlayer4.setLooping(true);
                }
                music_count+=1;
            }
        });

        cheatSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cheat_count==0){
                    out.setVisibility(View.VISIBLE);
                    in.setVisibility(View.GONE);
                    cheat.setVisibility(View.VISIBLE);
                    show_remain_times(gameView.remain_time);
                    gameView.setSwitch_is_cheat(1);
                    cheat_count=1;
                }
                else if (cheat_count==1){
                    out.setVisibility(View.VISIBLE);
                    in.setVisibility(View.GONE);
                    cheat.setVisibility(View.GONE);
                    gameView.setSwitch_is_cheat(0);
                    cheat_count=0;
                }

            }
        });

        division_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.division_2();
            }
        });

        mult_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.mult_2();
            }
        });

        put_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.post_name(setting_name.getText().toString());
                setting.setVisibility(View.GONE);
                main.setVisibility(View.VISIBLE);
                in.setVisibility(View.VISIBLE);
            }
        });

        put_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.post_rank();
                main.setVisibility(View.VISIBLE);
                rank_list.setVisibility(View.GONE);
                out.setVisibility(View.GONE);
                in.setVisibility(View.VISIBLE);
            }
        });


        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.restart){
                    restart.setVisibility(View.GONE);
                    restart_shadow.setVisibility(View.GONE);
                    is_not_sure.setVisibility(View.VISIBLE);
                    is_not_sure_shadow.setVisibility(View.VISIBLE);
                    is_sure.setVisibility(View.VISIBLE);
                    is_sure_shadow.setVisibility(View.VISIBLE);
                }
            }
        });

        setting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting.setVisibility(View.GONE);
                main.setVisibility(View.VISIBLE);
                in.setVisibility(View.VISIBLE);
            }
        });

        setting_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting.setVisibility(View.VISIBLE);
                main.setVisibility(View.GONE);
                out.setVisibility(View.GONE);
                setting_name.setText(gameView.user_name);
            }
        });

        rank_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.setVisibility(View.VISIBLE);
                rank_list.setVisibility(View.GONE);
                out.setVisibility(View.GONE);
                in.setVisibility(View.VISIBLE);
            }
        });


        rank1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.get_rank();
            }
        });

        is_not_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.is_not_sure){
                    restart.setVisibility(View.VISIBLE);
                    restart_shadow.setVisibility(View.VISIBLE);
                    is_not_sure.setVisibility(View.GONE);
                    is_sure.setVisibility(View.GONE);
                    is_not_sure_shadow.setVisibility(View.GONE);
                    is_sure_shadow.setVisibility(View.GONE);
                }
            }
        });

        is_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()== R.id.is_sure){
                    restart.setVisibility(View.VISIBLE);
                    restart_shadow.setVisibility(View.VISIBLE);
                    is_not_sure.setVisibility(View.GONE);
                    is_sure.setVisibility(View.GONE);
                    is_not_sure_shadow.setVisibility(View.GONE);
                    is_sure_shadow.setVisibility(View.GONE);
                    out.setVisibility(View.VISIBLE);
                    in.setVisibility(View.GONE);
                    gameView.startGame();
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()== R.id.back){
                    gameView.gameback();
                }
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.menu){
                    in.setVisibility(View.VISIBLE);
                    out.setVisibility(View.GONE);
                }
            }
        });

        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.back_menu){
                    out.setVisibility(View.VISIBLE);
                    in.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.saveGame();
    }

    public void clearScore(){
        score=0;
        showScore();
    }

    public void setScore(int s){
        score=s;
    }

    public void setScore_last(int s){
        score_last=s;
    }

    public int getScore(){
        return score;
    }

    public int getScore_last(){
        return score_last;
    }

    public void showScore(){
        tvScore.setText(score+"");
    }
    public void addScore(int s){
        score+=s;
        showScore();
    }

    public void showTopScore(int score_last){
        top_score_text.setText("Top:"+score_last);
    }

    public void show_remain_times(int time){
       remain_times.setText("剩余次数:"+time);
    }

    private TextView tvScore;
    private TextView top_score_text;
    public TextView rank_1;
    public TextView rank_2;
    public  TextView rank_3;
    public TextView rank_4;
    public TextView rank_5;
    public  TextView rank_6;
    public TextView rank_7;
    public TextView remain_times;
    private Button back;
    private Button menu;
    private Button back_menu;
    private Button restart;
    private Button is_sure;
    private Button is_not_sure;
    private Button rank1;
    private Button rank_back;
    private Button setting_back;
    private Button setting_menu;
    private Button put_name;
    private Button put_score;
    private Button division_2;
    private Button mult_2;
    private Button cheatSwitch;
    private Button music;
    private Button explain;
    private Button explain_back;
    private int score=0;
    private int score_last=0;
    private int music_count=0;
    private GameView gameView;
    private static MainActivity mainActivity=null;
    public FrameLayout in;
    public FrameLayout out;
    public FrameLayout rank_list;
    public LinearLayout main;
    public FrameLayout setting;
    public FrameLayout cheat;
    public FrameLayout explain_show;
    private View restart_shadow;
    private View is_sure_shadow;
    private View is_not_sure_shadow;
    private EditText setting_name;
    private int cheat_count=0;
    private MediaPlayer mediaPlayer1;
    private MediaPlayer mediaPlayer2;
    private MediaPlayer mediaPlayer3;
    private MediaPlayer mediaPlayer4;


    public static MainActivity getMainActivity() {
        return mainActivity;
    }

}