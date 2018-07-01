package barrylui.myteam.MySportsFeedAPI.MySportsFeedTeamRankingsModel;

/**
 * Created by The MACHINE on 4/24/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Conference {

    @SerializedName("@name")
    @Expose
    private String name;
    @SerializedName("teamentry")
    @Expose
    private List<Teamentry> teamentry = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Teamentry> getTeamentry() {
        return teamentry;
    }

    public void setTeamentry(List<Teamentry> teamentry) {
        this.teamentry = teamentry;
    }
}
