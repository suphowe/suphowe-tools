CREATE database if NOT EXISTS `sharding1` default character set utf8 collate utf8_general_ci;
use `sharding1`;

CREATE TABLE `goods_0` (
  `goods_id` bigint(20) NOT NULL,
  `goods_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `goods_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
DROP TABLE IF EXISTS `goods_1`;
CREATE TABLE `goods_1` (
  `goods_id` bigint(20) NOT NULL,
  `goods_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `goods_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE database if NOT EXISTS `sharding2` default character set utf8 collate utf8_general_ci;
use `sharding2`;

CREATE TABLE `goods_0` (
  `goods_id` bigint(20) NOT NULL,
  `goods_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `goods_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `goods_1`;
CREATE TABLE `goods_1` (
  `goods_id` bigint(20) NOT NULL,
  `goods_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `goods_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;