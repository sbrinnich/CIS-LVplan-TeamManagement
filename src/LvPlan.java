import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.TreeMap;

public class LvPlan {

    private String label;

    private TreeMap<LocalDate,LvDay> days = new TreeMap<>();

    public LvPlan(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }

    public TreeMap<LocalDate,LvDay> getDays() {
        return days;
    }

    public void addLv(LocalDate date, int hour, String desc){
        if(!days.containsKey(date)) {
            days.put(date, new LvDay());
        }
        days.get(date).addOccup(hour, desc);
    }

    public void printPlanToConsole(){
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

    public void printPlanToFile(String filepath){
        BufferedWriter writer = null;
        try{
            File file = new File(filepath);
            writer = new BufferedWriter(new FileWriter(file));

            // Save basic constructs
            String tableBegin = "<div style=\"padding-top: 10px;\" class=\"page-break-after\">\t<table class=\"stdplan\" " +
                    "width=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" name=\"Stundenplantabelle\" " +
                    "align=\"center\">\n<thead><tr>\n" +
                    "\t\t\t<th align=\"right\">Stunde&nbsp;<br>Beginn&nbsp;<br>Ende&nbsp;</th>\n" +
                    "\t\t\t<th><div align=\"center\">1<br>&nbsp;08:00&nbsp;<br>&nbsp;08:45&nbsp;</div></th>\n" +
                    "\t\t\t<th><div align=\"center\">2<br>&nbsp;08:45&nbsp;<br>&nbsp;09:30&nbsp;</div></th>\n" +
                    "\t\t\t<th><div align=\"center\">3<br>&nbsp;09:40&nbsp;<br>&nbsp;10:25&nbsp;</div></th>\n" +
                    "\t\t\t<th><div align=\"center\">4<br>&nbsp;10:25&nbsp;<br>&nbsp;11:10&nbsp;</div></th>\n" +
                    "\t\t\t<th><div align=\"center\">5<br>&nbsp;11:20&nbsp;<br>&nbsp;12:05&nbsp;</div></th>\n" +
                    "\t\t\t<th><div align=\"center\">6<br>&nbsp;12:05&nbsp;<br>&nbsp;12:50&nbsp;</div></th>\n" +
                    "\t\t\t<th><div align=\"center\">7<br>&nbsp;12:50&nbsp;<br>&nbsp;13:35&nbsp;</div></th>\n" +
                    "\t\t\t<th><div align=\"center\">8<br>&nbsp;13:35&nbsp;<br>&nbsp;14:20&nbsp;</div></th>\n" +
                    "\t\t\t<th><div align=\"center\">9<br>&nbsp;14:30&nbsp;<br>&nbsp;15:15&nbsp;</div></th>\n" +
                    "\t\t\t<th><div align=\"center\">10<br>&nbsp;15:15&nbsp;<br>&nbsp;16:00&nbsp;</div></th>\n" +
                    "\t\t\t<th><div align=\"center\">11<br>&nbsp;16:10&nbsp;<br>&nbsp;16:55&nbsp;</div></th>\n" +
                    "\t\t\t<th><div align=\"center\">12<br>&nbsp;16:55&nbsp;<br>&nbsp;17:40&nbsp;</div></th>\n" +
                    "\t\t\t<th><div align=\"center\">13<br>&nbsp;17:50&nbsp;<br>&nbsp;18:35&nbsp;</div></th>\n" +
                    "\t\t\t<th><div align=\"center\">14<br>&nbsp;18:35&nbsp;<br>&nbsp;19:20&nbsp;</div></th>\n" +
                    "\t\t\t<th><div align=\"center\">15<br>&nbsp;19:30&nbsp;<br>&nbsp;20:15&nbsp;</div></th>\n" +
                    "\t\t\t<th><div align=\"center\">16<br>&nbsp;20:15&nbsp;<br>&nbsp;21:00&nbsp;</div></th>\n" +
                    "</tr></thead><tbody>\n";
            String tableEnd = "</tbody></table>\n";

            String rowBegin = "<tr><td>";
            String rowEnd = "</tr>\n";

            String cellPlainBegin = "<td valign=\"center\" align=\"center\">";
            String cellOccupBegin = "<td nowrap valign=\"top\"><A class=\"stpl_detail\" href=\"#\">";
            String cellPlainEnd = "</td>";
            String cellOccupEnd = "</A></td>";

            String occupBegin = "<DIV align=\"center\" style=\"background-color: #C8FF00; margin-bottom: 3px;\">";
            String occupEnd = "</DIV>";

            // Write basic html construct
            writer.write("<html><head><title>Kalender</title><meta http-equiv=\"Content-Type\" " +
                    "content=\"text/html; charset=UTF-8\"><link rel=\"stylesheet\" " +
                    "href=\"style.css\" type=\"text/css\"><link rel=\"stylesheet\" media=\"print\" " +
                    "href=\"cis.css\" type=\"text/css\"><link rel=\"stylesheet\" type=\"text/css\" " +
                    "media=\"print\" href=\"print.css\" /></head><body id=\"inhalt\">");

            if(days.size() > 0){
                boolean run = true;
                LocalDate curr = days.firstKey();
                curr = curr.minusDays(curr.getDayOfWeek().getValue()-1);
                while(run){
                    if(curr.getDayOfWeek() == DayOfWeek.MONDAY){
                        writer.write(tableBegin);
                    }
                    writer.write(rowBegin);
                    writer.write(curr.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.GERMAN)+"<br>");
                    writer.write(curr.getDayOfMonth()+". ");
                    writer.write(curr.getMonth().getDisplayName(TextStyle.SHORT, Locale.GERMAN)+" ");
                    writer.write(curr.getYear()+"<br>");
                    writer.write(cellPlainEnd);

                    for(int i = 0; i < 16; i++){
                        if(days.containsKey(curr) && days.get(curr).getOccups(i).size() > 0){
                            writer.write(cellOccupBegin);
                            for(String occ : days.get(curr).getOccups(i)){
                                writer.write(occupBegin);
                                writer.write(occ.replaceAll("\n","<br>"));
                                writer.write(occupEnd);
                            }
                            writer.write(cellOccupEnd);
                        }else{
                            writer.write(cellPlainBegin);
                            writer.write(cellPlainEnd);
                        }
                    }

                    writer.write(rowEnd);

                    if(curr.getDayOfWeek() == DayOfWeek.SATURDAY &&
                            (curr.isAfter(days.lastKey()) || curr.isEqual(days.lastKey()))){
                        run = false;
                    }

                    if(curr.getDayOfWeek() == DayOfWeek.SATURDAY){
                        writer.write(tableEnd);
                        curr = curr.plusDays(1);
                    }
                    curr = curr.plusDays(1);
                }
            }

            writer.write("</body></html>");
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
