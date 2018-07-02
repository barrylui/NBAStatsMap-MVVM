package barrylui.myteam.MySportsFeedAPI;

import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

import barrylui.myteam.InternetUtilities.BasicAuthInterceptor;
import barrylui.myteam.MainActivity;
import barrylui.myteam.MySportsFeedAPI.MySportsFeedTeamStats.TeamData;
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

    //Fetches JSON data for stats of all 30 NBA Teams
    //Stores relevant data in Singleton Hashmap class
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
                            HashMap<String, Double> statsMap = new HashMap<String, Double>();

                            String teamAbbrv = response.body().getConferenceteamstandings().getConference().get(i).getTeamentry().get(j).getTeam().getAbbreviation();

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
