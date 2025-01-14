package backend;

import java.util.ArrayList;
import java.util.List;

public class Match {

    public enum MatchStatus {
        Ongoing,
        Completed
    }

    private int id, totalRuns, totalWickets, ballsBowled, currentOver;
    private double totalOvers;
    private String tossWinner, choice, battingFirst, bowlingFirst;
    private boolean isFirstInning;
    private Team team1, team2, battingTeam, bowlingTeam;
    private Player striker, nonStriker, currentBowler;
    private List<Integer> overRuns;
    private List<Integer> overWickets;
    private MatchStatus status;


    public Match() {
        overRuns = new ArrayList<>();
        overWickets = new ArrayList<>();
    }


    public Match(int id, Team team1, Team team2, int totalRuns, int totalWickets, int totalOvers, int ballsBowled, MatchStatus status) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.totalRuns = totalRuns;
        this.totalWickets = totalWickets;
        this.totalOvers = totalOvers;
        this.ballsBowled = ballsBowled;
        this.status = status;
        this.overRuns = new ArrayList<>();
        this.overWickets = new ArrayList<>();
    }


    public int getId() { return id; }
    public int getTotalRuns() { return totalRuns; }
    public int getTotalWickets() { return totalWickets; }
    public double getTotalOvers() { return totalOvers; }
    public int getBallsBowled() { return ballsBowled; }
    public int getCurrentOver() { return currentOver; }
    public String getTossWinner() { return tossWinner; }
    public String getChoice() { return choice; }
    public String getBattingFirst() { return battingFirst;}
    public String getBowlingFirst() { return bowlingFirst;}
    public MatchStatus getStatus() { return status; }
    public boolean isFirstInning() { return isFirstInning; }
    public Team getTeam1() { return team1; }
    public Team getTeam2() { return team2; }
    public Player getStriker() { return striker;}
    public Player getNonStriker() { return nonStriker;}
    public Player getCurrentBowler() { return currentBowler;}
    public List<Integer> getOverRuns() { return overRuns; }
    public List<Integer> getOverWickets() { return overWickets; }
    public Team getBattingTeam() { return battingTeam; }
    public Team getBowlingTeam() { return bowlingTeam; }
    
    public void setId(int id) { this.id = id; }
    public void setTotalRuns(int totalRuns) { this.totalRuns = totalRuns; }
    public void setTotalWickets(int totalWickets) { this.totalWickets = totalWickets; }
    public void setTotalOvers(double totalOvers) { this.totalOvers = totalOvers; }
    public void setBallsBowled(int ballsBowled) { this.ballsBowled = ballsBowled; }
    public void setCurrentOver(int currentOver) { this.currentOver = currentOver; }
    public void setTossWinner(String tossWinner) { this.tossWinner = tossWinner; }
    public void setChoice(String choice) { this.choice = choice; }
    public void setBattingFirst(String battingFirst) { this.battingFirst = battingFirst; }
    public void setBowlingFirst(String bowlingFirst) { this.bowlingFirst = bowlingFirst; }
    public void setStatus(MatchStatus status) { this.status = status; }
    public void setFirstInning(boolean isFirstInning) { this.isFirstInning = isFirstInning; }
    public void setTeam1(Team team1) { this.team1 = team1; }
    public void setTeam2(Team team2) { this.team2 = team2; }
    public void setStriker(Player striker) { this.striker = striker; }
    public void setNonStriker(Player nonStriker) { this.nonStriker = nonStriker; }
    public void setCurrentBowler(Player currentBowler) { this.currentBowler = currentBowler; }
    public void setOverRuns(List<Integer> overRuns) { this.overRuns = overRuns; }
    public void setOverWickets(List<Integer> overWickets) { this.overWickets = overWickets; }
    public void setBattingTeam(Team battingTeam) { this.battingTeam = battingTeam; }
    public void setBowlingTeam(Team bowlingTeam) { this.bowlingTeam = bowlingTeam; }


    public void startMatch(String tossWinner, String choice) {
        if (tossWinner.equals("Team 1")) {
            if (choice.equals("Bat")) {
                this.battingTeam = team1;
                this.bowlingTeam = team2;
            } else {
                this.battingTeam = team2;
                this.bowlingTeam = team1;
            }
        } else {
            if (choice.equals("Bat")) {
                this.battingTeam = team2;
                this.bowlingTeam = team1;
            } else {
                this.battingTeam = team1;
                this.bowlingTeam = team2;
            }
        }
        this.striker = battingTeam.getPlayers().get(0);
        this.nonStriker = battingTeam.getPlayers().get(1);
        this.currentBowler = bowlingTeam.getPlayers().get(0);
    }

    public void switchInning() {
        Team temp = battingTeam;
        battingTeam = bowlingTeam;
        bowlingTeam = temp;
    }

    public void selectBatsman(Player batsman, boolean isStriker) {
        if (isStriker) {
            this.striker = batsman;
        } else {
            this.nonStriker = batsman;
        }
    }

    public void selectBowler(Player bowler) {
        this.currentBowler = bowler;
    }


    public Player getBestBatsman() {
        Player best = null;
        for (Player player : battingTeam.getPlayers()) {
            if (best == null || player.getRunsScored() > best.getRunsScored()) {
                best = player;
            }
        }
        return best;
    }

    public Player getBestBowler() {
        Player best = null;
        for (Player player : bowlingTeam.getPlayers()) {
            if (best == null || player.getWickets() > best.getWickets()) {
                best = player;
            }
        }
        return best;
    }

    public boolean isMatchTied() {
        return team1.isAllOut() && team2.isAllOut() && team1.getPlayers().stream().mapToInt(Player::getRunsScored).sum() == team2.getPlayers().stream().mapToInt(Player::getRunsScored).sum();
    }

    public void endMatch() {
        System.out.println("Match ended. Winner: " + (team1.isAllOut() ? "Team 2" : "Team 1"));
    }
    
    public void addOverRun(int runs) {
        overRuns.add(runs);
    }

    public void addOverWicket(int wickets) {
        overWickets.add(wickets);
    }

    public void addRunsToOver(int over, int runs) {
        while (overRuns.size() <= over) {
            overRuns.add(0);
        }
        overRuns.set(over, overRuns.get(over) + runs);
    }

    public void addWicketToOver(int over) {
        while (overWickets.size() <= over) {
            overWickets.add(0);
        }
        overWickets.set(over, overWickets.get(over) + 1);
    }
}