package barrylui.myteam.PlayerStats;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


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
}
