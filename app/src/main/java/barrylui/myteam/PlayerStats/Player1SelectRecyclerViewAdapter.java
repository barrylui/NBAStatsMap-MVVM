package barrylui.myteam.PlayerStats;


import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import barrylui.myteam.R;
import barrylui.myteam.SuredBitsAPI.SuredBitsPlayerModel.PlayerInfoModel;


public class Player1SelectRecyclerViewAdapter extends RecyclerView.Adapter<Player1SelectRecyclerViewAdapter.Player1SelectViewHolder>{
    private static List<PlayerInfoModel> mDataset;
    private static Stack<ImageView> imageViewStack = new Stack<>();
    private static Stack<TextView> textViewStack = new Stack<>();
    private Context mContext;
    private static final String TAG = "RosterView";
    private static OnPlayer1SelectClickListener mItemClickListener;
    public int selected_position = -1;

    public Player1SelectRecyclerViewAdapter(Context context, List<PlayerInfoModel> list){
        mDataset = list;
        mContext = context;
        resetPosition();
    }

    //resets player selected
    public void resetPosition (){
        selected_position = -2;
    }



    @Override
    public Player1SelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View teamView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_player, parent, false);
        return new Player1SelectViewHolder(teamView);
    }

    @Override
    public void onBindViewHolder(final Player1SelectViewHolder viewHolder, final int position) {
        String playerId = String.valueOf(mDataset.get(position).getPlayerId());
        //url for headshot for player
        final String url = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/" + playerId + ".png";

        //last name to display
        final String theLastName = mDataset.get(position).getLastName();
        //display last name
        viewHolder.playerName.setText(theLastName);

        final Context playerPhotoContext = viewHolder.playerHeadshot.getContext();
        //Load player headshot, provide default no picture placeholder image
        Picasso.with(playerPhotoContext).load(url).placeholder(R.drawable.default_nba_headshot_v2).error(R.drawable.default_nba_headshot_v2).into(viewHolder.playerHeadshot);
        //Player image is not transparent if selected, player image is transparent if not selected
        viewHolder.playerHeadshot.setAlpha(selected_position == position? 1f : .3f);
        //Change textcolor of name depending on if it is selected or not
        viewHolder.playerName.setTextColor(selected_position == position? Color.parseColor("#ffffff"):Color.parseColor("#7F7E7E"));
    }

    //Interface method to communicate with main activity
    public interface OnPlayer1SelectClickListener {
        public void onPlayer1SelectClick(View view, int position, String playerName, boolean currentItemSelected);
    }

    public void SetOnTeam1SelectClickListener(final OnPlayer1SelectClickListener theClickListener){
        this.mItemClickListener = theClickListener;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class Player1SelectViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/
    {
        ImageView playerHeadshot;
        LinearLayout linearLayout;
        TextView playerName;

        public Player1SelectViewHolder(View view) {
            super(view);
            final View v = view;

            linearLayout = (LinearLayout) v.findViewById(R.id.cardview_player);
            playerHeadshot = (ImageView) v.findViewById(R.id.playerImage);
            playerName = (TextView) v.findViewById(R.id.playerLastName);

            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //If current player selected is the same the player user selected, deselect it
                    if(selected_position == getAdapterPosition()){
                        selected_position = -1;
                        notifyItemChanged(selected_position);
                        notifyDataSetChanged();
                        //Interface call here to load data into textfields and chart
                        mItemClickListener.onPlayer1SelectClick(v, getLayoutPosition(),
                                mDataset.get(getLayoutPosition()).getFirstName() + " " + mDataset.get(getLayoutPosition()).getLastName(), true );
                    }
                    //select the player the usr selected
                    else{
                        selected_position = getAdapterPosition();
                        notifyItemChanged(selected_position);
                        notifyDataSetChanged();
                        mItemClickListener.onPlayer1SelectClick(v, getLayoutPosition(),
                                mDataset.get(getLayoutPosition()).getFirstName() + " " + mDataset.get(getLayoutPosition()).getLastName(), false);
                    }
                }
            });

        }
    }
}
