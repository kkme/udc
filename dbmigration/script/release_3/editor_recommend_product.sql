create table editor_recommend_product(id BIGINT(12) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,userID varchar(60) NOT NULL,productID varchar(1024) NOT NULL,pushTime datetime Not NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8;