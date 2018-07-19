package barrylui.myteam.PlayerStats;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import barrylui.myteam.Data.NBAPlayerDataSingleton;

//Viewmodel for PlayerStats module
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

    // Creates a playerStatsObject using the player's name as a parameter
    public PlayerStatsObject createPlayerStatsObject(String playerName){
        if (playerName.equals(" Nene")){
            playerName = "Nene Hilario";
        }
        //Retrieves data entry for specific player
        HashMap<String, Double> playerStatsMap = NBAPlayerDataSingleton.getInstance().getPlayerDataMap().get(playerName);

        //Creates playerStatsObject using data for specific player
        PlayerStatsObject playerStats1Object = new PlayerStatsObject(playerStatsMap.get("Scoring"), playerStatsMap.get("Assists"), playerStatsMap.get("Rebounding"),
                playerStatsMap.get("Blocks"), playerStatsMap.get("Steals"), playerStatsMap.get("FTPercent"), playerName);

        return playerStats1Object;
    }

    //Calculates player's ranking in each statistical category
    //Loads all stats into arrays
    //Sorts Array
    //Takes max value in each stat category and that value is used as the max for each axis in the radar chart
    //100 is divided by the max value to obtain the multiplication factor to be used to multiply player stats to obtain their radar chart value for each stat

    public HashMap<String, Double> getPlayerStatRanking(PlayerStatsObject thePlayerStatsObject){
        //Object to be returned
        HashMap<String, Double> playerStatsRankingMap = new HashMap<>();

        Set<HashMap.Entry<String, HashMap<String, Double>>> playerEntrySet = NBAPlayerDataSingleton.getInstance().getPlayerDataMap().entrySet();
        //int numberOfNBAPlayers = NBAPlayerDataSingleton.getInstance().getPlayerDataMap().size();
        //Arrays to load stats into
        //616 players
        double[] pointsArray = new double[616];
        double[] assistsArray = new double[616];
        double[] reboundsArray = new double[616];
        double[] blocksArray = new double[616];
        double[] stealsArray = new double[616];
        double[] FTArray = new double[616];

        //For each entry in hashmap store the stats in the appropriate arrays
        int i = 0;
        for(HashMap.Entry<String, HashMap<String, Double>> playerStatsEntry : playerEntrySet){
            pointsArray[i] = playerStatsEntry.getValue().get("Scoring");
            assistsArray[i] = playerStatsEntry.getValue().get("Assists");
            reboundsArray[i] = playerStatsEntry.getValue().get("Rebounding");
            blocksArray[i] = playerStatsEntry.getValue().get("Blocks");
            stealsArray[i] = playerStatsEntry.getValue().get("Steals");
            FTArray[i] = playerStatsEntry.getValue().get("FTPercent");
            i++;
        }

        //Sort the arrays to get max
        Arrays.sort(pointsArray);
        Arrays.sort(assistsArray);
        Arrays.sort(reboundsArray);
        Arrays.sort(blocksArray);
        Arrays.sort(stealsArray);
        Arrays.sort(FTArray);

        //Obtain league leaders in each category
        double pointLeaderValue = pointsArray[615];
        double assistLeaderValue = assistsArray[615];
        double reboundLeaderValue = reboundsArray[615];
        double blocksLeaderValue = blocksArray[615];
        double stealsLeaderValue = stealsArray[615];
        double freeThrowLeaderValue = FTArray[615];

        //Obtain multiplication factor
        double pointMultiplicationFactor = (double)100/pointLeaderValue;
        double assistsMultiplicationFactor = (double)100/assistLeaderValue;
        double reboundMultiplicationFactor = (double)100/reboundLeaderValue;
        double stealsMultiplicationFactor = (double)100/stealsLeaderValue;
        double blocksMultiplicationFactor = (double)100/blocksLeaderValue;
        double freeThrowMultiplicationFactor = (double)100/(double)55;


        //Apply multiplication factor to stats to obtain radarChartValue
        double scoringRadarChartValue = thePlayerStatsObject.getPpg() * pointMultiplicationFactor;
        double assistsRadarChartValue = thePlayerStatsObject.getApg() * assistsMultiplicationFactor;
        double reboundsRadarChartValue = thePlayerStatsObject.getRpg() * reboundMultiplicationFactor;
        double stealsRadarChartValue = thePlayerStatsObject.getSpg() * stealsMultiplicationFactor;
        double blocksRadarChartValue = thePlayerStatsObject.getBpg() * blocksMultiplicationFactor;
        double freeThrow = thePlayerStatsObject.getFtp() -(double)40;
        double freeThrowRadarChartValue = freeThrow * freeThrowMultiplicationFactor;

        //put radarChart values in hashmap to return
        playerStatsRankingMap.put("PPG", scoringRadarChartValue);
        playerStatsRankingMap.put("APG", assistsRadarChartValue);
        playerStatsRankingMap.put("RPG", reboundsRadarChartValue);
        playerStatsRankingMap.put("SPG", stealsRadarChartValue);
        playerStatsRankingMap.put("BPG", blocksRadarChartValue);
        playerStatsRankingMap.put("FTP", freeThrowRadarChartValue);

        return playerStatsRankingMap;
    }

}
