package frontend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeamSetupGUI extends BackgroundPanel {
    private JTextField team1Field;
    private JTextField team2Field;
    private JTable team1Table;
    private JTable team2Table;

    public TeamSetupGUI(Image backgroundImage) {
        super(backgroundImage);
        setLayout(new BorderLayout(20, 20)); 
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        JPanel teamNamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        teamNamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        teamNamePanel.setBackground(new Color(255, 255, 255, 50));
        
        team1Field = new JTextField(20); 
        team1Field.setToolTipText("Enter Team 1 name");
        team1Field.setFont(new Font("Garamond", Font.PLAIN, 16));
        team1Field.setBackground(new Color(57, 62, 70));
        team1Field.setForeground(Color.WHITE);
        team1Field.setCaretColor(Color.WHITE);
        team1Field.setHorizontalAlignment(SwingConstants.LEFT);
        
        JLabel team1NameLabel = new JLabel("Team 1 Name:", SwingConstants.LEFT);
        team1NameLabel.setFont(new Font("Garamond", Font.BOLD, 16));
        team1NameLabel.setForeground(Color.WHITE);
        
        teamNamePanel.add(team1NameLabel);
        teamNamePanel.add(team1Field);
        


        team2Field = new JTextField(20);
        team2Field.setToolTipText("Enter Team 2 name");
        team2Field.setFont(new Font("Garamond", Font.PLAIN, 16));
        team2Field.setBackground(new Color(57, 62, 70));
        team2Field.setForeground(Color.WHITE);
        team2Field.setCaretColor(Color.WHITE);
        team2Field.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel team2NameLabel = new JLabel("Team 2 Name:", SwingConstants.LEFT);
        team2NameLabel.setFont(new Font("Garamond", Font.BOLD, 16));
        team2NameLabel.setForeground(Color.WHITE);

        teamNamePanel.add(team2NameLabel);
        teamNamePanel.add(team2Field);

        add(teamNamePanel, BorderLayout.NORTH);

        JPanel playerPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        playerPanel.setBackground(new Color(255, 255, 255, 50));
        playerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        team1Table = createPlayerTable("Team 1");
        JScrollPane team1ScrollPane = new JScrollPane(team1Table);
        team1ScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        team1ScrollPane.setOpaque(false);
        team1ScrollPane.getViewport().setOpaque(false);
        playerPanel.add(team1ScrollPane);

        team2Table = createPlayerTable("Team 2");
        JScrollPane team2ScrollPane = new JScrollPane(team2Table);
        team2ScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        team2ScrollPane.setOpaque(false);
        team2ScrollPane.getViewport().setOpaque(false);
        playerPanel.add(team2ScrollPane);

        add(playerPanel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit Teams");
        submitButton.setFont(new Font("Garamond", Font.BOLD, 18));
        submitButton.setBackground(new Color(34, 40, 49)); 
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 
        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(new Color(57, 62, 70));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(new Color(34, 40, 49));
            }
        });
        submitButton.addActionListener(new SubmitActionListener());
        add(submitButton, BorderLayout.SOUTH);
    }
    private JTable createPlayerTable(String teamName) {
        String[] columns = {"Player Name", "Role"};        
        DefaultTableModel tableModel = new DefaultTableModel(columns, 11) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1;
            }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Garamond", Font.PLAIN, 14));
        table.setBackground(new Color(57, 62, 70));
        table.setForeground(Color.WHITE);
        table.setGridColor(Color.BLACK);
        table.setShowGrid(true);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setRowHeight(25);
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(createRoleDropdown()));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1));

        JTableHeader tableHeader = table.getTableHeader(); 
        tableHeader.setBackground(new Color(34, 40, 49));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setFont(new Font("Garamond", Font.BOLD, 16));
        tableHeader.setOpaque(false);



        table.setOpaque(false);

        return table;
    }

    private JComboBox<String> createRoleDropdown() {
        return new JComboBox<>(new String[]{"Batsman", "Bowler", "All-Rounder"});
    }

    private class SubmitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String team1Name = team1Field.getText().trim();
            String team2Name = team2Field.getText().trim();

            if (team1Name.isEmpty() || team2Name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Both teams must have a name!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!validateTeamPlayers(team1Table, team1Name) || !validateTeamPlayers(team2Table, team2Name)) {
                return;
            }

            JOptionPane.showMessageDialog(null, "Teams setup successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }

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
