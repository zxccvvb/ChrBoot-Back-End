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