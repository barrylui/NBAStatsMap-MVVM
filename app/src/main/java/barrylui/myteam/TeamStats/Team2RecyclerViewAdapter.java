package barrylui.myteam.TeamStats;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import barrylui.myteam.R;


public class Team2RecyclerViewAdapter extends RecyclerView.Adapter<Team2RecyclerViewAdapter.Team2ViewHolder>{
    private static List<Map<String, ?>> mDataset;
    private Context mContext;
    private Stack<Team2ViewHolder> viewHolderStack = new Stack<>();
    private static final String TAG = "RosterView";
    private static Stack<ImageView> imageStack = new Stack<ImageView>();
    private static OnItemClickListener mItemClickListener;

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
    }

    public interface OnItemClickListener{
        public void onItem2Click(View view, int position, String teamAbbrv, int color, boolean currentItemSelected);
    }

    public void SetOnItemClickListener(OnItemClickListener clickListener){
        this.mItemClickListener = clickListener;
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
            final View v = view;
            teamPicture = (ImageView) view.findViewById(R.id.teamLogo);
            linearLayout = (LinearLayout) view.findViewById(R.id.cardview_team);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (imageStack.isEmpty()==false && teamPicture==imageStack.peek()){
                        ImageView previousImageView = imageStack.pop();
                        teamPicture.setAlpha(.3f);
                        mItemClickListener.onItem2Click(v, getLayoutPosition(), (String)mDataset.get(getLayoutPosition()).get("name"), (Integer)mDataset.get(getLayoutPosition()).get("color"), true);
                    }
                    else{
                        //Deselects last team from the UI by changing its alpha
                        if(imageStack.isEmpty()==false){
                            ImageView previousImageView = imageStack.pop();
                            previousImageView.setAlpha(.3f);
                        }
                        //moves cursor to item selected by highlighting team logo
                        //push view holder into stack so it can be referenced later and deselected from the UI
                        imageStack.push(teamPicture);
                        teamPicture.setAlpha(1f);
                        //Get Team data in Fragment
                        if(mItemClickListener!=null){
                            mItemClickListener.onItem2Click(view, getLayoutPosition(),(String)mDataset.get(getLayoutPosition()).get("name"), (Integer)mDataset.get(getLayoutPosition()).get("color"), false);
                        }}
                }
            });

        }
    }
}