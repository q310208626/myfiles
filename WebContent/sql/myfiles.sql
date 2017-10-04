-- MySQL dump 10.13  Distrib 5.7.19, for Win64 (x86_64)
--
-- Host: localhost    Database: myfiles
-- ------------------------------------------------------
-- Server version	5.7.19

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
-- Table structure for table `man_privilege`
--

DROP TABLE IF EXISTS `man_privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `man_privilege` (
  `id` int(11) NOT NULL,
  `upload_pvl` int(11) DEFAULT '0',
  `update_pvl` int(11) DEFAULT '0',
  `grant_pvl` int(11) DEFAULT '0',
  `all_files_pvl` int(11) DEFAULT '0',
  `main_pvl` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `manger_privilege` FOREIGN KEY (`id`) REFERENCES `myfiles_manager` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `man_privilege`
--

LOCK TABLES `man_privilege` WRITE;
/*!40000 ALTER TABLE `man_privilege` DISABLE KEYS */;
INSERT INTO `man_privilege` VALUES (5,0,0,0,0,0),(6,0,0,0,0,0),(7,1,1,1,1,1);
/*!40000 ALTER TABLE `man_privilege` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `myfile`
--

DROP TABLE IF EXISTS `myfile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `myfile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(30) DEFAULT NULL,
  `save_path` varchar(30) DEFAULT NULL,
  `owner_id` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `last_modified_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `owner_id` (`owner_id`),
  KEY `last_modified_id` (`last_modified_id`),
  CONSTRAINT `myfile_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `myfiles_manager` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `myfile_ibfk_2` FOREIGN KEY (`last_modified_id`) REFERENCES `myfiles_manager` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myfile`
--

LOCK TABLES `myfile` WRITE;
/*!40000 ALTER TABLE `myfile` DISABLE KEYS */;
/*!40000 ALTER TABLE `myfile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `myfiles_manager`
--

DROP TABLE IF EXISTS `myfiles_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `myfiles_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `is_activited` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myfiles_manager`
--

LOCK TABLES `myfiles_manager` WRITE;
/*!40000 ALTER TABLE `myfiles_manager` DISABLE KEYS */;
INSERT INTO `myfiles_manager` VALUES (5,'linshaojia','linshaojia',1),(6,'linlin123','linlin123',0),(7,'adminmfm','sorrylinshaojia',1);
/*!40000 ALTER TABLE `myfiles_manager` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-30 16:51:55
