Classes and Attributes
Main

Methods:
main(String[] args)
frontend.MainWindow

Attributes:
JPanel mainPanel
DataManager dataManager
HashMap<String, JPanel> screenMap
BackgroundPanel bp
Color primaryBackgroundColor
Color secondaryBackgroundColor
Color foregroundColor
Color redColor
Methods:
MainWindow()
registerScreen(String screenName, JPanel screen)
switchToScreen(String screenName)
frontend.BackgroundPanel

Attributes:
Image backgroundImage
Color backgroundColor
Color primaryBackgroundColor
Color secondaryBackgroundColor
Color foregroundColor
Color transparentColor
Color selectionForegroundColor
Color blackColor
Color hoverBackgroundColor
Color redColor
Methods:
BackgroundPanel(Image backgroundImage)
paintComponent(Graphics g)
frontend.ScoreCard

Attributes:
JTable battingTable
JTable bowlerTable
JTable overTable
JLabel scoreLabel
JLabel runRateLabel
JLabel projectedScoreLabel
JLabel currentTeamLabel
JLabel inningLabel
boolean isFirstInning
static final int TOTAL_OVERS_IN_MATCH
static final int MAX_WICKETS
DataManager dataManager
Match currentMatch
Player striker
Player nonStriker
Player currentBowler
Team battingTeam
Team bowlingTeam
Team team1
Team team2
int target
Methods:
ScoreCard(Image backgroundImage)
setupTopPanel()
setupCenterPanel()
setupButtonPanel()
createTableScrollPane(JTable table, String title)
createButton(String text, ActionListener listener)
createLabel(String text, int style, int size)
createNonEditableTable(DefaultTableModel model)
createScoreTable()
createBowlerTable()
createOverTable()
loadMatchData()
startMatch()
selectPlayers()
selectNewBowler()
endMatch()
resetUI()
setupMatch(Match match)
resetMatchData()
addRun(int runs)
extraRun()
addWicket()
nextBallUpdate()
switchStriker()
switchInning()
updateScoreLabel()
calculateRunRate()
calculateProjectedScore()
populateTables()
refreshData()
updateBattingTable()
updateBowlingTable()
updateOverTable()
frontend.HomeScreen

Attributes:
JButton teamSetupButton
JButton scorecardButton
JButton playerStatsButton
JButton matchSummaryButton
JButton leaderboardButton
JButton commentaryButton
Methods:
HomeScreen(MainWindow mainWindow, Image backgroundImage)
createImageButton(String altText)
frontend.TeamSetup

Attributes:
JTextField team1Field
JTextField team2Field
JTable team1Table
JTable team2Table
Methods:
TeamSetup(Image backgroundImage)
populatePlayerTable(JTable table, List<Player> players, int startId)
loadExistingData()
createPlayerTable(String teamName)
createRoleDropdown()
SubmitActionListener
frontend.PlayerStats

Attributes:
JTable playerStatsTable
DefaultTableModel playerStatsModel
DataManager dataManager
Methods:
PlayerStats(Image backgroundImage)
createPlayerStatsModel()
createNonEditableTable(DefaultTableModel model)
updatePlayerStats(String playerName, int runs, int balls, int fours, int sixes, int wickets, double overs)
refreshPlayerStats()
resetStats()
frontend.MatchSummary

Attributes:
JLabel bestBatsmanLabel
JLabel bestBowlerLabel
JLabel team1ScoreLabel
JLabel team2ScoreLabel
DefaultListModel<String> highlightReelListModel
DataManager dataManager
Methods:
MatchSummary(Image backgroundImage)
updateTotalScores()
updateTopPerformers()
updateHighlightReel(String[] highlights)
backend.DataManager

Attributes:
Logger logger
Methods:
addOrUpdateTeam(Team team)
getTeamById(int id)
getAllTeams()
getTeamName(int id)
deleteTeam(int teamId)
resetPlayerData()
resetOverSummary(int matchId)
addOrUpdatePlayer(Player player, int index)
getPlayersByTeam(int teamId)
getAllPlayers()
getFilteredPlayers()
getTopPerformers(String role, int limit)
saveTeamData(Team team)
getAllMatches()
getOngoingMatch()
saveMatchData(Match match)
saveOverSummary(int matchId, int overNumber, int runs, int wickets)
getOverSummaryByMatch(int matchId)
savePlayerData(Player player)
deleteAllPlayers()
clearPlayerStats()
endMatch(int matchId)
getPlayerByName(String nextPlayerName)
updatePlayer(Player striker)
updateMatch(Match currentMatch)
updatePlayerFours(Player striker)
updatePlayerSixes(Player striker)
saveTeamScore(int teamId, int runs, int wickets, double overs)
getTeamScore(int teamId)
startNewMatch(String tossWinner, String choice)
backend.Team

Attributes:
int id
String name
List<Player> players
Methods:
Team(int id, String name)
getId()
getName()
getPlayers()
setId(int id)
setName(String name)
setPlayers(List<Player> players)
addPlayer(Player player)
getTotalRuns()
getAvailableBatsmen()
getAvailableBowlers(int maxOvers)
getOutPlayers()
isAllOut()
resetPlayers()
getRemainingPlayers()
backend.Player

Attributes:
int id
int teamId
String name
String role
int runsScored
int runsConceded
int wickets
int ballsFaced
double oversBowled
int fours
int sixes
int maidens
boolean isOut
Methods:
Player()
Player(int id, int teamId, String name, String role)
getId()
getTeamId()
getName()
getRole()
getRunsScored()
getRunsConceded()
getWickets()
getBallsFaced()
getOversBowled()
getFours()
getSixes()
getMaidens()
getIsOut()
setId(int id)
setTeamId(int teamId)
setName(String name)
setRole(String role)
setRunsScored(int runsScored)
setRunsConceded(int runsConceded)
setWickets(int wickets)
setBallsFaced(int ballsFaced)
setOversBowled(double oversBowled)
setFours(int fours)
setSixes(int sixes)
setMaidens(int maidens)
setIsOut(boolean isOut)
backend.Match

Attributes:
int id
Team team1
Team team2
int totalRuns
int totalWickets
double totalOvers
int ballsBowled
MatchStatus status
String tossWinner
String choice
Methods:
Match()
Match(int id, Team team1, Team team2, int totalRuns, int totalWickets, double totalOvers, int ballsBowled, MatchStatus status)
getId()
getTeam1()
getTeam2()
getTotalRuns()
getTotalWickets()
getTotalOvers()
getBallsBowled()
getStatus()
getTossWinner()
getChoice()
setId(int id)
setTeam1(Team team1)
setTeam2(Team team2)
setTotalRuns(int totalRuns)
setTotalWickets(int totalWickets)
setTotalOvers(double totalOvers)
setBallsBowled(int ballsBowled)
setStatus(MatchStatus status)
setTossWinner(String tossWinner)
setChoice(String choice)
Relationships
frontend.MainWindow has a composition relationship with frontend.BackgroundPanel, frontend.HomeScreen, frontend.TeamSetup, frontend.ScoreCard, frontend.PlayerStats, and frontend.MatchSummary.
frontend.ScoreCard has an aggregation relationship with backend.DataManager, backend.Match, backend.Player, and backend.Team.
frontend.HomeScreen has an aggregation relationship with frontend.MainWindow.
frontend.TeamSetup has an aggregation relationship with backend.DataManager, backend.Team, and backend.Player.
frontend.PlayerStats has an aggregation relationship with backend.DataManager and backend.Player.
frontend.MatchSummary has an aggregation relationship with backend.DataManager and backend.Player.
backend.DataManager has an aggregation relationship with backend.Team, backend.Player, and backend.Match.
backend.Team has an aggregation relationship with backend.Player.
backend.Match has an aggregation relationship with backend.Team.