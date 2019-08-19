/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.7.18-log : Database - code006
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`code006` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `code006`;

/*Table structure for table `t_article` */

DROP TABLE IF EXISTS `t_article`;

CREATE TABLE `t_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `content` longtext,
  `include_date` varchar(100) DEFAULT NULL,
  `share_user` varchar(100) DEFAULT NULL,
  `share_url` varchar(200) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `share_date` varchar(100) DEFAULT NULL,
  `is_index` bit(1) NOT NULL DEFAULT b'0',
  `state` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`share_url`)
) ENGINE=MyISAM AUTO_INCREMENT=1230 DEFAULT CHARSET=utf8;

/*Data for the table `t_article` */