package barrylui.myteam.Data.Repository.SuredBitsAPI;

import android.util.Log;

import java.util.List;

import barrylui.myteam.Data.Local.NBATeamAssetsData;
import barrylui.myteam.Data.Local.NBATeamRosterSingleton;
import barrylui.myteam.Data.Repository.SuredBitsAPI.SuredBitsPlayerModel.PlayerInfoModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SuredBitsAPIRetrofitClient {
    private static SuredBitsAPIService suredBitsAPIService;
    private static Retrofit suredBitsRetrofit;
    private static final String BASE_URL = SuredBitsAPIService.BASE_URL;
    private static String TAG = "SuredBits";
    private static NBATeamAssetsData nbaTeamData = new NBATeamAssetsData();

    public static Retrofit getInstance(){
        if (suredBitsRetrofit == null){
            suredBitsRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return suredBitsRetrofit;
    }

    public static void loadTeamRosters(){
        SuredBitsAPIService suredBitsAPIService = SuredBitsAPIRetrofitClient.getInstance().create(SuredBitsAPIService.class);

        //For each NBA Team load the suredBitsData and store it in the NBATeamRosterSingleton
        for(int i=0; i<nbaTeamData.getSize(); i++){
            final String name = (String)nbaTeamData.getTeamsList().get(i).get("name");
            String teamName = name;
            //Handle the name difference so the suredbits api call can be made
            if(teamName.equals("OKL")){
                teamName = "OKC";
            }
            if(name.equals("BRO")){
                teamName = "BKN";
            }

            Call<List<PlayerInfoModel>> rosterCall = suredBitsAPIService.getTeamInfo(teamName);
            rosterCall.enqueue(new Callback<List<PlayerInfoModel>>() {
                @Override
                public void onResponse(Call<List<PlayerInfoModel>> call, Response<List<PlayerInfoModel>> response) {
                    //If connection is successful
                    if (response.code()==200){
                        //get list of players on team
                        List<PlayerInfoModel> playersOnTeamRoster = response.body();
                        //Store data in singleton team roster instance
                        NBATeamRosterSingleton.getInstance().getTeamRosterHashMap().put(name, playersOnTeamRoster);
                    }
                }

                @Override
                public void onFailure(Call<List<PlayerInfoModel>> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                }
            });
        }

    }
}
