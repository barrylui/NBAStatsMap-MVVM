package barrylui.myteam.PlayerStats;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import barrylui.myteam.R;
import barrylui.myteam.SuredBitsAPI.SuredBitsPlayerModel.PlayerInfoModel;


public class Player2SelectRecyclerViewAdapter extends RecyclerView.Adapter<Player2SelectRecyclerViewAdapter.Player2SelectViewHolder>{
    private static List<PlayerInfoModel> mDataset;
    private static Stack<ImageView> ImageviewStack = new Stack<>();
    private Context mContext;
    private static final String TAG = "RosterView";
    private static OnPlayer2SelectClickListener mItemClickListener;

    public Player2SelectRecyclerViewAdapter(Context context, List<PlayerInfoModel> list){
        mDataset = list;
        mContext = context;
    }


    @Override
    public Player2SelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View teamView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_player, parent, false);
        return new Player2SelectViewHolder(teamView);
    }

    @Override
    public void onBindViewHolder(final Player2SelectViewHolder viewHolder, final int position) {
        String playerId = String.valueOf(mDataset.get(position).getPlayerId());
        final String url = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/" + playerId + ".png";


        final String theLastName = mDataset.get(position).getLastName();

        viewHolder.playerName.setText(theLastName);

        final Context playerPhotoContext = viewHolder.playerHeadshot.getContext();
        Picasso.with(playerPhotoContext).load(url).placeholder(R.drawable.default_nba_headshot_v2).error(R.drawable.default_nba_headshot_v2).into(viewHolder.playerHeadshot);
        //final int logoimage = (Integer)mDataset.get(position).get("image");
        //viewHolder.teamPicture.setImageResource(logoimage);
        //viewHolder.teamPicture.setAlpha(.3f);
        //viewHolder.linearLayout.setBackgroundResource(R.color.blackgraycomp);
    }

    public interface OnPlayer2SelectClickListener {
        public void onPlayer1SelectClick(View view, int position, String teamAbbrv, int color);
    }

    public void SetOnTeam1SelectClickListener(final OnPlayer2SelectClickListener theClickListener){
        this.mItemClickListener = theClickListener;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class Player2SelectViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/
    {
        ImageView playerHeadshot;
        LinearLayout linearLayout;
        TextView playerName;

        public Player2SelectViewHolder(View view) {
            super(view);
            final View v = view;

            linearLayout = (LinearLayout) v.findViewById(R.id.cardview_player);
            playerHeadshot = (ImageView) v.findViewById(R.id.playerImage);
            playerName = (TextView) v.findViewById(R.id.playerLastName);

            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //Call interface method here so PlayerStats Fragment can hide recyclerview and then display the button and new recyclerview with players
                    //mItemClickListener.onPlayer1SelectClick(v, getLayoutPosition(), (String)mDataset.get(getLayoutPosition()).get("name"), (Integer)mDataset.get(getLayoutPosition()).get("color"));
                }
            });

        }
    }
}
