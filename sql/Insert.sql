SET FOREIGN_KEY_CHECKS=0;

truncate t_menu;
truncate t_element;
truncate t_file;
truncate t_operation_log;
truncate t_operation;
truncate t_power;
truncate t_power_type;

insert into t_power_type (id, name)
values (1, 'operation'),
       (2, 'menu'),
       (3, 'element'),
       (4, 'file');

begin;
insert into t_power (type_id)
values (1);
insert into t_operation (name, code, power_id, parent_id)
values ('Select All Power Type', 'system:power_type:all',  last_insert_id(), null);
commit;


begin;
insert into t_power (type_id)
values (1);
insert into t_operation (name, code, power_id, parent_id)
values ('Select All Power', 'system:power:all',  last_insert_id(), null);
commit;

begin;
insert into t_power (type_id)
values (1);
insert into t_operation (name, code,  power_id, parent_id)
values ('Select All User', 'system:operation:all', last_insert_id(), null);
commit;

begin;
insert into t_power (type_id)
values (1);
insert into t_operation (name, code,  power_id, parent_id)
values ('Select All User', 'system:menu:all', last_insert_id(), null);
commit;

begin;
insert into t_power (type_id)
values (1);
insert into t_operation (name, code,  power_id, parent_id)
values ('Select All User', 'system:element:all', last_insert_id(), null);
commit;

begin;
insert into t_power (type_id)
values (1);
insert into t_operation (name, code,  power_id, parent_id)
values ('Select All User', 'system:file:all', last_insert_id(), null);
commit;

SET FOREIGN_KEY_CHECKS=1;