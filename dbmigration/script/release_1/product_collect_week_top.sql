create table product_collect_week_top(id BIGINT(13) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,productID varchar(1024) NOT NULL,collectedNum integer NOT NULL,typeID varchar(100) NOT NULL DEFAULT 'Combine_Default',week integer NOT NULL,year integer NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8;