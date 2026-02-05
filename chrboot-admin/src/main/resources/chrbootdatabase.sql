-- 员工表
CREATE TABLE `chrbootdatabase`.`chr_employee` (
                                                  `id`          BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '员工id',
                                                  `nickname`    VARCHAR(32) NOT NULL UNIQUE COMMENT '昵称',
                                                  `username`    VARCHAR(32) NOT NULL UNIQUE COMMENT '员工名称',
                                                  `password`    VARCHAR(128) NOT NULL COMMENT '员工密码',
                                                  `status`      INT NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:启用',
                                                  `is_deleted`  BOOLEAN NOT NULL DEFAULT 0 COMMENT '逻辑删除字段',
                                                  #   `version`    bigint NOT NULL DEFAULT 0 COMMENT '乐观锁字段',
                                                  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
                                                  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
                                                  `create_user` BIGINT DEFAULT NULL COMMENT '创建人',
                                                  `update_user` BIGINT DEFAULT NULL COMMENT '修改人'
)COMMENT='用户表';


-- 用户表
CREATE TABLE `chrbootdatabase`.`chr_user` (
                                              `id`          BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '用户id',
                                              `nickname`    VARCHAR(32) NOT NULL UNIQUE COMMENT '昵称',
                                              `username`    VARCHAR(32) NOT NULL UNIQUE COMMENT '用户名',
                                              `password`    VARCHAR(128) NOT NULL COMMENT '用户密码',
                                              `status`      INT NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:启用',
                                              `is_deleted`  BOOLEAN NOT NULL DEFAULT 0 COMMENT '逻辑删除字段',
                                              #   `version`    bigint NOT NULL DEFAULT 0 COMMENT '乐观锁字段',
                                              `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
                                              `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
                                              `create_user` BIGINT DEFAULT NULL COMMENT '创建人',
                                              `update_user` BIGINT DEFAULT NULL COMMENT '修改人'
)COMMENT='用户表';

INSERT INTO chrbootdatabase.chr_employee
(nickname, username, PASSWORD)
VALUES ('admin', 'admin', '{bcrypt}$2a$10$gtZMB8L5t9Ce7kuf3liRpe1SJbAoPOXicsFgDZ3X6xPIJws/m2pGe');

INSERT INTO chrbootdatabase.chr_user
(nickname, username, PASSWORD)
VALUES ('admin', 'admin', '{bcrypt}$2a$10$gtZMB8L5t9Ce7kuf3liRpe1SJbAoPOXicsFgDZ3X6xPIJws/m2pGe');


