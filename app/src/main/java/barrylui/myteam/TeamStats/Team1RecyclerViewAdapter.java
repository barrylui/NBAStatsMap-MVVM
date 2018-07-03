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
    private static List<Map<String, ?>> mDataset;
    private static Stack<ImageView> ImageviewStack = new Stack<>();
    private Context mContext;
    private static final String TAG = "RosterView";
    private TeamStatsViewModel mModel;
    private static OnItemClickListener mItemClickListener;

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
    }

    public interface OnItemClickListener{
        public void onItem1Click(View view, int position, String teamAbbrv, int color);
    }

    public void SetOnItemClickListener(final OnItemClickListener theClickListener){
        this.mItemClickListener = theClickListener;
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

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //moves cursor to item selected
                    if(ImageviewStack.isEmpty()==false) {
                        //previousViewHolder.linearLayout.setBackgroundResource(R.color.blackgraycomp);
                        ImageView previousImageView = ImageviewStack.pop();
                        previousImageView.setAlpha(.3f);
                    }


                    //Highlight team selected
                    //push view holder into stack
                    ImageviewStack.push(teamPicture);
                    teamPicture.setAlpha(1f);
                    //Get Team data
                    if(mItemClickListener !=null){
                        mItemClickListener.onItem1Click(v, getLayoutPosition(),(String)mDataset.get(getLayoutPosition()).get("name"), (Integer)mDataset.get(getLayoutPosition()).get("color"));
                    }
                }
            });

        }
    }
}
