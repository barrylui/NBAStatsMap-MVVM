package barrylui.myteam.Data;

import java.util.HashMap;

//Singleton HashMap object that contains player stats
//The first key is the name of the player and the value is another hasmap
//The nested Hashmap's keys are the stats and the values are the stat values
public class NBAPlayerDataSingleton {
    private HashMap<String, HashMap<String, Double>> playerDataMap =
            new HashMap<>();
    private static NBAPlayerDataSingleton instance = null;
    private NBAPlayerDataSingleton(){}

    public static NBAPlayerDataSingleton getInstance(){
        if(instance==null){
            instance = new NBAPlayerDataSingleton();
        }
        return instance;
    }

    public HashMap<String, HashMap<String, Double>> getPlayerDataMap() {
        return playerDataMap;
    }
}
