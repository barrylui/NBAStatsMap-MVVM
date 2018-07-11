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

import barrylui.myteam.R;
import barrylui.myteam.Data.NBATeamAssetsData;


public class PlayerStatsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView slot1Team1;
    private RecyclerView slot2Team2;
    private RecyclerView slot1Player1;
    private RecyclerView slot2Player2;

    private TextView slot1TextView;
    private TextView slot2TextView;

    private Button slot1Button;
    private Button slot2Button;

    private ArrayList<Entry> playerentry1 = new ArrayList<>();
    private ArrayList<Entry> playerentry2 = new ArrayList<>();
    private ArrayList<String> labelsArrayForRadarChart;
    private RadarChart playerRadarChart;

    private NBATeamAssetsData nbaTeamData = new NBATeamAssetsData();
    LinearLayoutManager team1LayoutManager;
    LinearLayoutManager team2LayoutManager;
    LinearLayoutManager playerOnTeam1LayoutManager;
    LinearLayoutManager playOnTeam2LayoutManager;

    private Team1SelectRecycleViewAdapter team1selectAdapter = null;
    private Team2SelectRecycleViewAdapter team2selectAdapter = null;


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



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_stats, container, false);


        playerRadarChart = (RadarChart)view.findViewById(R.id.player_radar_chart);

        slot1Team1 = (RecyclerView)view.findViewById(R.id.team1_select_recycleview);
        slot2Team2 = (RecyclerView)view.findViewById(R.id.team2_select_recycleview);

        slot1Player1 = (RecyclerView)view.findViewById(R.id.team1_player_select_recyclerview);
        slot2Player2 = (RecyclerView)view.findViewById(R.id.team2_player_select_recyclerview);

        slot1TextView = (TextView)view.findViewById(R.id.slot1_header);
        slot2TextView = (TextView)view.findViewById(R.id.slot2_header);

        slot1Button = (Button)view.findViewById(R.id.slot1BacktoTeamButton);
        slot2Button = (Button)view.findViewById(R.id.slot2BacktoTeamButton);

        slot1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot1Button.setVisibility(View.INVISIBLE);
                slot1Player1.setVisibility(View.INVISIBLE);
                slot1Team1.setVisibility(View.VISIBLE);
                slot1TextView.setText(getString(R.string.slot1));
            }
        });


        slot2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot2Button.setVisibility(View.INVISIBLE);
                slot2Player2.setVisibility(View.INVISIBLE);
                slot2Team2.setVisibility(View.VISIBLE);
                slot2TextView.setText(getString(R.string.slot1));
            }
        });

        slot1Player1.setVisibility(View.INVISIBLE);
        slot2Player2.setVisibility(View.INVISIBLE);

        slot1Button.setVisibility(View.INVISIBLE);
        slot2Button.setVisibility(View.INVISIBLE);

        slot1Team1.setHasFixedSize(false);
        slot2Team2.setHasFixedSize(false);
        slot1Player1.setHasFixedSize(false);
        slot2Player2.setHasFixedSize(false);

        //Setup Adapters here

        team1LayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        team2LayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        slot1Team1.setLayoutManager(team1LayoutManager);
        slot2Team2.setLayoutManager(team2LayoutManager);

        team1selectAdapter = new Team1SelectRecycleViewAdapter(getActivity(), nbaTeamData.getTeamsList());
        team2selectAdapter = new Team2SelectRecycleViewAdapter(getActivity(), nbaTeamData.getTeamsList());

        slot1Team1.setAdapter(team1selectAdapter);
        slot2Team2.setAdapter(team2selectAdapter);

        team1selectAdapter.notifyDataSetChanged();
        team2selectAdapter.notifyDataSetChanged();

        team1selectAdapter.SetOnTeam1SelectClickListener(new Team1SelectRecycleViewAdapter.OnTeam1SelectClickListener() {
            @Override
            public void onTeam1SelectClick(View view, int position, String teamAbbrv, int color) {
                slot1Team1.setVisibility(View.INVISIBLE);
                slot1Player1.setVisibility(View.VISIBLE);
                slot1TextView.setText(getString(R.string.slot2));
                slot1Player1.setVisibility(View.VISIBLE);
                slot1Button.setVisibility(View.VISIBLE);
            }
        });

        team2selectAdapter.SetOnTeam2SelectClickListener(new Team2SelectRecycleViewAdapter.OnTeam2SelectClickListener() {
            @Override
            public void onTeam2SelectClick(View view, int position, String teamAbbrv, int color) {
                slot2Team2.setVisibility(View.INVISIBLE);
                slot2Player2.setVisibility(View.VISIBLE);
                slot2TextView.setText(getString(R.string.slot2));
                slot2Player2.setVisibility(View.VISIBLE);
                slot2Button.setVisibility(View.VISIBLE);
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
