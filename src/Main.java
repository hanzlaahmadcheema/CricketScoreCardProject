import frontend.MainWindow;

public class Main {
    public static void main(String[] args) {
        
        System.out.println("Welcome to Cricket Scorecard!");


        try {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);

        } catch (Exception e) {
            System.err.println("Failed to initialize the main window: " + e.getMessage());
        }


    }
}
