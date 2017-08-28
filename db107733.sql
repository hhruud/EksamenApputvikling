-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 05. Jun, 2017 15:22 PM
-- Server-versjon: 5.7.18
-- PHP Version: 5.6.30-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `db107733`
--

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `Turer`
--

CREATE TABLE IF NOT EXISTS `Turer` (
`TurNr` int(10) unsigned NOT NULL,
  `Navn` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Type` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Beskrivelse` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `Lengde` varchar(20) NOT NULL,
  `Breddegrad` decimal(11,7) DEFAULT NULL,
  `Lengdegrad` decimal(11,7) DEFAULT NULL,
  `Moh` int(5) NOT NULL,
  `Regav` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Url` varchar(2000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

--
-- Dataark for tabell `Turer`
--

INSERT INTO `Turer` (`TurNr`, `Navn`, `Type`, `Beskrivelse`, `Lengde`, `Breddegrad`, `Lengdegrad`, `Moh`, `Regav`, `Url`) VALUES
(1, 'Gygrestolen', 'Husmannsplass', 'En fottur på 6 bratte kilometer tar deg opp til det mytiske platået der gygra skal ha kastet kampesteiner utover Bø i raseri. Sagnet sier at når Gygrestolen faller skal verden gå under. Fjellplatået Gygrestolen er splittet i to hoveddeler der det ytterste platået siger litt utover hvert år.', '4 Km', '59.3921606', '9.1061648', 355, 'Håkon Ruud', 'http://itfag.usn.no/~107733/DSC00093.JPG\r\n'),
(33, 'Garstang ', 'Utsiktsplass', 'Fin Tur test test  teststst test test  testststtest test  testststtest test  testststtest test  teststst', '5Km', '59.5638900', '9.2691000', 2529, 'Håkon Ruud', 'http://itfag.usn.no/~107733/DSC00093.JPG\r\n'),
(34, 'Tur til Blefjell', 'Fjelltop', 'Blefjell er et fjellområde på grensen mellom Buskerud og Telemark fylker. Blefjell omfatter deler av de fem kommunene Rollag, Flesberg og Kongsberg i Buskerud og Notodden og Tinn i Telemark.', '10 Km', '59.5639100', '9.2690600', 3793, 'Håkon Ruud', 'http://itfag.usn.no/~107733/DSC00093.JPG'),
(35, 'Gaustatoppen', 'Utsiktsplass', 'Gaustatoppen eller Gausta er et fjell ovenfor Rjukan i Tinn kommune og delvis i Tuddal i Hjartdal kommune i Telemark. Det har en høyde på 1 883 meter over havet og er fylkets høyeste fjell. Det er ca. 1 600 meter fra dalbunnen og opp til toppen.', '3,5 Km', '59.5639100', '9.2690600', 35, 'Håkon Ruud', 'http://itfag.usn.no/~107733/DSC00093.JPG'),
(36, 'Himingen', 'Fjelltop', 'Bli med en tur til Himingen 1066 moh. Turen starter på den nest øverste parkeringsplassen og er godt merka med skilt i starten og ellers er stien merka med rød ', '4 km', '59.5639000', '9.2690800', 5014, 'Håkon Ruud', 'http://itfag.usn.no/~107733/DSC00093.JPG');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Turer`
--
ALTER TABLE `Turer`
 ADD PRIMARY KEY (`TurNr`), ADD KEY `TurNr` (`TurNr`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Turer`
--
ALTER TABLE `Turer`
MODIFY `TurNr` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=37;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
