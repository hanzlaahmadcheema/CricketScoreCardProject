package backend;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DataManager {
     private static final Logger logger = Logger.getLogger(DataManager.class.getName());

          public void addOrUpdateTeam(Team team) {
               String query;
               if (team.getId() == 1 || team.getId() == 2) {
               query = "INSERT INTO teams (id, name) VALUES (?, ?) ON DUPLICATE KEY UPDATE name = ?";
               try (Connection connection = DatabaseConnection.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setInt(1, team.getId());
                    stmt.setString(2, team.getName());
                    stmt.setString(3, team.getName());
                    stmt.executeUpdate();
               } catch (Exception e) {
                    logger.severe("Error adding or updating team: " + e.getMessage());
               }
               } else {
               throw new IllegalArgumentException("Invalid team ID. Only IDs 1 and 2 are allowed.");
               }
          }
     
          public Team getTeamById(int id) {
               String query = "SELECT * FROM teams WHERE id = ?";
               try (Connection connection = DatabaseConnection.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(query)) {
               stmt.setInt(1, id);
               try (ResultSet result = stmt.executeQuery()) {
                    if (result.next()) {
                         return new Team(result.getInt("id"), result.getString("name"));
                    }
               }
               } catch (Exception e) {
               logger.severe("Error retrieving team by ID: " + e.getMessage());
               }
               return null;
          }
          public List<Team> getAllTeams() {
               List<Team> teams = new ArrayList<>();
               String query = "SELECT * FROM teams";
               try (Connection connection = DatabaseConnection.getConnection();
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                   while (rs.next()) {
                       Team team = new Team(rs.getInt("id"), rs.getString("name"));
                       List<Player> players = getPlayersByTeam(team.getId());
                       team.setPlayers(players);
           
                       teams.add(team);
                   }
               } catch (Exception e) {
                   System.err.println("Error retrieving teams: " + e.getMessage());
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
                    logger.severe("Error deleting team: " + e.getMessage());

               }
          }

          
    public void resetPlayerData() {
        String query = "UPDATE players SET runs = 0, wickets = 0, balls_faced = 0, overs_bowled = 0, fours = 0, sixes = 0, maidens = 0, is_out = false";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.executeUpdate();
            logger.info("Player data reset successfully.");
        } catch (Exception e) {
            logger.severe("Error resetting player data: " + e.getMessage());
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
                    logger.severe("Error adding or updating player: " + e.getMessage());
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
                       player.setTeamId(rs.getInt("team_id"));
                       player.setName(rs.getString("name"));
                       player.setRole(rs.getString("role"));
                       player.setRuns(rs.getInt("runs"));
                       player.setWickets(rs.getInt("wickets"));
                       player.setBallsFaced(rs.getInt("balls_faced"));
                       player.setOversBowled(rs.getDouble("overs_bowled"));
                       player.setFours(rs.getInt("fours"));
                       player.setSixes(rs.getInt("sixes"));
                       player.setMaidens(rs.getInt("maidens"));
                       players.add(player);
                   }
           
               } catch (Exception e) {
                   System.err.println("Error retrieving players by team ID: " + e.getMessage());
               }
               return players;
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
               logger.severe("Error updating player stats: " + e.getMessage());
          }
     }

     public void updatePlayerBowlingStats(Player bowler) {
        String query = "UPDATE players SET overs_bowled = ?, runs = ?, wickets = ?, balls_bowled = ?, maidens = ?, economy = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, bowler.getOversBowled());
            stmt.setInt(2, bowler.getRuns());
            stmt.setInt(3, bowler.getWickets());
            stmt.setDouble(4, bowler.getBallsBowled());
            stmt.setInt(5, bowler.getMaidens());
            stmt.setDouble(6, bowler.getEconomy());
            stmt.setInt(7, bowler.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error updating player bowling stats: " + e.getMessage());
        }
    }
    
    public void addOrUpdateMatch(Match match) {
        String query = "INSERT INTO matches (id, team1_id, team2_id, toss_winner, choice, total_runs, total_wickets, total_overs, balls_bowled, status) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE "
                     + "team1_id = ?, team2_id = ?, toss_winner = ?, choice = ?, total_runs = ?, total_wickets = ?, total_overs = ?, balls_bowled = ?, status = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, match.getId());
            stmt.setInt(2, match.getTeam1().getId());
            stmt.setInt(3, match.getTeam2().getId());
            stmt.setString(4, match.getTossWinner());
            stmt.setString(5, match.getChoice());
            stmt.setInt(6, match.getTotalRuns());
            stmt.setInt(7, match.getTotalWickets());
            stmt.setDouble(8, match.getTotalOvers());
            stmt.setInt(9, match.getBallsBowled());
            stmt.setString(10, match.getStatus().name());
    
            // Update part
            stmt.setInt(11, match.getTeam1().getId());
            stmt.setInt(12, match.getTeam2().getId());
            stmt.setString(13, match.getTossWinner());
            stmt.setString(14, match.getChoice());
            stmt.setInt(15, match.getTotalRuns());
            stmt.setInt(16, match.getTotalWickets());
            stmt.setDouble(17, match.getTotalOvers());
            stmt.setInt(18, match.getBallsBowled());
            stmt.setString(19, match.getStatus().name());
    
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOrUpdatePlayerStats(Player player) {
        String query = "INSERT INTO players (id, team_id, name, role, runs, wickets, balls_faced, overs_bowled, fours, sixes, maidens) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE "
                     + "runs = ?, wickets = ?, balls_faced = ?, overs_bowled = ?, fours = ?, sixes = ?, maidens = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, player.getId());
            stmt.setInt(2, player.getTeamId());
            stmt.setString(3, player.getName());
            stmt.setString(4, player.getRole());
            stmt.setInt(5, player.getRuns());
            stmt.setInt(6, player.getWickets());
            stmt.setInt(7, player.getBallsFaced());
            stmt.setDouble(8, player.getOversBowled());
            stmt.setInt(9, player.getFours());
            stmt.setInt(10, player.getSixes());
            stmt.setInt(11, player.getMaidens());

            
            stmt.setInt(12, player.getRuns());
            stmt.setInt(13, player.getWickets());
            stmt.setInt(14, player.getBallsFaced());
            stmt.setDouble(15, player.getOversBowled());
            stmt.setInt(16, player.getFours());
            stmt.setInt(17, player.getSixes());
            stmt.setInt(18, player.getMaidens());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveOverSummary(int matchId, int overNumber, int runs, int wickets) {
        String query = "INSERT INTO overs (match_id, over_number, runs, wickets) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, matchId);
            stmt.setInt(2, overNumber);
            stmt.setInt(3, runs);
            stmt.setInt(4, wickets);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<int[]> getOverSummaryByMatch(int matchId) {
        String query = "SELECT * FROM overs WHERE match_id = ?";
        List<int[]> overSummary = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, matchId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                overSummary.add(new int[] {
                        rs.getInt("over_number"),
                        rs.getInt("runs"),
                        rs.getInt("wickets")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return overSummary;
    }

    public void saveTeamData(Team team) {
        String query = "INSERT INTO teams (id, name) VALUES (?, ?) ON DUPLICATE KEY UPDATE name = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, team.getId());
            stmt.setString(2, team.getName());
            stmt.setString(3, team.getName());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Match> getAllMatches() {
        String query = "SELECT * FROM matches";
        List<Match> matches = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                matches.add(getMatchById(rs.getInt("id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matches;
    }

    public List<Player> getTopPerformers(String role, int limit) {
        String query = "SELECT * FROM players WHERE role = ? ORDER BY runs DESC LIMIT ?";
        List<Player> players = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, role);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Player player = new Player();
                player.setId(rs.getInt("id"));
                player.setName(rs.getString("name"));
                player.setRole(rs.getString("role"));
                player.setRuns(rs.getInt("runs"));
                player.setWickets(rs.getInt("wickets"));
                players.add(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players;
    }

    public Match getOngoingMatch() {
        String query = "SELECT * FROM matches WHERE status = 'Ongoing' LIMIT 1";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Match match = new Match();
                match.setId(rs.getInt("id"));
                match.setStatus(Match.MatchStatus.valueOf(rs.getString("status")));
                match.setTossWinner(rs.getString("toss_winner"));
                match.setChoice(rs.getString("choice"));
                match.setTotalRuns(rs.getInt("total_runs"));
                match.setTotalWickets(rs.getInt("total_wickets"));
                match.setTotalOvers(rs.getDouble("total_overs"));
                match.setBallsBowled(rs.getInt("balls_bowled"));
    
                // Retrieve teams
                Team team1 = getTeamById(rs.getInt("team1_id"));
                Team team2 = getTeamById(rs.getInt("team2_id"));
    
                if (team1 != null) {
                    team1.setPlayers(getPlayersByTeam(team1.getId())); // Populate players
                }
    
                if (team2 != null) {
                    team2.setPlayers(getPlayersByTeam(team2.getId())); // Populate players
                }
    
                match.setTeam1(team1);
                match.setTeam2(team2);
    
                return match;
            }
        } catch (Exception e) {
            logger.severe("Error retrieving ongoing match: " + e.getMessage());
        }
        return null;
    }

    public boolean checkOngoingMatch() {
     String query = "SELECT COUNT(*) FROM matches WHERE status = ?";
     try (Connection connection = DatabaseConnection.getConnection();
           PreparedStatement stmt = connection.prepareStatement(query)) {
          stmt.setString(1, Match.MatchStatus.Ongoing.toString());
          ResultSet rs = stmt.executeQuery();
          if (rs.next()) {
               return rs.getInt(1) > 0;
          }
     } catch (Exception e) {
          logger.severe("Error checking Ongoing match: " + e.getMessage());
     }
     return false;
}


    public Match getMatchById(int matchId) {
     String query = "SELECT * FROM matches WHERE id = ?";
     try (Connection connection = DatabaseConnection.getConnection();
          PreparedStatement stmt = connection.prepareStatement(query)) {
         stmt.setInt(1, matchId);
         ResultSet rs = stmt.executeQuery();
         if (rs.next()) {
             Match match = new Match();
             match.setId(rs.getInt("id"));
             match.setTeam1(getTeamById(rs.getInt("team1_id")));
             match.setTeam2(getTeamById(rs.getInt("team2_id")));
             match.setCurrentOver(rs.getInt("current_over"));
             match.setTotalRuns(rs.getInt("runs"));
             match.setTotalWickets(rs.getInt("wickets"));
             match.setTotalOvers(rs.getInt("overs"));
             match.setBallsBowled(rs.getInt("balls_bowled"));
             match.setStatus(Match.MatchStatus.valueOf(rs.getString("status")));
             match.setTossWinner(rs.getString("toss_winner"));
             match.setChoice(rs.getString("choice"));
             return match;
         }
     } catch (Exception e) {
         logger.severe("Error retrieving match by ID: " + e.getMessage());
     }
     return null;
 }
 
 public Match startNewMatch(String tossWinner, String choice) {
     List<Team> teams = getAllTeams();
     if (teams.size() < 2) {
         System.err.println("Not enough teams available.");
         return null;
     }
 
     Team team1 = teams.get(0);
     Team team2 = teams.get(1);
     
     team1.setPlayers(getPlayersByTeam(team1.getId()));
     team2.setPlayers(getPlayersByTeam(team2.getId()));
     
 
     if (team1.getPlayers().size() < 11 || team2.getPlayers().size() < 11) {
         System.err.println("Teams do not have enough players.");
         return null;
     }
 
     Match match = new Match(1, team1, team2, 0, 0, 0, 0, Match.MatchStatus.Ongoing);
     match.setTossWinner(tossWinner);
     match.setChoice(choice);
 
     addOrUpdateMatch(match);
 
     return match;
 }
 
 
 public void endMatch(int matchId) {
     String query = "UPDATE matches SET status = ? WHERE id = ?";
     try (Connection connection = DatabaseConnection.getConnection();
          PreparedStatement stmt = connection.prepareStatement(query)) {
         stmt.setString(1, Match.MatchStatus.Completed.toString());
         stmt.setInt(2, matchId);
         stmt.executeUpdate();
     } catch (Exception e) {
         logger.severe("Error ending match: " + e.getMessage());
     }
 }

    public Player getPlayerByName(String nextPlayerName) {
        String query = "SELECT * FROM players WHERE name = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nextPlayerName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Player player = new Player();
                player.setId(rs.getInt("id"));
                player.setName(rs.getString("name"));
                player.setRole(rs.getString("role"));
                return player;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePlayer(Player striker) {
        addOrUpdatePlayerStats(striker);
    }

    public void updateMatch(Match currentMatch) {
        addOrUpdateMatch(currentMatch);
    }

    public void updatePlayerFours(Player striker) {
        String query = "UPDATE players SET fours = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, striker.getFours());
            stmt.setInt(2, striker.getId());
            stmt.executeUpdate();
            } catch (Exception e) {
                logger.severe("Error updating player fours: " + e.getMessage());
            }
    }

    public void updatePlayerSixes(Player striker) {
        String query = "UPDATE players SET sixes = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, striker.getSixes());
            stmt.setInt(2, striker.getId());
            stmt.executeUpdate();
            } catch (Exception e) {
                logger.severe("Error updating player sixes: " + e.getMessage());
        }
    }


    public void saveMatchData(Match match) {
        String query = "UPDATE matches SET team1_id = ?, team2_id = ?, toss_winner = ?, choice = ?, status = ?, total_runs = ?, total_wickets = ?, total_overs = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, match.getTeam1().getId());
            stmt.setInt(2, match.getTeam2().getId());
            stmt.setString(3, match.getTossWinner());
            stmt.setString(4, match.getChoice());
            stmt.setString(5, match.getStatus().name());
            stmt.setInt(6, match.getTotalRuns());
            stmt.setInt(7, match.getTotalWickets());
            stmt.setDouble(8, match.getTotalOvers());
            stmt.setInt(9, match.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error saving match data: " + e.getMessage());
        }
    }

    public void savePlayerData(Player player) {
        String query = "UPDATE players SET team_id = ?, name = ?, role = ?, runs = ?, wickets = ?, balls_faced = ?, overs_bowled = ?, fours = ?, sixes = ?, maidens = ?, is_out = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, player.getTeamId());
            stmt.setString(2, player.getName());
            stmt.setString(3, player.getRole());
            stmt.setInt(4, player.getRuns());
            stmt.setInt(5, player.getWickets());
            stmt.setInt(6, player.getBallsFaced());
            stmt.setDouble(7, player.getOversBowled());
            stmt.setInt(8, player.getFours());
            stmt.setInt(9, player.getSixes());
            stmt.setInt(10, player.getMaidens());
            stmt.setBoolean(11, player.getIsOut());
            stmt.setInt(12, player.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error saving player data: " + e.getMessage());
        }
    }

        public void deleteAllPlayers() {
        String query = "DELETE FROM players";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.executeUpdate();
            logger.info("All players deleted successfully.");
        } catch (Exception e) {
            logger.severe("Error deleting all players: " + e.getMessage());
        }
    }
}
