package frontend;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;


public class Leaderboard extends BackgroundPanel {
    
    private JTable leaderboardTable;
    private DefaultTableModel tableModel;
    private static final String LEADERBOARD_FILE = "leaderboard.txt"; 

    
    public Leaderboard(Image backgroundImage) {
        super(backgroundImage); 
        setLayout(new BorderLayout(20, 20)); 
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        
        JLabel titleLabel = new JLabel("Leaderboard and Records", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        titleLabel.setForeground(foregroundColor);
        add(titleLabel, BorderLayout.NORTH); 

        
        String[] columns = {"Player Name", "Matches", "Total Runs", "Wickets", "Highest Score", "Best Bowling"};
        tableModel = new DefaultTableModel(columns, 0); 
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

        add(tableScrollPane, BorderLayout.CENTER); 

        
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

        add(buttonPanel, BorderLayout.SOUTH); 
    }

    
    private void styleButton(JButton button) {
        button.setFont(new Font("Calibri", Font.BOLD, 16));
        button.setBackground(secondaryBackgroundColor);
        button.setForeground(foregroundColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    
    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model); 
        table.setRowHeight(25); 
        table.setFont(new Font("Calibri", Font.PLAIN, 16)); 
        table.setBackground(secondaryBackgroundColor); 
        table.setForeground(foregroundColor); 
        table.setGridColor(blackColor); 
        table.setShowGrid(true); 
        table.getTableHeader().setReorderingAllowed(false); 
        table.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 16)); 
        table.getTableHeader().setBackground(primaryBackgroundColor); 
        table.getTableHeader().setForeground(foregroundColor); 
        table.getTableHeader().setResizingAllowed(false); 
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(); 
        centerRenderer.setHorizontalAlignment(JLabel.CENTER); 
        for (int i = 0; i < table.getColumnCount(); i++) { 
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer); 
        }
        return table;
    }

    
    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LEADERBOARD_FILE))) {
            int rowCount = tableModel.getRowCount();
            int colCount = tableModel.getColumnCount();

            
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

    
    private void loadData() {
        File file = new File(LEADERBOARD_FILE);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "No saved data found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            tableModel.setRowCount(0); 
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                tableModel.addRow(rowData); 
            }
            JOptionPane.showMessageDialog(this, "Data loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public void addPlayer(String playerName, int matches, int totalRuns, int wickets, int highestScore, String bestBowling) {
        tableModel.addRow(new Object[]{playerName, matches, totalRuns, wickets, highestScore, bestBowling});
    }

    
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
