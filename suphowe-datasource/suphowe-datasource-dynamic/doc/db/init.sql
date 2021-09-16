CREATE database if NOT EXISTS `dynamic1` default character set utf8 collate utf8_general_ci;
use dynamic1;

CREATE TABLE IF NOT EXISTS `datasource_config`
(
    `id`       bigint(13)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `host`     varchar(255) NOT NULL COMMENT '数据库地址',
    `port`     int(6)       NOT NULL COMMENT '数据库端口',
    `username` varchar(100) NOT NULL COMMENT '数据库用户名',
    `password` varchar(100) NOT NULL COMMENT '数据库密码',
    `database` varchar(100) DEFAULT 0 COMMENT '数据库名称',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='数据源配置表';

INSERT INTO `datasource_config`(`id`, `host`, `port`, `username`, `password`, `database`) VALUES (1, '127.0.0.1', 3306, 'root', 'root', 'dynamic1');
INSERT INTO `datasource_config`(`id`, `host`, `port`, `username`, `password`, `database`) VALUES (2, '127.0.0.1', 3306, 'root', 'root', 'dynamic2');
INSERT INTO `datasource_config`(`id`, `host`, `port`, `username`, `password`, `database`) VALUES (3, '127.0.0.1', 3306, 'root', 'root', 'dynamic3');

CREATE TABLE IF NOT EXISTS `test_user`
(
    `id`   bigint(13)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(255) NOT NULL COMMENT '姓名',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8 COMMENT ='用户表';

INSERT INTO `test_user`(`id`, `name`) values (1, '默认数据库用户1');
INSERT INTO `test_user`(`id`, `name`) values (2, '默认数据库用户2');

CREATE database if NOT EXISTS `dynamic2` default character set utf8 collate utf8_general_ci;
use dynamic2;
CREATE TABLE IF NOT EXISTS `test_user`
(
    `id`   bigint(13)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(255) NOT NULL COMMENT '姓名',
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8 COMMENT ='用户表';

INSERT INTO `test_user`(`id`, `name`) values (1, '测试库2用户1');
INSERT INTO `test_user`(`id`, `name`) values (2, '测试库2用户2');

CREATE database if NOT EXISTS `dynamic3` default character set utf8 collate utf8_general_ci;
use dynamic3;
CREATE TABLE IF NOT EXISTS `test_user`
(
    `id`   bigint(13)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(255) NOT NULL COMMENT '姓名',
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8 COMMENT ='用户表';

INSERT INTO `test_user`(`id`, `name`) values (1, '测试库3用户1');
INSERT INTO `test_user`(`id`, `name`) values (2, '测试库3用户2');