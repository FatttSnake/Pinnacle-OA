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
insert into t_power (type_id)
values (1);
insert into t_menu (id, name, url, power_id, parent_id)
VALUES (1, '公用', '/', last_insert_id(), null);
commit;

begin;
insert into t_power (type_id)
values (2);
insert into t_element(id, name, power_id, menu_id)
VALUES (1, '公用', last_insert_id(), 1);
commit;

begin;
insert into t_power(type_id)
values (3);
insert into t_operation(name, code, power_id, element_id, parent_id)
VALUES ('查询当前用户权限', 'common:power:self', last_insert_id(), 1, null);
commit;

begin;
insert into t_power(type_id)
values (3);
insert into t_operation(name, code, power_id, element_id, parent_id)
VALUES ('查询当前用户信息', 'common:info:self', last_insert_id(), 1, null);
commit;

begin;
insert into t_power (type_id)
values (1);
insert into t_menu (id, name, url, power_id, parent_id)
VALUES (2, '角色管理', '/system/role', last_insert_id(), null);
commit;

begin;
insert into t_power (type_id)
values (2);
insert into t_element (id, name, power_id, menu_id)
VALUES (2, '角色列表', last_insert_id(), 2);
commit;

begin;
insert into t_power (type_id)
values (3);
insert into t_operation (name, code, power_id, element_id, parent_id)
VALUES ('查询所有权限', 'system:power:all', last_insert_id(), 2, null);
commit;

begin;
insert into t_power (type_id)
values (3);
insert into t_operation (name, code, power_id, element_id, parent_id)
VALUES ('查询所有角色', 'system:role:all', last_insert_id(), 2, null);
commit;

begin;
insert into t_power (type_id)
values (3);
insert into t_operation (name, code, power_id, element_id, parent_id)
VALUES ('查询所有用户', 'system:user:all', last_insert_id(), 2, null);
commit;

insert into t_role (id, name)
values (1655784840189972481, '员工'),
       (1655784928056467457, '组长'),
       (1655785102375940097, '主管'),
       (1655785102375940098, '总管');

insert into t_power_role(id, power_id, role_id)
VALUES (1656219345971326978, 1, 1655784840189972481),
       (1656219345971326979, 2, 1655784840189972481),
       (1656219345971326980, 3, 1655784840189972481),
       (1656219345971326981, 4, 1655784840189972481),
       (1656219345971326982, 5, 1655785102375940098),
       (1656219345971326983, 6, 1655785102375940098),
       (1656219345971326984, 7, 1655785102375940098),
       (1656219345971326985, 8, 1655785102375940098),
       (1656219345971326986, 9, 1655785102375940098);

select * from t_role
left join t_power_role tpr on t_role.id = tpr.role_id
left join t_power tp on tp.id = tpr.power_id
left join t_menu tm on tp.id = tm.power_id
left join t_element te on tp.id = te.power_id
left join t_operation t on tp.id = t.power_id;

SET FOREIGN_KEY_CHECKS = 1;