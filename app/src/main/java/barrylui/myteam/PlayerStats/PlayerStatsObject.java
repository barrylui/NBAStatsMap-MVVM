package barrylui.myteam.PlayerStats;

public class PlayerStatsObject {
    private double ppg;
    private double apg;
    private double rpg;
    private double bpg;
    private double spg;
    private double ftp;

    public PlayerStatsObject (Double points, Double assists, Double rebounds, Double blocks, Double steals, Double freethrowpercent, String jerseyNum, String height, String age, String position){
        ppg = points;
        apg = assists;
        rpg = rebounds;
        bpg = blocks;
        spg = steals;
        ftp = freethrowpercent;
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
}
