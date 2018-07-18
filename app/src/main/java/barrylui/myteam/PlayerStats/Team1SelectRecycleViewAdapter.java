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

//Adapter class for Recyclerview to select a Team, once a team is chosen the team's roster will replace this recyclerview
public class Team1SelectRecycleViewAdapter extends RecyclerView.Adapter<Team1SelectRecycleViewAdapter.Team1SelectViewHolder>{
    private static List<Map<String, ?>> mDataset;
    private static Stack<ImageView> ImageviewStack = new Stack<>();
    private Context mContext;
    private static final String TAG = "RosterView";
    private static OnTeam1SelectClickListener mItemClickListener;

    public Team1SelectRecycleViewAdapter(Context context, List<Map<String, ?>> list){
        mDataset = list;
        mContext = context;
    }

    @Override
    public Team1SelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View teamView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_team, parent, false);
        return new Team1SelectViewHolder(teamView);
    }

    @Override
    public void onBindViewHolder(final Team1SelectViewHolder viewHolder, final int position) {
        final int logoimage = (Integer)mDataset.get(position).get("image");
        viewHolder.teamPicture.setImageResource(logoimage);
    }

    public interface OnTeam1SelectClickListener {
        public void onTeam1SelectClick(View view, int position, String teamAbbrv, int color);
    }

    public void SetOnTeam1SelectClickListener(final OnTeam1SelectClickListener theClickListener){
        this.mItemClickListener = theClickListener;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class Team1SelectViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/
    {
        ImageView teamPicture;
        LinearLayout linearLayout;

        public Team1SelectViewHolder(View view) {
            super(view);
            final View v = view;
            teamPicture = (ImageView) v.findViewById(R.id.teamLogo);
            linearLayout = (LinearLayout) v.findViewById(R.id.cardview_team);

            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //Call interface method here so PlayerStats Fragment can hide recyclerview and then display the button and new recyclerview with players
                    mItemClickListener.onTeam1SelectClick(v, getLayoutPosition(), (String)mDataset.get(getLayoutPosition()).get("name"), (Integer)mDataset.get(getLayoutPosition()).get("color"));
                }
            });

        }
    }
}
