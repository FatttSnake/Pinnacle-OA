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

SET FOREIGN_KEY_CHECKS = 1;

select *
from t_role
         left join t_power_role tpr on t_role.id = tpr.role_id
         left join t_power tp on tp.id = tpr.power_id
         left join t_menu tm on tp.id = tm.power_id
         left join t_element te on tp.id = te.power_id
         left join t_operation t on tp.id = t.power_id;

select *
from t_group
         left join t_role_group trg on t_group.id = trg.group_id
         left join t_role tr on tr.id = trg.role_id;

update t_user
set deleted = id
where id = 1658537970212278274;


select *
from t_user
         inner join t_user_role tur on t_user.id = tur.user_id
         inner join t_role tr on tr.id = tur.role_id
         inner join t_user_group tug on t_user.id = tug.user_id
         inner join t_group tg on tg.id = tug.group_id;

select t_user.id            as user_id,
       t_user.username      as user_username,
       t_user.department_id as user_department,
       t_user.enable        as user_enable,
       t_user.deleted       as user_deleted,
       t_user.version       as user_version,
       tr.id                as role_id,
       tr.name              as role_name,
       tr.deleted           as role_deleted,
       tr.version           as role_version,
       tg.id                as group_id,
       tg.name              as group_name,
       tg.deleted           as group_deleted,
       tg.version           as group_version
from t_user
         left join (select * from t_user_role where deleted = 0) as tur on t_user.id = tur.user_id
         left join (select * from t_role where deleted = 0) as tr on tr.id = tur.role_id
         left join (select * from t_user_group where deleted = 0) as tug on t_user.id = tug.user_id
         left join (select * from t_group where deleted = 0) as tg on tg.id = tug.group_id
where t_user.deleted = 0;

select distinct t_user.id            as user_id,
                t_user.username      as user_username,
                t_user.passwd        as user_passwd,
                t_user.department_id as user_department,
                t_user.enable        as user_enable,
                t_user.deleted       as user_deleted,
                t_user.version       as user_version,
                tm.id                as menu_id,
                tm.name              as menu_name,
                tm.url               as menu_url,
                tm.power_id          as menu_powerId,
                tm.parent_id         as menu_parentId,
                te.id                as element_id,
                te.name              as element_name,
                te.power_id          as element_powerId,
                te.menu_id           as element_menuId,
                t.id                 as operation_id,
                t.name               as operation_name,
                t.code               as operation_code,
                t.power_id           as operation_powerId,
                t.element_id         as operation_elementId,
                t.parent_id          as operation_parentId
from t_user
         left join (select * from t_user_group where deleted = 0) as tug on t_user.id = tug.user_id
         left join (select * from t_group where deleted = 0) as tg on tg.id = tug.group_id
         left join (select * from t_role_group where deleted = 0) as trg on tg.id = trg.group_id
         left join (select * from t_user_role where deleted = 0) as tur on t_user.id = tur.user_id
         left join (select * from t_role where deleted = 0) as tr on tr.id = trg.role_id or tr.id = tur.role_id
         left join (select * from t_power_role where deleted = 0) as tpr on tpr.role_id = tr.id
         left join t_power as tp on tp.id = tpr.power_id
         left join t_menu tm on tp.id = tm.power_id
         left join t_element te on tp.id = te.power_id
         left join t_operation t on tp.id = t.power_id
where t_user.deleted = 0;