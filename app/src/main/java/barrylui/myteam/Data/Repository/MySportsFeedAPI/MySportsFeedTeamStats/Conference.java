package barrylui.myteam.Data.Repository.MySportsFeedAPI.MySportsFeedTeamStats;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
