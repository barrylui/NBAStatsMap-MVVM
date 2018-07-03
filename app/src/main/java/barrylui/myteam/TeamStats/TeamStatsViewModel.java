package barrylui.myteam.TeamStats;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class TeamStatsViewModel extends ViewModel {
    public MutableLiveData<TeamStatsObject> team1Stats;
    public MutableLiveData<TeamStatsObject> team2Stats;

    public MutableLiveData<TeamStatsObject> getTeam1Stats() {
        if(team1Stats == null){
            team1Stats = new MutableLiveData<TeamStatsObject>();
        }
        return team1Stats;
    }

    public MutableLiveData<TeamStatsObject> getTeam2Stats() {
        if(team2Stats == null){
            team2Stats = new MutableLiveData<TeamStatsObject>();
        }
        return team2Stats;
    }

}
