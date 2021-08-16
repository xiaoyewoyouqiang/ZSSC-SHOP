/*
SQLyog Ultimate v9.30 
MySQL - 5.5.40 : Database - 2008-shop-02
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`2008-shop-02` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `2008-shop-02`;

/*Table structure for table `t_address` */

DROP TABLE IF EXISTS `t_address`;

CREATE TABLE `t_address` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(10) DEFAULT NULL,
  `username` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `def` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_address` */

insert  into `t_address`(`id`,`uid`,`username`,`phone`,`address`,`def`) values (1,17,'千锋','13480743212','广东省深圳市宝安区西部硅谷',0),(2,17,'千锋','13489743213','广东省深圳市南山区大学城创客小镇',0),(3,17,'千锋','13588784567','广东省深圳市南山区深圳大学',0),(4,18,'qfAdmin','10086','广西',0),(5,18,'qfAdmin','10086','广东',0),(6,18,'qfAdmin','10086','湖南',1),(7,18,'qfAdmin','10086','北京',0),(8,17,'小千','12306','广东省深圳市罗湖区xxxx',0),(9,17,'小千','12306','广东省深圳市罗湖区xxxx',0),(10,17,'小千','12306','广东省深圳市罗湖区xxxx',0),(11,17,'小千2','12306','广东省深圳市罗湖区xxxx',0),(12,17,'小千3','12306','广东省深圳市罗湖区xxxx',1),(13,22,'张三','12306','广东省深圳市宝安区后瑞',1),(14,26,'小千','12306','广东省深圳市罗湖区xxxx',1),(15,26,'小千2','12306','广东省深圳市罗湖区xxxx',NULL);

/*Table structure for table `t_car` */

DROP TABLE IF EXISTS `t_car`;

CREATE TABLE `t_car` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `gid` int(10) DEFAULT NULL,
  `uid` int(10) DEFAULT NULL,
  `count` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_car` */

insert  into `t_car`(`id`,`gid`,`uid`,`count`) values (39,70,26,1),(40,70,26,1),(41,69,26,1),(42,67,26,1);

/*Table structure for table `t_goods` */

DROP TABLE IF EXISTS `t_goods`;

CREATE TABLE `t_goods` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `gname` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `gprice` decimal(10,2) DEFAULT NULL,
  `gtype` int(10) DEFAULT NULL,
  `gdesc` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_goods` */

insert  into `t_goods`(`id`,`gname`,`gprice`,`gtype`,`gdesc`) values (37,'testmq','123.00',3,'testmq'),(38,'testmq','123.00',3,'testmq'),(39,'testmq','123.00',3,'testmq'),(63,'123','123.00',1,'123'),(64,'123','345.00',1,'123'),(65,'ITEM','123.00',1,'ITEMITEM'),(66,'item1','12.00',1,'111'),(67,'testItem','67.00',1,'67'),(68,'ttt','12.00',3,'12'),(69,'69','69.00',1,'69'),(70,'70','70.00',4,'70');

/*Table structure for table `t_goods_pic` */

DROP TABLE IF EXISTS `t_goods_pic`;

CREATE TABLE `t_goods_pic` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `png` text COLLATE utf8_bin,
  `gid` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_goods_pic` */

insert  into `t_goods_pic`(`id`,`png`,`gid`) values (37,'http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEHWyGAf-ffAAEq_HU6Wwc424.jpg',20),(38,'http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEHWyGAf-ffAAEq_HU6Wwc424.jpg',21),(39,'http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEHWyGAf-ffAAEq_HU6Wwc424.jpg',22),(82,'http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEHxy6AU4ytAAEq_HU6Wwc755.jpg',63),(83,'http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEHx6eAbgykAAEq_HU6Wwc276.jpg',64),(84,'http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEH_H6AVv_1AAEq_HU6Wwc490.jpg',65),(85,'http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIiCWADC6UAAEq_HU6Wwc745.jpg',66),(86,'http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIkN6AczgVAABE6qaEDp0374.png',67),(87,'http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIkmOAawdhAABE6qaEDp0077.png',68),(88,'http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlSmAdAp5AAPnSdTH6I4179.jpg',69),(89,'http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlb2AdgTJAABE6qaEDp0047.png',70);

/*Table structure for table `t_order_1` */

DROP TABLE IF EXISTS `t_order_1`;

CREATE TABLE `t_order_1` (
  `id` varchar(100) COLLATE utf8_bin NOT NULL,
  `uid` int(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `username` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `total_price` decimal(10,0) DEFAULT NULL,
  `pay_type` int(2) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_order_1` */

insert  into `t_order_1`(`id`,`uid`,`create_time`,`address`,`phone`,`username`,`total_price`,`pay_type`,`status`) values ('20210803001301886',13,'2021-08-03 16:49:12','广东省深圳市罗湖区xxxx','12306','abc','139',1,4),('20210803001331075',13,'2021-08-03 16:31:06','广东省深圳市罗湖区xxxx','12306','abc','139',1,4),('202108030013329310',13,'2021-08-03 17:14:56','广东省深圳市罗湖区xxxx','12306','abc','69',1,4),('20210803001344858',13,'2021-08-03 17:06:40','广东省深圳市罗湖区xxxx','12306','abc','69',1,4),('20210803001362834',13,'2021-08-03 16:30:02','广东省深圳市罗湖区xxxx','12306','abc','139',1,4),('202108030013691911',13,'2021-08-03 17:23:28','广东省深圳市罗湖区xxxx','12306','abc','69',1,4),('202108030013780512',13,'2021-08-03 18:10:14','广东省深圳市罗湖区xxxx','12306','abc','69',1,4);

/*Table structure for table `t_order_2` */

DROP TABLE IF EXISTS `t_order_2`;

CREATE TABLE `t_order_2` (
  `id` varchar(100) COLLATE utf8_bin NOT NULL,
  `uid` int(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `username` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `total_price` decimal(10,0) DEFAULT NULL,
  `pay_type` int(2) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_order_2` */

insert  into `t_order_2`(`id`,`uid`,`create_time`,`address`,`phone`,`username`,`total_price`,`pay_type`,`status`) values ('20210225002200466',22,'2021-02-25 16:20:31','广东省深圳市宝安区后瑞','12306','张三','7748',1,2),('202103010022138610',22,'2021-03-01 10:43:07','广东省深圳市宝安区后瑞','12306','张三','7748',1,2),('20210301002224988',22,'2021-03-01 10:37:49','广东省深圳市宝安区后瑞','12306','张三','7748',1,1),('202103010022363711',22,'2021-03-01 10:48:34','广东省深圳市宝安区后瑞','12306','张三','7748',1,2),('20210301002249789',22,'2021-03-01 10:42:00','广东省深圳市宝安区后瑞','12306','张三','7748',1,1),('20210301002297017',22,'2021-03-01 10:36:34','广东省深圳市宝安区后瑞','12306','张三','7748',1,1),('202103020022714813',22,'2021-03-02 10:40:37','广东省深圳市宝安区后瑞','12306','张三','0',1,1),('202103020022971712',22,'2021-03-02 10:40:25','广东省深圳市宝安区后瑞','12306','张三','2699',1,1),('20210803002637421',26,'2021-08-03 15:58:00','广东省深圳市罗湖区xxxx','12306','小千','206',1,4),('202108030027230021',27,'2021-08-03 20:59:14','广东省深圳市罗湖区xxxx','12306','adminn','140',1,1),('202108030027288119',27,'2021-08-03 20:57:51','广东省深圳市罗湖区xxxx','12306','adminn','140',1,2),('202108030027355024',27,'2021-08-03 21:45:59','广东省深圳市罗湖区xxxx','12306','adminn','12',1,4),('202108030027439920',27,'2021-08-03 20:58:38','广东省深圳市罗湖区xxxx','12306','adminn','140',1,3),('202108030027455817',27,'2021-08-03 18:35:41','广东省深圳市罗湖区xxxx','12306','adminn','70',1,4),('202108030027468122',27,'2021-08-03 21:40:22','广东省深圳市罗湖区xxxx','12306','adminn','140',1,4),('202108030027518016',27,'2021-08-03 18:20:26','广东省深圳市罗湖区xxxx','12306','adminn','70',1,5),('202108030027948623',27,'2021-08-03 21:43:01','广东省深圳市罗湖区xxxx','12306','adminn','69',1,4);

/*Table structure for table `t_order_detail_1` */

DROP TABLE IF EXISTS `t_order_detail_1`;

CREATE TABLE `t_order_detail_1` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `oid` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `gid` int(10) DEFAULT NULL,
  `count` int(10) DEFAULT NULL,
  `subtotal` decimal(10,0) DEFAULT NULL,
  `gname` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `gdesc` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `gpng` text COLLATE utf8_bin,
  `gprice` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_order_detail_1` */

insert  into `t_order_detail_1`(`id`,`oid`,`gid`,`count`,`subtotal`,`gname`,`gdesc`,`gpng`,`gprice`) values (1,'20210803001362834',70,1,'70','70','70','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlb2AdgTJAABE6qaEDp0047.png','70.00'),(2,'20210803001362834',69,1,'69','69','69','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlSmAdAp5AAPnSdTH6I4179.jpg','69.00'),(3,'20210803001331075',70,1,'70','70','70','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlb2AdgTJAABE6qaEDp0047.png','70.00'),(4,'20210803001331075',69,1,'69','69','69','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlSmAdAp5AAPnSdTH6I4179.jpg','69.00'),(5,'20210803001301886',70,1,'70','70','70','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlb2AdgTJAABE6qaEDp0047.png','70.00'),(6,'20210803001301886',69,1,'69','69','69','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlSmAdAp5AAPnSdTH6I4179.jpg','69.00'),(7,'20210803001344858',69,1,'69','69','69','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlSmAdAp5AAPnSdTH6I4179.jpg','69.00'),(8,'202108030013329310',69,1,'69','69','69','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlSmAdAp5AAPnSdTH6I4179.jpg','69.00'),(9,'202108030013691911',69,1,'69','69','69','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlSmAdAp5AAPnSdTH6I4179.jpg','69.00'),(10,'202108030013780512',69,1,'69','69','69','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlSmAdAp5AAPnSdTH6I4179.jpg','69.00');

/*Table structure for table `t_order_detail_2` */

DROP TABLE IF EXISTS `t_order_detail_2`;

CREATE TABLE `t_order_detail_2` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `oid` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `gid` int(10) DEFAULT NULL,
  `count` int(10) DEFAULT NULL,
  `subtotal` decimal(10,0) DEFAULT NULL,
  `gname` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `gdesc` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `gpng` text COLLATE utf8_bin,
  `gprice` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_order_detail_2` */

insert  into `t_order_detail_2`(`id`,`oid`,`gid`,`count`,`subtotal`,`gname`,`gdesc`,`gpng`,`gprice`) values (11,'20210225002200466',8,1,'1899','华为 畅享20 SE手机','SE手机 6.67英寸全高清大屏5000mAh大电池 幻夜黑 （8GB+128GB）碎屏保套餐版','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWATZUaAMngXAAD3Gbx1i5c523.jpg','1899.00'),(12,'20210225002200466',10,1,'350','迪奥(Dior)口红','口红烈艳蓝金唇膏缎光999# 3.5g(口红礼盒 经典正红色 保湿滋润 情人节礼物 赠礼袋 新老款随机）','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWATZc6AcdipAAFVHxd_uJQ203.jpg','350.00'),(13,'20210225002200466',14,1,'5499','美的(Midea)639升对开门双开门冰箱','9分钟急速净味除菌风冷无霜一级双变频智能家电速冷速冻BCD-639WKPZM(E)','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWAXWnOANDMCAAB37pPjBqw433.jpg','5499.00'),(14,'20210301002297017',8,1,'1899','华为 畅享20 SE手机','SE手机 6.67英寸全高清大屏5000mAh大电池 幻夜黑 （8GB+128GB）碎屏保套餐版','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWATZUaAMngXAAD3Gbx1i5c523.jpg','1899.00'),(15,'20210301002297017',10,1,'350','迪奥(Dior)口红','口红烈艳蓝金唇膏缎光999# 3.5g(口红礼盒 经典正红色 保湿滋润 情人节礼物 赠礼袋 新老款随机）','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWATZc6AcdipAAFVHxd_uJQ203.jpg','350.00'),(16,'20210301002297017',14,1,'5499','美的(Midea)639升对开门双开门冰箱','9分钟急速净味除菌风冷无霜一级双变频智能家电速冷速冻BCD-639WKPZM(E)','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWAXWnOANDMCAAB37pPjBqw433.jpg','5499.00'),(17,'20210301002224988',8,1,'1899','华为 畅享20 SE手机','SE手机 6.67英寸全高清大屏5000mAh大电池 幻夜黑 （8GB+128GB）碎屏保套餐版','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWATZUaAMngXAAD3Gbx1i5c523.jpg','1899.00'),(18,'20210301002224988',10,1,'350','迪奥(Dior)口红','口红烈艳蓝金唇膏缎光999# 3.5g(口红礼盒 经典正红色 保湿滋润 情人节礼物 赠礼袋 新老款随机）','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWATZc6AcdipAAFVHxd_uJQ203.jpg','350.00'),(19,'20210301002224988',14,1,'5499','美的(Midea)639升对开门双开门冰箱','9分钟急速净味除菌风冷无霜一级双变频智能家电速冷速冻BCD-639WKPZM(E)','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWAXWnOANDMCAAB37pPjBqw433.jpg','5499.00'),(20,'20210301002249789',8,1,'1899','华为 畅享20 SE手机','SE手机 6.67英寸全高清大屏5000mAh大电池 幻夜黑 （8GB+128GB）碎屏保套餐版','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWATZUaAMngXAAD3Gbx1i5c523.jpg','1899.00'),(21,'20210301002249789',10,1,'350','迪奥(Dior)口红','口红烈艳蓝金唇膏缎光999# 3.5g(口红礼盒 经典正红色 保湿滋润 情人节礼物 赠礼袋 新老款随机）','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWATZc6AcdipAAFVHxd_uJQ203.jpg','350.00'),(22,'20210301002249789',14,1,'5499','美的(Midea)639升对开门双开门冰箱','9分钟急速净味除菌风冷无霜一级双变频智能家电速冷速冻BCD-639WKPZM(E)','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWAXWnOANDMCAAB37pPjBqw433.jpg','5499.00'),(23,'202103010022138610',8,1,'1899','华为 畅享20 SE手机','SE手机 6.67英寸全高清大屏5000mAh大电池 幻夜黑 （8GB+128GB）碎屏保套餐版','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWATZUaAMngXAAD3Gbx1i5c523.jpg','1899.00'),(24,'202103010022138610',10,1,'350','迪奥(Dior)口红','口红烈艳蓝金唇膏缎光999# 3.5g(口红礼盒 经典正红色 保湿滋润 情人节礼物 赠礼袋 新老款随机）','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWATZc6AcdipAAFVHxd_uJQ203.jpg','350.00'),(25,'202103010022138610',14,1,'5499','美的(Midea)639升对开门双开门冰箱','9分钟急速净味除菌风冷无霜一级双变频智能家电速冷速冻BCD-639WKPZM(E)','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWAXWnOANDMCAAB37pPjBqw433.jpg','5499.00'),(26,'202103010022363711',8,1,'1899','华为 畅享20 SE手机','SE手机 6.67英寸全高清大屏5000mAh大电池 幻夜黑 （8GB+128GB）碎屏保套餐版','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWATZUaAMngXAAD3Gbx1i5c523.jpg','1899.00'),(27,'202103010022363711',10,1,'350','迪奥(Dior)口红','口红烈艳蓝金唇膏缎光999# 3.5g(口红礼盒 经典正红色 保湿滋润 情人节礼物 赠礼袋 新老款随机）','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWATZc6AcdipAAFVHxd_uJQ203.jpg','350.00'),(28,'202103010022363711',14,1,'5499','美的(Midea)639升对开门双开门冰箱','9分钟急速净味除菌风冷无霜一级双变频智能家电速冷速冻BCD-639WKPZM(E)','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWAXWnOANDMCAAB37pPjBqw433.jpg','5499.00'),(29,'202103020022971712',15,1,'2699','雷诺(RARONE)手表','情人节礼物机械情侣表一对百年好合男女对表腕表 钢带 定制礼盒 唯爱系列','http://192.168.147.101:8080/group1/M00/00/00/wKiTZWAaWyKAHEu2AAJjagVMXKU346.jpg','2699.00'),(30,'20210803002637421',70,1,'70','70','70','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlb2AdgTJAABE6qaEDp0047.png','70.00'),(31,'20210803002637421',69,1,'69','69','69','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlSmAdAp5AAPnSdTH6I4179.jpg','69.00'),(32,'20210803002637421',67,1,'67','testItem','67','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIkN6AczgVAABE6qaEDp0374.png','67.00'),(33,'202108030027518016',70,1,'70','70','70','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlb2AdgTJAABE6qaEDp0047.png','70.00'),(34,'202108030027455817',70,1,'70','70','70','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlb2AdgTJAABE6qaEDp0047.png','70.00'),(35,'202108030027854818',70,1,'70','70','70','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlb2AdgTJAABE6qaEDp0047.png','70.00'),(36,'202108030027288119',70,2,'140','70','70','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlb2AdgTJAABE6qaEDp0047.png','70.00'),(37,'202108030027439920',70,2,'140','70','70','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlb2AdgTJAABE6qaEDp0047.png','70.00'),(38,'202108030027230021',70,2,'140','70','70','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlb2AdgTJAABE6qaEDp0047.png','70.00'),(39,'202108030027468122',70,2,'140','70','70','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlb2AdgTJAABE6qaEDp0047.png','70.00'),(40,'202108030027948623',69,1,'69','69','69','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIlSmAdAp5AAPnSdTH6I4179.jpg','69.00'),(41,'202108030027355024',66,1,'12','item1','111','http://192.168.126.129:8080/group1/M00/00/00/wKh-gWEIiCWADC6UAAEq_HU6Wwc745.jpg','12.00');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `email` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `age` int(10) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`username`,`password`,`sex`,`email`,`phone`,`age`,`birthday`) values (10,'admin2','123456',0,'admin2@qf.com','13480743212',90,'2021-01-27'),(11,'admin4','123456',0,'admin3@qf.com','13480743212',18,'2021-01-27'),(12,'张三1','123456789',0,'zs1@qf.com','13480743222',21,'2021-01-02'),(13,'abc1',NULL,1,NULL,NULL,88,NULL),(14,'abc2',NULL,1,NULL,NULL,88,NULL),(15,'abc3',NULL,1,NULL,NULL,88,NULL),(16,'xxx',NULL,NULL,NULL,NULL,NULL,NULL),(17,'aa','123456',NULL,NULL,NULL,NULL,NULL),(20,'la',NULL,NULL,NULL,NULL,NULL,NULL),(21,'qq',NULL,NULL,NULL,NULL,NULL,NULL),(26,'admin','$2a$10$8yLwMBjuDj.AcpUROSE8n.x/Hz6IEfep0QUVvNc9iYNOyaw98VVhW',NULL,'857802146@qq.com',NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
