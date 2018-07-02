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


public class Team2RecyclerViewAdapter extends RecyclerView.Adapter<Team2RecyclerViewAdapter.Team2ViewHolder>{
    private List<Map<String, ?>> mDataset;
    private Context mContext;
    private Stack<Team2ViewHolder> viewHolderStack = new Stack<>();
    private static final String TAG = "RosterView";
    //OnItemClickListener mItemClickListener;

    public Team2RecyclerViewAdapter(Context context, List<Map<String, ?>> list){
        mDataset = list;
        mContext = context;
    }


    @Override
    public Team2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View playerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_team, parent, false);
        return new Team2ViewHolder(playerView);
    }

    @Override
    public void onBindViewHolder(final Team2ViewHolder viewHolder, final int position) {
        final int logoimage = (Integer)mDataset.get(position).get("image");
        final Team2ViewHolder currentViewHolder = viewHolder;
        viewHolder.teamPicture.setImageResource(logoimage);
        viewHolder.teamPicture.setAlpha(.3f);
        viewHolder.linearLayout.setBackgroundResource(R.color.blackgraycomp);

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(viewHolderStack.isEmpty()==false){
                    Team2ViewHolder previousViewHolder = viewHolderStack.pop();
                    previousViewHolder.teamPicture.setAlpha(.3f);
                    //previousViewHolder.linearLayout.setBackgroundResource(R.color.blackgraycomp);
                }
                currentViewHolder.teamPicture.setAlpha(1f);
                //currentViewHolder.linearLayout.setBackgroundResource(R.color.blacklightcomp);
                viewHolderStack.push(currentViewHolder);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class Team2ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/
    {
        ImageView teamPicture;
        LinearLayout linearLayout;
        //GradientDrawableDrawable circleBackground;

        public Team2ViewHolder(View view) {
            super(view);
            teamPicture = (ImageView) view.findViewById(R.id.teamLogo);
            linearLayout = (LinearLayout) view.findViewById(R.id.cardview_team);
            //            //view.setOnClickListener(this);

        }
    }
}