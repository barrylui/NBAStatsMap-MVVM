package barrylui.myteam.TeamDataNBA;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import barrylui.myteam.R;
//contains Team Abbreviations, References to Team logo image, Team color
public class NBATeamAssetsData {

    List<Map<String,?>> teamsList;

    public List<Map<String, ?>> getTeamsList() {
        return teamsList;
    }

    public int getSize(){
        return teamsList.size();
    }

    public void removeItem(int number){
        Object o = teamsList.get(number);
        teamsList.remove(o);
    }

    public void addItem(int number){
        teamsList.add(number, teamsList.get(number));
    }


    public HashMap getItem(int i){
        if (i >=0 && i < teamsList.size()){
            return (HashMap) teamsList.get(i);
        } else return null;
    }

    public NBATeamAssetsData(){
        teamsList = new ArrayList<Map<String,?>>();
        //#1-10

        teamsList.add(createTeam("MIL", R.drawable.ic_buckslogo, R.color.colorBucksPrimary));
        teamsList.add(createTeam("CHI", R.drawable.ic_bullslogo, R.color.colorBullsPrimary));
        teamsList.add(createTeam("CLE", R.drawable.ic_cavslogo, R.color.colorCavaliersPrimary));
        teamsList.add(createTeam("BOS", R.drawable.ic_celticslogo, R.color.colorCelticsPrimary));
        teamsList.add(createTeam("LAC", R.drawable.ic_clipperlogo, R.color.colorClippersPrimary));
        teamsList.add(createTeam("MEM", R.drawable.ic_grizzlies, R.color.colorGrizzliesPrimary));
        teamsList.add(createTeam("ATL", R.drawable.ic_hawkslogo, R.color.colorHawksPrimary));
        teamsList.add(createTeam("MIA", R.drawable.ic_heatlogo, R.color.colorHeatPrimary));
        teamsList.add(createTeam("CHA", R.drawable.ic_hornetslogo, R.color.colorHornetsPrimary));
        teamsList.add(createTeam("UTA", R.drawable.ic_jazzlogo, R.color.colorJazzPrimary));
        teamsList.add(createTeam("SAC", R.drawable.ic_kingslogo, R.color.colorKingsPrimary));
        teamsList.add(createTeam("NYK", R.drawable.ic_knickslogo, R.color.colorKnicksPrimary));
        teamsList.add(createTeam("LAL", R.drawable.ic_lakerslogo, R.color.colorLakersPrimary));
        teamsList.add(createTeam("ORL", R.drawable.ic_magiclogo, R.color.colorMagicPrimary));
        teamsList.add(createTeam("DAL", R.drawable.ic_mavs, R.color.colorMavericksPrimary));
        teamsList.add(createTeam("BRO", R.drawable.ic_netslogo, R.color.colorNetsPrimary));
        teamsList.add(createTeam("DEN", R.drawable.ic_nuggetslogo, R.color.colorNuggetsPrimary));
        teamsList.add(createTeam("IND", R.drawable.ic_pacerslogo, R.color.colorPacersPrimary));
        teamsList.add(createTeam("NOP", R.drawable.ic_pelicanslogo, R.color.colorPelicansPrimary));
        teamsList.add(createTeam("DET", R.drawable.ic_pistonslogo, R.color.colorPistonsPrimary));
        teamsList.add(createTeam("TOR", R.drawable.ic_raptorslogo, R.color.colorRaptorsPrimary));
        teamsList.add(createTeam("HOU", R.drawable.ic_rocketslogo, R.color.colorRocketsPrimary));
        teamsList.add(createTeam("PHI", R.drawable.ic_sixerslogo, R.color.colorSixersPrimary));
        teamsList.add(createTeam("SAS", R.drawable.ic_spurslogo, R.color.colorSpursPrimary));
        teamsList.add(createTeam("PHX", R.drawable.ic_sunslogo, R.color.colorSunsPrimary));
        teamsList.add(createTeam("OKL", R.drawable.ic_thunderlogo, R.color.colorThunderPrimary));
        teamsList.add(createTeam("POR", R.drawable.ic_trailblazerslogo, R.color.colorTrailblazersPrimary));
        teamsList.add(createTeam("MIN", R.drawable.ic_timberwolveslogo, R.color.colorTimberwolvesPrimary));
        teamsList.add(createTeam("GSW", R.drawable.ic_warriorslogo, R.color.colorWarriorsPrimary));
        teamsList.add(createTeam("WAS", R.drawable.ic_wizardslogo, R.color.colorWizardsPrimary));



    }


    private HashMap createTeam(String name, int image, int color) {
        HashMap team = new HashMap();
        team.put("name", name);
        team.put("image",image);
        team.put("color",color);
        return team;
    }
}

