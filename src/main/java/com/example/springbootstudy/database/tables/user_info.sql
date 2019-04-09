/*
 Navicat Premium Data Transfer

 Source Server         : 本机MySQL
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : SpringBootStudy

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 09/04/2019 16:18:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `phone` varchar(13) NOT NULL DEFAULT '' COMMENT '手机号码',
  `nickname` varchar(16) NOT NULL DEFAULT '' COMMENT '昵称',
  `personal_sign` varchar(128) NOT NULL DEFAULT '' COMMENT '个性签名',
  `sex` varchar(3) NOT NULL DEFAULT '' COMMENT '性别',
  `real_name` varchar(8) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `personal_id` varchar(20) NOT NULL DEFAULT '' COMMENT '身份证号码',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
