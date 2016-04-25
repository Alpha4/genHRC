-- phpMyAdmin SQL Dump
-- version 4.4.1.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 31, 2015 at 01:13 PM
-- Server version: 5.6.17
-- PHP Version: 5.4.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `formation`
--

-- --------------------------------------------------------

--
-- Table structure for table `Hash_table`
--

CREATE TABLE IF NOT EXISTS `Hash_table` (
  `src` varchar(8) NOT NULL,
  `HRC` varchar(256) NOT NULL,
  `Hash` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='table linking pairs of src-HRC to their hash value';

--
-- Dumping data for table `Hash_table`
--

INSERT INTO `Hash_table` (`src`, `HRC`, `Hash`) VALUES
('AZERTYUI', 'Z1234567', '1c7502948cb60dc8c623c4086128bcc3b9e40564');

-- --------------------------------------------------------

--
-- Table structure for table `HRC_abbr_relation`
--

CREATE TABLE IF NOT EXISTS `HRC_abbr_relation` (
  `HRC_abbr` varchar(8) NOT NULL,
  `HRC` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='relation between an HRC abbreviation and its real HRC';

--
-- Dumping data for table `HRC_abbr_relation`
--

INSERT INTO `HRC_abbr_relation` (`HRC_abbr`, `HRC`) VALUES
('Z1234567', 'TYGFR456789GFEDS'),
('Z7654321', 'R5T6Y7U8IJHY5TGRFDE4RTFV');

-- --------------------------------------------------------

--
-- Table structure for table `Process`
--

CREATE TABLE IF NOT EXISTS `Process` (
  `Reference` varchar(8) NOT NULL,
  `Input` varchar(256) NOT NULL,
  `Output` varchar(256) NOT NULL,
  `Input_ext` varchar(4) NOT NULL,
  `Output_ext` varchar(4) NOT NULL,
  `Command` varchar(512) NOT NULL,
  `ConfigFile` varchar(256) NOT NULL,
  `Description` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Informations about processes';

--
-- Dumping data for table `Process`
--

INSERT INTO `Process` (`Reference`, `Input`, `Output`, `Input_ext`, `Output_ext`, `Command`, `ConfigFile`, `Description`) VALUES
('TYGFR456', 'avi 1650x1080', 'avi 800x600', '.avi', '.avi', 'iconvert $in\r\n@1650x1080 -\r\nfscale @800x600\r\n$out', 'config.tar', 'Conversion\r\n1650x1080\r\nto 800x600'),
('789GFEDS', 'avi 800x600', 'mkv 1024x768', '.avi', '.mkv', 'ffmpeg -i $in -vf scale=1024:768 $out', '', 'resizes from 800x600 to 1024x768');

-- --------------------------------------------------------

--
-- Table structure for table `Src_abbr_relation`
--

CREATE TABLE IF NOT EXISTS `Src_abbr_relation` (
  `src` varchar(8) NOT NULL,
  `srcFileName` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='relation between an src ( abbreviation ) and its real file name';

--
-- Dumping data for table `Src_abbr_relation`
--

INSERT INTO `Src_abbr_relation` (`src`, `srcFileName`) VALUES
('G456TRF3', 'Video2.mkv'),
('AZERTYUI', 'video1.avi');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
