import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class LvDay {

    public static ArrayList< ArrayList<LocalTime> > hours = new ArrayList< ArrayList<LocalTime> >(Arrays.asList(
            new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(8,0),LocalTime.of(8,45))),
            new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(8,45),LocalTime.of(9,30))),
            new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(9,40),LocalTime.of(10,25))),
            new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(10,25),LocalTime.of(11,10))),
            new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(11,20),LocalTime.of(12,5))),
            new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(12,5),LocalTime.of(12,50))),
            new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(12,50),LocalTime.of(13,35))),
            new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(13,35),LocalTime.of(14,20))),
            new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(14,30),LocalTime.of(15,15))),
            new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(15,15),LocalTime.of(16,0))),
            new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(16,10),LocalTime.of(16,55))),
            new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(16,55),LocalTime.of(17,40))),
            new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(17,50),LocalTime.of(18,35))),
            new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(18,35),LocalTime.of(19,20))),
            new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(19,30),LocalTime.of(20,15))),
            new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(20,15),LocalTime.of(21,0)))
    ));

    private ArrayList< ArrayList<String> > occup = new ArrayList< ArrayList<String> >();

    public LvDay(){
        // Init occupations
        for(int i = 0; i < 16; i++){
            occup.add(new ArrayList<String>());
        }
    }

    public void addOccup(int hour, String occ){
        if(!occup.get(hour).contains(occ)) {
            occup.get(hour).add(occ);
        }
    }

    public ArrayList<String> getOccups(int hour){
        return occup.get(hour);
    }
}
