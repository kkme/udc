create table ios_push_info(id BIGINT(13) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,userID varchar(320),machineID varchar(128) NOT NULL,firstProductName varchar(255) NOT NULL,productIds varchar(500) NOT NULL,infoType tinyint UNSIGNED NOT NULL,token varchar(128) NOT NULL,state tinyint UNSIGNED NOT NULL,platform varchar(64) NOT NULL,createTime datetime NOT NULL,startTime datetime,endTime datetime,pushTime datetime) ENGINE=InnoDB DEFAULT CHARSET=utf8;