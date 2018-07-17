package barrylui.myteam.TeamStats;

import barrylui.myteam.R;

//This class is an Object class that contains stats for teams
//This class is used to encapsulate stats for teams to be used in the Team Stats module
public class TeamStatsObject {
    private Double ppg = 0.0;
    private Double oppg = 0.0;
    private Double rpg = 0.0;
    private Double apg = 0.0;
    private Double tpm = 0.0;
    private Double ftp = 0.0;
    private int color = R.color.colorThunderPrimary;
    private String wins ="";
    private String losses = "";
    private String fullName ="";
    private String teamAbbreviation="";

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
