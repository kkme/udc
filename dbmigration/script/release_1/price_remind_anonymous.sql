create table price_remind_anonymous(id BIGINT(13) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,productID varchar(1024) NOT NULL,email varchar(320) NOT NULL,subscribeTime datetime NOT NULL,subscribePrice numeric(12,2) NOT NULL,productUrl varchar(1024) NOT NULL,currentPrice numeric(12,2),targetPrice numeric(12,2),lastNoticeTime datetime,noticeNumber int,canRemind tinyint(4)) ENGINE=InnoDB DEFAULT CHARSET=utf8;