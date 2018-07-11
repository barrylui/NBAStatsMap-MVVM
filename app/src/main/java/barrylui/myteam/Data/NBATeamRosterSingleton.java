package barrylui.myteam.Data;

import java.util.HashMap;
import java.util.List;

import barrylui.myteam.SuredBitsAPI.SuredBitsPlayerModel.PlayerInfoModel;

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
