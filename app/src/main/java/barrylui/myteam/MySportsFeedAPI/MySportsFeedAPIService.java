package barrylui.myteam.MySportsFeedAPI;


import barrylui.myteam.MySportsFeedAPI.MySportsFeedPlayerStatsModel.PlayerStats;
import barrylui.myteam.MySportsFeedAPI.MySportsFeedTeamStats.TeamData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by The MACHINE on 4/24/2018.
 */

public interface MySportsFeedAPIService {

    String BASE_URL = "https://api.mysportsfeeds.com/v1.2/pull/nba/2017-2018-regular/";


    //Contains all 30 nba team's data
    @Headers("Content-type: application/json")
    @GET("conference_team_standings.json?teamstats=W,L,PTS/G,AST/G,REB/G,3PA/G,3PM/G,PTS/G,PTSA/G,FTA,FTM")
    Call<TeamData> getTeamStatsData();

    //Contains all player's statistical data
    @Headers("Content-type: application/json")
    @GET("cumulative_player_stats.json?playerstats=PTS/G,AST/G,REB/G,STL/G,BS/G,FTA,FTM")
    Call<PlayerStats> getAllPlayerStats();

}
