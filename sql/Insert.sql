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
values (2020151, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (2020151, '获取所有工作事项（管理）', 'work:admin:get', id, 2020100, null);
commit;

begin;
insert into t_power(id, type_id)
values (2020152, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (2020152, '添加工作事项（管理）', 'work:admin:add', id, 2020100, null);
commit;

begin;
insert into t_power(id, type_id)
values (2020153, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (2020153, '删除工作事项（管理）', 'work:admin:delete', id, 2020100, null);
commit;

begin;
insert into t_power(id, type_id)
values (2020154, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (2020154, '修改工作事项（管理）', 'work:admin:modify', id, 2020100, null);
commit;

begin;
insert into t_power (id, type_id)
values (3010000, 1);
insert into t_menu (id, name, url, power_id, parent_id)
VALUES (3010000, '公告查看', '/notice/view', id, null);
commit;

begin;
insert into t_power(id, type_id)
VALUES (3010100, 2);
insert into t_element(id, name, power_id, menu_id)
VALUES (3010100, '列表', id, 3010000);
commit;

begin;
insert into t_power(id, type_id)
values (3010101, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (3010101, '获取接收的公告', 'notice:self:get', id, 3010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (3020000, 1);
insert into t_menu (id, name, url, power_id, parent_id)
VALUES (3020000, '公告管理', '/notice/manage', id, null);
commit;

begin;
insert into t_power(id, type_id)
VALUES (3020100, 2);
insert into t_element(id, name, power_id, menu_id)
VALUES (3020100, '列表', id, 3020000);
commit;

begin;
insert into t_power(id, type_id)
values (3020101, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (3020101, '获取所有公告', 'notice:manage:get', id, 3020100, null);
commit;

begin;
insert into t_power(id, type_id)
values (3020102, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (3020102, '发布公告', 'notice:manage:add', id, 3020100, null);
commit;

begin;
insert into t_power(id, type_id)
values (3020103, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (3020103, '删除公告', 'notice:manage:delete', id, 3020100, null);
commit;

begin;
insert into t_power(id, type_id)
values (3020104, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (3020104, '修改公告', 'notice:manage:modify', id, 3020100, null);
commit;

begin;
insert into t_power (id, type_id)
values (3030000, 1);
insert into t_menu (id, name, url, power_id, parent_id)
VALUES (3030000, '公告类型管理', '/notice/typeManage', id, null);
commit;

begin;
insert into t_power(id, type_id)
VALUES (3030100, 2);
insert into t_element(id, name, power_id, menu_id)
VALUES (3030100, '列表', id, 3030000);
commit;

begin;
insert into t_power(id, type_id)
values (3030101, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (3030101, '获取已启用的公告类型', 'notice:type:enable', id, 3030100, null);
commit;

begin;
insert into t_power(id, type_id)
values (3030102, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (3030102, '获取所有公告类型', 'notice:type:get', id, 3030100, null);
commit;

begin;
insert into t_power(id, type_id)
values (3030103, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (3030103, '添加公告类型', 'notice:type:add', id, 3030100, null);
commit;

begin;
insert into t_power(id, type_id)
values (3030104, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (3030104, '修改公告类型', 'notice:type:modify', id, 3030100, null);
commit;

begin;
insert into t_power(id, type_id)
values (3030105, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (3030105, '删除公告类型', 'notice:type:delete', id, 3030100, null);
commit;

begin;
insert into t_power (id, type_id)
values (4010000, 1);
insert into t_menu (id, name, url, power_id, parent_id)
VALUES (4010000, '员工考勤', '/attendance/user', id, null);
commit;

begin;
insert into t_power(id, type_id)
VALUES (4010100, 2);
insert into t_element(id, name, power_id, menu_id)
VALUES (4010100, '列表', id, 4010000);
commit;

begin;
insert into t_power(id, type_id)
values (4010101, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (4010101, '个人签到', 'attendance:self:check', id, 4010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (4020000, 1);
insert into t_menu (id, name, url, power_id, parent_id)
VALUES (4020000, '考勤管理', '/attendance/manage', id, null);
commit;

begin;
insert into t_power(id, type_id)
VALUES (4020100, 2);
insert into t_element(id, name, power_id, menu_id)
VALUES (4020100, '列表', id, 4020000);
commit;

begin;
insert into t_power(id, type_id)
values (4020101, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (4020101, '获取管理考勤', 'attendance:manage:get', id, 4020100, null);
commit;

begin;
insert into t_power(id, type_id)
values (4020102, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (4020102, '删除管理考勤', 'attendance:manage:delete', id, 4020100, null);
commit;

begin;
insert into t_power(id, type_id)
values (4020103, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (4020103, '修改管理考勤', 'attendance:manage:modify', id, 4020100, null);
commit;

begin;
insert into t_power (id, type_id)
values (5010000, 1);
insert into t_menu (id, name, url, power_id, parent_id)
VALUES (5010000, '我的事务', '/affair/personal', id, null);
commit;

begin;
insert into t_power(id, type_id)
VALUES (5010100, 2);
insert into t_element(id, name, power_id, menu_id)
VALUES (5010100, '列表', id, 5010000);
commit;

begin;
insert into t_power(id, type_id)
values (5010101, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (5010101, '获取个人事务', 'affair:self:get', id, 5010100, null);
commit;

begin;
insert into t_power(id, type_id)
values (5010102, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (5010102, '创建事务', 'affair:self:add', id, 5010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (5020000, 1);
insert into t_menu (id, name, url, power_id, parent_id)
VALUES (5020000, '事务管理', '/affair/manage', id, null);
commit;

begin;
insert into t_power(id, type_id)
VALUES (5020100, 2);
insert into t_element(id, name, power_id, menu_id)
VALUES (5020100, '列表', id, 5020000);
commit;

begin;
insert into t_power(id, type_id)
values (5020101, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (5020101, '获取审批事务', 'affair:manage:get', id, 5020100, null);
commit;

begin;
insert into t_power(id, type_id)
values (5020102, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (5020102, '删除审批事务', 'affair:manage:delete', id, 5020100, null);
commit;

begin;
insert into t_power(id, type_id)
values (5020103, 3);
insert into t_operation(id, name, code, power_id, element_id, parent_id)
VALUES (5020103, '修改审批事务', 'affair:manage:modify', id, 5020100, null);
commit;



begin;
insert into t_power (id, type_id)
values (98010000, 1);
insert into t_menu (id, name, url, power_id, parent_id)
VALUES (98010000, '部门管理', '/info/department', id, null);
commit;

begin;
insert into t_power (id, type_id)
values (98010100, 2);
insert into t_element (id, name, power_id, menu_id)
VALUES (98010100, '列表', id, 98010000);
commit;

begin;
insert into t_power (id, type_id)
values (98010101, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (98010101, '查看所有部门', 'department:admin:get', id, 98010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (98010102, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (98010102, '增加部门', 'department:admin:add', id, 98010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (98010103, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (98010103, '删除部门', 'department:admin:delete', id, 98010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (98010104, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (98010104, '修改部门', 'department:admin:modify', id, 98010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (99010000, 1);
insert into t_menu (id, name, url, power_id, parent_id)
VALUES (99010000, '员工信息管理', '/info/staff', id, null);
commit;

begin;
insert into t_power (id, type_id)
values (99010100, 2);
insert into t_element (id, name, power_id, menu_id)
VALUES (99010100, '列表', id, 99010000);
commit;

begin;
insert into t_power (id, type_id)
values (99010101, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (99010101, '查看部门员工信息', 'staff:manege:get', id, 99010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (99010102, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (99010102, '修改部门员工信息', 'staff:manege:modify', id, 99010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (99010151, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (99010151, '修改员工信息（管理）', 'staff:admin:get', id, 99010100, null);
commit;

begin;
insert into t_power (id, type_id)
values (99010152, 3);
insert into t_operation (id, name, code, power_id, element_id, parent_id)
VALUES (99010152, '修改员工信息（管理）', 'staff:admin:modify', id, 99010100, null);
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
VALUES (101010101, '查询所有角色', 'system:role:get', id, 101010100, null);
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
VALUES (102010101, '查询所有用户组', 'system:group:get', id, 102010100, null);
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
VALUES (103010101, '查看所有用户', 'system:user:get', id, 103010100, null);
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