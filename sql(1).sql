/*
SQLyog Ultimate v10.00 Beta1
MySQL - 8.0.27 : Database - v_cloud_controller
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`v_cloud_controller` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `v_cloud_controller`;

/*Table structure for table `a_time_bill` */

CREATE TABLE `a_time_bill` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL DEFAULT '0' COMMENT '关联用户',
  `start_time` datetime NOT NULL  COMMENT '每一天的开始挂机时间',
  `end_time` datetime NOT NULL  COMMENT '每一天的最后结束时间',
  `valid_time` int NOT NULL DEFAULT '0' COMMENT '有效时间,以分钟做单位',
  `at_nlms` double NOT NULL DEFAULT '0' COMMENT '单价 计算公式：有效时间/60*单价',
  `pid` int NOT NULL DEFAULT '0' COMMENT '上级id 没有上级为0',
  `level_id` tinyint NOT NULL DEFAULT '0' COMMENT '上级的等级记录 用于计算上级佣金',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;

/*Data for the table `a_time_bill` */

insert  into `a_time_bill`(`id`,`user_id`,`start_time`,`end_time`,`valid_time`,`at_nlms`,`pid`,`level_id`) values (1,1,'2021-11-11 21:04:00','2021-11-11 21:04:09',60,1,0,4),(8,1,'2021-11-16 16:05:52','2021-11-16 16:05:52',0,0,0,0);

/*Table structure for table `addressable_path` */

CREATE TABLE `addressable_path` (
  `id` int NOT NULL AUTO_INCREMENT,
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '访问路径',
  `level_id` int NOT NULL DEFAULT '2' COMMENT '访问所需要的等级',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

/*Data for the table `addressable_path` */

insert  into `addressable_path`(`id`,`path`,`level_id`,`name`) values (1,'/login',2,'登录页面'),(2,'/syslog',1,'系统日志'),(3,'/admin',1,'后台管理'),(4,'/index',2,'首页'),(5,'/detail',2,'详情页'),(6,'/doc',2,'说明文档页');

/*Table structure for table `admin` */

CREATE TABLE `admin` (
  `id` int DEFAULT NULL,
  `username` varchar(10) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `token` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `admin` */

insert  into `admin`(`id`,`username`,`phone`,`password`,`token`) values (1,'admin','18888888888','e10adc3949ba59abbe56e057f20f883e',NULL);

/*Table structure for table `bill` */

CREATE TABLE `bill` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  `alipay` varchar(20) DEFAULT NULL,
  `wxpay` varchar(20) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `reason` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

/*Data for the table `bill` */

insert  into `bill`(`id`,`user_id`,`phone`,`money`,`balance`,`time`,`alipay`,`wxpay`,`status`,`reason`) values (1,NULL,'13333333333',100,300,'2021-12-01','13333333333',NULL,0,''),(2,NULL,'14444444444',100,0,'2021-12-02','14444444444',NULL,2,''),(3,NULL,'15555555555',100,100,'2021-12-03','15555555555',NULL,1,'不允许');

/*Table structure for table `bill_detail` */

CREATE TABLE `bill_detail` (
  `id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `order` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `yhgj` double DEFAULT '0',
  `yhvip` double DEFAULT '0',
  `yhdl` double DEFAULT '0',
  `dlgj` double DEFAULT '0',
  `dlvip` double DEFAULT '0',
  `dldl` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `bill_detail` */

insert  into `bill_detail`(`id`,`user_id`,`order`,`yhgj`,`yhvip`,`yhdl`,`dlgj`,`dlvip`,`dldl`) values (1,NULL,'1',100,0,0,0,0,0),(2,NULL,'2',100,0,0,0,0,0),(3,NULL,'3',100,0,0,0,0,0);

/*Table structure for table `dashboard_config` */

CREATE TABLE `dashboard_config` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `level_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `dashboard_config` */

/*Table structure for table `driver` */

CREATE TABLE `driver` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `invate` varchar(10) DEFAULT NULL,
  `mac` varchar(20) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `os` varchar(20) DEFAULT NULL,
  `platform` int DEFAULT NULL,
  `counttime` varchar(20) DEFAULT NULL,
  `lasttime` varchar(20) DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

/*Data for the table `driver` */

insert  into `driver`(`id`,`username`,`phone`,`invate`,`mac`,`ip`,`os`,`platform`,`counttime`,`lasttime`,`status`) values (1,'a','15555555555','0903','32eafa2322','127.0.0.1','xiaomi',1,'2021-12-04','2021-12-19',0),(2,'b','14444444444','0904','w2ew23we23','127.0.0.1','ios',1,'2021-12-03','2021-12-19',1),(3,'c','13333333333','0905','sd32ew32df','127.0.0.1','vivo',1,'2021-12-11','2021-12-19',1);

/*Table structure for table `driver_setting` */

CREATE TABLE `driver_setting` (
  `id` int DEFAULT NULL,
  `gjnum` varchar(10) DEFAULT NULL,
  `ipnum` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `driver_setting` */

insert  into `driver_setting`(`id`,`gjnum`,`ipnum`) values (1,'1','1');

/*Table structure for table `group_agent` */

CREATE TABLE `group_agent` (
  `name` varchar(20) DEFAULT NULL,
  `dlyj` double DEFAULT NULL,
  `dlvip` double DEFAULT NULL,
  `dldl` double DEFAULT NULL,
  `dlprice` double DEFAULT NULL,
  `invitenum` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `group_agent` */

/*Table structure for table `group_user` */

CREATE TABLE `group_user` (
  `name` varchar(20) DEFAULT NULL,
  `bill` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `type` int DEFAULT NULL,
  `time` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `group_user` */

/*Table structure for table `level` */

CREATE TABLE `level` (
  `id` int NOT NULL AUTO_INCREMENT,
  `explain` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '说明',
  `device_count` int NOT NULL DEFAULT '0' COMMENT '可绑定设备数量',
  `original_price` double NOT NULL DEFAULT '0' COMMENT '原价',
  `price` double NOT NULL DEFAULT '0' COMMENT '现价',
  `rebate` double NOT NULL DEFAULT '0' COMMENT '返佣(0.5)',
  `order_discount` double NOT NULL DEFAULT '0' COMMENT '下单优惠',
  `matime` int NOT NULL DEFAULT '0' COMMENT '挂机时长',
  `unit_price` double NOT NULL DEFAULT '1.5' COMMENT '挂机小时单价 单位：元',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `level` */

insert  into `level`(`id`,`explain`,`device_count`,`original_price`,`price`,`rebate`,`order_discount`,`matime`,`unit_price`) values (1,'管理员',99999,99999999,99999999,0,1,24,2),(2,'普通用户',1,0,0,0,1,10,1.5),(3,'VIP用户',1,99,66,0,1,12,2),(4,'普通代理',30,598,298,0.5,0.85,10,1.5),(5,'中级代理',80,998,598,0.5,0.8,10,1.5),(6,'高级代理',99999,9998,9998,0.5,0.7,10,1.5);

/*Table structure for table `log` */

CREATE TABLE `log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `size` varchar(20) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `md5` int NOT NULL,
  PRIMARY KEY (`id`,`md5`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `log` */

/*Table structure for table `note` */

CREATE TABLE `note` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(500) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `note` */

/*Table structure for table `order_form` */

CREATE TABLE `order_form` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `task_id` int NOT NULL COMMENT '任务id',
  `createtime` datetime  COMMENT '订单创建时间',
  `endtime` datetime  COMMENT '订单完成时间',
  `money` double DEFAULT '0' COMMENT '订单金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '订单备注',
  `status` varchar(110) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '订单状态',
  `count` int DEFAULT '0' COMMENT '设备数',
  `user_id` int NOT NULL COMMENT '关联用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

/*Data for the table `order_form` */

insert  into `order_form`(`id`,`task_id`,`createtime`,`endtime`,`money`,`remark`,`status`,`count`,`user_id`) values (1,1,'2021-11-10 19:53:55','2021-11-11 19:53:58',500,'越快越好','已完成',500,3);

/*Table structure for table `persistent_logins` */

CREATE TABLE `persistent_logins` (
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `persistent_logins` */

insert  into `persistent_logins`(`username`,`series`,`token`,`last_used`) values ('17670655697','0BHUOki8VwhBAK30SFCGQg==','ArmVxRqZhnvnbLkvnzqpiA==','2021-12-04 13:19:11'),('17670655697','0tAgIiW6rdyKpRM0QZYIaQ==','aReQPhZcDJD4W6TNP7a2hw==','2021-12-04 12:40:23'),('15398831891','5USLVC+DWOMfVcvekigrKA==','FDxo4f3/PZTRWcabKZ6CkA==','2021-12-03 12:15:08'),('13333333333','7P4wSIGlIhXTXmAcwssRmg==','niPkQT0UoEjrB6mWcUA1ww==','2021-11-12 20:06:13'),('13333333333','aXWsMpyF3TKncsx59dAWBw==','e2W1OozQwrVHslaCCp24CA==','2021-11-13 08:55:55'),('13333333333','bm9TV11zqeZVr2opYPELrQ==','25upGQCWmsW/Ki2kc7gHVQ==','2021-11-14 10:39:05'),('13333333333','cW3EaAarEH94kcNMBlu0Sg==','MBer1O78mVm9PMaUis3yCQ==','2021-11-12 20:11:39'),('17373970395','eteb3ZYKh2KEeb6NjUfQig==','Zt7qEILxq7cgzzx9vms/9g==','2021-12-08 06:38:42'),('13333333333','fGuTt8Qo39vqWcT0rkQGTg==','SxLi8oCNFx0G47Y1FxN38w==','2021-11-14 11:58:03'),('13055119216','FnQ1jrgypOftrWKFyLcciw==','3m/lFLThBj22khSXJjDC9Q==','2021-12-03 16:51:58'),('13333333333','fxcTemDLafqb5BiTyE/tHQ==','CfUmr0lUvC/VdD1cJoz95w==','2021-11-12 20:06:31'),('13333333333','hsYcZdiEj7Ub2d0YguAzEw==','J+tZLLmcGh0Zf0FNPflp0g==','2021-11-12 20:13:38'),('15264691533','HXMVCyFXCrWQY6GswuCWbg==','PDFaezWc9gtJ4n+SJcH+/Q==','2021-12-04 16:55:31'),('13333333333','jM0z6PhPiHm6Ao1UjB7Lqw==','v+3cnafaliCsAWwIbvMuQw==','2021-11-12 20:08:28'),('17670655697','MxeErDNHtNAvSj4d6ehFWQ==','q8i2xrjwUqRxcvGoz0C6gQ==','2021-12-04 13:44:19'),('13055119216','nhVEW9t8pc3yUA7H3VLGOQ==','iwmSfn3rkRAWnhGJf3aNQg==','2021-12-04 12:35:19'),('13333333333','NhyY58Fm0QdYekuo0Quy+A==','RYUOtNXcnFzsv54vvf6kuA==','2021-11-14 10:40:45'),('13055119216','rcIS9ITLQRM1lsm4dT5FQg==','weVaRqmyYzaQqnsZrkiysw==','2021-12-03 16:17:25'),('15398831891','rvYmQ8F4hKn+XDIO3ZQxow==','tHraELmbVCbrwp6CLzeQhg==','2021-12-02 18:34:59'),('13424848681','SqJE00DWq4fjig8I/NqxCg==','SepB8ZDEBXjYwqjok2gFOQ==','2021-12-01 14:38:28'),('13333333333','UMr83MHZbKG2YdswGCukZA==','zv5CPKGF7G+yl1Xf0HU/+g==','2021-11-12 20:04:42'),('17670655697','vO+cM9KrBAQRBtwrheVtIw==','e6Pj2s4O5tmhgZr1Z3w/bg==','2021-12-05 21:26:05'),('13055119216','wiYlZz2dQjMajZ3bQ/DiiQ==','KlUTiDKdQ8r7AxhqP31afQ==','2021-11-30 13:22:34'),('13333333333','xg66BNKep96nUUuWCKqZzQ==','kR83yP+JMuU/nvfnoOESXg==','2021-11-12 20:07:10');

/*Table structure for table `task` */

CREATE TABLE `task` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `visible` tinyint(1) NOT NULL DEFAULT '1' COMMENT '任务是否对客户可见',
  `money` double NOT NULL DEFAULT '0' COMMENT '任务单价',
  `discount` varchar(110) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务折扣',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务详情',
  `createtime` timestamp NOT NULL  COMMENT '创建时间',
  `updatetime` datetime NOT NULL  COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

/*Data for the table `task` */

insert  into `task`(`id`,`name`,`visible`,`money`,`discount`,`detail`,`createtime`,`updatetime`) values (1,'抖音点赞',1,0.5,'{100:0.8,500:0.65,1000:0.4}','抖音的指定视频点赞','2021-11-12 22:17:19','2021-11-12 22:17:22');

/*Table structure for table `task_log` */

CREATE TABLE `task_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `task_id` int NOT NULL COMMENT '任务id',
  `task_start_time` datetime NOT NULL  COMMENT '任务开始时间',
  `task_end_time` datetime NOT NULL  COMMENT '任务结束时间',
  `task_create_user` int NOT NULL DEFAULT '0' COMMENT '任务创建人',
  `task_status` int NOT NULL DEFAULT '0' COMMENT '任务最终状态',
  `task_byexecute_user` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务受影响的用户',
  `task_user_count` int NOT NULL DEFAULT '0' COMMENT '任务受影响的用户数量',
  `task_complete_count` int NOT NULL DEFAULT '0' COMMENT '任务完成的用户数量',
  `task_parameter` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '任务需要的参数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

/*Data for the table `task_log` */

insert  into `task_log`(`id`,`task_id`,`task_start_time`,`task_end_time`,`task_create_user`,`task_status`,`task_byexecute_user`,`task_user_count`,`task_complete_count`,`task_parameter`) values (1,1,'2021-11-10 19:33:43','2021-11-11 19:33:54',1,0,'1,2,3,4,5,6,9,10',8,8,'https://v.douyin.com/RFvFVDg/');

/*Table structure for table `transaction_record` */

CREATE TABLE `transaction_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL DEFAULT '0' COMMENT '关联用户',
  `record_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '记录详情',
  `money` double NOT NULL DEFAULT '0' COMMENT '金额',
  `createtime` datetime NOT NULL  COMMENT '创建时间',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '当前状态(0:待处理 1:已处理 2:忽略)',
  `record_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '流水单号',
  `payment_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '付款方式',
  `endtime` datetime NOT NULL  COMMENT '处理完成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

/*Data for the table `transaction_record` */

insert  into `transaction_record`(`id`,`user_id`,`record_detail`,`money`,`createtime`,`status`,`record_id`,`payment_method`,`endtime`) values (1,1,'购买代理',299,'2021-11-11 20:13:34',1,'DL123QWE564654','微信支付','2021-11-11 20:16:35');

/*Table structure for table `user` */

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `agentlevel` int NOT NULL DEFAULT '2' COMMENT '代理等级',
  `viplevel` int NOT NULL DEFAULT '2' COMMENT 'vip等级',
  `createtime` varchar(50) NOT NULL DEFAULT '' COMMENT '创建时间',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '邮箱',
  `pid` int DEFAULT '0' COMMENT '上级id',
  `loginfailure` int DEFAULT '0' COMMENT '登录失败次数',
  `loginip` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '登录ip',
  `token` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT 'seesion标识',
  `status` int DEFAULT '0' COMMENT '0:正常 1:停用',
  `updatetime` varchar(50) NOT NULL DEFAULT '' COMMENT '修改时间',
  `invitationcode` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '邀请码',
  `balance` double NOT NULL DEFAULT '0' COMMENT '余额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;

/*Data for the table `user` */

insert  into `user`(`id`,`phone`,`username`,`password`,`agentlevel`,`viplevel`,`createtime`,`email`,`pid`,`loginfailure`,`loginip`,`token`,`status`,`updatetime`,`invitationcode`,`balance`) values (12,'15555555555','a','e10adc3949ba59abbe56e057f20f883e',2,2,'2021-12-08',NULL,9,NULL,'223.73.115.100',NULL,0,'2021-12-08','0903',0),(13,'14444444444','b','e10adc3949ba59abbe56e057f20f883e',1,0,'2021-12-11','',0,0,'0:0:0:0:0:0:0:1','',0,'2021-12-11','0904',0),(14,'13333333333','c','e10adc3949ba59abbe56e057f20f883e',0,1,'2021-12-11','',0,0,'0:0:0:0:0:0:0:1','',1,'2021-12-11','0905',0),(16,'12222222222','zhangsan','e10adc3949ba59abbe56e057f20f883e',1,1,'20220110 020808','',0,0,'0:0:0:0:0:0:0:1','',0,'20220110 020808','0906',0),(17,'16666666666','lisi','e10adc3949ba59abbe56e057f20f883e',1,1,'2022-01-10','',13,0,'0:0:0:0:0:0:0:1','',0,'2022-01-10','0907',0);

/*Table structure for table `words` */

CREATE TABLE `words` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL DEFAULT '0' COMMENT '创建人',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '话术名称',
  `comment` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '话术内容',
  `createtime` datetime NOT NULL  COMMENT '创建时间',
  `updatetime` datetime NOT NULL  COMMENT '修改时间',
  `pid` int NOT NULL DEFAULT '0' COMMENT '父id，为0即为没有分组',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

/*Data for the table `words` */

insert  into `words`(`id`,`userid`,`name`,`comment`,`createtime`,`updatetime`,`pid`) values (1,1,'抖音评论话术','厉害厉害','2021-11-11 00:00:00','2021-11-11 00:00:00',0),(2,1,'抖音直播话术','主播牛逼','2021-11-11 00:00:00','2021-11-11 00:00:00',0),(3,1,'抖音直播话术','主播666','2021-11-11 00:00:00','2021-11-11 00:00:00',2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
