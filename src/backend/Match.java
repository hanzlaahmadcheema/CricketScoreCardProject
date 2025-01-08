package backend;

public class Match {
    private int id;
    private Team team1;
    private Team team2;
    private int runs;
    private int wickets;
    private int overs;
    private int currentOver;
    private int ballsBowled;
    private String status; 

    
    public Match(int id, int runs, int wickets, int overs, int ballsBowled, String status) {
        this.id = id;
        this.runs = runs;
        this.wickets = wickets;
        this.overs = overs;
        this.ballsBowled = ballsBowled;
        this.status = "Upcoming";
    }

    
    public int getId() { return id; }
    public Team getTeam1() { return team1; }
    public Team getTeam2() { return team2; }
    public int getRuns() { return runs; }
    public int getWickets() { return wickets; }
    public int getOvers() { return overs; }
    public int getCurrentOver() { return currentOver; }
    public int getBallsBowled() { return ballsBowled; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setTeam1(Team team1) { this.team1 = team1; }
    public void setTeam2(Team team2) { this.team2 = team2; }
    public void setRuns(int runs) { this.runs = runs; }
    public void setWickets(int wickets) { this.wickets = wickets; }
    public void setOvers(int overs) { this.overs = overs; }
    public void setCurrentOver(int currentOver) { this.currentOver = currentOver; }
    public void setBallsBowled(int ballsBowled) { this.ballsBowled = ballsBowled; }
    public void setStatus(String status) { this.status = status; }

    
    public void startMatch() {
        this.status = "Ongoing";
        System.out.println("Match started between " + team1.getName() + " and " + team2.getName());
    }

    public void endMatch() {
        this.status = "Completed";
        System.out.println("Match ended.");
    }

    public void updateScore(int runs, int wickets) {
        System.out.println("Score updated. Runs: " + runs + ", Wickets: " + wickets);
    }
}