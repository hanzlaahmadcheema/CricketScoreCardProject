package frontend;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private JPanel mainPanel;

    public MainWindow() {
        setTitle("Cricket Scorecard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu navigateMenu = new JMenu("Navigate");

        JMenuItem teamSetupMenuItem = new JMenuItem("Team Setup");
        JMenuItem scorecardMenuItem = new JMenuItem("Scorecard");
        JMenuItem matchSummaryMenuItem = new JMenuItem("Match Summary");
      
        teamSetupMenuItem.addActionListener(e -> switchToScreen("Team Setup"));
        scorecardMenuItem.addActionListener(e -> switchToScreen("Scorecard"));
        matchSummaryMenuItem.addActionListener(e -> switchToScreen("Match Summary"));

        navigateMenu.add(teamSetupMenuItem);
        navigateMenu.add(scorecardMenuItem);
        navigateMenu.add(matchSummaryMenuItem);

        menuBar.add(navigateMenu);
        setJMenuBar(menuBar);

        mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.add(new JLabel("Team Setup Screen"), "Team Setup");
        mainPanel.add(new JLabel("Scorecard Screen"), "Scorecard");
        mainPanel.add(new JLabel("Match Summary Screen"), "Match Summary");

        switchToScreen("Team Setup");
    }

    private void switchToScreen(String screenName) {
        CardLayout layout = (CardLayout) mainPanel.getLayout();
        layout.show(mainPanel, screenName);
    }
}