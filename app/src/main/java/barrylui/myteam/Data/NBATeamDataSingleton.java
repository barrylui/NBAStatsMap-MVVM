package barrylui.myteam.Data;

import java.util.HashMap;


//Singleton Class to store NBA Team Data loaded from the mysportsAPIJson
//Contains HashMap of HashMap with team Abbrv being the key and in the nested HashMap the key is the name of the stat category and the value are the stat values
//Contains Arrays to load team stat data into. This array will be sorted and provide rankings for each team in a stat for the radarchart to use
public class NBATeamDataSingleton {
    private HashMap<String, HashMap<String, Object>> teamDataMap =
            new HashMap<String, HashMap<String, Object>>();


    private static NBATeamDataSingleton instance = null;
    private NBATeamDataSingleton(){}

    public static NBATeamDataSingleton getInstance(){
        if(instance == null){
            instance = new NBATeamDataSingleton();
        }
        return instance;
    }


    public HashMap<String, HashMap<String, Object>> getTeamDataMap() {
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
