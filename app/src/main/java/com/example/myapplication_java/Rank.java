package com.example.myapplication_java;


public class Rank {

    int rankId;
    String rankName;
    int rankScore;


    public Rank(int rankId, String rankName, int rankScore) {
        this.rankId = rankId;
        this.rankName = rankName;
        this.rankScore = rankScore;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public int getRankScore() {
        return rankScore;
    }

    @Override
    public String toString() {
        return "Rank{" +
                "rankId='" + rankId + '\'' +
                ", rankName='" + rankName + '\'' +
                ", rankScore='" + rankScore + '\'' +
                '}';
    }

    public void setRankScore(int rankScore) {
        this.rankScore = rankScore;
    }
}
