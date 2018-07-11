package barrylui.myteam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.HashMap;

import barrylui.myteam.Data.NBATeamRosterSingleton;
import barrylui.myteam.MySportsFeedAPI.MySportsFeedRetrofitClient;
import barrylui.myteam.Data.NBAPlayerDataSingleton;
import barrylui.myteam.Data.NBATeamDataSingleton;
import barrylui.myteam.SuredBitsAPI.SuredBitsAPIRetrofitClient;

public class SplashLoadingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int DISPLAY_TIME = 5500; //Time to display splash screen, 5000 = 5 seconds

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    HashMap<String , HashMap<String, ?>> teamStatsHashMap = new HashMap<>();




    public SplashLoadingFragment() {
        // Required empty public constructor
    }

    public static SplashLoadingFragment newInstance(String param1, String param2) {
        SplashLoadingFragment fragment = new SplashLoadingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        final FetchDataAndLaunch mListener = (FetchDataAndLaunch)getContext();
        View rootView = inflater.inflate(R.layout.fragment_splash_loading, container, false);

        //Fetches JSON data for stats of all 30 NBA Teams
        //Stores relevant data in Singleton Hashmap class
        MySportsFeedRetrofitClient.getTeamData();


        MySportsFeedRetrofitClient.getPlayerData();


        SuredBitsAPIRetrofitClient.loadTeamRosters();
        //Displays splash screen for 5 seconds while the the JSON files are fetched



        Handler mHandler = new Handler();
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                //Runnable waits 5 secs to wait for application to load data
                //Checks to see if data loaded
                //If data is not loaded, show an alert dialog that shuts down the application once "OK" is pressed
                final CallOnFinish bListener = (CallOnFinish) getContext();
                if(NBATeamDataSingleton.getInstance().getTeamDataMap().isEmpty() || NBAPlayerDataSingleton.getInstance().getPlayerDataMap().isEmpty()|| NBATeamRosterSingleton.getInstance().getTeamRosterHashMap().isEmpty()){
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle(getString(R.string.dataDidNotLoad));
                    alertDialog.setMessage(getString(R.string.dataDidNotLoadMsg));
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dataDidNotLoadDialogButtonString), new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            bListener.callOnFinish();
                        }
                    });

                    alertDialog.show();

                }else{
                    mListener.fetchdataAndLaunch();//Starts application
                }

            }
        };
        mHandler.postDelayed(mRunnable, DISPLAY_TIME);
        return rootView;
    }


    //interface to communicate with parent activity to start the application
    public interface FetchDataAndLaunch {
        public void fetchdataAndLaunch();
    }

    public interface DataDidNotLoad{
        public void dataDidNotLoad();
    }

    public interface CallOnFinish{
        public void callOnFinish();
    }
}
