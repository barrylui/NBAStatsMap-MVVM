package barrylui.myteam.TeamStats;

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


public class Team1RecyclerViewAdapter extends RecyclerView.Adapter<Team1RecyclerViewAdapter.Team1ViewHolder>{
    private List<Map<String, ?>> mDataset;
    private Stack<Team1ViewHolder> viewHolderStack = new Stack<>();
    private Context mContext;
    private static final String TAG = "RosterView";
    //OnItemClickListener mItemClickListener;

    public Team1RecyclerViewAdapter(Context context, List<Map<String, ?>> list){
        mDataset = list;
        mContext = context;
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
        viewHolder.teamPicture.setAlpha(.3f);

        final Team1ViewHolder currentViewHolder = viewHolder;
        viewHolder.linearLayout.setBackgroundResource(R.color.blackgraycomp);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){



                //moves cursor to item selected
                if(viewHolderStack.isEmpty()==false){
                    Team1ViewHolder previousViewHolder = viewHolderStack.pop();
                    previousViewHolder.teamPicture.setAlpha(.3f);
                    //previousViewHolder.linearLayout.setBackgroundResource(R.color.blackgraycomp);
                }
                //Highlight team selected
                //Call for team's data
                //Call for all teams data and load into array
                //Perform binary search for team's data
                viewHolderStack.push(currentViewHolder);
                currentViewHolder.teamPicture.setAlpha(1f);
                //currentViewHolder.linearLayout.setBackgroundResource(R.color.blacklightcomp);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class Team1ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/
    {
        ImageView teamPicture;
        LinearLayout linearLayout;
        //GradientDrawableDrawable circleBackground;

        public Team1ViewHolder(View view) {
            super(view);
            teamPicture = (ImageView) view.findViewById(R.id.teamLogo);
            linearLayout = (LinearLayout) view.findViewById(R.id.cardview_team);

        }
    }
}
