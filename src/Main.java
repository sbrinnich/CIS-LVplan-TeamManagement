import javax.swing.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args){
        // Get team from properties file
        ArrayList<String> team = ConfigReader.readFile(System.getProperty("user.dir")+"\\team.properties");

        // Get user credentials for cis
        String authUser = JOptionPane.showInputDialog("Username");
        String authPw = Main.showPasswordDialog();

        // Create Csv Reader
        CsvReader reader = new CsvReader(authUser, authPw);

        // Create Team Manager
        TeamManager tm = new TeamManager(team, reader);
        tm.getTeamPlan(team.get(0)).printPlan();
    }

    private static String showPasswordDialog(){
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Password:");
        JPasswordField pass = new JPasswordField(30);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Eingabe",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[1]);
        if(option == 0) {
            char[] password = pass.getPassword();
            return new String(password);
        }
        return "";
    }

}
