-- phpMyAdmin SQL Dump
-- version 4.0.9
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 02, 2017 at 04:05 PM
-- Server version: 5.6.14
-- PHP Version: 5.5.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `attendance_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `cource`
--

CREATE TABLE IF NOT EXISTS `cource` (
  `cid` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cource`
--

INSERT INTO `cource` (`cid`, `name`) VALUES
(1, 'MCA I (LE)'),
(2, 'MCA I'),
(3, 'MCA II (LE)'),
(4, 'MCA II'),
(5, 'MCA III'),
(6, 'M.Com'),
(7, '4th Std.'),
(8, 'MA'),
(9, 'LKG');

-- --------------------------------------------------------

--
-- Table structure for table `photos`
--

CREATE TABLE IF NOT EXISTS `photos` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `photo` varchar(32) NOT NULL,
  `sid` int(11) NOT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `photo` (`photo`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=341 ;

--
-- Dumping data for table `photos`
--

INSERT INTO `photos` (`pid`, `photo`, `sid`) VALUES
(261, '19-02-17-11-12-08.png', 34),
(262, '19-02-17-11-12-16.png', 34),
(263, '19-02-17-11-12-24.png', 34),
(264, '19-02-17-11-13-00.png', 34),
(265, '19-02-17-11-13-49.png', 34),
(266, '19-02-17-11-54-29.png', 35),
(267, '19-02-17-11-55-20.png', 35),
(268, '19-02-17-11-55-25.png', 35),
(269, '19-02-17-11-55-39.png', 35),
(270, '19-02-17-11-55-47.png', 35),
(271, '19-02-17-11-57-35.png', 36),
(272, '19-02-17-11-57-47.png', 36),
(273, '19-02-17-11-58-00.png', 36),
(274, '19-02-17-11-58-08.png', 36),
(275, '19-02-17-11-58-15.png', 36),
(276, '19-02-17-12-01-10.png', 37),
(277, '19-02-17-12-01-17.png', 37),
(278, '19-02-17-12-01-22.png', 37),
(279, '19-02-17-12-01-26.png', 37),
(280, '19-02-17-12-01-34.png', 37),
(281, '19-02-17-12-18-08.png', 38),
(282, '19-02-17-12-18-51.png', 38),
(283, '19-02-17-12-18-57.png', 38),
(284, '19-02-17-12-19-21.png', 38),
(285, '19-02-17-12-19-30.png', 38),
(286, '19-02-17-12-29-34.png', 39),
(287, '19-02-17-12-29-38.png', 39),
(288, '19-02-17-12-29-45.png', 39),
(289, '19-02-17-12-29-51.png', 39),
(290, '19-02-17-12-29-57.png', 39),
(291, '20-02-17-09-35-56.png', 40),
(292, '20-02-17-09-36-11.png', 40),
(293, '20-02-17-09-36-16.png', 40),
(294, '20-02-17-09-36-24.png', 40),
(295, '20-02-17-09-36-34.png', 40),
(296, '20-02-17-09-38-02.png', 41),
(297, '20-02-17-09-38-06.png', 41),
(298, '20-02-17-09-38-14.png', 41),
(299, '20-02-17-09-38-33.png', 41),
(300, '20-02-17-09-38-25.png', 41),
(301, '20-02-17-12-30-27.png', 40),
(302, '20-02-17-12-31-10.png', 40),
(303, '20-02-17-12-31-03.png', 40),
(304, '20-02-17-12-30-50.png', 40),
(305, '20-02-17-12-31-17.png', 40),
(306, '21-02-17-14-03-29.png', 42),
(307, '21-02-17-14-03-36.png', 42),
(308, '21-02-17-14-03-57.png', 42),
(309, '21-02-17-14-04-13.png', 42),
(310, '21-02-17-14-04-18.png', 42),
(311, '21-02-17-14-13-14.png', 42),
(312, '21-02-17-14-13-22.png', 42),
(313, '21-02-17-14-13-33.png', 42),
(314, '21-02-17-14-13-38.png', 42),
(315, '21-02-17-14-13-56.png', 42),
(316, '21-02-17-14-19-23.png', 43),
(317, '21-02-17-14-19-55.png', 43),
(318, '21-02-17-14-20-00.png', 43),
(319, '21-02-17-14-20-05.png', 43),
(320, '21-02-17-14-20-10.png', 43),
(321, '21-02-17-14-25-51.png', 44),
(322, '21-02-17-14-25-57.png', 44),
(323, '21-02-17-14-26-04.png', 44),
(324, '21-02-17-14-26-20.png', 44),
(325, '21-02-17-14-26-26.png', 44),
(326, '21-02-17-14-43-33.png', 42),
(327, '21-02-17-14-43-41.png', 42),
(328, '21-02-17-14-43-46.png', 42),
(329, '21-02-17-14-43-55.png', 42),
(330, '21-02-17-14-44-06.png', 42),
(331, '21-02-17-14-47-10.png', 45),
(332, '21-02-17-14-47-16.png', 45),
(333, '21-02-17-14-47-50.png', 45),
(334, '21-02-17-14-47-38.png', 45),
(335, '21-02-17-14-47-44.png', 45),
(336, '21-02-17-15-07-26.png', 43),
(337, '21-02-17-15-08-50.png', 43),
(338, '21-02-17-15-08-13.png', 43),
(339, '21-02-17-15-08-24.png', 43),
(340, '21-02-17-15-08-36.png', 43);

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `rno` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  `gender` varchar(8) NOT NULL,
  `date_of_birth` date NOT NULL,
  `cid` int(11) NOT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=46 ;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`sid`, `rno`, `name`, `gender`, `date_of_birth`, `cid`) VALUES
(34, 2, 'AJITH KP', 'Male', '1993-08-09', 3),
(35, 4, 'ADWAITH', 'Male', '2017-02-19', 7),
(36, 10, 'SREEDEEP', 'Male', '2017-02-19', 7),
(37, 20, 'RITHIN JITH KP', 'Male', '2017-02-19', 9),
(38, 16, 'NANDU', 'Male', '2017-02-19', 9),
(39, 21, 'ROSHNI KK', 'Female', '1987-12-22', 8),
(40, 9, 'KAVITHA K', 'Female', '1994-10-14', 3),
(41, 22, 'SREELAKSHMI', 'Female', '1995-04-21', 3),
(42, 19, 'SAYOOJ S', 'Male', '1993-05-01', 3),
(43, 8, 'JOSHIN PURUSHOTHAMAN', 'Male', '1994-06-05', 3),
(44, 13, 'POOJA JANARDHANAN', 'Female', '1994-05-10', 3),
(45, 6, 'BIJISHA', 'Female', '1995-02-19', 3);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
