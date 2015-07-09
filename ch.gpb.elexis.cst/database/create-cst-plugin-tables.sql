CREATE DATABASE  IF NOT EXISTS `elexisprod3` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `elexisprod3`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: elexisprod3
-- ------------------------------------------------------
-- Server version	5.6.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cstgroup_labitem_joint`
--

DROP TABLE IF EXISTS `cstgroup_labitem_joint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cstgroup_labitem_joint` (
  `ID` varchar(25) DEFAULT NULL,
  `deleted` char(1) DEFAULT '0',
  `lastupdate` bigint(20) DEFAULT NULL,
  `GroupID` varchar(25) DEFAULT NULL,
  `ItemID` varchar(25) DEFAULT NULL,
  `Comment` longtext,
  UNIQUE KEY `GroupID` (`GroupID`,`ItemID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cstgroup_profile_joint`
--

DROP TABLE IF EXISTS `cstgroup_profile_joint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cstgroup_profile_joint` (
  `ID` varchar(25) DEFAULT NULL,
  `deleted` char(1) DEFAULT '0',
  `lastupdate` bigint(20) DEFAULT NULL,
  `CstgroupID` varchar(25) DEFAULT NULL,
  `ProfileID` varchar(25) DEFAULT NULL,
  `Comment` longtext,
  UNIQUE KEY `CstgroupID` (`CstgroupID`,`ProfileID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cstgroups`
--

DROP TABLE IF EXISTS `cstgroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cstgroups` (
  `ID` varchar(25) NOT NULL,
  `lastupdate` bigint(20) DEFAULT NULL,
  `deleted` char(1) DEFAULT '0',
  `KontaktID` varchar(25) DEFAULT NULL,
  `MandantID` varchar(25) DEFAULT NULL,
  `Name` varchar(30) DEFAULT NULL,
  `Description` varchar(256) DEFAULT NULL,
  `itemsRanking` longblob,
  `Icon` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cstlaboritem_abstracts`
--

DROP TABLE IF EXISTS `cstlaboritem_abstracts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cstlaboritem_abstracts` (
  `ID` varchar(25) NOT NULL,
  `lastupdate` bigint(20) DEFAULT NULL,
  `deleted` char(1) DEFAULT '0',
  `ItemID` varchar(25) DEFAULT NULL,
  `Description1` varchar(1024) DEFAULT NULL,
  `Description2` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cstprofile_gastrocolo`
--

DROP TABLE IF EXISTS `cstprofile_gastrocolo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cstprofile_gastrocolo` (
  `ID` varchar(25) NOT NULL,
  `lastupdate` bigint(20) DEFAULT NULL,
  `deleted` char(1) DEFAULT '0',
  `ProfileID` varchar(25) DEFAULT NULL,
  `DatumGastro` char(8) DEFAULT NULL,
  `DatumColo` char(8) DEFAULT NULL,
  `Text1` longtext,
  `Text2` longtext,
  `Text3` longtext,
  `Text4` longtext,
  `GastroMakroBefund` char(1) DEFAULT '0',
  `GastroHistoBefund` char(1) DEFAULT '0',
  `ColoMakroBefund` char(1) DEFAULT '0',
  `ColoHistoBefund` char(1) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cstprofile_proimmun`
--

DROP TABLE IF EXISTS `cstprofile_proimmun`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cstprofile_proimmun` (
  `ID` varchar(25) NOT NULL,
  `lastupdate` bigint(20) DEFAULT NULL,
  `deleted` char(1) DEFAULT '0',
  `datum` char(8) DEFAULT NULL,
  `ProfileID` varchar(25) DEFAULT NULL,
  `Text1` longtext,
  `Text2` longtext,
  `Text3` longtext,
  `Text4` longtext,
  `Tested` smallint(6) DEFAULT '0',
  `ToBeTested` smallint(6) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cstprofiles`
--

DROP TABLE IF EXISTS `cstprofiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cstprofiles` (
  `ID` varchar(25) NOT NULL,
  `lastupdate` bigint(20) DEFAULT NULL,
  `deleted` char(1) DEFAULT '0',
  `KontaktID` varchar(25) DEFAULT NULL,
  `MandantID` varchar(25) DEFAULT NULL,
  `Name` varchar(256) DEFAULT NULL,
  `Description` varchar(256) DEFAULT NULL,
  `Icon` varchar(25) DEFAULT NULL,
  `ValidFrom` char(8) DEFAULT NULL,
  `ValidTo` char(8) DEFAULT NULL,
  `Active` char(1) DEFAULT '1',
  `Template` char(1) DEFAULT '0',
  `AusgabeRichtung` char(1) DEFAULT '0',
  `Auswahlbefunde` longblob,
  `itemsRanking` longblob,
  `PlausibilityCheck` char(1) DEFAULT '0',
  `AnzeigeTyp` varchar(50) DEFAULT NULL,
  `OutputHeader` varchar(256) DEFAULT NULL,
  `CrawlBack` smallint(6) DEFAULT '180',
  `DaySpan1` smallint(6) DEFAULT '720',
  `DaySpan2` smallint(6) DEFAULT '360',
  `DaySpan3` smallint(6) DEFAULT '0',
  `Therapievorschlag` longtext,
  `Diagnose` longtext,
  `Period1DateStart` char(8) DEFAULT NULL,
  `Period1DateEnd` char(8) DEFAULT NULL,
  `Period2DateStart` char(8) DEFAULT NULL,
  `Period2DateEnd` char(8) DEFAULT NULL,
  `Period3DateStart` char(8) DEFAULT NULL,
  `Period3DateEnd` char(8) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'elexisprod3'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-07-05 15:26:39




