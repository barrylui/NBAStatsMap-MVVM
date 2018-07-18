package barrylui.myteam.PlayerStats;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Set;

import barrylui.myteam.Data.NBAPlayerDataSingleton;


public class PlayerStatsViewModel extends ViewModel{
    public MutableLiveData<PlayerStatsObject> player1Stats;
    public MutableLiveData<PlayerStatsObject> player2Stats;

    public MutableLiveData<PlayerStatsObject> getPlayer1Stats(){
        if(player1Stats == null){
            player1Stats = new MutableLiveData<PlayerStatsObject>();
        }
        return player1Stats;
    }

    public MutableLiveData<PlayerStatsObject> getPlayer2Stats(){
        if(player2Stats == null){
            player2Stats = new MutableLiveData<PlayerStatsObject>();
        }
        return player2Stats;
    }


    public PlayerStatsObject createPlayerStatsObject(String playerName){
        if (playerName.equals("PJ Tucker")){
            playerName = "P.J. Tucker";
        }
        //Retrieves data entry for specific player
        HashMap<String, Double> playerStatsMap = NBAPlayerDataSingleton.getInstance().getPlayerDataMap().get(playerName);

        //Creates playerStatsObject using data for specific player
        PlayerStatsObject playerStats1Object = new PlayerStatsObject(playerStatsMap.get("Scoring"), playerStatsMap.get("Assists"), playerStatsMap.get("Rebounding"),
                playerStatsMap.get("Blocks"), playerStatsMap.get("Steals"), playerStatsMap.get("FTPercent"), playerName);

        return playerStats1Object;
    }
    /*
    public HashMap<String, Object> getPlayerStatRanking(PlayerStatsObject thePlayerStatsObject){
    //Do binary search with the playerStatsObject stats and stats in PlayerDataSingleton
        int pointsCompareVal;
        int asssistsCompareVal;
        int reboundsCompareVal;
        int blocksCompareVal;
        int stealsComapreVal;
        int freeThrowCompareVal;

        double pointsRadarValue=0.0;
        double assistsRadarValue=0.0;
        double reboundsRadarValue=0.0;
        double blocksRadarValue=0.0;
        double stealsRadarValue=0.0;
        double freeThrowsRadarValue=0.0;

        //Object to be returned
        HashMap<String, Object> playerStatsRankingMap = new HashMap<>();


        return playerStatsRankingMap;
    }
    */
}
