package backend;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

     public void addOrUpdateTeam(Team team) {
          String query;
          if (team.getId() == 1 || team.getId() == 2) {
               // Update existing team
               query = "INSERT INTO teams (id, name) VALUES (?, ?) ON DUPLICATE KEY UPDATE name = ?";
               try (Connection connection = DatabaseConnection.getConnection();
                         PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setInt(1, team.getId());
                    stmt.setString(2, team.getName());
                    stmt.setString(3, team.getName());
                    stmt.executeUpdate();
               } catch (Exception e) {
                    e.printStackTrace();
               }
          } else {
               throw new IllegalArgumentException("Invalid team ID. Only IDs 1 and 2 are allowed.");
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

     public void addOrUpdatePlayer(Player player, int index) {
          String query = "INSERT INTO players (id, team_id, name, role) VALUES (?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE name = ?, role = ?";
          try (Connection connection = DatabaseConnection.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(query)) {
               stmt.setInt(1, player.getId());
               stmt.setInt(2, player.getTeamId());
               stmt.setString(3, player.getName());
               stmt.setString(4, player.getRole());
               stmt.setString(5, player.getName());
               stmt.setString(6, player.getRole());
               stmt.executeUpdate();
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

     public Match getOngoingMatch() {
          String query = "SELECT * FROM matches WHERE status = 'Ongoing'";
          try (Connection connection = DatabaseConnection.getConnection();
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
               if (rs.next()) {
                    return new Match(
                              rs.getInt("id"),
                              rs.getInt("runs"),
                              rs.getInt("wickets"),
                              rs.getInt("overs"),
                              rs.getInt("balls_bowled"),
                              rs.getString("status"));
               }
          } catch (Exception e) {
               e.printStackTrace();
          }
          return null; // No ongoing match
     }

 public void addMatch(Match match) {
     String query = "INSERT INTO matches (runs, wickets, overs, balls_bowled status) VALUES (?, ?, ?, ?, ?)";
     try (Connection connection = DatabaseConnection.getConnection();
     PreparedStatement stmt = connection.prepareStatement(query)) {
          stmt.setInt(1, match.getRuns());
          stmt.setInt(2, match.getWickets());
          stmt.setInt(3, match.getOvers());
          stmt.setInt(4, match.getBallsBowled());
          stmt.setString(5, match.getStatus());
          stmt.executeUpdate();
          } catch (Exception e) {
               e.printStackTrace();
               }
          }

     public void updateMatch(Match match) {
          String query = "UPDATE matches SET runs = ?, wickets = ?, overs = ?, balls_bowled = ?, status = ? WHERE id = ?";
          try (Connection connection = DatabaseConnection.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(query)) {
               stmt.setInt(1, match.getRuns());
               stmt.setInt(2, match.getWickets());
               stmt.setInt(3, match.getOvers());
               stmt.setInt(4, match.getBallsBowled());
               stmt.setString(5, match.getStatus());
               stmt.setInt(6, match.getId());
               stmt.executeUpdate();
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
