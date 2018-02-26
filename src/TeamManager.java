import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class TeamManager {

    private HashMap<String,LvPlan> teamPlans = new HashMap<>();

    private LvPlan totalPlan = new LvPlan("Team LV Plan");

    private CsvReader reader;

    public TeamManager(ArrayList<String> abbrevs, CsvReader reader){
        this.reader = reader;

        for(String abbrev : abbrevs){
            teamPlans.put(abbrev, new LvPlan(abbrev + " LV Plan"));
            readMemberCsvPlan(abbrev);
        }
    }

    private void readMemberCsvPlan(String abbrev){
        reader.setAbbrev(abbrev);
        reader.readCsv(teamPlans.get(abbrev));
    }

    public void generateTotalPlan(){
        for(String key : teamPlans.keySet()){
            LvPlan plan = teamPlans.get(key);
            for(LocalDate day : plan.getDays().keySet()){
                for(int i = 0; i < 16; i++){
                    if(plan.getDays().get(day).getOccups(i).size() > 0) {
                        for(String occ : plan.getDays().get(day).getOccups(i)) {
                            totalPlan.addLv(day, i, occ);
                        }
                    }
                }
            }
        }
    }

    public LvPlan getTeamPlan(String abbrev){
        return teamPlans.get(abbrev);
    }

    public LvPlan getTotalPlan() {
        return totalPlan;
    }
}
