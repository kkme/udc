create table user_basic_info(id BIGINT(12) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,email varchar(320),thirdID varchar(320),phoneNumber varchar(15),koudaiID varchar(64),name varchar(64) NOT NULL,machineID varchar(128) NOT NULL,passwd varchar(32) NOT NULL,gender char,birthday date,location varchar(64),registTime datetime NOT NULL,qq varchar(20),msn varchar(320),introduction varchar(210),lastLoginTime datetime NOT NULL,enabled boolean NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8;