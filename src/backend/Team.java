package backend;

import java.util.List;
import java.util.stream.Collectors;
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
    public String getName() { return name; }
    public List<Player> getPlayers() { return players; }


    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPlayers(List<Player> players) { this.players = players; }


    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public int getTotalRuns() {
        int totalRuns = 0;
        for (Player player : players) {
            totalRuns += player.getRunsScored();
        }
        return totalRuns;
    }
    
    public List<Player> getAvailableBatsmen() {
        List<Player> availableBatsmen = new ArrayList<>();
        for (Player player : players) {
            if (!player.getRole().equals("Bowler") && player.getBallsFaced() == 0) {
                availableBatsmen.add(player);
            }
        }
        return availableBatsmen;
    }

    public List<Player> getAvailableBowlers(int maxOvers) {
        List<Player> availableBowlers = new ArrayList<>();
        for (Player player : players) {
            if (player.getRole().equals("Bowler") && player.isEligibleBowler(maxOvers)) {
                availableBowlers.add(player);
            }
        }
        return availableBowlers;
    }

    public List<Player> getOutPlayers() {
        List<Player> outPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.getBallsFaced() > 0 && player.getRunsScored() == 0) {
                outPlayers.add(player);
            }
        }
        return outPlayers;
    }

    public boolean isAllOut() {
        return getAvailableBatsmen().isEmpty();
    }

    public void resetPlayers() {
        for (Player player : players) {
            player.resetStats();
        }
    }

        public List<Player> getRemainingPlayers() {
        return players.stream()
                      .filter(player -> !player.getIsOut())
                      .collect(Collectors.toList());
    }

    public Player getPlayerByName(String name) {
        return players.stream()
                      .filter(player -> player.getName().equalsIgnoreCase(name))
                      .findFirst()
                      .orElse(null);
    }
}