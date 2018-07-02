package barrylui.myteam;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.HashMap;

import barrylui.myteam.MySportsFeedAPI.MySportsFeedRetrofitClient;

public class SplashLoadingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int DISPLAY_TIME = 3000;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    HashMap<String , HashMap<String, ?>> teamStatsHashMap = new HashMap<>();




    public SplashLoadingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SplashLoadingFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        final DataLoadedLaunchApplication mListener = (DataLoadedLaunchApplication)getContext();
        View rootView = inflater.inflate(R.layout.fragment_splash_loading, container, false);

        //Fetches JSON data for stats of all 30 NBA Teams
        //Stores relevant data in Singleton Hashmap class
        MySportsFeedRetrofitClient.getTeamData();
        MySportsFeedRetrofitClient.getPlayerData();
        //Displays splash screen for 4 seconds while the the JSON file is fetched
        Handler mHandler = new Handler();
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                //launches application
                mListener.dataLoadLaunchApplication();
            }
        };
        mHandler.postDelayed(mRunnable, DISPLAY_TIME);
        return rootView;

    }


    //interface to communicate with parent activity to start the application
    public interface DataLoadedLaunchApplication {
        public void dataLoadLaunchApplication();
    }
}
