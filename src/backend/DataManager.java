package backend;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    
    public void addTeam(Team team) {
        String query = "INSERT INTO teams (name) VALUES (?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             stmt.setString(1, team.getName());
             stmt.executeUpdate();
             System.out.println("Team added successfully: " + team.getName());
        } catch (Exception e) {
             e.printStackTrace();
        }
    }    

    public List<Team> getAllTeams() {
        List<Team> teams = new ArrayList<>();
        String query = "SELECT * FROM teams";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             while (rs.next()) {
                 Team team = new Team(rs.getInt("id"), rs.getString("name"));
                 teams.add(team);
             }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return teams;
    }
    
    public void updateTeam(Team team) {
        String query = "UPDATE teams SET name = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             stmt.setString(1, team.getName());
             stmt.setInt(2, team.getId());
             stmt.executeUpdate();
             System.out.println("Team updated successfully: " + team.getName());
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    public void deleteTeam(int teamId) {
        String query = "DELETE FROM teams WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             stmt.setInt(1, teamId);
             stmt.executeUpdate();
             System.out.println("Team deleted successfully: ID " + teamId);
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    public void addPlayer(Player player) {
        String query = "INSERT INTO players (team_id, name, role) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             stmt.setInt(1, player.getId());
             stmt.setString(2, player.getName());
             stmt.setString(3, player.getRole());
             stmt.executeUpdate();
             System.out.println("Player added successfully: " + player.getName());
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    public void updatePlayerStats(Player player) {
        String query = "UPDATE players SET runs = ?, wickets = ?, balls_faced = ?, overs_bowled = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             stmt.setInt(1, player.getRuns());
             stmt.setInt(2, player.getWickets());
             stmt.setInt(3, player.getBallsFaced());
             stmt.setDouble(4, player.getOversBowled());
             stmt.setInt(5, player.getId());
             stmt.executeUpdate();
             System.out.println("Player stats updated successfully: " + player.getName());
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    public List<Player> getPlayersByTeam(int teamId) {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM players WHERE team_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             stmt.setInt(1, teamId);
             ResultSet rs = stmt.executeQuery();
             while (rs.next()) {
                 Player player = new Player();
                 player.setId(rs.getInt("id"));
                 player.setName(rs.getString("name"));
                 player.setRole(rs.getString("role"));
                 player.setRuns(rs.getInt("runs"));
                 player.setWickets(rs.getInt("wickets"));
                 player.setBallsFaced(rs.getInt("balls_faced"));
                 player.setOversBowled(rs.getDouble("overs_bowled"));
                 players.add(player);
             }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return players;
    }

    public void startMatch(int team1Id, int team2Id, String matchType) {
        String query = "INSERT INTO matches (team1_id, team2_id, status) VALUES (?, ?, 'Ongoing')";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             stmt.setInt(1, team1Id);
             stmt.setInt(2, team2Id);
             stmt.executeUpdate();
             System.out.println("Match started successfully between Team " + team1Id + " and Team " + team2Id);
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    public void updateMatchScore(int matchId, int runs, int wickets, int currentOver) {
        String query = "UPDATE matches SET total_runs = ?, total_wickets = ?, current_over = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             stmt.setInt(1, runs);
             stmt.setInt(2, wickets);
             stmt.setInt(3, currentOver);
             stmt.setInt(4, matchId);
             stmt.executeUpdate();
             System.out.println("Match score updated successfully for Match ID " + matchId);
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    public void endMatch(int matchId) {
        String query = "UPDATE matches SET status = 'Completed' WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             stmt.setInt(1, matchId);
             stmt.executeUpdate();
             System.out.println("Match ended successfully for Match ID " + matchId);
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    public List<Player> getLeaderboardData() {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM players ORDER BY runs DESC LIMIT 10";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             while (rs.next()) {
                 Player player = new Player();
                 player.setId(rs.getInt("id"));
                 player.setName(rs.getString("name"));
                 player.setRuns(rs.getInt("runs"));
                 player.setWickets(rs.getInt("wickets"));
                 players.add(player);
             }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return players;
    }

    public void updateLeaderboard(Player player) {
        String query = "UPDATE leaderboard SET total_runs = ?, total_wickets = ? WHERE player_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             stmt.setInt(1, player.getRuns());
             stmt.setInt(2, player.getWickets());
             stmt.setInt(3, player.getId());
             stmt.executeUpdate();
             System.out.println("Leaderboard updated for Player ID: " + player.getId());
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

}
