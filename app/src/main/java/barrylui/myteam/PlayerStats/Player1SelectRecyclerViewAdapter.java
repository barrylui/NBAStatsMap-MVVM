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
    static int selected_position = -1;

    public Player1SelectRecyclerViewAdapter(Context context, List<PlayerInfoModel> list){
        mDataset = list;
        mContext = context;
    }


    @Override
    public Player1SelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View teamView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_player, parent, false);
        return new Player1SelectViewHolder(teamView);
    }

    @Override
    public void onBindViewHolder(final Player1SelectViewHolder viewHolder, final int position) {
        String playerId = String.valueOf(mDataset.get(position).getPlayerId());
        final String url = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/" + playerId + ".png";

        final String theLastName = mDataset.get(position).getLastName();

        viewHolder.playerName.setText(theLastName);
        viewHolder.playerName.setTextColor(selected_position == position? Color.parseColor("#ffffff"):Color.parseColor("#7F7E7E"));

        final Context playerPhotoContext = viewHolder.playerHeadshot.getContext();
        Picasso.with(playerPhotoContext).load(url).placeholder(R.drawable.default_nba_headshot_v2).error(R.drawable.default_nba_headshot_v2).into(viewHolder.playerHeadshot);

        viewHolder.playerHeadshot.setAlpha(selected_position == position? 1f : .3f);

        //final int logoimage = (Integer)mDataset.get(position).get("image");
        //viewHolder.teamPicture.setImageResource(logoimage);
        //viewHolder.teamPicture.setAlpha(.3f);
        //viewHolder.linearLayout.setBackgroundResource(R.color.blackgraycomp);
    }

    public interface OnPlayer1SelectClickListener {
        public void onPlayer1SelectClick(View view, int position, String teamAbbrv, int color);
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

                    if(selected_position == getAdapterPosition()){
                        selected_position = -1;
                        notifyItemChanged(selected_position);
                        notifyDataSetChanged();
                        //Interface call here to load data into textfields and chart
                    }
                    else{
                        selected_position = getAdapterPosition();
                        notifyItemChanged(selected_position);
                        notifyDataSetChanged();
                    }
                    //Call interface method here so PlayerStats Fragment can hide recyclerview and then display the button and new recyclerview with players
                    //mItemClickListener.onPlayer1SelectClick(v, getLayoutPosition(), (String)mDataset.get(getLayoutPosition()).get("name"), (Integer)mDataset.get(getLayoutPosition()).get("color"));

                    /*//Checks if previous player is the same player being selected. if so remove the data
                    if(imageViewStack.isEmpty()==false && playerHeadshot == imageViewStack.peek()&&textViewStack.isEmpty()==false && playerName == textViewStack.peek()){
                        ImageView previousImageView = imageViewStack.pop();
                        TextView previousTextView = textViewStack.pop();
                        playerHeadshot.setAlpha(.3f);
                        playerName.setTextColor(Color.parseColor("#7F7E7E"));
                        //Interface call here
                    }
                    else{
                        //If another player was selected, deselect it
                        if(imageViewStack.isEmpty()==false && textViewStack.isEmpty()==false){
                            ImageView previousImageView = imageViewStack.pop();
                            TextView previousTextView = textViewStack.pop();
                            previousImageView.setAlpha(.3f);
                            previousTextView.setTextColor(Color.parseColor("#7F7E7E"));
                        }

                        textViewStack.push(playerName);
                        imageViewStack.push(playerHeadshot);
                        playerHeadshot.setAlpha(1f);
                        playerName.setTextColor(Color.WHITE);
                        //call interface method here
                    }
                    //Call interface method here so PlayerStats Fragment can hide recyclerview and then display the button and new recyclerview with players
                    //mItemClickListener.onPlayer1SelectClick(v, getLayoutPosition(), (String)mDataset.get(getLayoutPosition()).get("name"), (Integer)mDataset.get(getLayoutPosition()).get("color"));
                    */
                }
            });

        }
    }
}
