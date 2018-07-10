package barrylui.myteam.PlayerStats;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import barrylui.myteam.R;


public class Team2SelectRecycleViewAdapter extends RecyclerView.Adapter<Team2SelectRecycleViewAdapter.Team2SelectViewHolder>{
    private static List<Map<String, ?>> mDataset;
    private static Stack<ImageView> ImageviewStack = new Stack<>();
    private Context mContext;
    private static final String TAG = "RosterView";
    private static OnTeam2SelectClickListener mItemClickListener;

    public Team2SelectRecycleViewAdapter(Context context, List<Map<String, ?>> list){
        mDataset = list;
        mContext = context;
    }


    @Override
    public Team2SelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View teamView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_team, parent, false);
        return new Team2SelectViewHolder(teamView);
    }

    @Override
    public void onBindViewHolder(final Team2SelectViewHolder viewHolder, final int position) {
        final int logoimage = (Integer)mDataset.get(position).get("image");
        viewHolder.teamPicture.setImageResource(logoimage);
        //viewHolder.teamPicture.setAlpha(.3f);
        //viewHolder.linearLayout.setBackgroundResource(R.color.blackgraycomp);
    }

    public interface OnTeam2SelectClickListener {
        public void onTeam2SelectClick(View view, int position, String teamAbbrv, int color);
    }

    public void SetOnTeam2SelectClickListener(final OnTeam2SelectClickListener theClickListener){
        this.mItemClickListener = theClickListener;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class Team2SelectViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/
    {
        ImageView teamPicture;
        LinearLayout linearLayout;

        public Team2SelectViewHolder(View view) {
            super(view);
            final View v = view;
            teamPicture = (ImageView) v.findViewById(R.id.teamLogo);
            linearLayout = (LinearLayout) v.findViewById(R.id.cardview_team);

            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //Call interface method here so PlayerStats Fragment can hide recyclerview and then display the button and new recyclerview with players
                    mItemClickListener.onTeam2SelectClick(v, getLayoutPosition(), (String)mDataset.get(getLayoutPosition()).get("name"), (Integer)mDataset.get(getLayoutPosition()).get("color"));
                }
            });

        }
    }
}

