package frontend;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import backend.Player;
import java.util.List;

class CustomColor extends DefaultTableCellRenderer {
    private int strikerIndex;
    private int currentBowlerIndex;
    private List<Player> players;

    public CustomColor(int strikerIndex, int currentBowlerIndex, List<Player> players) {
        this.strikerIndex = strikerIndex;
        this.currentBowlerIndex = currentBowlerIndex;
        this.players = players;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (row < players.size()) {
            Player player = players.get(row);
            if (player.getIsOut()) {
                c.setBackground(Color.RED); // Color for players who are out
            } else if (row == strikerIndex) {
                c.setBackground(Color.GREEN); // Color for the striker
            } else if (row == currentBowlerIndex) {
                c.setBackground(Color.BLUE); // Color for the current bowler
            } else {
                c.setBackground(Color.WHITE); // Default color
            }
        } else {
            c.setBackground(Color.WHITE); // Default color for empty rows
        }

        return c;
    }
}