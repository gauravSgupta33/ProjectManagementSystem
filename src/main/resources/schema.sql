/* Create Table DDL for parent_task */
CREATE DATABASE  IF NOT EXISTS `test`;
USE `test`;
DROP TABLE IF EXISTS `parent_task`;
CREATE TABLE `parent_task` (
  `Parent_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ParentTask` varchar(255) NOT NULL,
  PRIMARY KEY (`Parent_ID`),
  UNIQUE KEY `Parent_ID_UNIQUE` (`Parent_ID`),
  UNIQUE KEY `ParentTask_UNIQUE` (`ParentTask`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


/* Create Table DDL for user */
CREATE DATABASE  IF NOT EXISTS `test`;
USE `test`;
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `User_ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `First_Name` varchar(45) NOT NULL,
  `Last_Name` varchar(45) NOT NULL,
  `Employee_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`User_ID`,`First_Name`,`Employee_ID`),
  UNIQUE KEY `User_ID_UNIQUE` (`User_ID`),
  UNIQUE KEY `Employee_ID_UNIQUE` (`Employee_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/* Create Table DDL for project */
CREATE DATABASE  IF NOT EXISTS `test`;
USE `test`;
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `Project_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ProjectName` varchar(100) NOT NULL,
  `StartDate` date DEFAULT NULL,
  `EndDate` date DEFAULT NULL,
  `Priority` int(11) NOT NULL,
  `Manager_ID` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`Project_ID`),
  UNIQUE KEY `Project_ID_UNIQUE` (`Project_ID`),
  UNIQUE KEY `ProjectName_UNIQUE` (`ProjectName`),
  KEY `User_ID_idx` (`Manager_ID`),
  CONSTRAINT `Employee_ID` FOREIGN KEY (`Manager_ID`) REFERENCES `user` (`User_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


/* Create Table DDL for task */
CREATE DATABASE  IF NOT EXISTS `test`;
USE `test`;
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `Task_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Parent_ID` int(11) DEFAULT NULL,
  `Project_ID` int(11) NOT NULL,
  `Task_Name` varchar(100) NOT NULL,
  `StartDate` date NOT NULL,
  `EndDate` date NOT NULL,
  `Priority` int(11) NOT NULL,
  `Status` int(11) DEFAULT NULL,
  `User_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Task_ID`),
  UNIQUE KEY `Task_ID_UNIQUE` (`Task_ID`),
  KEY `Project_ID_idx` (`Project_ID`),
  KEY `User_ID_idx` (`User_ID`),
  CONSTRAINT `Project_ID` FOREIGN KEY (`Project_ID`) REFERENCES `project` (`Project_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `User_ID` FOREIGN KEY (`User_ID`) REFERENCES `user` (`User_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
