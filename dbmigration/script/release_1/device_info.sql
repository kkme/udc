create table device_info(id BIGINT(12) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,machineID varchar(128) NOT NULL,macID varchar(64),token varchar(128),companyName varchar(128),machineName varchar(128),terminalUA varchar(128),screenRes varchar(64),IMEI varchar(64),IMSI varchar(64),UDID varchar(64),phoneNumber varchar(15),platform varchar(64),firewareVersion varchar(64),softwareVersion varchar(64),softwareBuild varchar(64)) ENGINE=InnoDB DEFAULT CHARSET=utf8;