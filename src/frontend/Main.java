package frontend;

// The entry point for the Cricket Scorecard application
public class Main {
    public static void main(String[] args) {
        // Print a welcome message to the console
        System.out.println("Welcome to Cricket Scorecard!");

        // Create and display the main application window
        try {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        } catch (Exception e) {
            System.err.println("Failed to initialize the main window: " + e.getMessage());
        }
        
    }
}
