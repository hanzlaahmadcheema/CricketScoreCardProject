package backend;

public class Match {
    private int id;
    private Team team1;
    private Team team2;
    private int currentOver;
    private String status; 

    
    public Match(int id, Team team1, Team team2) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.currentOver = 0;
        this.status = "Upcoming";
    }

    
    public int getId() { return id; }
    public Team getTeam1() { return team1; }
    public Team getTeam2() { return team2; }
    public int getCurrentOver() { return currentOver; }
    public String getStatus() { return status; }

    public void setCurrentOver(int currentOver) { this.currentOver = currentOver; }
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