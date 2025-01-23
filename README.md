# Cricket Score Card System 🎯

An advanced **Cricket Score Card System** built in **Java** using **Object-Oriented Programming (OOP)** principles. This project includes a dynamic admin panel for managing cricket matches, teams, players, and score updates, with seamless integration to a **MySQL database**.  

## 🚀 Features  

- **Admin Panel**: Manage matches, teams, and player data.  
- **Dynamic ScoreCard**: Live match updates with real-time scoring.  
- **User-Friendly GUI**: Built with **Java Swing** for smooth navigation.  
- **Database Integration**: All data is stored in **MySQL** with optimized queries.  
- **OOP Concepts**: Implemented inheritance, polymorphism, encapsulation, and abstraction.  
- **Match Summary & Leaderboard**: View match details and top performers.  

## 🛠️ Technologies Used  

- **Frontend**: Java Swing (GUI)  
- **Backend**: Java  
- **Database**: MySQL  
- **Version Control**: Git  

## 🗂️ Project Structure  

### Root Directory  
### `database/`  
- **`cricket_score.sql`**: Database dump file to set up the MySQL database.  

### `lib/`  
- **`mysql-connector-j-9.1.0.jar`**: MySQL JDBC driver for database connectivity.  

### `resources/`  
- **`diagram/Class Diagram.lua`**: Class diagram for the project.  
- **`images/icons/`**: Contains icons used in the user interface.  
- **`Notes.txt`**: Additional notes or references for the project.  

### `src/`  
#### `backend/`  
- **`DatabaseConnection.java`**: Manages the MySQL database connection.  
- **`DataManager.java`**: Handles database queries and CRUD operations.  
- **`Match.java`**: Represents match details like teams, scores, and status.  
- **`Player.java`**: Represents player statistics like runs and wickets.  
- **`Team.java`**: Represents team data, including players and total runs.  
- **`TestConnection.java`**: Utility to test the database connection.  

#### `database/`  
- **`dbConfig.properties`**: Configuration file for database credentials.  
- **`dbScripts/`**: Folder containing additional database scripts.  
- **`init.sql`**: SQL script to initialize the database.  

#### `frontend/`  
- **`BackgroundPanel.java`**: Custom JPanel with a background image.  
- **`HomeScreen.java`**: Main navigation screen for the application.  
- **`MainWindow.java`**: Main application window managing navigation.  
- **`MatchSummary.java`**: Displays match summaries and statistics.  
- **`PlayerStats.java`**: Displays individual player statistics.  
- **`ScoreCard.java`**: Manages and updates the live scorecard.  
- **`TeamSetup.java`**: Allows the admin to set up teams.  

#### Main Entry Point  
- **`Main.java`**: The entry point of the application.


## 💻 How to Run  

1. Clone the repository:  
   ```bash
   git clone https://github.com/your-username/cricket-score-card.git
   cd cricket-score-card
