package backend;

import java.util.List;
import java.util.ArrayList;

public class Leaderboard {
    private List<Player> topPlayers;

    
    public Leaderboard() {
        this.topPlayers = new ArrayList<>();
    }

    
    public List<Player> getTopPlayers() { return topPlayers; }

    
    public void updateLeaderboard(List<Player> players) {
        players.sort((p1, p2) -> Integer.compare(p2.getRunsScored(), p1.getRunsScored()));
        topPlayers = players.subList(0, Math.min(players.size(), 10));
    }

    public void displayTopPlayers() {
        System.out.println("Top Players:");
        for (Player player : topPlayers) {
            System.out.println(player.getName() + " - Runs: " + player.getRunsScored());
        }
    }
}
