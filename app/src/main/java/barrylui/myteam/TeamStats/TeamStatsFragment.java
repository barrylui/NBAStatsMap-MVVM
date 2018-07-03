package barrylui.myteam.TeamStats;

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
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import barrylui.myteam.TeamDataNBA.NBAPlayerDataSingleton;
import barrylui.myteam.TeamDataNBA.NBATeamAssetsData;
import barrylui.myteam.R;
import barrylui.myteam.TeamDataNBA.NBATeamDataSingleton;


public class TeamStatsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView team1Menu;
    private RecyclerView team2Menu;
    private TextView team1OffenseValue;
    private TextView team1DefenseValue;
    private TextView team1AssistsValue;
    private TextView team1ReboundsValue;
    private TextView team1ThreePointValue;
    private TextView team1FreeThrowValue;
    private TextView team1TeamName;
    private TextView team1OffenseRank;
    private TextView team1DefenseRank;
    private TextView team1AssistsRank;
    private TextView team1ReboundsRank;
    private TextView team1ThreePointRank;
    private TextView team1FreeThrowRank;

    private TextView team2OffenseValue;
    private TextView team2DefenseValue;
    private TextView team2AssistsValue;
    private TextView team2ReboundsValue;
    private TextView team2ThreePointValue;
    private TextView team2FreeThrowValue;
    private TextView team2TeamName;
    private TextView team2OffenseRank;
    private TextView team2DefenseRank;
    private TextView team2AssistsRank;
    private TextView team2ReboundsRank;
    private TextView team2ThreePointRank;
    private TextView team2FreeThrowRank;

    private ArrayList<Entry> entry1 = new ArrayList<>();
    private ArrayList<Entry> entry2 = new ArrayList<>();
    private NBATeamAssetsData nbaTeamData = new NBATeamAssetsData();
    LinearLayoutManager aLayoutManager;
    LinearLayoutManager bLayoutManager;
    private Team1RecyclerViewAdapter team1Adapter = null;
    private Team2RecyclerViewAdapter team2Adapter = null;
    private ArrayList<String> labels;
    private RadarChart radarChart;
    private TeamStatsViewModel mViewModel;

    //private OnFragmentInteractionListener mListener;

    public TeamStatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamStatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamStatsFragment newInstance(String param1, String param2) {
        TeamStatsFragment fragment = new TeamStatsFragment();
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

    public interface DataDidNotLoad{
        public void dataDidNotLoad();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //Checks to see if data loaded
        //If data is not loaded, inflate error fragment
        final DataDidNotLoad mListener = (DataDidNotLoad)getContext();
        if(NBATeamDataSingleton.getInstance().getTeamDataMap().isEmpty() || NBAPlayerDataSingleton.getInstance().getPlayerDataMap().isEmpty()){
            mListener.dataDidNotLoad();
        }

        View view = inflater.inflate(R.layout.fragment_team_stats, container, false);




        //Bind views


        radarChart = (RadarChart)view.findViewById(R.id.radarchart);

        //Selector menus for teams
        team1Menu = (RecyclerView)view.findViewById(R.id.team1_select_recycleview);
        team2Menu = (RecyclerView)view.findViewById(R.id.team2_recyclerview);

        //Team 1 Stat TextViews
        team1TeamName = (TextView)view.findViewById(R.id.Team1NameTextView);
        team1OffenseValue = (TextView)view.findViewById(R.id.offenseTeam1ValueTextView);
        team1DefenseValue = (TextView)view.findViewById(R.id.defenseTeam1ValueTextView);
        team1AssistsValue = (TextView)view.findViewById(R.id.assistsTeam1ValueTextView);
        team1ReboundsValue = (TextView)view.findViewById(R.id.reboundsTeam1ValueTextView);
        team1ThreePointValue = (TextView)view.findViewById(R.id.threepointTeam1ValueTextView);
        team1FreeThrowValue = (TextView)view.findViewById(R.id.freethrowTeam1ValueTextView);

        team1OffenseRank = (TextView) view.findViewById(R.id.offenseTeam1RankTextView);
        team1DefenseRank = (TextView) view.findViewById(R.id.defenseTeam1RankTextView);
        team1AssistsRank = (TextView) view.findViewById(R.id.assistsTeam1RankTextView);
        team1ReboundsRank = (TextView) view.findViewById(R.id.reboundsTeam1RankTextView);
        team1ThreePointRank = (TextView) view.findViewById(R.id.threepointTeam1RankTextView);
        team1FreeThrowRank = (TextView) view.findViewById(R.id.freethrowTeam1RankTextView);

        //Team 2 Stat TextViews
        team2OffenseValue = (TextView)view.findViewById(R.id.offenseTeam2ValueTextView);
        team2DefenseValue = (TextView)view.findViewById(R.id.defenseTeam2ValueTextView);
        team2AssistsValue = (TextView)view.findViewById(R.id.assistsTeam2ValueTextView);
        team2ReboundsValue = (TextView)view.findViewById(R.id.reboundsTeam2ValueTextView);
        team2ThreePointValue = (TextView)view.findViewById(R.id.threepointTeam2ValueTextView);
        team2FreeThrowValue = (TextView)view.findViewById(R.id.freethrowTeam2ValueTextView);
        team2TeamName = (TextView)view.findViewById(R.id.Team2NameTextView);

        team2OffenseRank = (TextView) view.findViewById(R.id.offenseTeam2RankTextView);
        team2DefenseRank = (TextView) view.findViewById(R.id.defenseTeam2RankTextView);
        team2AssistsRank = (TextView) view.findViewById(R.id.assistsTeam2RankTextView);
        team2ReboundsRank = (TextView) view.findViewById(R.id.reboundsTeam2RankTextView);
        team2ThreePointRank = (TextView) view.findViewById(R.id.threepointTeam2RankTextView);
        team2FreeThrowRank = (TextView) view.findViewById(R.id.freethrowTeam2RankTextView);




        //Set up Blank Radar Chart


        //Label axis on radar chart
        labels = new ArrayList<String>();
        labels.add("REBOUNDS");
        labels.add("OFFENSE");
        labels.add("DEFENSE");
        labels.add("ASSISTS");
        labels.add("FT%");
        labels.add("3PT SCORING");

        //Setup blank data for blank chart to be displayed

        //Empty dataset
        entry1.add(new Entry(0,0));
        entry1.add(new Entry(0,1));
        entry1.add(new Entry(0,2));
        entry1.add(new Entry(0,3));
        entry1.add(new Entry(0,4));
        entry1.add(new Entry(0,5));

        entry2.add(new Entry(0,0));
        entry2.add(new Entry(0,1));
        entry2.add(new Entry(0,2));
        entry2.add(new Entry(0,3));
        entry2.add(new Entry(0,4));
        entry2.add(new Entry(0,5));

        //Setup radarchart settings and bind radar chart
        RadarDataSet dataset1 = new RadarDataSet(entry1,"Team 1");
        RadarDataSet dataset2 = new RadarDataSet(entry2, "Team 2");

        //Draw settings
        dataset1.setFillAlpha(180);
        dataset1.setLineWidth(5f);
        dataset1.setDrawValues(false);

        //Add datasets to radarChart
        ArrayList<RadarDataSet> dataSets= new ArrayList<RadarDataSet>();
        dataSets.add(dataset1);
        dataSets.add(dataset2);
        RadarData theradardata = new RadarData(labels, dataSets);
        radarChart.setData(theradardata);

        //disable desciption
        Legend l = radarChart.getLegend();
        radarChart.setDescription("");
        l.setEnabled(false);

        //Sets labels of each axis to be white
        radarChart.getXAxis().setTextColor(Color.WHITE);
        //Max is 30 because there are 30 nba teams. Each stat is ranked against other teams and can be plotted to see how good/bad a team is in a paticular category
        YAxis yAxis = radarChart.getYAxis();
        yAxis.resetAxisMaxValue();
        yAxis.setAxisMaxValue(30);
        yAxis.setAxisMinValue(0);
        yAxis.setDrawLabels(false);

        radarChart.notifyDataSetChanged();
        radarChart.invalidate();

        //Get the ViewModel
        mViewModel = ViewModelProviders.of(this).get(TeamStatsViewModel.class);

        //Create the observer which updates the UI
        //If team1 or team 2 changes (when user selects a team)
        // change appropriate text fields and radar chart

        //Team 1 Stats LiveData onChanged
        final android.arch.lifecycle.Observer<TeamStatsObject> team1StatsObserver = new android.arch.lifecycle.Observer<TeamStatsObject>() {
            @Override
            public void onChanged(@Nullable TeamStatsObject teamStatsObject) {
                //Toast.makeText(getActivity(),"Team 1 changed", Toast.LENGTH_SHORT).show();

                //Call viewModel method here
                //Method must iterate through TeamData HashMap and store each stat in an array.
                //Method will take a teamStatsObject as a parameter
                //Method will perform linear search through arrays to determine the team's ranking for each stat
                //Return a hashmap of doubles
                //Hashmap will have the stat category as the key and the ranking value as the value
                //Keys : offenseRadarValue. offenseRank, defenseRadarValue, defenseRank, AssistsRadarValue, AssistsRank,
                //      ReboundsRadarValue, ReboundsRank, ThreesRadarValue, ThreesRank, FreeThrowRadarValue, FreeThrowRank
                // Use values to bind to RadarChart
                // Use values to bind to Ranking textViews


                //Bind available raw values to views and change fields to team colors
                team1OffenseValue.setText(String.valueOf(teamStatsObject.getPpg()));
                team1OffenseValue.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team1OffenseRank.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team1DefenseValue.setText(String.valueOf(teamStatsObject.getOppg()));
                team1DefenseValue.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team1DefenseRank.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team1AssistsValue.setText(String.valueOf(teamStatsObject.getApg()));
                team1AssistsValue.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team1AssistsRank.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team1ReboundsValue.setText(String.valueOf(teamStatsObject.getRpg()));
                team1ReboundsValue.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team1ReboundsRank.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team1ThreePointValue.setText(String.valueOf(teamStatsObject.getTpm()));
                team1ThreePointValue.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team1ThreePointRank.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                String freethrowpercentage =  decimalFormat.format(teamStatsObject.getFtp());

                team1FreeThrowValue.setText(freethrowpercentage);
                team1FreeThrowValue.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team1FreeThrowRank.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                String theTeamName = String.valueOf(teamStatsObject.getFullName());
                team1TeamName.setText(theTeamName.toUpperCase());
                team1TeamName.setTextColor(getResources().getColor(teamStatsObject.getColor()));
            }
        };

        //Team 2 Stats LiveData onChanged

        final android.arch.lifecycle.Observer<TeamStatsObject> team2StatsObserver = new android.arch.lifecycle.Observer<TeamStatsObject>() {
            @Override
            public void onChanged(@Nullable TeamStatsObject teamStatsObject) {


                //Call viewModel method here
                //Method must iterate through TeamData HashMap and store each stat in an array.
                //Method will take a teamStatsObject as a parameter
                //Method will perform linear search through arrays to determine the team's ranking for each stat
                //Return a hashmap of doubles
                //Hashmap will have the stat category as the key and the ranking value as the value
                //Keys : offenseRadarValue. offenseRank, defenseRadarValue, defenseRank, AssistsRadarValue, AssistsRank,
                //      ReboundsRadarValue, ReboundsRank, ThreesRadarValue, ThreesRank, FreeThrowRadarValue, FreeThrowRank
                // Use values to bind to RadarChart
                // Use values to bind to Ranking textViews

                //Toast.makeText(getActivity(),"Team 2 changed", Toast.LENGTH_LONG).show();


                //Bind available raw values to views and change fields to team colors
                team2OffenseValue.setText(String.valueOf(teamStatsObject.getPpg()));
                team2OffenseValue.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team2OffenseRank.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team2DefenseValue.setText(String.valueOf(teamStatsObject.getOppg()));
                team2DefenseValue.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team2DefenseRank.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team2AssistsValue.setText(String.valueOf(teamStatsObject.getApg()));
                team2AssistsValue.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team2AssistsRank.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team2ReboundsValue.setText(String.valueOf(teamStatsObject.getRpg()));
                team2ReboundsValue.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team2ReboundsRank.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team2ThreePointValue.setText(String.valueOf(teamStatsObject.getTpm()));
                team2ThreePointValue.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team2ThreePointRank.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                String freethrowpercentage =  decimalFormat.format(teamStatsObject.getFtp());

                team2FreeThrowValue.setText(freethrowpercentage);
                team2FreeThrowValue.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                team2FreeThrowRank.setTextColor(getResources().getColor(teamStatsObject.getColor()));

                String theTeamName = String.valueOf(teamStatsObject.getFullName());
                team2TeamName.setText(theTeamName.toUpperCase());
                team2TeamName.setTextColor(getResources().getColor(teamStatsObject.getColor()));
            }
        };

        //Observe the data for changes
        mViewModel.getTeam1Stats().observe(this, team1StatsObserver);
        mViewModel.getTeam2Stats().observe(this, team2StatsObserver);

        //Setup both recycler views

        team1Menu.setHasFixedSize(false);
        team2Menu.setHasFixedSize(false);

        //Horizontal recyclerview configuration
        aLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        bLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        team1Menu.setLayoutManager(aLayoutManager);
        team2Menu.setLayoutManager(bLayoutManager);

        //Bind adapters to recyclerviews
        team1Adapter = new Team1RecyclerViewAdapter(getActivity(), nbaTeamData.getTeamsList());
        team2Adapter = new Team2RecyclerViewAdapter(getActivity(), nbaTeamData.getTeamsList());

        team1Menu.setAdapter(team1Adapter);
        team2Menu.setAdapter(team2Adapter);

        //Notify and update
        team1Adapter.notifyDataSetChanged();
        team2Adapter.notifyDataSetChanged();

        //Set onclick for each team selected
        //Create a teamStatsObject by accessing NBATeamDataSingleton HashMap and looking up the data with teamAbbrv provided by the View
        //Set team slot's stats as the teamStatsObject so it will trigger an UI change
        team1Adapter.SetOnItemClickListener(new Team1RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItem1Click(View view, int position, String teamAbbrv, int color) {
                //Retrieves data entry for specific team
                HashMap<String, Object> teamHashMap = NBATeamDataSingleton.getInstance().getTeamDataMap().get(teamAbbrv);

                //Creates teamStatsObject using the data for the specific team
                TeamStatsObject teamStats1Object = new TeamStatsObject((Double)teamHashMap.get("offense"), (Double)teamHashMap.get("defense"),
                        (Double)teamHashMap.get("rebounds"), (Double)teamHashMap.get("assists"), (Double)teamHashMap.get("tpm"),
                        (Double)teamHashMap.get("ftp"), color, (String)teamHashMap.get("wins"),
                        (String)teamHashMap.get("losses"), (String)teamHashMap.get("teamName"), teamAbbrv);
                mViewModel.getTeam1Stats().setValue(teamStats1Object);
            }
        });


        team2Adapter.SetOnItemClickListener(new Team2RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItem2Click(View view, int position, String teamAbbrv, int color) {
                //Retrieves data entry for specific team
                HashMap<String, Object> teamHashMap = NBATeamDataSingleton.getInstance().getTeamDataMap().get(teamAbbrv);

                //Creates teamStatsObject using the data for the specific team
                TeamStatsObject teamStats2Object = new TeamStatsObject((Double)teamHashMap.get("offense"), (Double)teamHashMap.get("defense"),
                        (Double)teamHashMap.get("rebounds"), (Double)teamHashMap.get("assists"), (Double)teamHashMap.get("tpm"),
                        (Double)teamHashMap.get("ftp"), color, (String)teamHashMap.get("wins"),
                        (String)teamHashMap.get("losses"), (String)teamHashMap.get("teamName"), teamAbbrv);
                mViewModel.getTeam2Stats().setValue(teamStats2Object);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
