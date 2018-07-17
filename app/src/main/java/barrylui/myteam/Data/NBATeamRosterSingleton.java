package barrylui.myteam.Data;

import java.util.HashMap;
import java.util.List;

import barrylui.myteam.SuredBitsAPI.SuredBitsPlayerModel.PlayerInfoModel;

//Singleton object that stores player roster data for each nba team
//The key is the teamName and the values are List of playerInfomodel - contains vitals on player
public class NBATeamRosterSingleton {
    private HashMap<String, List<PlayerInfoModel>> teamRosterHashMap = new HashMap<>();
    private static NBATeamRosterSingleton instance = null;
    private NBATeamRosterSingleton(){}

    public static NBATeamRosterSingleton getInstance(){
        if(instance==null){
            instance = new NBATeamRosterSingleton();
        }
        return instance;
    }

    public HashMap<String, List<PlayerInfoModel>> getTeamRosterHashMap() {
        return teamRosterHashMap;
    }
}
