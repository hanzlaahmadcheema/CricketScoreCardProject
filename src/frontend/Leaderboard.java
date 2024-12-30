package frontend;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

// Leaderboard class to display and manage player records and statistics
public class Leaderboard extends BackgroundPanel {
    // Components for the leaderboard table and its data model
    private JTable leaderboardTable;
    private DefaultTableModel tableModel;
    private static final String LEADERBOARD_FILE = "leaderboard.txt"; // File to save/load leaderboard data

    // Constructor to set up the Leaderboard UI
    public Leaderboard(Image backgroundImage) {
        super(backgroundImage); // Call parent constructor to set background image
        setLayout(new BorderLayout(20, 20)); // Use BorderLayout with spacing
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        // Title label
        JLabel titleLabel = new JLabel("Leaderboard and Records", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        titleLabel.setForeground(foregroundColor);
        add(titleLabel, BorderLayout.NORTH); // Add title at the top

        // Table setup
        String[] columns = {"Player Name", "Matches", "Total Runs", "Wickets", "Highest Score", "Best Bowling"};
        tableModel = new DefaultTableModel(columns, 0); // Create table model with column headers
        leaderboardTable = createStyledTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(leaderboardTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(transparentColor), 
            "Leaderboard", 
            TitledBorder.CENTER, 
            TitledBorder.TOP, 
            new Font("Calibri", Font.BOLD, 24), 
            foregroundColor
        ));
        tableScrollPane.setBackground(backgroundColor);
        tableScrollPane.getViewport().setOpaque(false);;

        add(tableScrollPane, BorderLayout.CENTER); // Add table to the center

        // Button panel for Save and Load actions
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(backgroundColor);

        JButton saveButton = new JButton("Save Data");
        JButton loadButton = new JButton("Load Data");

        styleButton(saveButton);
        styleButton(loadButton);

        saveButton.addActionListener(e -> saveData());
        loadButton.addActionListener(e -> loadData());

        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the bottom
    }

    // Method to style buttons consistently
    private void styleButton(JButton button) {
        button.setFont(new Font("Calibri", Font.BOLD, 16));
        button.setBackground(secondaryBackgroundColor);
        button.setForeground(foregroundColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    // Method to create a styled JTable
    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model); // Create table with the given model
        table.setRowHeight(25); // Set row height
        table.setFont(new Font("Calibri", Font.PLAIN, 16)); // Set font for table cells
        table.setBackground(secondaryBackgroundColor); // Set background color
        table.setForeground(foregroundColor); // Set foreground color
        table.setGridColor(blackColor); // Set grid color
        table.setShowGrid(true); // Show grid lines
        table.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        table.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 16)); // Set font for column headers
        table.getTableHeader().setBackground(primaryBackgroundColor); // Set background color for column headers
        table.getTableHeader().setForeground(foregroundColor); // Set foreground color for column headers 
        table.getTableHeader().setResizingAllowed(false); // Disable column resizing
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(); // Create a cell renderer
        centerRenderer.setHorizontalAlignment(JLabel.CENTER); // Set alignment to center
        for (int i = 0; i < table.getColumnCount(); i++) { // Loop through each column
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer); // Set cell renderer for each column
        }
        return table;
    }

    // Method to save leaderboard data to a file
    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LEADERBOARD_FILE))) {
            int rowCount = tableModel.getRowCount();
            int colCount = tableModel.getColumnCount();

            // Write each row of data to the file
            for (int i = 0; i < rowCount; i++) {
                StringBuilder row = new StringBuilder();
                for (int j = 0; j < colCount; j++) {
                    row.append(tableModel.getValueAt(i, j));
                    if (j < colCount - 1) {
                        row.append(",");
                    }
                }
                writer.println(row.toString());
            }
            JOptionPane.showMessageDialog(this, "Data saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to load leaderboard data from a file
    private void loadData() {
        File file = new File(LEADERBOARD_FILE);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "No saved data found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            tableModel.setRowCount(0); // Clear existing data
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                tableModel.addRow(rowData); // Add each row to the table
            }
            JOptionPane.showMessageDialog(this, "Data loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to add a new player to the leaderboard
    public void addPlayer(String playerName, int matches, int totalRuns, int wickets, int highestScore, String bestBowling) {
        tableModel.addRow(new Object[]{playerName, matches, totalRuns, wickets, highestScore, bestBowling});
    }

    // Method to remove a player from the leaderboard
    public void removePlayer(String playerName) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(playerName)) {
                tableModel.removeRow(i);
                JOptionPane.showMessageDialog(this, "Player removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Player not found!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
