create table product_collect_top(id BIGINT(13) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,productID varchar(1024) NOT NULL,collectedNum integer NOT NULL,typeID varchar(100),statTime date NOT NULL,useType tinyint(4) NOT NULL default 0) ENGINE=InnoDB DEFAULT CHARSET=utf8;