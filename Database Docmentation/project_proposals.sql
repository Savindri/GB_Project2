-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 25, 2021 at 04:32 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gb_projects`
--

-- --------------------------------------------------------

--
-- Table structure for table `project_proposals`
--

CREATE TABLE `project_proposals` (
  `proposal_ID` int(11) NOT NULL,
  `projectName` varchar(50) NOT NULL,
  `budget` double NOT NULL,
  `completionDate` date NOT NULL,
  `productCategory` varchar(20) NOT NULL,
  `sellOrNot` varchar(10) NOT NULL,
  `description` varchar(400) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `project_proposals`
--

INSERT INTO `project_proposals` (`proposal_ID`, `projectName`, `budget`, `completionDate`, `productCategory`, `sellOrNot`, `description`, `status`) VALUES
(10, 'ABC', 10000, '2021-08-12', 'IT', 'sell', 'product description.', 'pending'),
(11, 'Project A', 1000000, '2021-10-12', 'IT', 'Not', 'product description.', 'pending'),
(12, 'Project B', 1000000, '2022-10-12', 'IT', 'Not', 'product description.', 'pending'),
(13, 'Project C', 1000000, '2022-12-12', 'IT', 'Sell', 'product description.', 'pending');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `project_proposals`
--
ALTER TABLE `project_proposals`
  ADD PRIMARY KEY (`proposal_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `project_proposals`
--
ALTER TABLE `project_proposals`
  MODIFY `proposal_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
