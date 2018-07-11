package barrylui.myteam.Data;

import java.util.HashMap;

public class NBAPlayerDataSingleton {
    private HashMap<String, HashMap<String, ?>> playerDataMap =
            new HashMap<>();
    private static NBAPlayerDataSingleton instance = null;
    private NBAPlayerDataSingleton(){}

    public static NBAPlayerDataSingleton getInstance(){
        if(instance==null){
            instance = new NBAPlayerDataSingleton();
        }
        return instance;
    }

    public HashMap<String, HashMap<String, ?>> getPlayerDataMap() {
        return playerDataMap;
    }
}
