SET FOREIGN_KEY_CHECKS = 0;

truncate t_menu;
truncate t_element;
truncate t_file;
truncate t_operation_log;
truncate t_operation;
truncate t_power;
truncate t_power_type;
truncate t_role;
truncate t_power_role;

insert into t_power_type (id, name)
values (1, 'menu'),
       (2, 'element'),
       (3, 'operation');

begin;
insert into t_power (id, type_id)
values (1010000, 1);
insert into t_menu (id, name, url, power_id, parent_id)
VALUES (1010000, '公用', '/', id, null);
commit;

begin;
insert into t_power (id,type_id)
values (1010100, 2);
insert into t_element(id, name, power_id, menu_id)
VALUES (1010100, '公用', id, 1010000);
commit;

begin;
insert into t_power(id,type_id)
values (1010101, 3);
insert into t_operation(id,name, code, power_id, element_id, parent_id)
VALUES (1010101, '查询当前用户信息', 'common:user:self', id, 1010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (2010000, 1);
insert into t_menu (id, name, url, power_id, parent_id)
VALUES (2010000, '工作事项', '/work/task', id, null);
commit;

begin;
insert into t_power(id, type_id)
VALUES (2010100, 2);
insert into t_element(id, name, power_id, menu_id)
VALUES (2010100, '列表', id, 2010000);
commit;

begin;
insert into t_power(id,type_id)
values (2010101,3);
insert into t_operation(id,name, code, power_id, element_id, parent_id)
VALUES (2010101, '获取首页待办工作', 'work:self:home', id, 2010100, null);
commit;

begin;
insert into t_power(id,type_id)
values (2010102,3);
insert into t_operation(id,name, code, power_id, element_id, parent_id)
VALUES (2010102, '获取个人工作', 'work:self:get', id, 2010100, null);
commit;

begin;
insert into t_power(id,type_id)
values (2010103,3);
insert into t_operation(id,name, code, power_id, element_id, parent_id)
VALUES (2010103, '获取工作详细内容', 'work:self:detail', id, 2010100, null);
commit;

begin;
insert into t_power(id,type_id)
values (2010104,3);
insert into t_operation(id,name, code, power_id, element_id, parent_id)
VALUES (2010104, '更新工作状态', 'work:self:status', id, 2010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (2020000, 1);
insert into t_menu (id, name, url, power_id, parent_id)
VALUES (2020000, '工作管理', '/work/manage', id, null);
commit;

begin;
insert into t_power(id, type_id)
VALUES (2020100, 2);
insert into t_element(id, name, power_id, menu_id)
VALUES (2020100, '列表', id, 2020000);
commit;

begin;
insert into t_power(id, type_id)
values (2020101, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (2020101, '获取发布的工作事项', 'work:manage:get', id, 2020100, null);
commit;

begin;
insert into t_power(id, type_id)
values (2020102, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (2020102, '发布工作事项', 'work:manage:add', id, 2020100, null);
commit;

begin;
insert into t_power(id, type_id)
values (2020103, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (2020103, '删除发布的工作事项', 'work:manage:delete', id, 2020100, null);
commit;

begin;
insert into t_power(id, type_id)
values (2020104, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (2020104, '修改发布的工作内容', 'work:manage:modify', id, 2020100, null);
commit;

begin;
insert into t_power(id, type_id)
values (2020105, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (2020105, '获取所有工作事项（管理）', 'work:admin:get', id, 2020100, null);
commit;

begin;
insert into t_power(id, type_id)
values (2020106, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (2020106, '添加工作事项（管理）', 'work:admin:add', id, 2020100, null);
commit;

begin;
insert into t_power(id, type_id)
values (2020107, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (2020107, '删除工作事项（管理）', 'work:admin:delete', id, 2020100, null);
commit;

begin;
insert into t_power(id, type_id)
values (2020108, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (2020108, '修改工作事项（管理）', 'work:admin:modify', id, 2020100, null);
commit;



begin;
insert into t_power (id, type_id)
values (101010000, 1);
insert into t_menu (id, name, url, power_id, parent_id)
VALUES (101010000, '角色管理（权限相关）', '/power/role', id, null);
commit;

begin;
insert into t_power (id, type_id)
values (101010100, 2);
insert into t_element (id, name, power_id, menu_id)
VALUES (101010100, '列表', id, 101010000);
commit;

begin;
insert into t_power (id, type_id)
values (101010101, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (101010101, '查询所有角色', 'system:role:all', id, 101010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (101010102, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (101010102, '添加角色', 'system:role:add', id, 101010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (101010103, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (101010103, '删除角色', 'system:role:delete', id, 101010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (101010104, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (101010104, '修改角色', 'system:role:modify', id, 101010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (102010000, 1);
insert into t_menu (id, name, url, power_id, parent_id)
VALUES (102010000, '用户组管理（权限相关）', '/power/group', id, null);
commit;

begin;
insert into t_power (id, type_id)
values (102010100, 2);
insert into t_element (id, name, power_id, menu_id)
VALUES (102010100, '列表', id, 102010000);
commit;

begin;
insert into t_power (id, type_id)
values (102010101, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (102010101, '查询所有用户组', 'system:group:all', id, 102010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (102010102, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (102010102, '添加用户组', 'system:group:add', id, 102010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (102010103, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (102010103, '删除用户组', 'system:group:delete', id, 102010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (102010104, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (102010104, '修改用户组', 'system:group:modify', id, 102010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (103010000, 1);
insert into t_menu (id, name, url, power_id, parent_id)
VALUES (103010000, '用户管理（权限相关）', '/power/user', id, null);
commit;

begin;
insert into t_power (id, type_id)
values (103010100, 2);
insert into t_element (id, name, power_id, menu_id)
VALUES (103010100, '列表', id, 103010000);
commit;

begin;
insert into t_power (id, type_id)
values (103010101, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (103010101, '查看所有用户', 'system:user:all', id, 103010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (103010102, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (103010102, '查看单个用户', 'system:user:one', id, 103010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (103010103, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (103010103, '添加用户', 'system:user:add', id, 103010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (103010104, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (103010104, '删除用户', 'system:user:delete', id, 103010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (103010105, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (103010105, '修改用户', 'system:user:modify', id, 103010100, null);
commit;

SET FOREIGN_KEY_CHECKS = 1;