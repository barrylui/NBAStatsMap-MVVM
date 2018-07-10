package barrylui.myteam.TeamStats;

import barrylui.myteam.R;

public class TeamStatsObject {
    public Double ppg = 0.0;
    public Double oppg = 0.0;
    public Double rpg = 0.0;
    public Double apg = 0.0;
    public Double tpm = 0.0;
    public Double ftp = 0.0;
    public int color = R.color.colorThunderPrimary;
    public String wins ="";
    public String losses = "";
    public String fullName ="";
    public String teamAbbreviation="";

    public String getFullName() {
        return fullName;
    }

    public String getTeamAbbreviation() {
        return teamAbbreviation;
    }

    public Double getPpg() {
        return ppg;
    }

    public Double getOppg() {
        return oppg;
    }

    public Double getRpg() {
        return rpg;
    }

    public Double getApg() {
        return apg;
    }

    public Double getTpm() {
        return tpm;
    }

    public Double getFtp() {
        return ftp;
    }

    public int getColor() {
        return color;
    }

    public String getWins() {
        return wins;
    }

    public String getLosses() {
        return losses;
    }



    public TeamStatsObject(Double offense, Double defense, Double rebounds, Double assists, Double threes, Double freethrow, int teamColor, String dubs, String ls, String team, String abbrv){
        ppg = offense;
        oppg = defense;
        rpg = rebounds;
        apg = assists;
        tpm = threes;
        ftp = freethrow;
        color = teamColor;
        wins = dubs;
        losses = ls;
        fullName = team;
        teamAbbreviation = abbrv;
    }
}
