package backend;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Connected to database!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
