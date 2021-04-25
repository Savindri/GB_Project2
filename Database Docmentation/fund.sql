-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 25, 2021 at 03:47 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fund`
--

-- --------------------------------------------------------

--
-- Table structure for table `fund`
--

CREATE TABLE `fund` (
  `FundID` int(11) NOT NULL,
  `ProjectID` int(11) NOT NULL,
  `F_Request_Date` timestamp NULL DEFAULT current_timestamp(),
  `Fund_Announcment` varchar(200) NOT NULL DEFAULT 'Not Granted',
  `F_Duration` varchar(100) NOT NULL,
  `A_Instructions` varchar(500) NOT NULL DEFAULT 'None',
  `D_modified_date` timestamp NULL DEFAULT NULL,
  `Fund_amount` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `fund`
--

INSERT INTO `fund` (`FundID`, `ProjectID`, `F_Request_Date`, `Fund_Announcment`, `F_Duration`, `A_Instructions`, `D_modified_date`, `Fund_amount`) VALUES
(13, 3, '2021-04-20 14:59:39', 'Not Granted', '2 Years', 'None', NULL, 5000000),
(20, 3, '2021-04-20 16:22:06', 'Not Granted', '5 Years', 'None', '2021-04-24 18:30:00', 25000),
(24, 2, '2021-04-25 13:45:37', 'Not Granted', '2 Years', 'None', NULL, 25000),
(25, 4, '2021-04-25 13:46:20', 'Not Granted', '1 Year', 'None', NULL, 600000),
(26, 5, '2021-04-25 13:46:44', 'Not Granted', '8 Years', 'None', NULL, 1000000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `fund`
--
ALTER TABLE `fund`
  ADD PRIMARY KEY (`FundID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `fund`
--
ALTER TABLE `fund`
  MODIFY `FundID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
