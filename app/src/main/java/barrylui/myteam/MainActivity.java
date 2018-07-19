package barrylui.myteam;

import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.view.View;

import java.io.Serializable;

import barrylui.myteam.About.AboutFragment;
import barrylui.myteam.PlayerStats.PlayerStatsFragment;
import barrylui.myteam.TeamStats.TeamStatsFragment;


public class MainActivity extends AppCompatActivity implements SplashLoadingFragment.FetchDataAndLaunch, Serializable, SplashLoadingFragment.DataDidNotLoadRestartApp {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    private static BottomNavigationView navigation;
    //private static HashMap<String, HashMap<String, Double>> teamDataMap =
            //new HashMap<String, HashMap<String, Double>>();
    private static String TAG = "MainActivity";
    private static AboutFragment aboutFragment = new AboutFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Forces phone to maintain portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        //Setup bottom navigation bar
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //Hide bottom navigation bar while splash screen is displaying
        navigation.setVisibility(View.GONE);
        loadFragment(new SplashLoadingFragment());
    }


    //Navigation bar listener
    //Loads fragment into container based on item selected
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.navigation_team_stats:
                    if (getSupportFragmentManager().findFragmentById(R.id.frame_container) instanceof TeamStatsFragment){
                        //do nothing, the selected fragment is the current fragment
                        return true;
                    }
                    else {
                        fragment = new TeamStatsFragment();
                        loadFragment(fragment);
                        return true;
                    }

                case R.id.navigation_player_stats:
                    if (getSupportFragmentManager().findFragmentById(R.id.frame_container) instanceof PlayerStatsFragment){
                        //do nothing, the selected fragment is the current fragment
                        return true;
                    }
                    else {
                        fragment = new PlayerStatsFragment();
                        loadFragment(fragment);
                        return true;
                    }
                case R.id.navigation_about:
                    if (getSupportFragmentManager().findFragmentById(R.id.frame_container) instanceof AboutFragment){
                        //do nothing, the selected fragment is the current fragment
                        return true;
                    }
                    else {
                        fragment = new AboutFragment();
                        loadFragment(fragment);
                        return true;
                    }
            }
            return false;
        }
    };





    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
    //Starts the application after loading the data
    //Makes the bottom navigation bar visibile and loads the teamstats fragment
    public void fetchdataAndLaunch(){
        navigation.setVisibility(View.VISIBLE);
        loadFragment(aboutFragment);
        //getWindow().clearFlags(FLAG_FULLSCREEN);
    }

    //If JSON data could not be fetched close the application
    public void dataDidNotLoadRestartApp(){
        finish();
    }
}
