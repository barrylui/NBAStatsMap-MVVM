package barrylui.myteam.PlayerStats;

import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import barrylui.myteam.Data.NBATeamRosterSingleton;
import barrylui.myteam.R;
import barrylui.myteam.Data.NBATeamAssetsData;
import barrylui.myteam.SuredBitsAPI.SuredBitsPlayerModel.PlayerInfoModel;


public class PlayerStatsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView Team1TeamSelectRecyclerView;
    private RecyclerView Team2TeamSelectRecyclerView;
    private RecyclerView Team1RosterRecyclerView;
    private RecyclerView Team2RosterReyclerView;

    private TextView slot1TextView;
    private TextView slot2TextView;

    private Button backToTeamSelectionButton1;
    private Button backToTeamSelectionButton2;

    private ArrayList<Entry> playerentry1 = new ArrayList<>();
    private ArrayList<Entry> playerentry2 = new ArrayList<>();
    private ArrayList<String> labelsArrayForRadarChart;
    private RadarChart playerRadarChart;

    private NBATeamAssetsData nbaTeamData = new NBATeamAssetsData();
    LinearLayoutManager team1LayoutManager;
    LinearLayoutManager team2LayoutManager;
    LinearLayoutManager playerOnTeam1LayoutManager;
    LinearLayoutManager playerOnTeam2LayoutManager;

    private Team1SelectRecycleViewAdapter team1selectAdapter = null;
    private Team2SelectRecycleViewAdapter team2selectAdapter = null;
    private Player1SelectRecyclerViewAdapter player1SelectAdapter = null;
    private Player2SelectRecyclerViewAdapter player2SelectAdapter = null;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

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
        playerentry1.clear();
        playerentry2.clear();

        View view = inflater.inflate(R.layout.fragment_player_stats, container, false);


        //Bind views
        playerRadarChart = (RadarChart)view.findViewById(R.id.player_radar_chart);

        Team1TeamSelectRecyclerView = (RecyclerView)view.findViewById(R.id.team1_select_recycleview);
        Team2TeamSelectRecyclerView = (RecyclerView)view.findViewById(R.id.team2_select_recycleview);

        Team1RosterRecyclerView = (RecyclerView)view.findViewById(R.id.team1_player_select_recyclerview);
        Team2RosterReyclerView = (RecyclerView)view.findViewById(R.id.team2_player_select_recyclerview);

        slot1TextView = (TextView)view.findViewById(R.id.slot1_header);
        slot2TextView = (TextView)view.findViewById(R.id.slot2_header);

        backToTeamSelectionButton1 = (Button)view.findViewById(R.id.slot1BacktoTeamButton);
        backToTeamSelectionButton2 = (Button)view.findViewById(R.id.slot2BacktoTeamButton);


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
            }
        });


        labelsArrayForRadarChart = new ArrayList<String>();
        labelsArrayForRadarChart.add("Points");
        labelsArrayForRadarChart.add("Assists");
        labelsArrayForRadarChart.add("Rebounds");
        labelsArrayForRadarChart.add("Blocks");
        labelsArrayForRadarChart.add("Steals");
        labelsArrayForRadarChart.add("FT%");


        if(playerentry1.isEmpty()){
            playerentry1.add(new Entry(0, 0));
            playerentry1.add(new Entry(0, 1));
            playerentry1.add(new Entry(0, 2));
            playerentry1.add(new Entry(0, 3));
            playerentry1.add(new Entry(0, 4));
            playerentry1.add(new Entry(0, 5));
        }

        if(playerentry2.isEmpty()){
            playerentry2.add(new Entry(0, 0));
            playerentry2.add(new Entry(0, 1));
            playerentry2.add(new Entry(0, 2));
            playerentry2.add(new Entry(0, 3));
            playerentry2.add(new Entry(0, 4));
            playerentry2.add(new Entry(0, 5));
        }

        final RadarDataSet playerDataSet1 = new RadarDataSet(playerentry1, "Team 1");
        final RadarDataSet playerDataSet2 = new RadarDataSet(playerentry2, "Team 2");

        playerDataSet1.setFillAlpha(100);
        playerDataSet1.setLineWidth(4f);
        playerDataSet1.setDrawValues(false);
        playerDataSet1.setDrawFilled(true);

        playerDataSet2.setFillAlpha(100);
        playerDataSet2.setLineWidth(4f);
        playerDataSet2.setDrawValues(false);
        playerDataSet2.setDrawFilled(true);

        final ArrayList<RadarDataSet> playerDataSets = new ArrayList<RadarDataSet>();
        playerDataSets.add(playerDataSet1);
        playerDataSets.add(playerDataSet2);
        RadarData thePlayerData = new RadarData(labelsArrayForRadarChart, playerDataSets);
        playerRadarChart.setData(thePlayerData);

        //Disable description
        Legend legend = playerRadarChart.getLegend();
        playerRadarChart.setDescription("");
        legend.setEnabled(false);

        playerRadarChart.getXAxis().setTextColor(Color.WHITE);
        YAxis yAxis = playerRadarChart.getYAxis();
        yAxis.resetAxisMaxValue();
        yAxis.setAxisMaxValue(100);
        yAxis.setAxisMinValue(0);
        yAxis.setDrawLabels(false);

        playerRadarChart.notifyDataSetChanged();
        playerRadarChart.invalidate();


        return view;
    }
}
