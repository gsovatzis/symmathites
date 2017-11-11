CREATE DATABASE  IF NOT EXISTS `symmathites` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `symmathites`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: symmathites
-- ------------------------------------------------------
-- Server version	5.6.14

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
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `idGroup` int(11) NOT NULL AUTO_INCREMENT,
  `CreatedByUser` int(11) DEFAULT NULL,
  `GroupName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idGroup`),
  KEY `fk_created_by_user_idx` (`CreatedByUser`),
  CONSTRAINT `fk_created_by_user` FOREIGN KEY (`CreatedByUser`) REFERENCES `users` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` VALUES (1,4,'omada1'),(2,6,'omada2'),(3,9,'omada3');
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usercomments`
--

DROP TABLE IF EXISTS `usercomments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usercomments` (
  `idComment` int(11) NOT NULL AUTO_INCREMENT,
  `Comment` varchar(45) DEFAULT NULL,
  `ForUser` int(11) DEFAULT NULL,
  `PostedByUser` int(11) DEFAULT NULL,
  PRIMARY KEY (`idComment`),
  KEY `fk_for_user_idx` (`ForUser`),
  KEY `fk_posted_by_user_idx` (`PostedByUser`),
  CONSTRAINT `fk_posted_by_user` FOREIGN KEY (`PostedByUser`) REFERENCES `users` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_for_user` FOREIGN KEY (`ForUser`) REFERENCES `users` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usercomments`
--

LOCK TABLES `usercomments` WRITE;
/*!40000 ALTER TABLE `usercomments` DISABLE KEYS */;
INSERT INTO `usercomments` VALUES (1,'adu15 jio22',3,8),(2,'hud7 hsu 88',9,5),(3,'nb2bs sjw',1,4),(4,'98c aua saj',6,10);
/*!40000 ALTER TABLE `usercomments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) DEFAULT NULL,
  `Password` varchar(45) DEFAULT NULL,
  `Firstname` varchar(45) DEFAULT NULL,
  `Lastname` varchar(45) DEFAULT NULL,
  `Age` int(11) DEFAULT NULL,
  `SchoolName` varchar(45) DEFAULT NULL,
  `IsApproved` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'c.giannis','123456','Giannis','Chatzis',8,'Aristiou 3h dimotikou',1),(2,'a.giorgos','234567','Giorgos','Andreakis',11,'Larisis 6h dimotiou',0),(3,'g.konstantinos','345678','Konstantinos','Gikas',6,'Amarousiou 1h dimotikou',1),(4,'p.christou','456789','Pavlos','Christou',9,'Pentelhs 4h dimotikou',1),(5,'t,tzovas','567890','Thanasis','Tzovas',7,'Chalandriou 2a dimotikou',1),(6,'e. Paras','678901','Eleni','Paraskeuopoulou',10,'Kostea 5h dimotikou',0),(7,'n.iosifidi','789012','Nefeli','Iosifidi',6,'Amarousiou 1h dimotikou',0),(8,'e.fosteri','890123','Evgenia','Fosteri',8,'Aristiou 4h dimotikou',1),(9,'p.rousos','901234','Panos','Rousos',7,'Kritis 2a dimotikou',1),(10,'c.demenegi','012345','Christina','Demenegi',9,'Chalandriou 3h dimotikou',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userstogroup`
--

DROP TABLE IF EXISTS `userstogroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userstogroup` (
  `idGroup` int(11) DEFAULT NULL,
  `idUser` int(11) DEFAULT NULL,
  KEY `fk_group_idx` (`idGroup`),
  KEY `fk_users_idx` (`idUser`),
  CONSTRAINT `fk_users` FOREIGN KEY (`idUser`) REFERENCES `users` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_group` FOREIGN KEY (`idGroup`) REFERENCES `groups` (`idGroup`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userstogroup`
--

LOCK TABLES `userstogroup` WRITE;
/*!40000 ALTER TABLE `userstogroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `userstogroup` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-11-10 19:43:19
