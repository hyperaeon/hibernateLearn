/*
SQLyog Enterprise - MySQL GUI
MySQL - 5.5.16 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `customers` (
	`ID` bigint not null primary key ,
	`NAME` varchar (45) not null,
	`EMAIL` varchar (384) not null,
	`PASSWORD` varchar (24) not null,
	`PHONE` int ,
	`ADDRESS` varchar (765),
	`SEX` varchar (3),
	`IS_MARRIED` bit (1),
	`DESCRIPTION` text ,
	`IMAGE` blob ,
	`BIRTHDAY` date ,
	`REGISTERED_TIME` timestamp 
); 
