package barrylui.myteam.TeamStats;

import android.content.Context;
import android.media.Image;
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

//Adapter class for Recyclerview to select a Team, once a team is chosen the team's data will bind to the text fields and radar chart
public class Team1RecyclerViewAdapter extends RecyclerView.Adapter<Team1RecyclerViewAdapter.Team1ViewHolder>{
    private static List<Map<String, ?>> mDataset;
    private Context mContext;
    private static final String TAG = "RosterView";
    private TeamStatsViewModel mModel;
    private static OnItemClickListener mItemClickListener;
    static int selected_position = -1;

    public Team1RecyclerViewAdapter(Context context, List<Map<String, ?>> list){
        mDataset = list;
        mContext = context;
        resetPosition();
    }


    //Resets item selected in recycler view
    public void resetPosition (){
        selected_position = -2;
    }


    @Override
    public Team1ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View playerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_team, parent, false);
        return new Team1ViewHolder(playerView);
    }

    @Override
    public void onBindViewHolder(final Team1ViewHolder viewHolder, final int position) {
        final int logoimage = (Integer)mDataset.get(position).get("image");
        viewHolder.teamPicture.setImageResource(logoimage);
        // If selected make it non transparent, if not selected make it transparent
        viewHolder.teamPicture.setAlpha(selected_position == position? 1f : .3f);
    }

    public interface OnItemClickListener{
        public void onItem1Click(View view, int position, String teamAbbrv, int color, boolean currentItemSelected);
    }

    public void SetOnItemClickListener(final OnItemClickListener theClickListener){
        this.mItemClickListener = theClickListener;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class Team1ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/
    {
        ImageView teamPicture;
        LinearLayout linearLayout;

        public Team1ViewHolder(View view) {
            super(view);
            final View v = view;
            teamPicture = (ImageView) v.findViewById(R.id.teamLogo);
            linearLayout = (LinearLayout) v.findViewById(R.id.cardview_team);


            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //If the currently selected team is chosen again by the user, deselect the team
                    if(selected_position == getAdapterPosition()){
                        selected_position = -1;
                        notifyItemChanged(selected_position);
                        notifyDataSetChanged();
                        //Pass to fragment to handle with parameters
                        mItemClickListener.onItem1Click(v, getLayoutPosition(),(String)mDataset.get(getLayoutPosition()).get("name"), (Integer)mDataset.get(getLayoutPosition()).get("color"), true);
                    }
                    //Select the team
                    else{
                        selected_position = getAdapterPosition();
                        notifyItemChanged(selected_position);
                        notifyDataSetChanged();
                        //Pass to fragment to handle with parameters
                        mItemClickListener.onItem1Click(v, getLayoutPosition(),(String)mDataset.get(getLayoutPosition()).get("name"), (Integer)mDataset.get(getLayoutPosition()).get("color"), false);
                    }

                }
            });

        }
    }
}