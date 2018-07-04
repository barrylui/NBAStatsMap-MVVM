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




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

        if(entry1.isEmpty()){
            //Empty dataset
            entry1.add(new Entry(0,0));
            entry1.add(new Entry(0,1));
            entry1.add(new Entry(0,2));
            entry1.add(new Entry(0,3));
            entry1.add(new Entry(0,4));
            entry1.add(new Entry(0,5));
        }

        if(entry2.isEmpty()){
            entry2.add(new Entry(0,0));
            entry2.add(new Entry(0,1));
            entry2.add(new Entry(0,2));
            entry2.add(new Entry(0,3));
            entry2.add(new Entry(0,4));
            entry2.add(new Entry(0,5));
        }

        //Setup radarchart settings and bind radar chart
        final RadarDataSet dataset1 = new RadarDataSet(entry1,"Team 1");
        final RadarDataSet dataset2 = new RadarDataSet(entry2, "Team 2");

        //Draw settings
        dataset1.setFillAlpha(100);
        dataset1.setLineWidth(4f);
        dataset1.setDrawValues(false);
        dataset1.setDrawFilled(true);

        dataset2.setFillAlpha(100);
        dataset2.setLineWidth(4f);
        dataset2.setDrawValues(false);
        dataset2.setDrawFilled(true);

        //Add datasets to radarChart
        final ArrayList<RadarDataSet> dataSets= new ArrayList<RadarDataSet>();
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
        yAxis.setAxisMaxValue(30f);
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

                //calls ViewModel method to obtain team ranking data for each category.
                HashMap<String, Object> teamRankingAndRadarMap = mViewModel.getTeamStatRanking(teamStatsObject);

                //Sets text colors for different teams
                int textColor = getResources().getColor(teamStatsObject.getColor());
                //Handles two cases where we dont want the primary color being the text color
                if (textColor == (Integer)getResources().getColor(R.color.colorPelicansPrimary)){
                    textColor = getResources().getColor(R.color.colorPelicansText);
                }
                if(textColor == (Integer)getResources().getColor(R.color.colorJazzPrimary)){
                    textColor = getResources().getColor(R.color.colorJazzText);
                }

                team1OffenseValue.setTextColor(textColor);
                team1OffenseRank.setTextColor(textColor);
                team1DefenseValue.setTextColor(textColor);
                team1DefenseRank.setTextColor(textColor);
                team1AssistsValue.setTextColor(textColor);
                team1AssistsRank.setTextColor(textColor);
                team1AssistsValue.setTextColor(textColor);
                team1ReboundsValue.setTextColor(textColor);
                team1ReboundsRank.setTextColor(textColor);
                team1ThreePointValue.setTextColor(textColor);
                team1ThreePointRank.setTextColor(textColor);
                team1FreeThrowValue.setTextColor(textColor);
                team1FreeThrowRank.setTextColor(textColor);
                team1TeamName.setTextColor(textColor);

                team1OffenseRank.setText((String)teamRankingAndRadarMap.get("offenseRank"));
                team1DefenseRank.setText((String)teamRankingAndRadarMap.get("defenseRank"));
                team1ReboundsRank.setText((String)teamRankingAndRadarMap.get("reboundsRank"));
                team1AssistsRank.setText((String)teamRankingAndRadarMap.get("assistsRank"));
                team1ThreePointRank.setText((String)teamRankingAndRadarMap.get("threesRank"));
                team1FreeThrowRank.setText((String)teamRankingAndRadarMap.get("freeThrowRank"));

                team1OffenseValue.setText(String.valueOf(teamStatsObject.getPpg()));
                team1DefenseValue.setText(String.valueOf(teamStatsObject.getOppg()));
                team1AssistsValue.setText(String.valueOf(teamStatsObject.getApg()));
                team1ReboundsValue.setText(String.valueOf(teamStatsObject.getRpg()));
                team1ThreePointValue.setText(String.valueOf(teamStatsObject.getTpm()));

                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                double freethrowRadarValue = (30 * (teamStatsObject.getFtp())/100);
                String freethrowpercentage =  decimalFormat.format(teamStatsObject.getFtp());
                team1FreeThrowValue.setText(freethrowpercentage);


                String theTeamName = String.valueOf(teamStatsObject.getFullName());
                team1TeamName.setText(theTeamName.toUpperCase());

                entry1.clear();

                entry1.add(new Entry((float)(double)teamRankingAndRadarMap.get("reboundsRadarVal"),0));
                entry1.add(new Entry((float)(double)teamRankingAndRadarMap.get("offenseRadarVal"),1));
                entry1.add(new Entry((float)(double)teamRankingAndRadarMap.get("defenseRadarVal"),2));
                entry1.add(new Entry((float)(double)teamRankingAndRadarMap.get("assistsRadarVal"),3));
                entry1.add(new Entry((float)freethrowRadarValue,4));
                entry1.add(new Entry((float)(double)teamRankingAndRadarMap.get("threesRadarVal"),5));

                dataset1.setColor(getResources().getColor(teamStatsObject.getColor()));
                dataset1.setFillColor(getResources().getColor(teamStatsObject.getColor()));


                radarChart.notifyDataSetChanged();
                radarChart.invalidate();
            }
        };

        //Team 2 Stats LiveData onChanged

        final android.arch.lifecycle.Observer<TeamStatsObject> team2StatsObserver = new android.arch.lifecycle.Observer<TeamStatsObject>() {
            @Override
            public void onChanged(@Nullable TeamStatsObject teamStatsObject) {


                //calls ViewModel method to obtain team ranking data for each category.
                HashMap<String, Object> teamRankingAndRadarMap = mViewModel.getTeamStatRanking(teamStatsObject);

                //Sets text colors for different teams
                int textColor = getResources().getColor(teamStatsObject.getColor());
                //Handles two cases where we dont want the primary color being the text color
                if (textColor == (Integer)getResources().getColor(R.color.colorPelicansPrimary)){
                    textColor = getResources().getColor(R.color.colorPelicansText);
                }
                if(textColor == (Integer)getResources().getColor(R.color.colorJazzPrimary)){
                    textColor = getResources().getColor(R.color.colorJazzText);
                }

                team2OffenseValue.setTextColor(textColor);
                team2OffenseRank.setTextColor(textColor);
                team2DefenseValue.setTextColor(textColor);
                team2DefenseRank.setTextColor(textColor);
                team2AssistsValue.setTextColor(textColor);
                team2AssistsRank.setTextColor(textColor);
                team2AssistsValue.setTextColor(textColor);
                team2ReboundsValue.setTextColor(textColor);
                team2ReboundsRank.setTextColor(textColor);
                team2ThreePointValue.setTextColor(textColor);
                team2ThreePointRank.setTextColor(textColor);
                team2FreeThrowValue.setTextColor(textColor);
                team2FreeThrowRank.setTextColor(textColor);
                team2TeamName.setTextColor(textColor);

                team2OffenseRank.setText((String)teamRankingAndRadarMap.get("offenseRank"));
                team2DefenseRank.setText((String)teamRankingAndRadarMap.get("defenseRank"));
                team2ReboundsRank.setText((String)teamRankingAndRadarMap.get("reboundsRank"));
                team2AssistsRank.setText((String)teamRankingAndRadarMap.get("assistsRank"));
                team2ThreePointRank.setText((String)teamRankingAndRadarMap.get("threesRank"));
                team2FreeThrowRank.setText((String)teamRankingAndRadarMap.get("freeThrowRank"));

                team2OffenseValue.setText(String.valueOf(teamStatsObject.getPpg()));
                team2DefenseValue.setText(String.valueOf(teamStatsObject.getOppg()));
                team2AssistsValue.setText(String.valueOf(teamStatsObject.getApg()));
                team2ReboundsValue.setText(String.valueOf(teamStatsObject.getRpg()));
                team2ThreePointValue.setText(String.valueOf(teamStatsObject.getTpm()));

                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                double freethrowRadarValue = (30 * (teamStatsObject.getFtp())/100);
                String freethrowpercentage =  decimalFormat.format(teamStatsObject.getFtp());
                team2FreeThrowValue.setText(freethrowpercentage);


                String theTeamName = String.valueOf(teamStatsObject.getFullName());
                team2TeamName.setText(theTeamName.toUpperCase());

                entry2.clear();

                entry2.add(new Entry((float)(double)teamRankingAndRadarMap.get("reboundsRadarVal"),0));
                entry2.add(new Entry((float)(double)teamRankingAndRadarMap.get("offenseRadarVal"),1));
                entry2.add(new Entry((float)(double)teamRankingAndRadarMap.get("defenseRadarVal"),2));
                entry2.add(new Entry((float)(double)teamRankingAndRadarMap.get("assistsRadarVal"),3));
                entry2.add(new Entry((float)freethrowRadarValue,4));
                entry2.add(new Entry((float)(double)teamRankingAndRadarMap.get("threesRadarVal"),5));

                dataset2.setColor(getResources().getColor(teamStatsObject.getColor()));
                dataset2.setFillColor(getResources().getColor(teamStatsObject.getColor()));

                radarChart.notifyDataSetChanged();
                radarChart.invalidate();
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
            public void onItem1Click(View view, int position, String teamAbbrv, int color, boolean currentItemSelected) {

                if(currentItemSelected == true){
                    team1OffenseRank.setText(getString(R.string.empty_string));
                    team1DefenseRank.setText(getString(R.string.empty_string));
                    team1AssistsRank.setText(getString(R.string.empty_string));
                    team1ReboundsRank.setText(getString(R.string.empty_string));
                    team1ThreePointRank.setText(getString(R.string.empty_string));
                    team1FreeThrowRank.setText(getString(R.string.empty_string));
                    team1TeamName.setText(getString(R.string.team1));
                    team1TeamName.setTextColor(getResources().getColor(R.color.white));

                    team1OffenseValue.setText(getString(R.string.empty_string));
                    team1DefenseValue.setText(getString(R.string.empty_string));
                    team1AssistsValue.setText(getString(R.string.empty_string));
                    team1ReboundsValue.setText(getString(R.string.empty_string));
                    team1ThreePointValue.setText(getString(R.string.empty_string));
                    team1FreeThrowValue.setText(getString(R.string.empty_string));

                    entry1.clear();

                    entry1.add(new Entry(0,0));
                    entry1.add(new Entry(0,1));
                    entry1.add(new Entry(0,2));
                    entry1.add(new Entry(0,3));
                    entry1.add(new Entry(0,4));
                    entry1.add(new Entry(0,5));

                    radarChart.notifyDataSetChanged();
                    radarChart.invalidate();

                }
                else{
                    //Retrieves data entry for specific team
                    HashMap<String, Object> teamHashMap = NBATeamDataSingleton.getInstance().getTeamDataMap().get(teamAbbrv);

                    //Creates teamStatsObject using the data for the specific team
                    TeamStatsObject teamStats1Object = new TeamStatsObject((Double)teamHashMap.get("offense"), (Double)teamHashMap.get("defense"),
                            (Double)teamHashMap.get("rebounds"), (Double)teamHashMap.get("assists"), (Double)teamHashMap.get("tpm"),
                            (Double)teamHashMap.get("ftp"), color, (String)teamHashMap.get("wins"),
                            (String)teamHashMap.get("losses"), (String)teamHashMap.get("teamName"), teamAbbrv);
                    mViewModel.getTeam1Stats().setValue(teamStats1Object);
                }
            }
        });


        team2Adapter.SetOnItemClickListener(new Team2RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItem2Click(View view, int position, String teamAbbrv, int color, boolean currentItemSelected) {

                if(currentItemSelected == true){
                    team2OffenseRank.setText(getString(R.string.empty_string));
                    team2DefenseRank.setText(getString(R.string.empty_string));
                    team2AssistsRank.setText(getString(R.string.empty_string));
                    team2ReboundsRank.setText(getString(R.string.empty_string));
                    team2ThreePointRank.setText(getString(R.string.empty_string));
                    team2FreeThrowRank.setText(getString(R.string.empty_string));
                    team2TeamName.setText(getString(R.string.team2));
                    team2TeamName.setTextColor(getResources().getColor(R.color.white));

                    team2OffenseValue.setText(getString(R.string.empty_string));
                    team2DefenseValue.setText(getString(R.string.empty_string));
                    team2AssistsValue.setText(getString(R.string.empty_string));
                    team2ReboundsValue.setText(getString(R.string.empty_string));
                    team2ThreePointValue.setText(getString(R.string.empty_string));
                    team2FreeThrowValue.setText(getString(R.string.empty_string));

                    entry2.clear();

                    entry2.add(new Entry(0,0));
                    entry2.add(new Entry(0,1));
                    entry2.add(new Entry(0,2));
                    entry2.add(new Entry(0,3));
                    entry2.add(new Entry(0,4));
                    entry2.add(new Entry(0,5));

                    radarChart.notifyDataSetChanged();
                    radarChart.invalidate();

                }
                else{
                    //Retrieves data entry for specific team
                    HashMap<String, Object> teamHashMap = NBATeamDataSingleton.getInstance().getTeamDataMap().get(teamAbbrv);

                    //Creates teamStatsObject using the data for the specific team
                    TeamStatsObject teamStats2Object = new TeamStatsObject((Double)teamHashMap.get("offense"), (Double)teamHashMap.get("defense"),
                            (Double)teamHashMap.get("rebounds"), (Double)teamHashMap.get("assists"), (Double)teamHashMap.get("tpm"),
                            (Double)teamHashMap.get("ftp"), color, (String)teamHashMap.get("wins"),
                            (String)teamHashMap.get("losses"), (String)teamHashMap.get("teamName"), teamAbbrv);
                    mViewModel.getTeam2Stats().setValue(teamStats2Object);
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
