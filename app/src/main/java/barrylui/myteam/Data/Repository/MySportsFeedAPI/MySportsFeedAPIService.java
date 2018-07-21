package barrylui.myteam.Data.Repository.MySportsFeedAPI;


import barrylui.myteam.Data.Repository.MySportsFeedAPI.MySportsFeedPlayerInfoModel.PlayerInfo;
import barrylui.myteam.Data.Repository.MySportsFeedAPI.MySportsFeedPlayerStatsModel.PlayerStats;
import barrylui.myteam.Data.Repository.MySportsFeedAPI.MySportsFeedTeamStats.TeamData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;



// MYSPORTSFEED API SERVICE
// Contains interface with calls to specific end points for player and team stats

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

    @Headers("Content-type: application/json")
    @GET("roster_players.json?")
    Call<PlayerInfo> getPlayerInfo();
}
