-- 用户表
CREATE TABLE `chrbootdatabase`.`chr_user` (
  `id`          bigint NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '用户id',
  `nickname`    VARCHAR(32) NOT NULL UNIQUE COMMENT '昵称',
  `username`    VARCHAR(32) NOT NULL UNIQUE COMMENT '用户名',
  `password`    VARCHAR(64) NOT NULL COMMENT '用户密码',
  `status`      int NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:启用',
  `is_deleted`  BOOLEAN NOT NULL DEFAULT 0 COMMENT '逻辑删除字段',
#   `version`    bigint NOT NULL DEFAULT 0 COMMENT '乐观锁字段',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `update_user` bigint DEFAULT NULL COMMENT '修改人'
)COMMENT='用户表';

-- 字典类型表
CREATE TABLE chr_dict_type (
   `id`          bigint NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '字典类型id',
   `name`        VARCHAR(32) NOT NULL UNIQUE COMMENT '类型名称',
   `description` VARCHAR(128) NOT NULL COMMENT '中文说明',
   `is_deleted`  BOOLEAN NOT NULL DEFAULT 0 COMMENT '逻辑删除字段',
   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
   `update_time` datetime DEFAULT NULL COMMENT '更新时间',
   `create_user` bigint DEFAULT NULL COMMENT '创建人',
   `update_user` bigint DEFAULT NULL COMMENT '修改人'
) COMMENT='字典类型';

-- 字典值表
CREATE TABLE chr_dict_item (
   `id`          bigint NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '字典值id',
   `type_id`     bigint NOT NULL COMMENT '对应 chr_dict_type.id',
   `value`       int NOT NULL COMMENT '值编码',
   `label`       VARCHAR(64) NOT NULL COMMENT '显示文本',
   `sort`        int NOT NULL DEFAULT 0,
   `is_deleted`  BOOLEAN NOT NULL DEFAULT 0 COMMENT '逻辑删除字段',
   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
   `update_time` datetime DEFAULT NULL COMMENT '更新时间',
   `create_user` bigint DEFAULT NULL COMMENT '创建人',
   `update_user` bigint DEFAULT NULL COMMENT '修改人'
) COMMENT='字典值';