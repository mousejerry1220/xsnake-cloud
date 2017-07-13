/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.12 : Database - xsnake_cloud
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`xsnake_cloud` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `xsnake_cloud`;

/*Table structure for table `t_sys_common_delete` */

DROP TABLE IF EXISTS `t_sys_common_delete`;

CREATE TABLE `t_sys_common_delete` (
  `id` varchar(32) NOT NULL,
  `deleteFlag` varchar(1) NOT NULL,
  `deleteDate` datetime NOT NULL,
  `deleteBy` varchar(32) NOT NULL,
  `businessType` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_common_info` */

DROP TABLE IF EXISTS `t_sys_common_info`;

CREATE TABLE `t_sys_common_info` (
  `id` varchar(32) NOT NULL,
  `createBy` varchar(32) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `lastUpdateBy` varchar(32) DEFAULT NULL,
  `lastUpdateDate` datetime DEFAULT NULL,
  `businessType` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_common_right` */

DROP TABLE IF EXISTS `t_sys_common_right`;

CREATE TABLE `t_sys_common_right` (
  `id` varchar(32) NOT NULL,
  `ownerEmployeeId` varchar(32) NOT NULL,
  `ownerPositionId` varchar(32) NOT NULL,
  `ownerOrgId` varchar(32) NOT NULL,
  `businessType` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_data_header` */

DROP TABLE IF EXISTS `t_sys_data_header`;

CREATE TABLE `t_sys_data_header` (
  `id` varchar(100) NOT NULL,
  `status` varchar(100) DEFAULT NULL,
  `groupCode` varchar(100) DEFAULT NULL,
  `deleteFlag` varchar(1) DEFAULT NULL,
  `deleteBy` varchar(32) DEFAULT NULL,
  `deleteDate` datetime DEFAULT NULL,
  `createBy` varchar(32) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `lastUpdateBy` varchar(32) DEFAULT NULL,
  `lastUpdateDate` datetime DEFAULT NULL,
  `ownerEmployeeId` varchar(32) DEFAULT NULL,
  `ownerPositionId` varchar(32) DEFAULT NULL,
  `ownerOrgId` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_data_line` */

DROP TABLE IF EXISTS `t_sys_data_line`;

CREATE TABLE `t_sys_data_line` (
  `id` varchar(100) NOT NULL,
  `headerId` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_lov_group` */

DROP TABLE IF EXISTS `t_sys_lov_group`;

CREATE TABLE `t_sys_lov_group` (
  `code` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL COMMENT '备注',
  `systemFlag` varchar(1) NOT NULL COMMENT '系统标示，系统必须的或者自定义的',
  `treeFlag` varchar(1) DEFAULT NULL,
  `expandFlag` varchar(1) DEFAULT NULL COMMENT '是否扩展，如果是扩展则主数据在data_business表，否则在lov_memeber表',
  `mainFlag` varchar(1) DEFAULT NULL COMMENT '当扩展数据为Y的时候，生是否主数据',
  `mainCode` varchar(100) DEFAULT NULL COMMENT '当不是主数据时生效，选择关联的主数据，非扩展数据不可当做主数据',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_lov_group_catalog` */

DROP TABLE IF EXISTS `t_sys_lov_group_catalog`;

CREATE TABLE `t_sys_lov_group_catalog` (
  `code` varchar(100) NOT NULL,
  `groupCode` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `sn` int(11) DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`code`,`groupCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_lov_group_event` */

DROP TABLE IF EXISTS `t_sys_lov_group_event`;

CREATE TABLE `t_sys_lov_group_event` (
  `code` varchar(100) NOT NULL,
  `groupCode` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `preconditionStatusCode` varchar(100) NOT NULL COMMENT '动作的前提条件状态',
  `editFlag` varchar(1) NOT NULL COMMENT '事件是否可以编辑数据',
  `copyFlag` varchar(1) DEFAULT NULL COMMENT '事件如果不是即时事件那么再数据变更未确定时，看到的依然是历史数据',
  `xflowCode` varchar(100) DEFAULT NULL COMMENT '时间如果是过程事件，则可以选择关联的流程代码',
  PRIMARY KEY (`code`,`groupCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_lov_group_event_field` */

DROP TABLE IF EXISTS `t_sys_lov_group_event_field`;

CREATE TABLE `t_sys_lov_group_event_field` (
  `eventCode` varchar(100) NOT NULL,
  `groupCode` varchar(100) NOT NULL,
  `fieldCode` varchar(100) NOT NULL,
  `editFlag` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`eventCode`,`groupCode`,`fieldCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_lov_group_event_result` */

DROP TABLE IF EXISTS `t_sys_lov_group_event_result`;

CREATE TABLE `t_sys_lov_group_event_result` (
  `eventCode` varchar(100) NOT NULL,
  `groupCode` varchar(100) NOT NULL,
  `eventResult` varchar(100) NOT NULL,
  `statusCode` varchar(100) DEFAULT NULL,
  `replaceFlag` varchar(1) DEFAULT NULL COMMENT '如果事件的copyFlag=Y说明会拷贝一份数据，replaceFlag=Y时则替换原有数据',
  PRIMARY KEY (`eventCode`,`groupCode`,`eventResult`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_lov_group_event_script` */

DROP TABLE IF EXISTS `t_sys_lov_group_event_script`;

CREATE TABLE `t_sys_lov_group_event_script` (
  `code` varchar(100) NOT NULL COMMENT '用作事件的拦截脚本',
  `name` varchar(100) DEFAULT NULL,
  `eventCode` varchar(100) NOT NULL,
  `content` varchar(8000) DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `eventType` varchar(30) DEFAULT NULL COMMENT '脚本执行的类型，如果事件为可编辑字段，则可以选编辑前执行，否则只能选提交时执行',
  `scriptType` varchar(30) DEFAULT NULL COMMENT '脚本的类型，可以选择SQL或者JS，SQL返回单个字段，JS返回字符串均为失败原因，如果都为空则表示不拦截',
  PRIMARY KEY (`code`,`eventCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_lov_group_field` */

DROP TABLE IF EXISTS `t_sys_lov_group_field`;

CREATE TABLE `t_sys_lov_group_field` (
  `code` varchar(100) NOT NULL,
  `groupCode` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `fieldType` varchar(100) NOT NULL COMMENT '字符串，整数，小数，日期，引用业务，引用的行，引用LOV，接口回写',
  `catalogCode` varchar(100) NOT NULL COMMENT '把一个实体信息根据规则分为多个目录(如：客户信息包含企业基本信息，联系信息，财务信息等）',
  `maxLength` int(11) DEFAULT '0' COMMENT '当字段类型为字符串时可用',
  `sn` int(11) NOT NULL DEFAULT '0',
  `nullFlag` varchar(1) NOT NULL COMMENT '可以为空标志，Y可为空，N不可为空',
  `relationGroupCode` varchar(100) DEFAULT NULL COMMENT '如果为引用类型则生效，指向要选择的实体定义',
  `relationRange` varchar(100) DEFAULT NULL COMMENT '如果为引用类型，LOV可选内容有我能看到的，我不能看到的，全部的',
  `relationStatus` varchar(100) DEFAULT NULL COMMENT '如果为引用类型，LOV可选内容有删除的，过期的，有效的，全部的（过期+删除+有效），无效的（删除+过期）',
  `relationShowType` varchar(100) DEFAULT NULL COMMENT '如果为引用类型，LOV显示的类型：下拉，输入下拉，弹出',
  `relationShowValue` varchar(100) DEFAULT NULL COMMENT '如果为引用类型，显示的值，可以为表达式',
  PRIMARY KEY (`code`,`groupCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_lov_group_status` */

DROP TABLE IF EXISTS `t_sys_lov_group_status`;

CREATE TABLE `t_sys_lov_group_status` (
  `code` varchar(100) NOT NULL,
  `groupCode` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`code`,`groupCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_lov_member` */

DROP TABLE IF EXISTS `t_sys_lov_member`;

CREATE TABLE `t_sys_lov_member` (
  `id` varchar(32) NOT NULL,
  `groupCode` varchar(100) NOT NULL,
  `code` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `sn` int(11) NOT NULL COMMENT '排序号',
  `searchKey` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_lov_tree` */

DROP TABLE IF EXISTS `t_sys_lov_tree`;

CREATE TABLE `t_sys_lov_tree` (
  `code` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `systemFlag` varchar(1) DEFAULT NULL COMMENT '系统内置数据标示',
  `version` int(11) DEFAULT '1',
  `remark` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_lov_tree_backup` */

DROP TABLE IF EXISTS `t_sys_lov_tree_backup`;

CREATE TABLE `t_sys_lov_tree_backup` (
  `row_id` varchar(32) NOT NULL,
  `treeCode` varchar(100) NOT NULL,
  `createDate` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `tableName` varchar(30) NOT NULL,
  PRIMARY KEY (`row_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_lov_tree_node` */

DROP TABLE IF EXISTS `t_sys_lov_tree_node`;

CREATE TABLE `t_sys_lov_tree_node` (
  `id` varchar(32) NOT NULL COMMENT '节点',
  `name` varchar(100) NOT NULL COMMENT '节点名',
  `code` varchar(100) NOT NULL,
  `parentId` varchar(32) NOT NULL COMMENT '父节点',
  `treeCode` varchar(32) NOT NULL COMMENT '树ID',
  `level` int(11) NOT NULL COMMENT '层级',
  `idPath` varchar(1000) NOT NULL,
  `namePath` varchar(4000) NOT NULL,
  `codePath` varchar(4000) NOT NULL,
  `nodeSourceCode` varchar(30) NOT NULL COMMENT '节点来源，一个树可能来自不同数据表',
  `sn` int(11) DEFAULT '0',
  KEY `NewIndex1` (`id`,`treeCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_lov_tree_node_source` */

DROP TABLE IF EXISTS `t_sys_lov_tree_node_source`;

CREATE TABLE `t_sys_lov_tree_node_source` (
  `treeCode` varchar(100) NOT NULL,
  `nodeSourceCode` varchar(100) NOT NULL,
  PRIMARY KEY (`treeCode`,`nodeSourceCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `v_employee_simple` */

DROP TABLE IF EXISTS `v_employee_simple`;

/*!50001 DROP VIEW IF EXISTS `v_employee_simple` */;
/*!50001 DROP TABLE IF EXISTS `v_employee_simple` */;

/*!50001 CREATE TABLE  `v_employee_simple`(
 `id` varchar(32) NOT NULL ,
 `code` varchar(100) NOT NULL ,
 `name` varchar(100) NOT NULL ,
 `sn` int(11) NOT NULL 
)*/;

/*Table structure for table `v_sys_common_delete` */

DROP TABLE IF EXISTS `v_sys_common_delete`;

/*!50001 DROP VIEW IF EXISTS `v_sys_common_delete` */;
/*!50001 DROP TABLE IF EXISTS `v_sys_common_delete` */;

/*!50001 CREATE TABLE  `v_sys_common_delete`(
 `id` varchar(32) NOT NULL ,
 `deleteFlag` varchar(1) NOT NULL ,
 `deleteDate` datetime NOT NULL ,
 `deleteBy` varchar(32) NOT NULL ,
 `businessType` varchar(100) NOT NULL ,
 `deleteByName` varchar(100) NULL 
)*/;

/*Table structure for table `v_sys_common_info` */

DROP TABLE IF EXISTS `v_sys_common_info`;

/*!50001 DROP VIEW IF EXISTS `v_sys_common_info` */;
/*!50001 DROP TABLE IF EXISTS `v_sys_common_info` */;

/*!50001 CREATE TABLE  `v_sys_common_info`(
 `id` varchar(32) NOT NULL ,
 `createBy` varchar(32) NULL ,
 `createDate` datetime NULL ,
 `lastUpdateBy` varchar(32) NULL ,
 `lastUpdateDate` datetime NULL ,
 `businessType` varchar(100) NULL ,
 `createByName` varchar(100) NULL ,
 `lastUpdateByName` varchar(100) NULL 
)*/;

/*Table structure for table `v_sys_lov_group` */

DROP TABLE IF EXISTS `v_sys_lov_group`;

/*!50001 DROP VIEW IF EXISTS `v_sys_lov_group` */;
/*!50001 DROP TABLE IF EXISTS `v_sys_lov_group` */;

/*!50001 CREATE TABLE  `v_sys_lov_group`(
 `code` varchar(100) NOT NULL ,
 `name` varchar(100) NOT NULL ,
 `remark` varchar(4000) NULL ,
 `systemFlag` varchar(1) NOT NULL ,
 `treeFlag` varchar(1) NULL ,
 `createBy` varchar(32) NULL ,
 `createDate` datetime NULL ,
 `lastUpdateBy` varchar(32) NULL ,
 `lastUpdateDate` datetime NULL ,
 `createByName` varchar(100) NULL ,
 `lastUpdateByName` varchar(100) NULL ,
 `deleteFlag` varchar(1) NULL ,
 `deleteDate` datetime NULL ,
 `deleteBy` varchar(32) NULL ,
 `deleteByName` varchar(100) NULL 
)*/;

/*Table structure for table `v_sys_lov_member` */

DROP TABLE IF EXISTS `v_sys_lov_member`;

/*!50001 DROP VIEW IF EXISTS `v_sys_lov_member` */;
/*!50001 DROP TABLE IF EXISTS `v_sys_lov_member` */;

/*!50001 CREATE TABLE  `v_sys_lov_member`(
 `id` varchar(32) NOT NULL ,
 `groupCode` varchar(100) NOT NULL ,
 `code` varchar(100) NOT NULL ,
 `name` varchar(100) NOT NULL ,
 `remark` varchar(4000) NULL ,
 `sn` int(11) NOT NULL ,
 `searchKey` varchar(100) NULL ,
 `createBy` varchar(32) NULL ,
 `createDate` datetime NULL ,
 `lastUpdateBy` varchar(32) NULL ,
 `lastUpdateDate` datetime NULL ,
 `createByName` varchar(100) NULL ,
 `lastUpdateByName` varchar(100) NULL ,
 `deleteFlag` varchar(1) NULL ,
 `deleteDate` datetime NULL ,
 `deleteBy` varchar(32) NULL ,
 `deleteByName` varchar(100) NULL 
)*/;

/*Table structure for table `v_sys_lov_tree_node` */

DROP TABLE IF EXISTS `v_sys_lov_tree_node`;

/*!50001 DROP VIEW IF EXISTS `v_sys_lov_tree_node` */;
/*!50001 DROP TABLE IF EXISTS `v_sys_lov_tree_node` */;

/*!50001 CREATE TABLE  `v_sys_lov_tree_node`(
 `id` varchar(32) NOT NULL ,
 `name` varchar(100) NOT NULL ,
 `code` varchar(100) NOT NULL ,
 `parentId` varchar(32) NOT NULL ,
 `treeCode` varchar(32) NOT NULL ,
 `level` int(11) NOT NULL ,
 `idPath` varchar(1000) NOT NULL ,
 `namePath` varchar(4000) NOT NULL ,
 `codePath` varchar(4000) NOT NULL ,
 `nodeSourceCode` varchar(30) NOT NULL ,
 `sn` int(11) NULL  default '0' 
)*/;

/*View structure for view v_employee_simple */

/*!50001 DROP TABLE IF EXISTS `v_employee_simple` */;
/*!50001 DROP VIEW IF EXISTS `v_employee_simple` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_employee_simple` AS (select `m`.`id` AS `id`,`m`.`code` AS `code`,`m`.`name` AS `name`,`m`.`sn` AS `sn` from `t_sys_lov_member` `m` where (`m`.`groupCode` = 'EMPLOYEE')) */;

/*View structure for view v_sys_common_delete */

/*!50001 DROP TABLE IF EXISTS `v_sys_common_delete` */;
/*!50001 DROP VIEW IF EXISTS `v_sys_common_delete` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_sys_common_delete` AS (select `cd`.`id` AS `id`,`cd`.`deleteFlag` AS `deleteFlag`,`cd`.`deleteDate` AS `deleteDate`,`cd`.`deleteBy` AS `deleteBy`,`cd`.`businessType` AS `businessType`,`es`.`name` AS `deleteByName` from (`t_sys_common_delete` `cd` left join `v_employee_simple` `es` on((`cd`.`deleteBy` = `es`.`id`)))) */;

/*View structure for view v_sys_common_info */

/*!50001 DROP TABLE IF EXISTS `v_sys_common_info` */;
/*!50001 DROP VIEW IF EXISTS `v_sys_common_info` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_sys_common_info` AS (select `ci`.`id` AS `id`,`ci`.`createBy` AS `createBy`,`ci`.`createDate` AS `createDate`,`ci`.`lastUpdateBy` AS `lastUpdateBy`,`ci`.`lastUpdateDate` AS `lastUpdateDate`,`ci`.`businessType` AS `businessType`,`ces`.`name` AS `createByName`,`ues`.`name` AS `lastUpdateByName` from ((`t_sys_common_info` `ci` left join `v_employee_simple` `ces` on((`ci`.`createBy` = `ces`.`id`))) left join `v_employee_simple` `ues` on((`ci`.`lastUpdateBy` = `ues`.`id`)))) */;

/*View structure for view v_sys_lov_group */

/*!50001 DROP TABLE IF EXISTS `v_sys_lov_group` */;
/*!50001 DROP VIEW IF EXISTS `v_sys_lov_group` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_sys_lov_group` AS (select `g`.`code` AS `code`,`g`.`name` AS `name`,`g`.`remark` AS `remark`,`g`.`systemFlag` AS `systemFlag`,`g`.`treeFlag` AS `treeFlag`,`ci`.`createBy` AS `createBy`,`ci`.`createDate` AS `createDate`,`ci`.`lastUpdateBy` AS `lastUpdateBy`,`ci`.`lastUpdateDate` AS `lastUpdateDate`,`ci`.`createByName` AS `createByName`,`ci`.`lastUpdateByName` AS `lastUpdateByName`,`cd`.`deleteFlag` AS `deleteFlag`,`cd`.`deleteDate` AS `deleteDate`,`cd`.`deleteBy` AS `deleteBy`,`cd`.`deleteByName` AS `deleteByName` from ((`t_sys_lov_group` `g` left join `v_sys_common_info` `ci` on(((`g`.`code` = `ci`.`id`) and (`ci`.`businessType` = 'LOV_GROUP')))) left join `v_sys_common_delete` `cd` on(((`g`.`code` = `cd`.`id`) and (`cd`.`businessType` = 'LOV_GROUP'))))) */;

/*View structure for view v_sys_lov_member */

/*!50001 DROP TABLE IF EXISTS `v_sys_lov_member` */;
/*!50001 DROP VIEW IF EXISTS `v_sys_lov_member` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_sys_lov_member` AS (select `m`.`id` AS `id`,`m`.`groupCode` AS `groupCode`,`m`.`code` AS `code`,`m`.`name` AS `name`,`m`.`remark` AS `remark`,`m`.`sn` AS `sn`,`m`.`searchKey` AS `searchKey`,`ci`.`createBy` AS `createBy`,`ci`.`createDate` AS `createDate`,`ci`.`lastUpdateBy` AS `lastUpdateBy`,`ci`.`lastUpdateDate` AS `lastUpdateDate`,`ci`.`createByName` AS `createByName`,`ci`.`lastUpdateByName` AS `lastUpdateByName`,`cd`.`deleteFlag` AS `deleteFlag`,`cd`.`deleteDate` AS `deleteDate`,`cd`.`deleteBy` AS `deleteBy`,`cd`.`deleteByName` AS `deleteByName` from ((`t_sys_lov_member` `m` left join `v_sys_common_info` `ci` on(((`m`.`id` = `ci`.`id`) and (`ci`.`businessType` = 'LOV_MEMBER')))) left join `v_sys_common_delete` `cd` on(((`m`.`id` = `cd`.`id`) and (`cd`.`businessType` = 'LOV_MEMBER'))))) */;

/*View structure for view v_sys_lov_tree_node */

/*!50001 DROP TABLE IF EXISTS `v_sys_lov_tree_node` */;
/*!50001 DROP VIEW IF EXISTS `v_sys_lov_tree_node` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_sys_lov_tree_node` AS (select `tn`.`id` AS `id`,`tn`.`name` AS `name`,`tn`.`code` AS `code`,`tn`.`parentId` AS `parentId`,`tn`.`treeCode` AS `treeCode`,`tn`.`level` AS `level`,`tn`.`idPath` AS `idPath`,`tn`.`namePath` AS `namePath`,`tn`.`codePath` AS `codePath`,`tn`.`nodeSourceCode` AS `nodeSourceCode`,`tn`.`sn` AS `sn` from (`t_sys_lov_tree_node` `tn` left join `t_sys_common_delete` `cd` on(((`tn`.`id` = `cd`.`id`) and (`cd`.`deleteFlag` = 'N') and (`cd`.`businessType` = 'LOV_MEMBER')))) where isnull(`cd`.`id`)) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
