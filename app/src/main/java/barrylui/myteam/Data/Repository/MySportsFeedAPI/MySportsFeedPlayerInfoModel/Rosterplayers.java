package barrylui.myteam.Data.Repository.MySportsFeedAPI.MySportsFeedPlayerInfoModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Rosterplayers {

    @SerializedName("lastUpdatedOn")
    @Expose
    private String lastUpdatedOn;
    @SerializedName("playerentry")
    @Expose
    private List<Playerentry> playerentry = null;

    public String getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public List<Playerentry> getPlayerentry() {
        return playerentry;
    }

    public void setPlayerentry(List<Playerentry> playerentry) {
        this.playerentry = playerentry;
    }

}