package barrylui.myteam.PlayerStats;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import barrylui.myteam.Data.Local.NBATeamRosterSingleton;
import barrylui.myteam.R;
import barrylui.myteam.Data.Local.NBATeamAssetsData;
import barrylui.myteam.Data.Repository.SuredBitsAPI.SuredBitsPlayerModel.PlayerInfoModel;

//Player Stats module
//View and compare player stats and visualize player strengths and weaknesses
public class PlayerStatsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Menus
    //Team select
    private RecyclerView Team1TeamSelectRecyclerView;
    private RecyclerView Team2TeamSelectRecyclerView;
    //Player select
    private RecyclerView Team1RosterRecyclerView;
    private RecyclerView Team2RosterReyclerView;

    //Headers
    private TextView slot1TextView;
    private TextView slot2TextView;

    //Player 1 stat fields
    private TextView player1HeaderTextView;
    private TextView player1PointsValueTextView;
    private TextView player1AssistsValueTextView;
    private TextView player1ReboundsValueTextView;
    private TextView player1BlocksValueTextView;
    private TextView player1StealsValueTextView;
    private TextView player1FreeThrowValueTextView;

    //Player 2 stat fields
    private TextView player2HeaderTextView;
    private TextView player2PointsValueTextView;
    private TextView player2AssistsValueTextView;
    private TextView player2ReboundsValueTextView;
    private TextView player2BlocksValueTextView;
    private TextView player2StealsValueTextView;
    private TextView player2FreeThrowValueTextView;

    //Back to team selection buttons to change ui states
    private Button backToTeamSelectionButton1;
    private Button backToTeamSelectionButton2;

    //Radar chart
    private ArrayList<Entry> player1ChartValueArray = new ArrayList<>();
    private ArrayList<Entry> player2ChartValueArray = new ArrayList<>();
    private ArrayList<String> labelsArrayForRadarChart;
    private RadarChart playerRadarChart;

    //Contains data for teams
    private NBATeamAssetsData nbaTeamData = new NBATeamAssetsData();

    LinearLayoutManager team1LayoutManager;
    LinearLayoutManager team2LayoutManager;
    LinearLayoutManager playerOnTeam1LayoutManager;
    LinearLayoutManager playerOnTeam2LayoutManager;

    //Recycleview adapters
    private Team1SelectRecycleViewAdapter team1selectAdapter = null;
    private Team2SelectRecycleViewAdapter team2selectAdapter = null;
    private Player1SelectRecyclerViewAdapter player1SelectAdapter = null;
    private Player2SelectRecyclerViewAdapter player2SelectAdapter = null;

    private PlayerStatsViewModel mViewModel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlayerStatsFragment() {
        // Required empty public constructor
    }

    public static PlayerStatsFragment newInstance(String param1, String param2) {
        PlayerStatsFragment fragment = new PlayerStatsFragment();
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
        View view = inflater.inflate(R.layout.fragment_player_stats, container, false);

        //Bind views
        playerRadarChart = (RadarChart)view.findViewById(R.id.player_radar_chart);

        Team1TeamSelectRecyclerView = (RecyclerView)view.findViewById(R.id.team1_select_recycleview);
        Team2TeamSelectRecyclerView = (RecyclerView)view.findViewById(R.id.team2_select_recycleview);

        Team1RosterRecyclerView = (RecyclerView)view.findViewById(R.id.team1_player_select_recyclerview);
        Team2RosterReyclerView = (RecyclerView)view.findViewById(R.id.team2_player_select_recyclerview);

        //Recyclerview / menu headers
        slot1TextView = (TextView)view.findViewById(R.id.slot1_header);
        slot2TextView = (TextView)view.findViewById(R.id.slot2_header);

        //Player 1 Stat text fields
        player1HeaderTextView = (TextView)view.findViewById(R.id.player1_header);
        player1PointsValueTextView = (TextView)view.findViewById(R.id.player1_points_Value_textView);
        player1AssistsValueTextView  = (TextView)view.findViewById(R.id.player1_assists_Value_textView);
        player1ReboundsValueTextView  = (TextView)view.findViewById(R.id.player1_rebounds_Value_textView);
        player1BlocksValueTextView  = (TextView)view.findViewById(R.id.player1_blocks_Value_textView);
        player1StealsValueTextView = (TextView)view.findViewById(R.id.player1_Steals_Value_textView);
        player1FreeThrowValueTextView  = (TextView)view.findViewById(R.id.player1_ft_Value_textView);

        //Player 2 stat text fields
        player2HeaderTextView = (TextView)view.findViewById(R.id.player2_header);
        player2PointsValueTextView = (TextView)view.findViewById(R.id.player2_offenseValue_textView);
        player2AssistsValueTextView  = (TextView)view.findViewById(R.id.player2_Assists_Value_textView);
        player2ReboundsValueTextView  = (TextView)view.findViewById(R.id.player2_rebounds_Value_textView);
        player2BlocksValueTextView  = (TextView)view.findViewById(R.id.player2_blocks_Value_textView);
        player2StealsValueTextView = (TextView)view.findViewById(R.id.player2_steals_Value_textView);
        player2FreeThrowValueTextView  = (TextView)view.findViewById(R.id.player2_ft_Value_textView);
        //Back button to change UI state
        backToTeamSelectionButton1 = (Button)view.findViewById(R.id.slot1BacktoTeamButton);
        backToTeamSelectionButton2 = (Button)view.findViewById(R.id.slot2BacktoTeamButton);

        //Set-up blank radar chart

        //Labels to label axis on radar chart
        labelsArrayForRadarChart = new ArrayList<String>();
        labelsArrayForRadarChart.add("POINTS");
        labelsArrayForRadarChart.add("ASSISTS");
        labelsArrayForRadarChart.add("REBOUNDS");
        labelsArrayForRadarChart.add("STEALS");
        labelsArrayForRadarChart.add("BLOCKS");
        labelsArrayForRadarChart.add("FT%");

        clearRadarArray(player1ChartValueArray);
        clearRadarArray(player2ChartValueArray);

        //Bind data to dataset
        final RadarDataSet playerDataSet1 = new RadarDataSet(player1ChartValueArray, "Team 1");
        final RadarDataSet playerDataSet2 = new RadarDataSet(player2ChartValueArray, "Team 2");

        playerDataSet1.setFillColor(getResources().getColor(R.color.colorHawksPrimary));
        playerDataSet1.setColor(getResources().getColor(R.color.colorHawksPrimary));
        setRadarDataSetSettings(playerDataSet1);

        playerDataSet2.setColor(getResources().getColor(R.color.colorMagicPrimary));
        playerDataSet2.setFillColor(getResources().getColor(R.color.colorMagicPrimary));
        setRadarDataSetSettings(playerDataSet2);

        //Array that will contain datasets for the radar chart
        final ArrayList<RadarDataSet> playerDataSets = new ArrayList<RadarDataSet>();
        playerDataSets.add(playerDataSet1);
        playerDataSets.add(playerDataSet2);
        //Bind data to radar chart
        RadarData thePlayerData = new RadarData(labelsArrayForRadarChart, playerDataSets);
        playerRadarChart.setData(thePlayerData);

        //Disable description
        Legend legend = playerRadarChart.getLegend();
        playerRadarChart.setDescription("");
        legend.setEnabled(false);

        playerRadarChart.getXAxis().setTextColor(Color.WHITE);
        YAxis yAxis = playerRadarChart.getYAxis();
        yAxis.resetAxisMaxValue();
        yAxis.setAxisMaxValue(100f);
        yAxis.setAxisMinValue(0);
        yAxis.setDrawLabels(false);

        //Update radar chart
        playerRadarChart.notifyDataSetChanged();
        playerRadarChart.invalidate();

        //Get the viewModel
        mViewModel = ViewModelProviders.of(this).get(PlayerStatsViewModel.class);

        //Player 1 LiveData on changed method
        //Binds text fields and radar chart to playerStatsData retrieved based on player selected
        final Observer<PlayerStatsObject> player1StatsObserver = new Observer<PlayerStatsObject>(){
            @Override
            public void onChanged(@Nullable PlayerStatsObject thePlayer1StatsObject){
                //Bind text views to appropriate stats
                player1HeaderTextView.setText(thePlayer1StatsObject.getPlayerName());
                player1PointsValueTextView.setText(String.valueOf(thePlayer1StatsObject.getPpg()));
                player1AssistsValueTextView.setText(String.valueOf(thePlayer1StatsObject.getApg()));
                player1ReboundsValueTextView.setText(String.valueOf(thePlayer1StatsObject.getRpg()));
                player1BlocksValueTextView.setText(String.valueOf(thePlayer1StatsObject.getBpg()));
                player1StealsValueTextView.setText(String.valueOf(thePlayer1StatsObject.getSpg()));

                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                String freethrowpercentage = decimalFormat.format(thePlayer1StatsObject.getFtp());
                player1FreeThrowValueTextView.setText(freethrowpercentage);

                //Clear RadarChart dataset
                clearRadarArray(player1ChartValueArray);

                //Add radar chart entry for player selected
                HashMap<String, Double> playerRadarChartValuesMap = mViewModel.getPlayerStatRanking(thePlayer1StatsObject);
                player1ChartValueArray.add(new Entry((float)(double)playerRadarChartValuesMap.get("PPG"), 0));
                player1ChartValueArray.add(new Entry((float)(double)playerRadarChartValuesMap.get("APG"), 1));
                player1ChartValueArray.add(new Entry((float)(double)playerRadarChartValuesMap.get("RPG"), 2));
                player1ChartValueArray.add(new Entry((float)(double)playerRadarChartValuesMap.get("SPG"), 3));
                player1ChartValueArray.add(new Entry((float)(double)playerRadarChartValuesMap.get("BPG"), 4));
                player1ChartValueArray.add(new Entry((float)(double)playerRadarChartValuesMap.get("FTP"), 5));

                //Update RadarChart
                playerRadarChart.notifyDataSetChanged();
                playerRadarChart.invalidate();
            }
        };
        //Player 2 LiveData on changed method
        //Binds text fields and radar chart to playerStatsData retrieved based on player selected
        final Observer<PlayerStatsObject> player2StatsObserver = new Observer<PlayerStatsObject>(){
            @Override
            public void onChanged(@Nullable PlayerStatsObject thePlayer2StatsObject){
                //Bind text views to appropriate stats
                player2HeaderTextView.setText(thePlayer2StatsObject.getPlayerName());
                player2PointsValueTextView.setText(String.valueOf(thePlayer2StatsObject.getPpg()));
                player2AssistsValueTextView.setText(String.valueOf(thePlayer2StatsObject.getApg()));
                player2ReboundsValueTextView.setText(String.valueOf(thePlayer2StatsObject.getRpg()));
                player2BlocksValueTextView.setText(String.valueOf(thePlayer2StatsObject.getBpg()));
                player2StealsValueTextView.setText(String.valueOf(thePlayer2StatsObject.getSpg()));

                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                String freethrowpercentage = decimalFormat.format(thePlayer2StatsObject.getFtp());
                player2FreeThrowValueTextView.setText(freethrowpercentage);

                //Clear RadarChart dataset
                clearRadarArray(player2ChartValueArray);

                //Add radar chart entry for player selected
                HashMap<String, Double> playerRadarChartValuesMap = mViewModel.getPlayerStatRanking(thePlayer2StatsObject);
                player2ChartValueArray.add(new Entry((float)(double)playerRadarChartValuesMap.get("PPG"), 0));
                player2ChartValueArray.add(new Entry((float)(double)playerRadarChartValuesMap.get("APG"), 1));
                player2ChartValueArray.add(new Entry((float)(double)playerRadarChartValuesMap.get("RPG"), 2));
                player2ChartValueArray.add(new Entry((float)(double)playerRadarChartValuesMap.get("SPG"), 3));
                player2ChartValueArray.add(new Entry((float)(double)playerRadarChartValuesMap.get("BPG"), 4));
                player2ChartValueArray.add(new Entry((float)(double)playerRadarChartValuesMap.get("FTP"), 5));

                //Update RadarChart
                playerRadarChart.notifyDataSetChanged();
                playerRadarChart.invalidate();
            }
        };

        mViewModel.getPlayer1Stats().observe(this, player1StatsObserver);
        mViewModel.getPlayer2Stats().observe(this, player2StatsObserver);

        //Hide Team Roster / Player Select recyclerView
        Team1RosterRecyclerView.setVisibility(View.INVISIBLE);
        Team2RosterReyclerView.setVisibility(View.INVISIBLE);

        //Hide back to team selection button
        backToTeamSelectionButton1.setVisibility(View.INVISIBLE);
        backToTeamSelectionButton2.setVisibility(View.INVISIBLE);

        Team1TeamSelectRecyclerView.setHasFixedSize(false);
        Team2TeamSelectRecyclerView.setHasFixedSize(false);
        Team1RosterRecyclerView.setHasFixedSize(false);
        Team2RosterReyclerView.setHasFixedSize(false);

        //Setup Adapters here
        team1LayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        team2LayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        playerOnTeam1LayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        playerOnTeam2LayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        Team1TeamSelectRecyclerView.setLayoutManager(team1LayoutManager);
        Team2TeamSelectRecyclerView.setLayoutManager(team2LayoutManager);
        Team1RosterRecyclerView.setLayoutManager(playerOnTeam1LayoutManager);
        Team2RosterReyclerView.setLayoutManager(playerOnTeam2LayoutManager);

        team1selectAdapter = new Team1SelectRecycleViewAdapter(getActivity(), nbaTeamData.getTeamsList());
        team2selectAdapter = new Team2SelectRecycleViewAdapter(getActivity(), nbaTeamData.getTeamsList());

        Team1TeamSelectRecyclerView.setAdapter(team1selectAdapter);
        Team2TeamSelectRecyclerView.setAdapter(team2selectAdapter);

        team1selectAdapter.notifyDataSetChanged();
        team2selectAdapter.notifyDataSetChanged();

        //Back to team selection button
        backToTeamSelectionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hide button
                backToTeamSelectionButton1.setVisibility(View.INVISIBLE);
                //Hide team roster / player select recycler view
                Team1RosterRecyclerView.setVisibility(View.INVISIBLE);
                //Show team select recyclerview
                Team1TeamSelectRecyclerView.setVisibility(View.VISIBLE);
                //Change header
                slot1TextView.setText(getString(R.string.slot1));

                //Clear chart and text fields of player data

                //Clear text fields
                clearTextFields(1);
                //Clear chartvaluearray
                clearRadarArray(player1ChartValueArray);
                //Update radar chart with empty data to display blank radar chart
                playerRadarChart.notifyDataSetChanged();
                playerRadarChart.invalidate();
            }
        });

        //Back to team selection button
        backToTeamSelectionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hide button
                backToTeamSelectionButton2.setVisibility(View.INVISIBLE);
                //Hide team roster / player select recycler view
                Team2RosterReyclerView.setVisibility(View.INVISIBLE);
                //Show team select recyclerview
                Team2TeamSelectRecyclerView.setVisibility(View.VISIBLE);
                //Change header
                slot2TextView.setText(getString(R.string.slot1));

                //Clear chart and text fields of player data
                //Clear text fields
                clearTextFields(2);
                //Clear chartvaluearray
                clearRadarArray(player2ChartValueArray);
                //Update radar chart with empty data to display blank radar chart
                playerRadarChart.notifyDataSetChanged();
                playerRadarChart.invalidate();
            }
        });

        //Behavior for when user taps the initial recyclerview to select a team to view the team's roster
        //Hide the team menu, display the roster menu, change menu header, make back button visible to go back to team selected
        team1selectAdapter.SetOnTeam1SelectClickListener(new Team1SelectRecycleViewAdapter.OnTeam1SelectClickListener() {
            @Override
            public void onTeam1SelectClick(View view, int position, String teamAbbrv, int color) {
                //Show team roster / player select recycler view
                Team1RosterRecyclerView.setVisibility(View.VISIBLE);
                //Get team's list of players
                List<PlayerInfoModel> teamRoster = NBATeamRosterSingleton.getInstance().getTeamRosterHashMap().get(teamAbbrv);
                //Bind list to team roster / player select recyclerview
                player1SelectAdapter = new Player1SelectRecyclerViewAdapter(getActivity(), teamRoster);
                //Setup Adapter
                Team1RosterRecyclerView.setAdapter(player1SelectAdapter);
                player1SelectAdapter.notifyDataSetChanged();

                //Hide team select recyclerview
                Team1TeamSelectRecyclerView.setVisibility(View.INVISIBLE);
                //Change header
                slot1TextView.setText(getString(R.string.slot2));
                //Show back to team selection button
                backToTeamSelectionButton1.setVisibility(View.VISIBLE);

                player1SelectAdapter.SetOnTeam1SelectClickListener(new Player1SelectRecyclerViewAdapter.OnPlayer1SelectClickListener() {
                    @Override
                    public void onPlayer1SelectClick(View view, int position, String playerName, boolean currentItemSelected) {
                        //If the user taps a player that is currently selected, deselect the player and unbind the data from the text fields and radar chart
                        if(currentItemSelected == true){
                            //Clear text fields and radar chart array
                            clearTextFields(1);
                            clearRadarArray(player1ChartValueArray);
                            //Update radar chart with empty data to display blank radar chart
                            playerRadarChart.notifyDataSetChanged();
                            playerRadarChart.invalidate();
                        }
                        //Else load the player's data and bind it to the text fields and radar chart
                        else{
                            PlayerStatsObject playerStats1Object = mViewModel.createPlayerStatsObject(playerName);
                            mViewModel.getPlayer1Stats().setValue(playerStats1Object);

                        }
                    }
                });

            }
        });

        //Behavior for when user taps the initial recyclerview to select a team to view the team's roster
        //Hide the team menu, display the roster menu, change menu header, make back button visible to go back to team selected
        team2selectAdapter.SetOnTeam2SelectClickListener(new Team2SelectRecycleViewAdapter.OnTeam2SelectClickListener() {
            @Override
            public void onTeam2SelectClick(View view, int position, String teamAbbrv, int color) {
                //Show team roster / player select recycler view
                Team2RosterReyclerView.setVisibility(View.VISIBLE);
                //Get team's list of players
                List<PlayerInfoModel> teamRoster = NBATeamRosterSingleton.getInstance().getTeamRosterHashMap().get(teamAbbrv);
                //Bind list to team roster / player select recyclerview
                player2SelectAdapter = new Player2SelectRecyclerViewAdapter(getActivity(), teamRoster);
                //Setup Adapter
                Team2RosterReyclerView.setAdapter(player2SelectAdapter);
                player2SelectAdapter.notifyDataSetChanged();
                //Hide team select recyclerview
                Team2TeamSelectRecyclerView.setVisibility(View.INVISIBLE);
                //Change header
                slot2TextView.setText(getString(R.string.slot2));
                //Show back to team selection button
                backToTeamSelectionButton2.setVisibility(View.VISIBLE);

                player2SelectAdapter.SetOnTeam2SelectClickListener(new Player2SelectRecyclerViewAdapter.OnPlayer2SelectClickListener() {
                    @Override
                    public void onPlayer2SelectClick(View view, int position, String playerName, boolean currentItemSelected) {
                        if(currentItemSelected == true){
                            //Clear text fields and radar chart array
                            clearTextFields(2);
                            clearRadarArray(player2ChartValueArray);
                            //Update radar chart with empty data to display blank radar chart
                            playerRadarChart.notifyDataSetChanged();
                            playerRadarChart.invalidate();
                        }
                        //Else load the player's data and bind it to the text fields and radar chart
                        else{
                            PlayerStatsObject playerStats2Object = mViewModel.createPlayerStatsObject(playerName);
                            mViewModel.getPlayer2Stats().setValue(playerStats2Object);
                        }
                    }
                });
            }
        });
        return view;
    }

    //configures settings for radarchart
    public void setRadarDataSetSettings(RadarDataSet radarDataSet){
        //Sets the alpha value (transparency) that is used for filling the line surface (0-255), default: 85, 255 = fully opaque, 0 = fully transparent
        radarDataSet.setFillAlpha(100);
        //Set the line width for this DataSet (min = 0.2f, max = 10f); default 1f NOTE: thinner line == better performance, thicker line == worse performance
        radarDataSet.setLineWidth(4f);
        radarDataSet.setDrawValues(false);
        //Set to true if the DataSet should be drawn filled (surface, area)
        radarDataSet.setDrawFilled(true);
    }


    //Resets text stats fields to empty strings and provides the appropriate empty header
    public void clearTextFields(int slot){
        if (slot == 1){
            player1PointsValueTextView.setText(getString(R.string.empty_string));
            player1AssistsValueTextView.setText(getString(R.string.empty_string));
            player1ReboundsValueTextView.setText(getString(R.string.empty_string));
            player1BlocksValueTextView.setText(getString(R.string.empty_string));
            player1StealsValueTextView.setText(getString(R.string.empty_string));
            player1FreeThrowValueTextView.setText(getString(R.string.empty_string));
            //Clear player name
            player1HeaderTextView.setText(getString(R.string.player1));
        }
        if (slot == 2){
            player2PointsValueTextView.setText(getString(R.string.empty_string));
            player2AssistsValueTextView.setText(getString(R.string.empty_string));
            player2ReboundsValueTextView.setText(getString(R.string.empty_string));
            player2BlocksValueTextView.setText(getString(R.string.empty_string));
            player2StealsValueTextView.setText(getString(R.string.empty_string));
            player2FreeThrowValueTextView.setText(getString(R.string.empty_string));
            //Clear player name
            player2HeaderTextView.setText(getString(R.string.player2));
        }
    }

    //Method to clear radar chart dataset
    public void clearRadarArray(ArrayList<Entry> radarEntryArray){
        radarEntryArray.clear();

        radarEntryArray.add(new Entry(0, 0));
        radarEntryArray.add(new Entry(0, 1));
        radarEntryArray.add(new Entry(0, 2));
        radarEntryArray.add(new Entry(0, 3));
        radarEntryArray.add(new Entry(0, 4));
        radarEntryArray.add(new Entry(0, 5));
    }
}
