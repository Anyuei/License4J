/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80034
 Source Host           : localhost:3306
 Source Schema         : stmf_license

 Target Server Type    : MySQL
 Target Server Version : 80034
 File Encoding         : 65001

 Date: 11/08/2023 15:01:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for stmf_license
-- ----------------------------
DROP TABLE IF EXISTS `stmf_license`;
CREATE TABLE `stmf_license`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `system_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '被授权系统名称',
  `license` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '许可证书内容',
  `license_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '许可证书名称',
  `pri_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '私钥',
  `pub_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '公钥',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '有效期开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '有效期结束时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '证书创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '证书修改人',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
