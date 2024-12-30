package frontend;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TeamSetup class for configuring team names and player details
public class TeamSetup extends BackgroundPanel {
    // Fields for team names
    private JTextField team1Field;
    private JTextField team2Field;

    // Tables for player details
    private JTable team1Table;
    private JTable team2Table;

    // Constructor to set up the Team Setup GUI
    public TeamSetup(Image backgroundImage) {
        super(backgroundImage);
        setLayout(new BorderLayout(20, 20)); // Layout with spacing between components
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the panel

        // Panel for team names
        JPanel teamNamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        teamNamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        teamNamePanel.setBackground(backgroundColor); // Semi-transparent background

        // Team 1 name field and label
        team1Field = new JTextField(20); 
        team1Field.setToolTipText("Enter Team 1 name");
        team1Field.setFont(new Font("Calibri", Font.PLAIN, 16));
        team1Field.setBackground(secondaryBackgroundColor);
        team1Field.setForeground(foregroundColor);
        team1Field.setCaretColor(foregroundColor);
        team1Field.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel team1NameLabel = new JLabel("Team 1 Name:", SwingConstants.LEFT);
        team1NameLabel.setFont(new Font("Calibri", Font.BOLD, 16));
        team1NameLabel.setForeground(foregroundColor);

        teamNamePanel.add(team1NameLabel);
        teamNamePanel.add(team1Field);

        // Team 2 name field and label
        team2Field = new JTextField(20);
        team2Field.setToolTipText("Enter Team 2 name");
        team2Field.setFont(new Font("Calibri", Font.PLAIN, 16));
        team2Field.setBackground(secondaryBackgroundColor);
        team2Field.setForeground(foregroundColor);
        team2Field.setCaretColor(foregroundColor);
        team2Field.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel team2NameLabel = new JLabel("Team 2 Name:", SwingConstants.LEFT);
        team2NameLabel.setFont(new Font("Calibri", Font.BOLD, 16));
        team2NameLabel.setForeground(foregroundColor);

        teamNamePanel.add(team2NameLabel);
        teamNamePanel.add(team2Field);

        add(teamNamePanel, BorderLayout.NORTH); // Add team name panel to the top

        // Panel for player tables
        JPanel playerPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        playerPanel.setBackground(backgroundColor);
        playerPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(transparentColor), 
            "Add Players", 
            TitledBorder.CENTER, 
            TitledBorder.TOP, 
            new Font("Calibri", Font.BOLD, 24), 
            foregroundColor
        ));

        // Table for Team 1 players
        team1Table = createPlayerTable("Team 1");
        JScrollPane team1ScrollPane = new JScrollPane(team1Table);
        team1ScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        team1ScrollPane.setOpaque(false);
        team1ScrollPane.getViewport().setOpaque(false);
        playerPanel.add(team1ScrollPane);

        // Table for Team 2 players
        team2Table = createPlayerTable("Team 2");
        JScrollPane team2ScrollPane = new JScrollPane(team2Table);
        team2ScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        team2ScrollPane.setOpaque(false);
        team2ScrollPane.getViewport().setOpaque(false);
        playerPanel.add(team2ScrollPane);

        add(playerPanel, BorderLayout.CENTER); // Add player panel to the center

        // Submit button for team setup
        JButton submitButton = new JButton("Submit Teams");
        submitButton.setFont(new Font("Calibri", Font.BOLD, 18));
        submitButton.setBackground(primaryBackgroundColor); 
        submitButton.setForeground(foregroundColor);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 
        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(secondaryBackgroundColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(primaryBackgroundColor);
            }
        });
        submitButton.addActionListener(new SubmitActionListener()); // Attach action listener
        add(submitButton, BorderLayout.SOUTH); // Add button to the bottom
    }

    // Method to create a player table for each team
    private JTable createPlayerTable(String teamName) {
        String[] columns = {"Player Name", "Role"};        
        DefaultTableModel tableModel = new DefaultTableModel(columns, 11) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1; // Allow editing only for Player Name and Role
            }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Calibri", Font.PLAIN, 14));
        table.setBackground(secondaryBackgroundColor);
        table.setForeground(foregroundColor);
        table.setGridColor(blackColor);
        table.setRowHeight(25);
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(createRoleDropdown())); // Dropdown for roles

        // Style the table header
        JTableHeader tableHeader = table.getTableHeader(); 
        tableHeader.setBackground(primaryBackgroundColor);
        tableHeader.setForeground(selectionForegroundColor);
        tableHeader.setFont(new Font("Calibri", Font.BOLD, 16));

        return table;
    }

    // Method to create a dropdown for player roles
    private JComboBox<String> createRoleDropdown() {
        return new JComboBox<>(new String[]{"Batsman", "Bowler", "All-Rounder"});
    }

    // Action listener for the submit button
    private class SubmitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String team1Name = team1Field.getText().trim();
            String team2Name = team2Field.getText().trim();

            // Validate team names
            if (team1Name.isEmpty() || team2Name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Both teams must have a name!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate players for each team
            if (!validateTeamPlayers(team1Table, team1Name) || !validateTeamPlayers(team2Table, team2Name)) {
                return;
            }

            JOptionPane.showMessageDialog(null, "Teams setup successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        // Validate player names and roles
        private boolean validateTeamPlayers(JTable table, String teamName) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                String playerName = (String) model.getValueAt(i, 0);
                String role = (String) model.getValueAt(i, 1);

                if (playerName == null || playerName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All players in " + teamName + " must have a name!", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                if (role == null || role.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All players in " + teamName + " must have a role!", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            return true;
        }
    }
}
