-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 19, 2025 at 08:17 PM
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
-- Table structure for table `matches`
--

CREATE TABLE `matches` (
  `id` int(11) NOT NULL,
  `team1_id` int(11) NOT NULL,
  `team2_id` int(11) NOT NULL,
  `current_over` int(11) DEFAULT 0,
  `runs` int(11) DEFAULT 0,
  `wickets` int(11) DEFAULT 0,
  `overs` int(11) DEFAULT 0,
  `balls_bowled` int(11) DEFAULT 0,
  `status` enum('Ongoing','Completed','Switching Inning','Upcoming') DEFAULT 'Upcoming',
  `toss_winner` varchar(50) DEFAULT NULL,
  `choice` varchar(50) DEFAULT NULL,
  `total_runs` int(11) DEFAULT NULL,
  `total_wickets` int(11) DEFAULT NULL,
  `total_overs` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `matches`
--

INSERT INTO `matches` (`id`, `team1_id`, `team2_id`, `current_over`, `runs`, `wickets`, `overs`, `balls_bowled`, `status`, `toss_winner`, `choice`, `total_runs`, `total_wickets`, `total_overs`) VALUES
(1, 1, 2, 0, 0, 0, 0, 0, 'Ongoing', 'aaa', 'Bat', 38, 0, 1.3);

-- --------------------------------------------------------

--
-- Table structure for table `overs`
--

CREATE TABLE `overs` (
  `id` int(11) NOT NULL,
  `match_id` int(11) NOT NULL,
  `over_number` int(11) NOT NULL,
  `runs` int(11) NOT NULL,
  `wickets` int(11) NOT NULL
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
  `wickets` int(11) DEFAULT 0,
  `ballsFaced` int(11) DEFAULT NULL,
  `oversBowled` double DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `fours` int(11) DEFAULT NULL,
  `sixes` int(11) DEFAULT NULL,
  `maidens` int(11) DEFAULT NULL,
  `is_out` tinyint(1) DEFAULT NULL,
  `ballsBowled` int(11) DEFAULT NULL,
  `economy` double DEFAULT NULL,
  `runsScored` int(11) DEFAULT 0,
  `runsConceded` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `players`
--

INSERT INTO `players` (`id`, `team_id`, `name`, `role`, `wickets`, `ballsFaced`, `oversBowled`, `created_at`, `fours`, `sixes`, `maidens`, `is_out`, `ballsBowled`, `economy`, `runsScored`, `runsConceded`) VALUES
(1, 1, 'hasim', 'Batsman', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0),
(2, 1, 'tYYAB', 'Batsman', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0),
(3, 1, 'HANZALA', 'Batsman', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0),
(4, 1, 'MUBASHIR', 'Bowler', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0),
(5, 1, 'ROMAN', 'All-Rounder', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0),
(6, 1, 'BILAWAL', 'Bowler', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0),
(7, 1, 'aa', 'All-Rounder', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0),
(8, 1, 'BABAR', 'Bowler', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0),
(9, 1, 'IRFAN', 'Batsman', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0),
(10, 1, 'ASLAM', 'Bowler', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 3, 0, 0, 18),
(11, 1, 'AHMAD', 'Bowler', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 6, 0, 0, 20),
(12, 2, 'KASIM', 'Batsman', 0, 2, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 6, 0),
(13, 2, 'QASIM', 'Bowler', 0, 7, 0, '2025-01-17 17:21:26', 2, 3, 0, 0, 0, 0, 32, 0),
(14, 2, 'CASIM', 'Batsman', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0),
(15, 2, 'KACIM', 'All-Rounder', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0),
(16, 2, 'CACIM', 'Bowler', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0),
(17, 2, 'QACIM', 'All-Rounder', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0),
(18, 2, 'QAIS', 'Batsman', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0),
(19, 2, 'aaa', 'All-Rounder', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0),
(20, 2, 'KAIS', 'Batsman', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0),
(21, 2, 'CHUU', 'Batsman', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0),
(22, 2, 'CHII', 'Bowler', 0, 0, 0, '2025-01-17 17:21:26', 0, 0, 0, 0, 0, 0, 0, 0);

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
(1, 'LLP', '2025-01-15 07:26:51'),
(2, 'aaa', '2025-01-15 07:26:51');

-- --------------------------------------------------------

--
-- Table structure for table `team_scores`
--

CREATE TABLE `team_scores` (
  `team_id` int(11) NOT NULL,
  `runs` int(11) NOT NULL,
  `wickets` int(11) NOT NULL,
  `overs` decimal(4,1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `team_scores`
--

INSERT INTO `team_scores` (`team_id`, `runs`, `wickets`, `overs`) VALUES
(1, 0, 0, 0.0),
(2, 38, 0, 1.3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `matches`
--
ALTER TABLE `matches`
  ADD PRIMARY KEY (`id`),
  ADD KEY `team1_id` (`team1_id`),
  ADD KEY `team2_id` (`team2_id`);

--
-- Indexes for table `overs`
--
ALTER TABLE `overs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `match_id` (`match_id`,`over_number`);

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
-- Indexes for table `team_scores`
--
ALTER TABLE `team_scores`
  ADD PRIMARY KEY (`team_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `matches`
--
ALTER TABLE `matches`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

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
-- Constraints for table `matches`
--
ALTER TABLE `matches`
  ADD CONSTRAINT `matches_ibfk_1` FOREIGN KEY (`team1_id`) REFERENCES `teams` (`id`),
  ADD CONSTRAINT `matches_ibfk_2` FOREIGN KEY (`team2_id`) REFERENCES `teams` (`id`);

--
-- Constraints for table `overs`
--
ALTER TABLE `overs`
  ADD CONSTRAINT `overs_ibfk_1` FOREIGN KEY (`match_id`) REFERENCES `matches` (`id`);

--
-- Constraints for table `players`
--
ALTER TABLE `players`
  ADD CONSTRAINT `players_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
