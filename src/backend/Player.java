package backend;


import java.util.*;

public class Player {
    private int id;
    private int teamId;
    private String name;
    private String role; 
    private int runs;
    private int wickets;
    private int ballsFaced;
    private double oversBowled;

    public Player(){
        
    }
    
    public Player(int id, int teamId, String name, String role) {
        this.id = id;
        this.teamId = teamId;
        this.name = name;
        this.role = role;
        this.runs = 0;
        this.wickets = 0;
        this.ballsFaced = 0;
        this.oversBowled = 0.0;
    }

    
    public int getId() { return id; }
    public int getTeamId() { return teamId;}
    public String getName() { return name; }
    public String getRole() { return role; }
    public int getRuns() { return runs; }
    public int getWickets() { return wickets; }
    public int getBallsFaced() { return ballsFaced; }
    public double getOversBowled() { return oversBowled; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setRuns(int runs) { this.runs = runs; }
    public void setRole(String role) { this.role = role; }
    public void setWickets(int wickets) { this.wickets = wickets; }
    public void setBallsFaced(int ballsFaced) { this.ballsFaced = ballsFaced; }
    public void setOversBowled(double oversBowled) { this.oversBowled = oversBowled; }

    
    public void updateStats(int runs, int wickets, int ballsFaced, double oversBowled) {
        this.runs += runs;
        this.wickets += wickets;
        this.ballsFaced += ballsFaced;
        this.oversBowled += oversBowled;
    }

    public double calculateStrikeRate() {
        return ballsFaced == 0 ? 0 : ((double) runs / ballsFaced) * 100;
    }
}