-- 用户表
CREATE TABLE `chrbootdatabase`.`sys_user`
(
    user_id     BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '用户id',
    role_id     BIGINT                DEFAULT NULL COMMENT '角色id',
    nickname    VARCHAR(32)  NOT NULL UNIQUE COMMENT '昵称',
    username    VARCHAR(32)  NOT NULL UNIQUE COMMENT '用户名',
    password    VARCHAR(128) NOT NULL COMMENT '用户密码',
    user_type   TINYINT(1)    NOT NULL DEFAULT 0 COMMENT '0:普通用户 1:管理员',

    status      TINYINT(1)   NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:启用',
    is_deleted  BOOLEAN      NOT NULL DEFAULT 0 COMMENT '逻辑删除字段',
    #   `version    bigint NOT NULL DEFAULT 0 COMMENT '乐观锁字段',
    create_time DATETIME              DEFAULT NULL COMMENT '创建时间',
    update_time DATETIME              DEFAULT NULL COMMENT '更新时间',
    create_user BIGINT                DEFAULT NULL COMMENT '创建人',
    update_user BIGINT                DEFAULT NULL COMMENT '修改人'
) COMMENT ='用户表';

-- 管理端菜单表
CREATE TABLE `chrbootdatabase`.`sys_menu`
(
    menu_id     BIGINT                     NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '管理端菜单ID',
    menu_name   VARCHAR(64)                NOT NULL COMMENT '菜单名称',
    menu_type   TINYINT(1)    DEFAULT 0    NOT NULL COMMENT '菜单的类型 0:普通菜单 1:目录 2:内嵌iFrame 3:外链跳转)',
    router_name VARCHAR(255)  DEFAULT ''   NOT NULL COMMENT '路由名称（需保持和前端对应的vue文件中的name保持一致defineOptions方法中设置的name）',
    parent_id   BIGINT        DEFAULT 0    NOT NULL COMMENT '父菜单ID',
    path        VARCHAR(255)  DEFAULT NULL COMMENT '组件路径（对应前端项目view文件夹中的路径）',
    is_button   BOOLEAN       DEFAULT 0    NOT NULL COMMENT '是否按钮',
    permission  VARCHAR(128)  DEFAULT NULL COMMENT '权限标识',
    meta_info   VARCHAR(1024) DEFAULT '{}' NOT NULL COMMENT '路由元信息（前端根据这个信息进行逻辑处理）',
    remark      VARCHAR(256)  DEFAULT NULL COMMENT '备注',

    status      TINYINT(1)                 NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:启用',
    is_deleted  BOOLEAN                    NOT NULL DEFAULT 0 COMMENT '逻辑删除字段',
    #   version    bigint NOT NULL DEFAULT 0 COMMENT '乐观锁字段',
    create_time DATETIME      DEFAULT NULL COMMENT '创建时间',
    update_time DATETIME      DEFAULT NULL COMMENT '更新时间',
    create_user BIGINT        DEFAULT NULL COMMENT '创建人',
    update_user BIGINT        DEFAULT NULL COMMENT '修改人'
);

-- 管理端角色表
CREATE TABLE `chrbootdatabase`.`sys_role`
(
    role_id     BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '管理端角色ID',
    role_name   VARCHAR(32)  NOT NULL COMMENT '角色名称',
    role_key    VARCHAR(128) NOT NULL COMMENT '角色权限字符串',
    role_sort   INT          NOT NULL COMMENT '显示顺序',
    data_scope  TINYINT(1)            DEFAULT 1 COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3: 本部门数据权限 4: 本部门及以下数据权限 5: 本人权限）',
    dept_id_set VARCHAR(1024)         DEFAULT '' COMMENT '角色所拥有的部门数据权限',
    remark      VARCHAR(512) NULL comment '备注',

    status      TINYINT(1)   NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:启用',
    is_deleted  BOOLEAN      NOT NULL DEFAULT 0 COMMENT '逻辑删除字段',
    #   `version    bigint NOT NULL DEFAULT 0 COMMENT '乐观锁字段',
    create_time DATETIME              DEFAULT NULL COMMENT '创建时间',
    update_time DATETIME              DEFAULT NULL COMMENT '更新时间',
    create_user BIGINT                DEFAULT NULL COMMENT '创建人',
    update_user BIGINT                DEFAULT NULL COMMENT '修改人'
);

-- 管理端角色菜单表
CREATE TABLE `chrbootdatabase`.`sys_role_menu`
(
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id) -- 设置复合主键
);

-- 管理端角色用户表
CREATE TABLE `chrbootdatabase`.`sys_role_user`
(
    role_id     BIGINT NOT NULL COMMENT '角色ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    PRIMARY KEY (role_id, user_id) -- 设置复合主键
);
