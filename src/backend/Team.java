package backend;

import java.util.List;
import java.util.ArrayList;

public class Team {
    private int id;
    private String name;
    private List<Player> players;

    
    public Team(int id, String name) {
        this.id = id;
        this.name = name;
        this.players = new ArrayList<>();
    }

    
    public int getId() { return id; }   
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public List<Player> getPlayers() { return players; }

    
    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(int playerId) {
        players.removeIf(player -> player.getId() == playerId);
    }

    public void updateTeamStats() {
        int totalRuns = players.stream().mapToInt(Player::getRuns).sum();
        int totalWickets = players.stream().mapToInt(Player::getWickets).sum();
        System.out.println("Total Runs: " + totalRuns);
        System.out.println("Total Wickets: " + totalWickets);
    }
}