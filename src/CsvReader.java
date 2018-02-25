import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CsvReader {

    private String link, authUser, authPw;

    public CsvReader(String authUser, String authPw){
        this.link = "";
        this.authUser = authUser;
        this.authPw = authPw;
    }

    public void setAbbrev(String abbrev){
        this.link = "https://cis.technikum-wien.at/cis/private/lvplan/stpl_kalender.php?type=student&pers_uid="+abbrev+
                "&ort_kurzbz=&stg_kz=257&sem=&ver=&grp=&gruppe_kurzbz=&lva=&begin=1517612400&ende=1533074400&format=csv";
    }

    public void readCsv(LvPlan lvplan) {
        BufferedReader buffer = null;
        String line = "", wholeLine = "";
        String csvSplitBy = ",", csvEndLine = ",,,,,";
        boolean firstLine = true;
        ArrayList<Integer> hrs;

        try {
            URL url = new URL(this.link);
            URLConnection connection = url.openConnection();

            String userpass = this.authUser+":"+this.authPw;
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

            connection.setRequestProperty ("Authorization", basicAuth);

            InputStreamReader input = new InputStreamReader(connection.getInputStream());

            buffer = new BufferedReader(input);
            while ((line = buffer.readLine()) != null) {
                if(firstLine){
                    firstLine = false;
                }else {
                    wholeLine += line;
                    if(wholeLine.endsWith(csvEndLine)) {
                        String[] splitted = wholeLine.replaceAll("\"","").split(csvSplitBy);
                        hrs = getHours(splitted[6], splitted[8]);
                        for(int hr : hrs){
                            lvplan.addLv(LocalDate.parse(splitted[5],DateTimeFormatter.ofPattern("dd.MM.uuuu")), hr, splitted[3]);
                        }
                        wholeLine = "";
                    }
                }
            }

        } catch (ArrayIndexOutOfBoundsException | IOException e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private ArrayList<Integer> getHours(String start, String end){
        // Parse start and end time
        LocalTime st = LocalTime.parse(start, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime en = LocalTime.parse(end, DateTimeFormatter.ofPattern("HH:mm:ss"));

        ArrayList<Integer> hours = new ArrayList<>();
        for(int i = 0; i < 16; i++){
            ArrayList<LocalTime> t = LvDay.hours.get(i);
            // Check if hour is included in given time
            if(st.compareTo(t.get(0)) == 0 || en.compareTo(t.get(1)) == 0 ||
                    (st.isBefore(t.get(0)) && en.isAfter(t.get(1)))){
                hours.add(i);
            }
        }

        return hours;
    }

}
