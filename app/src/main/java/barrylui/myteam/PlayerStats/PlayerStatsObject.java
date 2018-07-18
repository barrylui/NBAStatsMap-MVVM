package barrylui.myteam.PlayerStats;

//PlayerStatsObject class that will be observed by the livedata
public class PlayerStatsObject {
    private double ppg;
    private double apg;
    private double rpg;
    private double bpg;
    private double spg;
    private double ftp;
    private String playerName;

    public PlayerStatsObject (Double points, Double assists, Double rebounds, Double blocks, Double steals, Double freethrowpercent, String name){
        ppg = points;
        apg = assists;
        rpg = rebounds;
        bpg = blocks;
        spg = steals;
        ftp = freethrowpercent;
        playerName = name;
    }


    public double getPpg() {
        return ppg;
    }

    public double getApg() {
        return apg;
    }

    public double getRpg() {
        return rpg;
    }

    public double getBpg() {
        return bpg;
    }

    public double getSpg() {
        return spg;
    }

    public double getFtp() {
        return ftp;
    }

    public String getPlayerName() { return playerName;}
}
