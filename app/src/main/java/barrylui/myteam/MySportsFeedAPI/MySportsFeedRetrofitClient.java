package barrylui.myteam.MySportsFeedAPI;

import android.util.Log;

import java.util.HashMap;

import barrylui.myteam.InternetUtilities.BasicAuthInterceptor;
import barrylui.myteam.MySportsFeedAPI.MySportsFeedPlayerStatsModel.PlayerStats;
import barrylui.myteam.MySportsFeedAPI.MySportsFeedTeamStats.TeamData;
import barrylui.myteam.TeamDataNBA.NBAPlayerDataSingleton;
import barrylui.myteam.TeamDataNBA.NBATeamDataSingleton;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MySportsFeedRetrofitClient {

    private static MySportsFeedAPIService mySportsFeedAPIService;
    private static Retrofit retrofit;
    private static final String BASE_URL = MySportsFeedAPIService.BASE_URL;
    private static String TAG = "MainActivity";
    //Retrofit instance
    public static Retrofit getInstance() {
        if (retrofit == null){
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor("blui", "gdorf151")).build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();



        }
        return retrofit;
    }

    //Retrieves JSON data for stats of all NBA Players
    //Stores relevant data in Singleton HashMap class for players
    public static void getPlayerData(){
        //Network call
        MySportsFeedAPIService mySportsFeedAPIService = MySportsFeedRetrofitClient.getInstance().create(MySportsFeedAPIService.class);
        Call<PlayerStats> call = mySportsFeedAPIService.getAllPlayerStats();
        call.enqueue(new Callback<PlayerStats>() {
            @Override
            //Load data into HashMap if connection is successfully made
            public void onResponse(Call<PlayerStats> call, Response<PlayerStats> response) {
                Log.d(TAG, "onResponse: 200");
                int numberOfNBAPlayers = response.body().getCumulativeplayerstats().getPlayerstatsentry().size();
                for(int i =0; i<numberOfNBAPlayers; i++){
                    HashMap<String, Double> playerStatsMap = new HashMap<>();
                    Double ppg = Double.parseDouble(response.body().getCumulativeplayerstats().getPlayerstatsentry().get(i).getStats().getPtsPerGame().getText());
                    Double apg = Double.parseDouble(response.body().getCumulativeplayerstats().getPlayerstatsentry().get(i).getStats().getAstPerGame().getText());
                    Double rpg = Double.parseDouble(response.body().getCumulativeplayerstats().getPlayerstatsentry().get(i).getStats().getRebPerGame().getText());
                    Double spg = Double.parseDouble(response.body().getCumulativeplayerstats().getPlayerstatsentry().get(i).getStats().getStlPerGame().getText());
                    Double bpg = Double.parseDouble(response.body().getCumulativeplayerstats().getPlayerstatsentry().get(i).getStats().getBlkPerGame().getText());
                    Double ftm = Double.parseDouble(response.body().getCumulativeplayerstats().getPlayerstatsentry().get(i).getStats().getFtMade().getText());
                    Double fta = Double.parseDouble(response.body().getCumulativeplayerstats().getPlayerstatsentry().get(i).getStats().getFtAtt().getText());

                    //Calculate free throw percent
                    Double ftpercent = (ftm/fta)*100;

                    playerStatsMap.put("Scoring", ppg);
                    playerStatsMap.put("Assists", apg);
                    playerStatsMap.put("Rebounding",rpg);
                    playerStatsMap.put("Steals", spg);
                    playerStatsMap.put("Blocks", bpg );
                    playerStatsMap.put("FTPercent",ftpercent);


                    //Get PlayerName
                    String playerName = response.body().getCumulativeplayerstats().getPlayerstatsentry().get(i).getPlayer().getFirstName() + " " +
                            response.body().getCumulativeplayerstats().getPlayerstatsentry().get(i).getPlayer().getLastName();

                    //Load player name as key and HashMap with stats into singleton hashMap class for players
                    NBAPlayerDataSingleton.getInstance().getPlayerDataMap().put(playerName, playerStatsMap);
                }
            }

            @Override
            public void onFailure(Call<PlayerStats> call, Throwable t) {
                Log.d(TAG, "something went wrong" + t.getMessage());
            }
        });
    }
    //Fetches JSON data for stats of all 30 NBA Teams
    //Stores relevant data in Singleton Hashmap class for teams
    public static void getTeamData(){
        MySportsFeedAPIService mySportsFeedAPIService = MySportsFeedRetrofitClient.getInstance().create(MySportsFeedAPIService.class);
        Call<TeamData> call = mySportsFeedAPIService.getTeamStatsData();
        call.enqueue(new Callback<TeamData>() {
            @Override
            public void onResponse(Call<TeamData> call, Response<TeamData> response) {
                if(response.code()==200){
                    Log.d(TAG, "onResponse: 200");
                    for(int i =0; i<2;i++){
                        for(int j=0; j<15;j++){
                            HashMap<String, Object> statsMap = new HashMap<String, Object>();


                            String teamCity = response.body().getConferenceteamstandings().getConference().get(i).getTeamentry().get(j).getTeam().getCity();
                            String teamName = teamCity + "\n" + response.body().getConferenceteamstandings().getConference().get(i).getTeamentry().get(j).getTeam().getName();
                            String teamAbbrv = response.body().getConferenceteamstandings().getConference().get(i).getTeamentry().get(j).getTeam().getAbbreviation();
                            String numberOfWins = response.body().getConferenceteamstandings().getConference().get(i).getTeamentry().get(j).getStats().getWins().getText();
                            String numberOfLosses = response.body().getConferenceteamstandings().getConference().get(i).getTeamentry().get(j).getStats().getLosses().getText();
                            Double ppg = Double.parseDouble(response.body().getConferenceteamstandings().getConference()
                                    .get(i).getTeamentry().get(j).getStats().getPtsPerGame().get(0).getText());
                            Double oppg = Double.parseDouble(response.body().getConferenceteamstandings().getConference()
                                    .get(i).getTeamentry().get(j).getStats().getPtsAgainstPerGame().getText());
                            Double rpg = Double.parseDouble(response.body().getConferenceteamstandings().getConference()
                                    .get(i).getTeamentry().get(j).getStats().getRebPerGame().getText());
                            Double apg = Double.parseDouble(response.body().getConferenceteamstandings().getConference()
                                    .get(i).getTeamentry().get(j).getStats().getAstPerGame().getText());
                            Double fta = Double.parseDouble(response.body().getConferenceteamstandings().getConference()
                                    .get(i).getTeamentry().get(j).getStats().getFtAtt().getText());
                            Double ftm = Double.parseDouble(response.body().getConferenceteamstandings().getConference()
                                    .get(i).getTeamentry().get(j).getStats().getFtMade().getText());
                            Double tpm = Double.parseDouble(response.body().getConferenceteamstandings().getConference()
                                    .get(i).getTeamentry().get(j).getStats().getFg3PtMadePerGame().getText());
                            Double ftp = (ftm/fta)*100;


                            statsMap.put("offense", ppg);
                            statsMap.put("defense",oppg);
                            statsMap.put("rebounds",rpg);
                            statsMap.put("assists",apg);
                            statsMap.put("tpm", tpm);
                            statsMap.put("ftp",ftp);
                            statsMap.put("wins", numberOfWins);
                            statsMap.put("losses", numberOfLosses);
                            statsMap.put("Abbrv",teamAbbrv);
                            statsMap.put("teamName", teamName);
                            statsMap.put("teamCity", teamCity);


                            //team abbr is key
                            //Hashmap with stats is value
                            NBATeamDataSingleton.getInstance().getTeamDataMap().put(teamAbbrv, statsMap);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TeamData> call, Throwable t) {
                Log.d(TAG, "onFailure: Something went wrong: " + t.getMessage());
            }
        });
    }
}
