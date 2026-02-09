INSERT INTO chrbootdatabase.sys_user
    (nickname, username, password,user_type)
VALUES ('admin', 'admin', '$2a$10$gtZMB8L5t9Ce7kuf3liRpe1SJbAoPOXicsFgDZ3X6xPIJws/m2pGe',1),
       ('test', 'test', '$2a$10$gtZMB8L5t9Ce7kuf3liRpe1SJbAoPOXicsFgDZ3X6xPIJws/m2pGe',1),
       ('common', 'common', '$2a$10$gtZMB8L5t9Ce7kuf3liRpe1SJbAoPOXicsFgDZ3X6xPIJws/m2pGe',0);


INSERT INTO chrbootdatabase.sys_role
    (role_name, role_key, role_sort, data_scope, dept_id_set, remark)
VALUES ('超级管理员', 'admin', 1, 1, null, null),
       ('普通角色', 'common', 2, 1, null, null);


INSERT INTO chrbootdatabase.sys_menu
(menu_name, menu_type, router_name, parent_id, path, is_button, permission, meta_info, remark)
VALUES ('用户管理', 0, 'User', 0, '/system/sysUser/index', false, 'system:sysUser:list', '{}', ''),
       ('用户添加', 0, '', 1, '/system/sysUser/index', true, 'system:sysUser:add', '{}', ''),
       ('用户修改', 0, '', 1, '/system/sysUser/index', true, 'system:sysUser:edit', '{}', '');

INSERT INTO chrbootdatabase.sys_role_menu
    (role_id, menu_id)
VALUES (1, 1),
       (1, 2),
       (1, 3);
INSERT INTO chrbootdatabase.sys_role_employee
    (role_id, employee_id)
VALUES (1, 1);
