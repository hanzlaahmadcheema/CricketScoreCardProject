package frontend;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
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

        JPanel teamNamePanel = new JPanel(new GridLayout(2, 2, 20, 20));
        teamNamePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 173, 181)), "Enter Team Names", TitledBorder.LEFT, TitledBorder.TOP, new Font("Monospaced", Font.BOLD, 18), new Color(0, 173, 181)));

        team1Field = new JTextField();
        team1Field.setToolTipText("Enter Team 1 name");
        teamNamePanel.add(new JLabel("Team 1 Name:", SwingConstants.RIGHT));
        teamNamePanel.add(team1Field);

        team2Field = new JTextField();
        team2Field.setToolTipText("Enter Team 2 name");
        teamNamePanel.add(new JLabel("Team 2 Name:", SwingConstants.RIGHT));
        teamNamePanel.add(team2Field);

        add(teamNamePanel, BorderLayout.NORTH);

        JPanel playerPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        playerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 173, 181)), "Add Players", TitledBorder.LEFT, TitledBorder.TOP, new Font("Monospaced", Font.BOLD, 18), new Color(0, 173, 181)));

        team1Table = createPlayerTable("Team 1");
        JScrollPane team1ScrollPane = new JScrollPane(team1Table);
        team1ScrollPane.setBorder(BorderFactory.createTitledBorder("Team 1 Players"));
        playerPanel.add(team1ScrollPane);

        team2Table = createPlayerTable("Team 2");
        JScrollPane team2ScrollPane = new JScrollPane(team2Table);
        team2ScrollPane.setBorder(BorderFactory.createTitledBorder("Team 2 Players"));
        playerPanel.add(team2ScrollPane);

        add(playerPanel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit Teams");
        submitButton.setFont(new Font("Monospaced", Font.BOLD, 18));
        submitButton.setBackground(new Color(0, 173, 181)); 
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 
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
        table.setFont(new Font("Monospaced", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(createRoleDropdown()));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

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
