/*
 Navicat MySQL Data Transfer

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : mu_admin

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 25/05/2022 16:35:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '账户id',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `order_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `product_num` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES (2, '2022-05-25 15:18:38', '2022-05-25 15:18:36', '订单1', 23);
INSERT INTO `order_info` VALUES (3, '2022-05-25 15:31:56', '2022-05-25 15:31:56', '订单2', 0);

-- ----------------------------
-- Table structure for staff_info
-- ----------------------------
DROP TABLE IF EXISTS `staff_info`;
CREATE TABLE `staff_info`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '账户id',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `staff_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `duty` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of staff_info
-- ----------------------------
INSERT INTO `staff_info` VALUES (2, '2022-05-25 15:46:39', '2022-05-25 15:21:52', '张三', '车床工');
INSERT INTO `staff_info` VALUES (3, '2022-05-25 15:31:44', '2022-05-25 15:31:44', '张四', '操作员');
INSERT INTO `staff_info` VALUES (4, '2022-05-25 16:22:56', '2022-05-25 16:22:56', '张五', '业务员');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '账户id',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `sys_user_id` int NOT NULL,
  `log_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '账户id',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `permission_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `permission_note` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '2022-05-24 12:05:56', '2022-05-23 21:20:55', 'sys-operate', '系统操作权限');
INSERT INTO `sys_permission` VALUES (5, '2022-05-23 21:20:55', '2022-05-23 21:20:55', 'staff-select', '员工-查询');
INSERT INTO `sys_permission` VALUES (6, '2022-05-23 21:20:55', '2022-05-23 21:20:55', 'staff-insert', '员工-创建');
INSERT INTO `sys_permission` VALUES (7, '2022-05-23 21:20:55', '2022-05-23 21:20:55', 'staff-update', '员工-更新');
INSERT INTO `sys_permission` VALUES (8, '2022-05-23 21:20:55', '2022-05-23 21:20:55', 'staff-delete', '员工-删除');
INSERT INTO `sys_permission` VALUES (9, '2022-05-23 21:20:55', '2022-05-23 21:20:55', 'order-select', '订单-查询');
INSERT INTO `sys_permission` VALUES (10, '2022-05-25 11:25:33', '2022-05-23 21:20:55', 'order-insert', '订单-创建');
INSERT INTO `sys_permission` VALUES (11, '2022-05-23 21:20:55', '2022-05-23 21:20:55', 'order-update', '订单-更新');
INSERT INTO `sys_permission` VALUES (12, '2022-05-23 21:20:55', '2022-05-23 21:20:55', 'order-delete', '订单-删除');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '账户id',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `role_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_note` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (0, '2022-05-23 19:57:15', '2022-05-23 19:57:18', 'root', '最高管理员');
INSERT INTO `sys_role` VALUES (1, '2022-05-23 21:27:11', '2022-05-23 21:27:13', 'admin', '系统管理员');
INSERT INTO `sys_role` VALUES (2, '2022-05-23 21:25:03', '2022-05-23 21:25:05', 'hr', '人事');
INSERT INTO `sys_role` VALUES (3, '2022-05-25 15:35:22', '2022-05-25 15:35:22', 'business', '业务');
INSERT INTO `sys_role` VALUES (10, '2022-05-23 19:58:07', '2022-05-23 19:58:10', 'visitor', '游客');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '账户id',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `sys_role_id` int NOT NULL,
  `sys_permission_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 133 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (134, '2022-05-25 16:22:43', '2022-05-25 16:22:43', 2, 8);
INSERT INTO `sys_role_permission` VALUES (135, '2022-05-25 16:22:43', '2022-05-25 16:22:43', 2, 6);
INSERT INTO `sys_role_permission` VALUES (136, '2022-05-25 16:22:43', '2022-05-25 16:22:43', 2, 7);
INSERT INTO `sys_role_permission` VALUES (137, '2022-05-25 16:22:43', '2022-05-25 16:22:43', 2, 5);
INSERT INTO `sys_role_permission` VALUES (138, '2022-05-25 16:22:47', '2022-05-25 16:22:47', 3, 9);
INSERT INTO `sys_role_permission` VALUES (139, '2022-05-25 16:22:47', '2022-05-25 16:22:47', 3, 10);
INSERT INTO `sys_role_permission` VALUES (140, '2022-05-25 16:22:47', '2022-05-25 16:22:47', 3, 11);
INSERT INTO `sys_role_permission` VALUES (141, '2022-05-25 16:22:47', '2022-05-25 16:22:47', 3, 12);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '账户id',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `sys_role_id` int UNSIGNED NOT NULL COMMENT '用户角色',
  `username` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_user_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (0, '2022-05-25 10:35:37', '2022-05-25 10:35:37', 0, 'root', '$2a$10$6QWtaTYnN8iF7D77sQTV0.VdMtCyWAmQ5yAQohs.drK6I4aTKkqIG');
INSERT INTO `sys_user` VALUES (31, '2022-05-25 15:47:29', '2022-05-25 15:47:29', 1, 'admin', '$2a$10$5WZO7HxxFtFLSiplnIEdle.uf2m.SkzA3vhBl.5vKLe3BO1eWrGI6');
INSERT INTO `sys_user` VALUES (32, '2022-05-25 16:07:23', '2022-05-25 16:07:23', 2, 'hr123123', '$2a$10$ZJ/u30CodlkWdAUK4WMD6uByBSE5LLMTT/Rsp/hIcDJmtFYOwtfXq');
INSERT INTO `sys_user` VALUES (33, '2022-05-25 16:15:04', '2022-05-25 16:15:04', 3, 'bu123123', '$2a$10$vGW6T/lhm.eV5vC01wo3N.gLG3s65.K0zrPILfS2Sa4l4EDs5pFi6');

SET FOREIGN_KEY_CHECKS = 1;
