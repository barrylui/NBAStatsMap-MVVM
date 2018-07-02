package barrylui.myteam.TeamDataNBA;

import java.util.HashMap;

public class NBATeamDataSingleton {
    private HashMap<String, HashMap<String, ?>> teamDataMap =
            new HashMap<String, HashMap<String, ?>>();
    private static NBATeamDataSingleton instance = null;
    private NBATeamDataSingleton(){}

    public static NBATeamDataSingleton getInstance(){
        if(instance == null){
            instance = new NBATeamDataSingleton();
        }
        return instance;
    }


    public HashMap<String, HashMap<String, ?>> getTeamDataMap() {
        return teamDataMap;
    }
/*
    public void put(String teamName, HashMap theMap){
        teamDataMap.put(teamName, theMap);
    }
    public boolean isEmpty(){
        boolean result = teamDataMap.isEmpty();
        return result;
    }
    */
}
