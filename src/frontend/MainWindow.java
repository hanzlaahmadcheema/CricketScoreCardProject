package frontend;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainWindow extends JFrame {
    private JPanel mainPanel;
    private HashMap<String, JPanel> screenMap;

    public MainWindow() {
        setTitle("Cricket Scoreboard");
        setSize(1250, 760);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setUndecorated(true);


        Image backgroundImage = new ImageIcon("src/frontend/images/backgroundImage.jpg").getImage();
        //list of fonts
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String font : fonts) {
            System.out.println(font);
        }
        JPanel navBarPanel = new JPanel();
        navBarPanel.setLayout(new BoxLayout(navBarPanel, BoxLayout.X_AXIS));
        navBarPanel.setBackground(new Color(34, 40, 49));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(34, 40, 49));

        String[] screens = {"Home", "Team Setup", "Scorecard", "Player Statistics","Match Summary", "Leaderboard", "Commentary"};
        for (String screen : screens) {
            JButton navButton = new JButton(screen);
            navButton.setFont(new Font("Garamond", Font.BOLD, 16));
            navButton.setBackground(new Color(57, 62, 70));
            navButton.setForeground(new Color(238, 238, 238));
            navButton.setFocusPainted(false);
            navButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            navButton.addActionListener(e -> switchToScreen(screen));
            buttonPanel.add(navButton);
        }

        navBarPanel.add(Box.createHorizontalGlue());
        navBarPanel.add(buttonPanel);
        navBarPanel.add(Box.createHorizontalGlue());

        add(navBarPanel, BorderLayout.NORTH);

        mainPanel = new BackgroundPanel(backgroundImage); 
        mainPanel.setLayout(new CardLayout()); 
        add(mainPanel, BorderLayout.CENTER);

        screenMap = new HashMap<>();
        registerScreen("Home", new HomeScreen(this, backgroundImage));
        registerScreen("Team Setup", new TeamSetupGUI(backgroundImage));
        registerScreen("Scorecard", new ScoreCardGUI(backgroundImage));
        registerScreen("Player Statistics", new PlayerStatsGUI(backgroundImage));
        registerScreen("Match Summary", new MatchSummaryGUI(backgroundImage));
        registerScreen("Leaderboard", new LeaderboardGUI(backgroundImage));
        registerScreen("Commentary", new CommentaryPanel(backgroundImage));

        switchToScreen("Home");
    }

    private void registerScreen(String screenName, JPanel screen) {
        screenMap.put(screenName, screen);
        mainPanel.add(screen, screenName);
    }

    public void switchToScreen(String screenName) {
        if (screenMap.containsKey(screenName)) {
            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, screenName);
        } else {
            System.err.println("Screen not found: " + screenName);
        }
    }

}
