/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50637
Source Host           : localhost:3306
Source Database       : wechat

Target Server Type    : MYSQL
Target Server Version : 50637
File Encoding         : 65001

Date: 2020-09-16 16:32:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for zs_message
-- ----------------------------
DROP TABLE IF EXISTS `zs_message`;
CREATE TABLE `zs_message` (
  `id` tinyint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of zs_message
-- ----------------------------
INSERT INTO `zs_message` VALUES ('1', 'welcome_message', '微信关注欢迎语', '你好，欢迎关注！');
INSERT INTO `zs_message` VALUES ('2', 'other_message', '发送其他消息回复', '已收到您的消息，我们将会尽快回复您。/爱心');
INSERT INTO `zs_message` VALUES ('6', 'contact_message', '联系方式', '电话：12312312');
INSERT INTO `zs_message` VALUES ('7', 'general_message', '普通回复', 'Hello');

-- ----------------------------
-- Table structure for zs_wechat_user
-- ----------------------------
DROP TABLE IF EXISTS `zs_wechat_user`;
CREATE TABLE `zs_wechat_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `openid` varchar(128) DEFAULT NULL,
  `nickname` varchar(255) NOT NULL DEFAULT '',
  `sex` tinyint(4) DEFAULT NULL,
  `head_img_url` varchar(255) DEFAULT NULL,
  `wechat_no` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL COMMENT '0:正常  1：注销',
  `phone` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of zs_wechat_user
-- ----------------------------
