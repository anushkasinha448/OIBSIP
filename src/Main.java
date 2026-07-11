package Task1;
import javax.swing.SwingUtilities;

import Task1.LoginForm;
public class Main {

    public static void main(String[] args) {

    	   SwingUtilities.invokeLater(() ->
           new DashboardForm(-1, "Guest"));

    }

}