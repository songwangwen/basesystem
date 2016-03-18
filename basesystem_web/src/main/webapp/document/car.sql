/*
SQLyog v10.2 
MySQL - 5.0.22-community-nt : Database - my_car
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`my_car` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `my_car`;

/*Table structure for table `system_menu` */

DROP TABLE IF EXISTS `system_menu`;

CREATE TABLE `system_menu` (
  `node_id` int(11) NOT NULL COMMENT '主键，手动添加，唯一',
  `NODE_NAME` varchar(200) default NULL COMMENT '菜单项名称',
  `ICON_CLS` varchar(200) default NULL COMMENT '菜单项图标，该图标在icon.css中进行配置',
  `URL` varchar(200) default NULL COMMENT '点击菜单项的跳转路径，如果是父节点，不存在直接跳转路径，则为#',
  `NODE_TYPE` varchar(200) default NULL COMMENT 'menu:表示左侧导航树\n            button:表示按钮',
  `PARENT_ID` int(11) default NULL COMMENT '父节点ID',
  `sort` int(11) default NULL COMMENT '父节点ID',
  `REMARK` varchar(2000) default NULL COMMENT '备注信息',
  `DELETE_FLAG` bit(1) default '\0' COMMENT '删除标志位\n            1：删除\n            0：正常',
  PRIMARY KEY  (`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保存系统菜单项目\n关联角色以及权限数据表�';

/*Data for the table `system_menu` */

insert  into `system_menu`(`node_id`,`NODE_NAME`,`ICON_CLS`,`URL`,`NODE_TYPE`,`PARENT_ID`,`sort`,`REMARK`,`DELETE_FLAG`) values (1,'系统管理','icon-system','#','menu',0,1,NULL,'\0'),(11,'用户管理','icon-user','/pages/system/userManager.jsp','menu',1,11,NULL,'\0'),(12,'角色管理','icon-group','/pages/system/roleManager.jsp','menu',1,12,NULL,'\0'),(111,'查看列表','icon-list','#','view',11,111,NULL,'\0'),(112,'新增','icon-add','#','button',11,112,NULL,'\0'),(113,'编辑','icon-edit','#','button',11,113,NULL,'\0'),(114,'重置密码','icon-key','#','button',11,114,NULL,'\0'),(115,'数据权限','icon-center-picture_key','#','button',11,115,NULL,'\0'),(116,'操作权限','icon-center-user_key','#','button',11,116,NULL,'\0'),(117,'导入Excel','icon-center-excel_imports','#','button',11,117,NULL,'\0'),(118,'导出Excel','icon-center-excel_imports','#','button',11,118,NULL,'\0'),(119,'角色配置','icon-center-group','#','button',11,119,NULL,'\0'),(121,'查看列表','icon-list','#','button',12,121,NULL,'\0'),(122,'新增','icon-add','#','button',12,122,NULL,'\0'),(123,'编辑','icon-edit','#','button',12,123,NULL,'\0'),(124,'数据权限','icon-center-picture_key','#','button',12,124,NULL,'\0'),(125,'操作权限','icon-center-user_key','#','button',12,125,NULL,'\0');

/*Table structure for table `system_role` */

DROP TABLE IF EXISTS `system_role`;

CREATE TABLE `system_role` (
  `ID` bigint(20) NOT NULL auto_increment,
  `role_name` varchar(200) default NULL COMMENT '角色名称',
  `remark` varchar(2000) default NULL COMMENT '备注信息',
  `create_datetime` datetime default NULL COMMENT '创建时间',
  `deleted` bit(1) default NULL COMMENT '删除标志位',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system_role` */

insert  into `system_role`(`ID`,`role_name`,`remark`,`create_datetime`,`deleted`) values (1,'管理员','管理员',NULL,'\0'),(2,'超级管理员',NULL,NULL,'\0'),(3,'普通用户',NULL,NULL,'\0'),(4,'角色名称','备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注','2015-09-25 16:21:45','\0');

/*Table structure for table `system_user` */

DROP TABLE IF EXISTS `system_user`;

CREATE TABLE `system_user` (
  `Id` bigint(20) NOT NULL auto_increment,
  `user_name` varchar(200) default NULL COMMENT '用户登录名',
  `password` varchar(200) default NULL COMMENT '用户密码',
  `real_name` varchar(200) default NULL COMMENT '真实姓名',
  `last_login_ip` varchar(100) default NULL COMMENT '最后登录IP	',
  `last_login_datetime` datetime default NULL COMMENT '最后登录时间',
  `create_datetime` datetime default NULL COMMENT '创建时间',
  `enabled` bit(1) default '\0' COMMENT '是否启用\n            1：启用\n            0：停用',
  `deleted` bit(1) default '\0' COMMENT '删除标志位\n            1：删除\n            0：正常',
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system_user` */

insert  into `system_user`(`Id`,`user_name`,`password`,`real_name`,`last_login_ip`,`last_login_datetime`,`create_datetime`,`enabled`,`deleted`) values (1,'songwangwen','e10adc3949ba59abbe56e057f20f883e','宋王文','192.168.10.255','2015-09-21 16:11:16','2015-09-21 15:50:27','','\0'),(2,'zhangsan','123456','张三',NULL,NULL,'2015-09-24 14:48:49','\0','\0'),(5,'zhangsan','e10adc3949ba59abbe56e057f20f883e','张三',NULL,NULL,'2015-09-24 15:55:53','\0','\0'),(6,'lisi','e10adc3949ba59abbe56e057f20f883e','李四',NULL,NULL,'2015-09-24 16:02:31','\0','\0'),(7,'ww','202cb962ac59075b964b07152d234b70','ww',NULL,NULL,'2015-09-24 16:04:40','\0','\0'),(8,'dssdf','81dc9bdb52d04dc20036dbd8313ed055','ddddddd',NULL,NULL,'2015-09-24 16:11:09','\0','\0'),(9,'lalalal','e10adc3949ba59abbe56e057f20f883e','ssksksk',NULL,NULL,'2015-09-24 16:18:44','\0','\0'),(10,'123','202cb962ac59075b964b07152d234b70','33333',NULL,NULL,'2015-09-24 16:20:55','\0','\0'),(11,'334','e369853df766fa44e1ed0ff613f563bd','34',NULL,NULL,'2015-09-24 16:22:46','\0','\0'),(12,'334','202cb962ac59075b964b07152d234b70','333',NULL,NULL,'2015-09-24 16:27:32','\0','\0'),(13,'334234','202cb962ac59075b964b07152d234b70','333333333',NULL,NULL,'2015-09-24 16:28:19','\0','\0');

/*Table structure for table `system_user_role_rel` */

DROP TABLE IF EXISTS `system_user_role_rel`;

CREATE TABLE `system_user_role_rel` (
  `user_id` bigint(20) default NULL,
  `role_id` bigint(20) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与人员关联表';

/*Data for the table `system_user_role_rel` */

insert  into `system_user_role_rel`(`user_id`,`role_id`) values (1,1),(1,2),(1,3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
