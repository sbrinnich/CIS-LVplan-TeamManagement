import java.time.LocalDate;
import java.util.HashMap;

public class LvPlan {

    private String label;

    private HashMap<LocalDate,LvDay> days = new HashMap<>();

    public LvPlan(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }

    public HashMap<LocalDate,LvDay> getDays() {
        return days;
    }

    public void addLv(LocalDate date, int hour, String desc){
        if(!days.containsKey(date)) {
            days.put(date, new LvDay());
        }
        days.get(date).addOccup(hour, desc);
    }

    public void printPlan(){
        System.out.println("            | 1  | 2  | 3  | 4  | 5  | 6  | 7  | 8  | 9  | 10 | 11 | 12 | 13 | 14 | 15 | 16 ");
        System.out.println("--------------------------------------------------------------------------------------------");
        for(LocalDate key : days.keySet()) {
            System.out.print(" " + key + " ");
            for(int i = 0; i < 16; i++){
                if(days.get(key).getOccups(i).size() > 0){
                    System.out.print("| x  ");
                }else{
                    System.out.print("|    ");
                }
            }
            System.out.print("\n");
        }
    }
}
