-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 08, 2025 at 04:21 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cricket_score`
--

-- --------------------------------------------------------

--
-- Table structure for table `leaderboard`
--

CREATE TABLE `leaderboard` (
  `id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `total_runs` int(11) DEFAULT 0,
  `total_wickets` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `matches`
--

CREATE TABLE `matches` (
  `id` int(11) NOT NULL,
  `team1_id` int(11) NOT NULL,
  `team2_id` int(11) NOT NULL,
  `status` enum('Ongoing','Completed','Upcoming') DEFAULT 'Upcoming',
  `current_over` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `players`
--

CREATE TABLE `players` (
  `id` int(11) NOT NULL,
  `team_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `role` enum('Batsman','Bowler','All-Rounder') NOT NULL,
  `runs` int(11) DEFAULT 0,
  `wickets` int(11) DEFAULT 0,
  `balls_faced` int(11) DEFAULT 0,
  `overs_bowled` double DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `players`
--

INSERT INTO `players` (`id`, `team_id`, `name`, `role`, `runs`, `wickets`, `balls_faced`, `overs_bowled`, `created_at`) VALUES
(1, 1, 'Babar Azam', 'Batsman', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(2, 1, 'Muhammad Rizwan (c) (wk)', 'Batsman', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(3, 1, 'Saim Ayub', 'Batsman', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(4, 1, 'Omair Yousuf', 'Batsman', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(5, 1, 'Saim Ayub', 'Batsman', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(6, 1, 'Usman Khan', 'Batsman', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(7, 1, 'Salman Agha', 'All-Rounder', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(8, 1, 'Abbas Afridi', 'Bowler', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(9, 1, 'Haris Rauf', 'Bowler', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(10, 1, 'Naseem Shah', 'Bowler', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(11, 1, 'Shaheen Shah Afridi', 'Bowler', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(12, 2, 'Rohit Sharma', 'Batsman', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(13, 2, 'Sarfraz Khan', 'Batsman', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(14, 2, 'Virat Kohli', 'Batsman', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(15, 2, 'Rishabh Pant (wk)', 'Batsman', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(16, 2, 'Shubman Gill', 'Batsman', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(17, 2, 'Ravichandran Ashwin', 'All-Rounder', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(18, 2, 'Ravindra Jadeja', 'All-Rounder', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(19, 2, ' Jasprit Bumrah (vc)', 'Bowler', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(20, 2, 'Mohammed Siraj', 'Bowler', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(21, 2, 'Harshit Rana', 'Bowler', 0, 0, 0, 0, '2025-01-08 14:02:10'),
(22, 2, 'Akash Deep', 'Bowler', 0, 0, 0, 0, '2025-01-08 14:02:10');

-- --------------------------------------------------------

--
-- Table structure for table `teams`
--

CREATE TABLE `teams` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `teams`
--

INSERT INTO `teams` (`id`, `name`, `created_at`) VALUES
(1, 'Pakistan', '2025-01-08 14:02:10'),
(2, 'India', '2025-01-08 14:02:10');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `leaderboard`
--
ALTER TABLE `leaderboard`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `player_id` (`player_id`);

--
-- Indexes for table `matches`
--
ALTER TABLE `matches`
  ADD PRIMARY KEY (`id`),
  ADD KEY `team1_id` (`team1_id`),
  ADD KEY `team2_id` (`team2_id`);

--
-- Indexes for table `players`
--
ALTER TABLE `players`
  ADD PRIMARY KEY (`id`),
  ADD KEY `team_id` (`team_id`);

--
-- Indexes for table `teams`
--
ALTER TABLE `teams`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `leaderboard`
--
ALTER TABLE `leaderboard`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `matches`
--
ALTER TABLE `matches`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `players`
--
ALTER TABLE `players`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=199;

--
-- AUTO_INCREMENT for table `teams`
--
ALTER TABLE `teams`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `leaderboard`
--
ALTER TABLE `leaderboard`
  ADD CONSTRAINT `leaderboard_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`);

--
-- Constraints for table `matches`
--
ALTER TABLE `matches`
  ADD CONSTRAINT `matches_ibfk_1` FOREIGN KEY (`team1_id`) REFERENCES `teams` (`id`),
  ADD CONSTRAINT `matches_ibfk_2` FOREIGN KEY (`team2_id`) REFERENCES `teams` (`id`);

--
-- Constraints for table `players`
--
ALTER TABLE `players`
  ADD CONSTRAINT `players_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
