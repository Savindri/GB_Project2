-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: Apr 23, 2021 at 09:06 PM
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
-- Database: `cart_paf`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `cartID` int(11) NOT NULL,
  `pro_ID` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unitPrice` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`cartID`, `pro_ID`, `quantity`, `unitPrice`) VALUES
(81, 3, 5, 200),
(82, 2, 5, 25000),
(83, 2, 5, 25000),
(84, 1, 5, 1000),
(86, 1, 5, 1000);

-- --------------------------------------------------------

--
-- Table structure for table `order_`
--

CREATE TABLE `order_` (
  `orderID` int(11) NOT NULL,
  `cartID` int(11) NOT NULL,
  `date` date NOT NULL,
  `custName` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `phone` int(10) NOT NULL,
  `email` varchar(50) NOT NULL,
  `total` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `order_`
--

INSERT INTO `order_` (`orderID`, `cartID`, `date`, `custName`, `address`, `phone`, `email`, `total`) VALUES
(52, 81, '2020-02-27', 'Kushani', 'Colomb0-03', 781112223, 'kushani@gmail.com', 1000),
(53, 82, '2020-03-02', 'Tharindra', 'Colomb0-04', 781345223, 'tharindra@gmail.com', 75000),
(55, 83, '2020-03-01', 'Pravinda', 'Colombo_03', 754467722, 'pravi@gmail.com', 125000),
(57, 84, '2020-03-03', 'Charith', 'Colombo_04', 754467788, 'charith@gmail.com', 5000),
(58, 86, '2020-03-04', 'Shalini', 'Colombo_05', 754467876, 'shalini@gmail.com', 5000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`cartID`);

--
-- Indexes for table `order_`
--
ALTER TABLE `order_`
  ADD PRIMARY KEY (`orderID`),
  ADD KEY `fk_cartID` (`cartID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `cartID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=87;

--
-- AUTO_INCREMENT for table `order_`
--
ALTER TABLE `order_`
  MODIFY `orderID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `order_`
--
ALTER TABLE `order_`
  ADD CONSTRAINT `fk_cartID` FOREIGN KEY (`cartID`) REFERENCES `cart` (`cartID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
