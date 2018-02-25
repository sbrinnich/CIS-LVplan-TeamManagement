import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigReader {

    public static ArrayList<String> readFile(String filepath){
        ArrayList<String> lines = new ArrayList<>();

        try {
            File file = new File(filepath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            while((line = reader.readLine()) != null){
                lines.add(line);
            }
        }catch(IOException e){
            System.err.println("Error reading properties file: "+e.getMessage());
            System.exit(1);
        }

        return lines;
    }

}
