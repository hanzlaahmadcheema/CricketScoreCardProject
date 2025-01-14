package backend;

public class Player {

    private int id, teamId, runsScored, runsConceded, wickets, ballsFaced, fours, sixes, maidens;
    private double oversBowled, economy, ballsBowled;
    private String name, role;
    private boolean isOut;


    public Player(){}
    public Player(int id, int teamId, String name, String role) {
        this.id = id;
        this.teamId = teamId;
        this.name = name;
        this.role = role;
        this.runsScored = 0;
        this.runsConceded = 0;
        this.wickets = 0;
        this.ballsFaced = 0;
        this.oversBowled = 0.0;
        this.fours = 0;
        this.sixes = 0;
        this.maidens = 0;
        this.ballsBowled = 0;
        this.isOut = false;
    }

    
    public int getId() { return id; }
    public int getTeamId() { return teamId;}
    public int getRunsScored() { return runsScored; }
    public int getRunsConceded() { return runsConceded; }
    public int getWickets() { return wickets; }
    public int getBallsFaced() { return ballsFaced; }
    public int getFours() { return fours; }
    public int getSixes() { return sixes; }
    public int getMaidens() { return maidens; }
    public double getOversBowled() { return oversBowled; }
    public double getEconomy() { return economy; }
    public double getBallsBowled() {return ballsBowled; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public boolean getIsOut() { return isOut; }


    public void setId(int id) { this.id = id; }
    public void setTeamId(int teamId) { this. teamId = teamId; }
    public void setRunsScored(int runsScored) { this.runsScored = runsScored; }
    public void setRunsConceded(int runsConceded) { this.runsConceded = runsConceded; }
    public void setWickets(int wickets) { this.wickets = wickets; }
    public void setBallsFaced(int ballsFaced) { this.ballsFaced = ballsFaced; }
    public void setFours(int fours) { this.fours = fours; }
    public void setSixes(int sixes) { this.sixes = sixes; } 
    public void setMaidens(int maidens) { this.maidens = maidens; }
    public void setOversBowled(double oversBowled) { this.oversBowled = oversBowled; }
    public void setEconomy(double economy) { this.economy = economy; }
    public void setBallsBowled(double ballsBowled) { this.ballsBowled = ballsBowled; }
    public void setName(String name) { this.name = name; }
    public void setRole(String role) { this.role = role; }
    public void setIsOut(boolean isOut) { this.isOut = isOut; }

    
    @Override
    public String toString() {
        return name + " (" + role + ")";
    }
    
    public void updateBattingStats(int runsScored, int balls, boolean isBoundary) {
        this.runsScored += runsScored;
        this.ballsFaced += balls;
        if (isBoundary) {
            if (runsScored == 4) this.fours++;
            if (runsScored == 6) this.sixes++;
        }
    }

    public void updateBowlerStats(int runsConceded, double overs, int wickets) {
        this.runsConceded += runsConceded;
        this.oversBowled += overs;
        this.wickets += wickets;
        this.ballsBowled += overs * 6;
        this.economy = calculateEconomy();
    }

    public boolean isEligibleBowler(int maxOvers) {
        return this.oversBowled < maxOvers;
        }

        public double calculateStrikeRate() {
        return Math.round((ballsFaced == 0 ? 0 : (runsScored * 100.0) / ballsFaced) * 100.0) / 100.0;
        }

        public double calculateEconomy() {
        return Math.round((oversBowled == 0 ? 0 : runsConceded / oversBowled) * 100.0) / 100.0;
        }

    public void resetStats() {
        this.runsScored = 0;
        this.runsConceded = 0;
        this.wickets = 0;
        this.ballsFaced = 0;
        this.oversBowled = 0.0;
        this.fours = 0;
        this.sixes = 0;
        this.maidens = 0;
        this.ballsBowled = 0.0;
        this.economy = 0.0;
    }

}