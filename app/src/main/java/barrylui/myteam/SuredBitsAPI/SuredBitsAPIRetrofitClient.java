package barrylui.myteam.SuredBitsAPI;

import android.util.Log;

import java.util.List;

import barrylui.myteam.Data.NBATeamAssetsData;
import barrylui.myteam.Data.NBATeamRosterSingleton;
import barrylui.myteam.SuredBitsAPI.SuredBitsPlayerModel.PlayerInfoModel;
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
            final String teamName = (String)nbaTeamData.getTeamsList().get(i).get("name");
            Call<List<PlayerInfoModel>> rosterCall = suredBitsAPIService.getTeamInfo(teamName);
            rosterCall.enqueue(new Callback<List<PlayerInfoModel>>() {
                @Override
                public void onResponse(Call<List<PlayerInfoModel>> call, Response<List<PlayerInfoModel>> response) {
                    if (response.code()==200){
                        List<PlayerInfoModel> playersOnTeamRoster = response.body();
                        NBATeamRosterSingleton.getInstance().getTeamRosterHashMap().put(teamName, playersOnTeamRoster);
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
