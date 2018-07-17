package barrylui.myteam.TeamStats;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import barrylui.myteam.Data.NBATeamDataSingleton;

public class TeamStatsViewModel extends ViewModel {
    public MutableLiveData<TeamStatsObject> team1Stats;
    public MutableLiveData<TeamStatsObject> team2Stats;


    public MutableLiveData<TeamStatsObject> getTeam1Stats() {
        if(team1Stats == null){
            team1Stats = new MutableLiveData<TeamStatsObject>();
        }
        return team1Stats;
    }

    public MutableLiveData<TeamStatsObject> getTeam2Stats() {
        if(team2Stats == null){
            team2Stats = new MutableLiveData<TeamStatsObject>();
        }
        return team2Stats;
    }

    //Method will take a teamStatsObject as a parameter
    //Method must iterate through TeamData HashMap and store each stat in an array.
    //Method will perform linear search through arrays to determine the team's ranking for each stat
    //Return a hashmap of Objects containing stats
    //Hashmap will have the stat category as the key and the ranking value as the value
    //Keys : offenseRadarValue. offenseRank, defenseRadarValue, defenseRank, AssistsRadarValue, AssistsRank,
    //      ReboundsRadarValue, ReboundsRank, ThreesRadarValue, ThreesRank, FreeThrowRadarValue, FreeThrowRank
    // Use values to bind to RadarChart
    // Use values to bind to Ranking textViews

    public HashMap<String, Object> getTeamStatRanking (TeamStatsObject theTeamStatsObject){
        int offenseCompareVal;
        int defenseCompareVal;
        int reboundsCompareVal;
        int assistsCompareVal;
        int threesCompareVal;
        int ftpercentCompareVal;

        double offenseRadarValue=0.0;
        String offenseRank = "";

        double defenseRadarValue=0.0;
        String defenseRank = "";

        double reboundRadarValue=0.0;
        String reboundsRank="";

        double assistRadarValue=0.0;
        String assistsRank = "";

        double threesRadarValue=0.0;
        String threesRank = "";

        double freeThrowRadarValue=0.0;
        String freeThrowRank = "";

        //Object to be returned
        HashMap<String, Object> statRankingsMap = new HashMap<>();

        //Create entry set
        Set<HashMap.Entry<String, HashMap<String, Object>>> teamEntrySet =  NBATeamDataSingleton.getInstance().getTeamDataMap().entrySet();

        //Arrays to load teamData into. size 30 for 30 NBA teams
        double[] rankOffense = new double[30];
        double[] rankDefense = new double[30];
        double[] rankRebound = new double[30];
        double[] rankAssists = new double[30];
        double[] rank3PTMade = new double[30];
        double[] rankFTP = new double[30];

        //Iterator variable
        int i = 0;
        //Load teamData into respective arrays
        for(HashMap.Entry<String, HashMap<String, Object>> teamStatsEntry : teamEntrySet){
                rankOffense[i] = (Double)teamStatsEntry.getValue().get("offense");
                rankDefense[i] = (Double)teamStatsEntry.getValue().get("defense");
                rankRebound[i] = (Double)teamStatsEntry.getValue().get("rebounds");
                rankAssists[i] = (Double)teamStatsEntry.getValue().get("assists");
                rank3PTMade[i] = (Double)teamStatsEntry.getValue().get("tpm");
                rankFTP[i] = (Double)teamStatsEntry.getValue().get("ftp");
                i++;
        }

        //Sort arrays so rank can be obtained
        Arrays.sort(rankOffense);
        Arrays.sort(rankDefense);
        Arrays.sort(rankRebound);
        Arrays.sort(rankAssists);
        Arrays.sort(rank3PTMade);
        Arrays.sort(rankFTP);

        //Perform linear search to obtain ranking for each category
        for(int j = 0; j<30; j++){
            offenseCompareVal = Double.compare(rankOffense[j], theTeamStatsObject.getPpg());
            defenseCompareVal = Double.compare(rankDefense[j], theTeamStatsObject.getOppg());
            reboundsCompareVal = Double.compare(rankRebound[j], theTeamStatsObject.getRpg());
            assistsCompareVal = Double.compare(rankAssists[j], theTeamStatsObject.getApg());
            threesCompareVal = Double.compare(rank3PTMade[j], theTeamStatsObject.getTpm());
            ftpercentCompareVal = Double.compare(rankFTP[j], theTeamStatsObject.getFtp());

            //Bind ranking and radar stat value to be passed

            //Find offense ranking and radar chart value
            if (offenseCompareVal == 0){
                offenseRadarValue = j;
                if(30-offenseRadarValue == 1){
                    offenseRank = Integer.toString(30 - (int)offenseRadarValue) + "st";
                } else if(30-offenseRadarValue == 2){
                    offenseRank = Integer.toString(30 - (int)offenseRadarValue) + "nd";
                } else if (30-offenseRadarValue == 3){
                    offenseRank = Integer.toString(30 - (int)offenseRadarValue) + "rd";
                } else {
                    offenseRank = Integer.toString( 30 - (int)offenseRadarValue) + "th";
                }

            }
            //Find defense ranking and radar chart value
            if (defenseCompareVal == 0){
                defenseRadarValue = j;
                if(defenseRadarValue == 1){
                    defenseRank = Integer.toString((int)defenseRadarValue) + "st";
                } else if(defenseRadarValue == 2){
                    defenseRank = Integer.toString((int)defenseRadarValue) + "nd";
                } else if (defenseRadarValue == 3){
                    defenseRank = Integer.toString((int)defenseRadarValue) + "rd";
                } else {
                    defenseRank = Integer.toString((int)defenseRadarValue) + "th";
                }
                defenseRadarValue = 30 - defenseRadarValue;
            }

            //Find rebound ranking and radar chart value
            if(reboundsCompareVal == 0){
                reboundRadarValue = j;
                if(30-reboundRadarValue == 1){
                    reboundsRank = Integer.toString(30 - (int)reboundRadarValue) + "st";
                } else if(30-reboundRadarValue == 2){
                    reboundsRank = Integer.toString(30 - (int)reboundRadarValue) + "nd";
                } else if (30-reboundRadarValue == 3){
                    reboundsRank = Integer.toString(30 - (int)reboundRadarValue) + "rd";
                } else {
                    reboundsRank = Integer.toString( 30 - (int)reboundRadarValue) + "th";
                }
            }

            //Find assist ranking and radar chart value
            if(assistsCompareVal == 0){
                assistRadarValue= j;
                if(30-assistRadarValue == 1){
                    assistsRank = Integer.toString(30 - (int)assistRadarValue) + "st";
                } else if(30-assistRadarValue == 2){
                    assistsRank = Integer.toString(30 - (int)assistRadarValue) + "nd";
                } else if (30-assistRadarValue == 3){
                    assistsRank = Integer.toString(30 - (int)assistRadarValue) + "rd";
                } else {
                    assistsRank = Integer.toString( 30 - (int)assistRadarValue) + "th";
                }
            }

            //Find three point made ranking and radar chart value
            if(threesCompareVal == 0 ){
                threesRadarValue = j;
                if(30-threesRadarValue == 1){
                    threesRank = Integer.toString(30 - (int)threesRadarValue) + "st";
                } else if(30-threesRadarValue == 2){
                    threesRank = Integer.toString(30 - (int)threesRadarValue) + "nd";
                } else if (30-threesRadarValue == 3){
                    threesRank = Integer.toString(30 - (int)threesRadarValue) + "3rd";
                } else {
                    threesRank = Integer.toString( 30 - (int)threesRadarValue) + "th";
                }
            }

            //Find free throw percent ranking and radar chart value
            if(ftpercentCompareVal == 0 ){
                freeThrowRadarValue = j;
                if(30-freeThrowRadarValue == 1){
                    freeThrowRank = Integer.toString(30 - (int)freeThrowRadarValue) + "st";
                } else if(30-freeThrowRadarValue == 2){
                    freeThrowRank = Integer.toString(30 - (int)freeThrowRadarValue) + "nd";
                } else if (30-freeThrowRadarValue == 3){
                    freeThrowRank = Integer.toString(30 - (int)freeThrowRadarValue) + "rd";
                } else {
                    freeThrowRank = Integer.toString( 30 - (int)freeThrowRadarValue) + "th";
                }
            }

        }

        //put data in hashmap to be passed
        statRankingsMap.put("offenseRadarVal", offenseRadarValue);
        statRankingsMap.put("offenseRank", offenseRank);
        statRankingsMap.put("defenseRadarVal", defenseRadarValue);
        statRankingsMap.put("defenseRank", defenseRank);
        statRankingsMap.put("reboundsRadarVal", reboundRadarValue);
        statRankingsMap.put("reboundsRank", reboundsRank);
        statRankingsMap.put("assistsRadarVal", assistRadarValue);
        statRankingsMap.put("assistsRank", assistsRank);
        statRankingsMap.put("threesRadarVal", threesRadarValue);
        statRankingsMap.put("threesRank", threesRank);
        statRankingsMap.put("freeThrowRank", freeThrowRank);

        //pass data
        return statRankingsMap;
    }
}
