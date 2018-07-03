package barrylui.myteam.PlayerStats;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import barrylui.myteam.R;


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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_stats, container, false);

        slot1Team1 = (RecyclerView)view.findViewById(R.id.team1_select_recycleview);
        slot2Team2 = (RecyclerView)view.findViewById(R.id.team2_select_recycleview);

        slot1Player1 = (RecyclerView)view.findViewById(R.id.team1_player_select_recyclerview);
        slot2Player2 = (RecyclerView)view.findViewById(R.id.team2_player_select_recyclerview);

        slot1TextView = (TextView)view.findViewById(R.id.slot1_header);
        slot2TextView = (TextView)view.findViewById(R.id.slot2_header);

        slot1Button = (Button)view.findViewById(R.id.slot1BacktoTeamButton);
        slot2Button = (Button)view.findViewById(R.id.slot2BacktoTeamButton);

        slot1Player1.setVisibility(View.GONE);
        slot2Player2.setVisibility(View.GONE);

        slot1Button.setVisibility(View.GONE);
        slot2Button.setVisibility(View.GONE);

        return view;
    }
}
