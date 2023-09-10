package com.example.myapplication_java;

import android.view.View;
import com.example.myapplication_java.Utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RankDao {

    public List<Rank> get_rank_list(){
        Connection connection= JDBCUtil.getConn();
        ArrayList<Rank> rankList=new ArrayList<>();

        String sql ="select * from Ranking_list ORDER BY rank_score desc";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                //通过res遍历列时，要从1开始
                Rank rank=new Rank(res.getInt(1), res.getNString(2),res.getInt(3) );
                rankList.add(rank);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rankList;
    }

    public String get_user_id(){
        Connection connection= JDBCUtil.getConn();

        String sql ="select count(*) from Ranking_list";
        String user_id="first";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                user_id=String.valueOf(res.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user_id;
    }

    public void post_name(String rankName,String rankScore,int input_count,String user_id){
        Connection connection= JDBCUtil.getConn();

        if (input_count==0){
            String sql="insert into Ranking_list(rank_name,rank_score) values(?,?)";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,rankName);
                preparedStatement.setInt(2,Integer.parseInt(rankScore));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            String sql="update Ranking_list set rank_name=? where rank_id=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,rankName);
                preparedStatement.setInt(2,Integer.parseInt(user_id));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public boolean add_rank_list(String rankName,String rankScore,int input_count,String user_id){

        Connection connection=JDBCUtil.getConn();
        if (input_count==0){
            String sql="insert into Ranking_list(rank_name,rank_score) values(?,?)";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,rankName);
                preparedStatement.setInt(2,Integer.parseInt(rankScore));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            String sql="update Ranking_list set rank_name=?,rank_score=? where rank_id=?";
            try {
                System.out.println("aaa");
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,rankName);
                preparedStatement.setInt(2,Integer.parseInt(rankScore));
                preparedStatement.setInt(3,Integer.parseInt(user_id));
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(user_id);
        System.out.println("--------------------------------------");
        return false;
    }

}
