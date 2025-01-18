package backend;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
           
        public String getTeamName(int id) {
            String query = "SELECT name FROM teams WHERE id = ?";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("name");
                }
            } catch (Exception e) {
                logger.severe("Error retrieving team name by ID: " + e.getMessage());
            }
            return null;
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
        String query = "UPDATE players SET runsScored = 0, runsConceded = 0, wickets = 0, ballsFaced = 0, oversBowled = 0, fours = 0, sixes = 0, maidens = 0, is_out = false";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error resetting player data: " + e.getMessage());
        }
    }

    public void resetOverSummary(int matchId) {
        String query = "DELETE FROM overs WHERE match_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, matchId);
            stmt.executeUpdate();
            logger.info("Over summary reset successfully for match ID: " + matchId);
        } catch (Exception e) {
            logger.severe("Error resetting over summary: " + e.getMessage());
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
                    player.setRunsScored(rs.getInt("runsScored"));
                    player.setRunsConceded(rs.getInt("runsConceded"));
                    player.setWickets(rs.getInt("wickets"));
                    player.setBallsFaced(rs.getInt("ballsFaced"));
                    player.setOversBowled(rs.getDouble("oversBowled"));
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
        
        public List<Player> getAllPlayers() {
            List<Player> players = new ArrayList<>();
            String query = "SELECT * FROM players"; 
            
            try (Connection connection = DatabaseConnection.getConnection();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                
                while (resultSet.next()) {
                    Player player = new Player();
                    player.setName(resultSet.getString("name"));
                    player.setRunsScored(resultSet.getInt("runsScored"));
                    player.setBallsFaced(resultSet.getInt("ballsFaced"));
                    player.setFours(resultSet.getInt("fours"));
                    player.setSixes(resultSet.getInt("sixes"));
                    player.setRunsConceded(resultSet.getInt("runsConceded"));
                    player.setWickets(resultSet.getInt("wickets"));
                    player.setOversBowled(resultSet.getDouble("oversBowled"));
                    
                    players.add(player);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return players;
        }
        
        
        public List<Player> getFilteredPlayers() {
            List<Player> players = getAllPlayers();
            return players.stream()
                          .filter(player -> player.getRunsScored() != 0 || player.getBallsFaced() != 0 || player.getFours() != 0 ||
                                            player.getSixes() != 0 || player.getWickets() != 0 || player.getOversBowled() > 0 ||
                                            player.getRunsConceded() != 0)
                          .collect(Collectors.toList());
        }

    public List<Player> getTopPerformers(String role, int limit) {
        String query;
        if (role.equalsIgnoreCase("batsman")) {
            query = "SELECT name, runsScored, ballsFaced FROM players WHERE role = ? ORDER BY runsScored DESC LIMIT ?";
        } else if (role.equalsIgnoreCase("bowler")) {
            query = "SELECT name, wickets, oversBowled FROM players WHERE role = ? ORDER BY wickets DESC LIMIT ?";
        } else {
            throw new IllegalArgumentException("Invalid role. Only 'batsman' and 'bowler' are allowed.");
        }

        List<Player> players = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, role);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Player player = new Player();
                player.setName(rs.getString("name"));
                if (role.equalsIgnoreCase("batsman")) {
                    player.setRunsScored(rs.getInt("runsScored"));
                    player.setBallsFaced(rs.getInt("ballsFaced"));
                } else if (role.equalsIgnoreCase("bowler")) {
                    player.setWickets(rs.getInt("wickets"));
                    player.setOversBowled(rs.getDouble("oversBowled"));
                }
                players.add(player);
            }
        } catch (Exception e) {
            logger.severe("Error retrieving top performers: " + e.getMessage());
        }
        return players;
    }

           public void updatePlayerBattingStats(Player player) {
            String query = "UPDATE players SET runsScored = ?, ballsFaced = ?, fours = ?, sixes = ? WHERE id = ?";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, player.getRunsScored());
                stmt.setInt(2, player.getBallsFaced());
                stmt.setInt(3, player.getFours());
                stmt.setInt(4, player.getSixes());
                stmt.setInt(5, player.getId());
                stmt.executeUpdate();
            } catch (Exception e) {
                logger.severe("Error updating player batting stats: " + e.getMessage());
            }
        }
        
        public void updatePlayerBowlingStats(Player player) {
            String query = "UPDATE players SET runsConceded = ?, oversBowled = ?, wickets = ?, ballsBowled = ?, maidens = ?, economy = ? WHERE id = ?";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, player.getRunsConceded());
                stmt.setDouble(2, player.getOversBowled());
                stmt.setInt(3, player.getWickets());
                stmt.setDouble(4, player.getBallsBowled());
                stmt.setInt(5, player.getMaidens());
                stmt.setDouble(6, player.getEconomy());
                stmt.setInt(7, player.getId());
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
        String query = "INSERT INTO players (id, team_id, name, role, runsScored, runsConceded, wickets, ballsFaced, oversBowled, fours, sixes, maidens) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE "
                     + "runsScored = ?, runsConceded= ?, wickets = ?, ballsFaced = ?, oversBowled = ?, fours = ?, sixes = ?, maidens = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, player.getId());
            stmt.setInt(2, player.getTeamId());
            stmt.setString(3, player.getName());
            stmt.setString(4, player.getRole());
            stmt.setInt(5, player.getRunsScored());
            stmt.setInt(6, player.getRunsConceded());
            stmt.setInt(7, player.getWickets());
            stmt.setInt(8, player.getBallsFaced());
            stmt.setDouble(9, player.getOversBowled());
            stmt.setInt(10, player.getFours());
            stmt.setInt(11, player.getSixes());
            stmt.setInt(12, player.getMaidens());

            
            stmt.setInt(13, player.getRunsScored());
            stmt.setInt(14, player.getRunsConceded());
            stmt.setInt(15, player.getWickets());
            stmt.setInt(16, player.getBallsFaced());
            stmt.setDouble(17, player.getOversBowled());
            stmt.setInt(18, player.getFours());
            stmt.setInt(19, player.getSixes());
            stmt.setInt(20, player.getMaidens());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveOverSummary(int matchId, int overNumber, int runs, int wickets) {
        String query = "INSERT INTO overs (id, match_id, over_number, runs, wickets) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE runs = ?, wickets = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, overNumber);
            stmt.setInt(2, matchId);
            stmt.setInt(3, overNumber);
            stmt.setInt(4, runs);
            stmt.setInt(5, wickets);
            stmt.setInt(6, runs);
            stmt.setInt(7, wickets);
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error saving over summary: " + e.getMessage());
        }
    }

    public List<int[]> getOverSummaryByMatch(int matchId) {
        String query = "SELECT * FROM overs WHERE match_id = ? ORDER BY over_number";
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
            logger.severe("Error retrieving over summary: " + e.getMessage());
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
    
                
                Team team1 = getTeamById(rs.getInt("team1_id"));
                Team team2 = getTeamById(rs.getInt("team2_id"));
    
                if (team1 != null) {
                    team1.setPlayers(getPlayersByTeam(team1.getId())); 
                }
    
                if (team2 != null) {
                    team2.setPlayers(getPlayersByTeam(team2.getId())); 
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
             match.setTotalRuns(rs.getInt("runsScored"));
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

    public void saveTeamScore(int teamId, int runs, int wickets, double overs) {
        String query = "INSERT INTO team_scores (team_id, runs, wickets, overs) VALUES (?, ?, ?, ?) "
                     + "ON DUPLICATE KEY UPDATE runs = VALUES(runs), wickets = VALUES(wickets), overs = VALUES(overs)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, teamId);
            stmt.setInt(2, runs);
            stmt.setInt(3, wickets);
            stmt.setDouble(4, overs);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public String getTeamScore(int teamId) {
        String query = "SELECT runs, wickets, overs FROM team_scores WHERE team_id = ?";
        String score = "0/0 (0.0)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, teamId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int runs = rs.getInt("runs");
                int wickets = rs.getInt("wickets");
                double overs = rs.getDouble("overs");
                score = runs + "/" + wickets + " (" + overs + ")";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return score;
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
        String query = "UPDATE players SET team_id = ?, name = ?, role = ?, runsScored = ?, runsConceded = ?, wickets = ?, ballsFaced = ?, oversBowled = ?, fours = ?, sixes = ?, maidens = ?, is_out = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, player.getTeamId());
            stmt.setString(2, player.getName());
            stmt.setString(3, player.getRole());
            stmt.setInt(4, player.getRunsScored());
            stmt.setInt(5, player.getRunsConceded());
            stmt.setInt(6, player.getWickets());
            stmt.setInt(7, player.getBallsFaced());
            stmt.setDouble(8, player.getOversBowled());
            stmt.setInt(9, player.getFours());
            stmt.setInt(10, player.getSixes());
            stmt.setInt(11, player.getMaidens());
            stmt.setBoolean(12, player.getIsOut());
            stmt.setInt(13, player.getId());
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

    public void clearPlayerStats() {
    String query = "UPDATE players SET "
            + "wickets = 0, "
            + "ballsFaced = 0, "
            + "oversBowled = 0, "
            + "fours = 0, "
            + "sixes = 0, "
            + "maidens = 0, "
            + "is_out = 0, "
            + "ballsBowled = 0, "
            + "economy = 0, "
            + "runsScored = 0, "
            + "runsConceded = 0, "
            + "created_at = NULL"; 

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        int rowsUpdated = preparedStatement.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error clearing player stats: " + e.getMessage());
    }
}

}
