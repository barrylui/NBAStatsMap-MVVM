package barrylui.myteam.TeamStats;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;

import java.util.ArrayList;

import barrylui.myteam.MainActivity;
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
    private NBATeamAssetsData nbaTeamData = new NBATeamAssetsData();
    LinearLayoutManager aLayoutManager;
    LinearLayoutManager bLayoutManager;
    private Team1RecyclerViewAdapter team1Adapter = null;
    private Team2RecyclerViewAdapter team2Adapter = null;
    private ArrayList<String> labels;
    private RadarChart radarChart;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team_stats, container, false);


        //Bind views
        radarChart = (RadarChart)view.findViewById(R.id.radarchart);
        team1Menu = (RecyclerView)view.findViewById(R.id.team1_recyclerview);
        team2Menu = (RecyclerView)view.findViewById(R.id.team2_recyclerview);

        //Setup both recycler views
        team1Menu.setHasFixedSize(false);
        team2Menu.setHasFixedSize(false);

        aLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        bLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        team1Menu.setLayoutManager(aLayoutManager);
        team2Menu.setLayoutManager(bLayoutManager);

        team1Adapter = new Team1RecyclerViewAdapter(getActivity(), nbaTeamData.getTeamsList());
        team2Adapter = new Team2RecyclerViewAdapter(getActivity(), nbaTeamData.getTeamsList());

        team1Menu.setAdapter(team1Adapter);
        team2Menu.setAdapter(team2Adapter);

        team1Adapter.notifyDataSetChanged();
        team2Adapter.notifyDataSetChanged();


        //Set up Blank Radar Chart


        //Label axis on radar chart
        labels = new ArrayList<String>();
        labels.add("REBOUNDS");
        labels.add("OFFENSE");
        labels.add("DEFENSE");
        labels.add("ASSISTS");
        labels.add("FT%");
        labels.add("3PT SCORING");

        //Input blank data for blank chart
        ArrayList<Entry> entry1 = new ArrayList<>();

        entry1.add(new Entry(0,0));
        entry1.add(new Entry(0,1));
        entry1.add(new Entry(0,2));
        entry1.add(new Entry(0,3));
        entry1.add(new Entry(0,4));
        entry1.add(new Entry(0,5));

        //Setup radarchart settings and bind radar chart
        RadarDataSet dataset1 = new RadarDataSet(entry1,"Team 1");

        dataset1.setFillAlpha(180);
        dataset1.setLineWidth(5f);
        dataset1.setDrawValues(false);

        ArrayList<RadarDataSet> dataSets= new ArrayList<RadarDataSet>();
        dataSets.add(dataset1);
        RadarData theradardata = new RadarData(labels, dataSets);
        radarChart.setData(theradardata);

        //disable desciption
        Legend l = radarChart.getLegend();
        radarChart.setDescription("");
        l.setEnabled(false);

        radarChart.getXAxis().setTextColor(Color.WHITE);
        //Max is 30 because there are 30 nba teams. Each stat is ranked against other teams and can be plotted to see how good/bad a team is in a paticular category
        YAxis yAxis = radarChart.getYAxis();
        yAxis.resetAxisMaxValue();
        yAxis.setAxisMaxValue(30);
        yAxis.setAxisMinValue(0);
        yAxis.setDrawLabels(false);

        radarChart.notifyDataSetChanged();
        radarChart.invalidate();


        if(NBATeamDataSingleton.getInstance().getTeamDataMap().isEmpty()==true && NBAPlayerDataSingleton.getInstance().getPlayerDataMap().isEmpty()==true){
            Toast.makeText(getActivity(),"Team data and Player data are empty", Toast.LENGTH_LONG).show();
        } else if(NBATeamDataSingleton.getInstance().getTeamDataMap().isEmpty()==false&&NBAPlayerDataSingleton.getInstance().getPlayerDataMap().isEmpty()==true){
            Toast.makeText(getActivity(),"Team data is loaded and Player data is Empty", Toast.LENGTH_LONG).show();
        } else if (NBATeamDataSingleton.getInstance().getTeamDataMap().isEmpty()==true&&NBAPlayerDataSingleton.getInstance().getPlayerDataMap().isEmpty()==false){
            Toast.makeText(getActivity(),"Team data is empty and Player data is loaded", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getActivity(),"All data is loaded", Toast.LENGTH_LONG).show();
        }
        return view;


    }
}
