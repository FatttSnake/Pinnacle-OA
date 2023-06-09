drop table if exists `t_affair`;
drop table if exists `t_affair_type`;
drop table if exists `t_attendance`;
drop table if exists `t_user_work`;
drop table if exists `t_work`;
drop table if exists `t_notice_receive`;
drop table if exists `t_notice`;
drop table if exists `t_notice_type`;
drop table if exists `t_staff`;
drop table if exists `t_operation_log`;
drop table if exists `t_user_role`;
drop table if exists `t_user_group`;
drop table if exists `t_user`;
drop table if exists `t_department`;
drop table if exists `t_role_group`;
drop table if exists `t_group`;
drop table if exists `t_power_role`;
drop table if exists `t_role`;
drop table if exists `t_operation`;
drop table if exists `t_menu`;
drop table if exists `t_element`;
drop table if exists `t_file`;
drop table if exists `t_power`;
drop table if exists `t_power_type`;


create table `t_power_type`
(
    `id`   bigint      not null primary key auto_increment,
    `name` varchar(50) not null comment '权限类型名'
) comment '权限类型';

create table `t_power`
(
    `id`      bigint not null primary key auto_increment,
    `type_id` bigint not null comment '权限类型',
    constraint t_power_type_id_fk foreign key (type_id) references t_power_type (id)
) comment '权限';

create table `t_menu`
(
    `id`        bigint      not null primary key auto_increment,
    `name`      varchar(30) not null comment ' 菜单名',
    `url`       varchar(100) null comment 'URL',
    `power_id`  bigint      not null comment '权限ID',
    `parent_id` bigint null comment '父ID',
    constraint t_menu_power_id_fk foreign key (power_id) references t_power (id)
) comment '菜单';

create table `t_element`
(
    `id`       bigint       not null primary key auto_increment,
    `name`     varchar(100) not null comment '元素名',
    `power_id` bigint       not null comment '权限ID',
    `menu_id`  bigint       not null comment '菜单ID',
    constraint t_element_power_id_fk foreign key (power_id) references t_power (id),
    constraint t_element_menu_id_fk foreign key (menu_id) references t_menu (id)
) comment '页面元素';

create table `t_file`
(
    `id`       bigint       not null primary key auto_increment,
    `name`     varchar(50)  not null comment '文件名',
    `path`     varchar(100) not null comment '文件路径',
    `power_id` bigint       not null comment '权限ID',
    constraint t_file_power_id_fk foreign key (power_id) references t_power (id)
) comment '文件';

create table `t_operation`
(
    `id`         bigint      not null primary key auto_increment,
    `name`       varchar(50) not null comment '功能名',
    `code`       varchar(50) null comment '功能编码',
    `power_id`   bigint      not null comment '权限ID',
    `parent_id`  bigint null comment '父ID',
    `element_id` bigint      not null comment '页面元素ID',
    constraint t_operation_power_id_fk foreign key (power_id) references t_power (id),
    constraint t_operation_element_id_fk foreign key (element_id) references t_element (id)
) comment '功能';

create table `t_department`
(
    `id`      bigint      not null primary key,
    `name`    varchar(50) not null comment '部门名',
    `tel`     varchar(20) null comment '部门电话',
    `address` varchar(100) null comment '部门地址',
    `deleted` bigint      not null default 0,
    `version` int         not null default 0,
    constraint t_user_unique unique (name, deleted)
) comment '部门';

create table `t_user`
(
    `id`            bigint      not null primary key,
    `username`      varchar(20) not null comment '用户名',
    `passwd`        char(70)    not null comment '密码',
    `department_id` bigint null comment '部门',
    `enable`        int         not null comment '启用',
    `deleted`       bigint      not null default 0,
    `version`       int         not null default 0,
    constraint t_user_department_id_fk foreign key (department_id) references t_department (id),
    constraint t_user_unique unique (username, deleted)
) comment '用户';

create table `t_group`
(
    `id`      bigint      not null primary key,
    `name`    varchar(30) not null comment '用户组名',
    `enable`  int         not null comment '启用',
    `deleted` bigint      not null default 0,
    `version` int         not null default 0,
    constraint t_group_unique unique (name, deleted)
) comment '用户组';

create table `t_user_group`
(
    `id`       bigint not null primary key,
    `user_id`  bigint not null comment '用户',
    `group_id` bigint not null comment '用户组',
    `deleted`  bigint not null default 0,
    `version`  int    not null default 0,
    constraint t_user_group_user_id_fk foreign key (user_id) references t_user (id),
    constraint t_user_group_group_id_fk foreign key (group_id) references t_group (id)
) comment '中间表-用户-用户组';

create table `t_role`
(
    `id`      bigint      not null primary key,
    `name`    varchar(20) not null comment '角色名',
    `enable`  int         not null comment '启用',
    `deleted` bigint      not null default 0,
    `version` int         not null default 0,
    constraint t_role_unique unique (name, deleted)
) comment '角色';

create table `t_role_group`
(
    `id`       bigint not null primary key,
    `role_id`  bigint not null comment '角色',
    `group_id` bigint not null comment '群组',
    `deleted`  bigint not null default 0,
    `version`  int    not null default 0,
    constraint t_role_group_role_id_fk foreign key (role_id) references t_role (id),
    constraint t_role_group_group_id_fk foreign key (group_id) references t_group (id)
) comment '中间表-角色-用户组';

create table `t_user_role`
(
    `id`      bigint not null primary key,
    `user_id` bigint not null comment '用户',
    `role_id` bigint not null comment '角色',
    `deleted` bigint not null default 0,
    `version` int    not null default 0,
    constraint t_user_role_user_id_fk foreign key (user_id) references t_user (id),
    constraint t_user_role_role_id_fk foreign key (role_id) references t_role (id)
) comment '中间表-用户-角色';

create table `t_power_role`
(
    `id`       bigint not null primary key,
    `power_id` bigint not null comment '权限',
    `role_id`  bigint not null comment '角色',
    `deleted`  bigint not null default 0,
    `version`  int    not null default 0,
    constraint t_power_role_power_id_fk foreign key (power_id) references t_power (id),
    constraint t_power_role_role_id_fk foreign key (role_id) references t_role (id)
) comment '中间表-权限-角色';

create table `t_operation_log`
(
    `id`             bigint       not null primary key,
    `user_id`        bigint       not null comment '用户',
    `operation_id`   bigint       not null comment '功能',
    `content`        varchar(500) not null comment '操作内容',
    `operating_time` datetime     not null default (utc_timestamp()) comment '操作时间',
    `deleted`        bigint       not null default 0,
    `version`        int          not null default 0,
    constraint t_operation_log_user_id_fk foreign key (user_id) references t_user (id),
    constraint t_operation_log_operation_id_fk foreign key (operation_id) references t_operation (id)
) comment '操作日志';

create table `t_staff`
(
    `id`         bigint      not null primary key,
    `user_id`    bigint      not null comment '用户',
    `first_name` varchar(20) not null comment '名',
    `last_name`  varchar(20) not null comment '姓',
    `gender`     int         not null default 0 comment '性别',
    `birth`      date null comment '生日',
    `email`      varchar(50) null comment '邮箱',
    `tel`        varchar(20) null comment '电话',
    `address`    varchar(100) null comment '地址',
    `deleted`    bigint      not null default 0,
    `version`    int         not null default 0,
    constraint t_staff_user_id_fk foreign key (user_id) references t_user (id)
) comment '员工';

create table `t_notice_type`
(
    `id`      bigint      not null primary key,
    `name`    varchar(20) not null comment '公告类型名',
    `enable`  int         not null default 1 comment '启用',
    `deleted` bigint      not null default 0,
    `version` int         not null default 0
) comment '公告类型';

create table `t_notice`
(
    `id`          bigint      not null primary key,
    `title`       varchar(50) not null comment '公告标题',
    `content`     text        not null comment '公告内容',
    `type_id`     bigint      not null comment '公告类型',
    `sender_id`   bigint      not null comment '发布者',
    `create_time` datetime    not null default (utc_timestamp()) comment '创建时间',
    `send_time`   datetime    not null comment '发送时间',
    `end_time`    datetime    not null comment '失效时间',
    `priority`    int         not null default 1 comment '优先级',
    `top`         int         not null default 0 comment '置顶',
    `modify_time` datetime    not null default (utc_timestamp()) comment '修改时间',
    `origin_id`   bigint null comment '源ID',
    `old`         int         not null default 0 comment '已修改',
    `deleted`     bigint      not null default 0,
    `version`     int         not null default 0,
    constraint t_notice_type_id_fk foreign key (type_id) references t_notice_type (id),
    constraint t_notice_sender_id_fk foreign key (sender_id) references t_user (id)
) comment '公告';

create table `t_notice_receive`
(
    `id`           bigint not null primary key,
    `user_id`      bigint not null comment '用户',
    `notice_id`    bigint not null comment '公告',
    `already_read` int    not null default 0 comment '已读',
    `deleted`      bigint not null default 0,
    `version`      int    not null default 0,
    constraint t_notice_receive_user_id_fk foreign key (user_id) references t_user (id),
    constraint t_notice_receive_notice_if_fk foreign key (notice_id) references t_notice (id)
) comment '公告接收';

create table `t_work`
(
    `id`           bigint       not null primary key,
    `content`      varchar(100) not null comment '工作内容',
    `publisher_id` bigint       not null comment '发布者',
    `create_time`  datetime     not null default (utc_timestamp()) comment '创建时间',
    `deadline`     datetime     not null comment '截止时间',
    `modify_time`  datetime     not null default (utc_timestamp()) comment '修改时间',
    `old`          int          not null default 0 comment '已修改',
    `origin_id`    bigint null comment '源ID',
    `deleted`      bigint       not null default 0,
    `version`      int          not null default 0,
    constraint t_work_publisher_id_fk foreign key (publisher_id) references t_user (id)
) comment '工作事项';

create table `t_user_work`
(
    `id`            bigint not null primary key,
    `user_id`       bigint not null comment '用户',
    `work_id`       bigint not null comment '工作事项',
    `status`        int    not null default 0 comment '工作状态',
    `complete_time` datetime null comment '完成时间',
    `deleted`       bigint not null default 0,
    `version`       int    not null default 0,
    constraint t_user_work_user_id_fk foreign key (user_id) references t_user (id),
    constraint t_user_work_work_id_fk foreign key (work_id) references t_work (id)
) comment '中间表-用户-工作事项';

create table `t_affair_type`
(
    `id`      bigint      not null primary key,
    `name`    varchar(20) not null comment '事务类型名',
    `enable`  int         not null default 1 comment '启用',
    `deleted` bigint      not null default 0,
    `version` int         not null default 0
) comment '事务类型';

create table `t_affair`
(
    `id`           bigint      not null primary key,
    `title`        varchar(50) not null comment '事务标题',
    `content`      text        not null comment '事务内容',
    `type_id`      bigint      not null comment '事务类型',
    `status`       int         not null default 0 comment '事务状态',
    `applicant_id` bigint      not null comment '申请者',
    `inspector_id` bigint      not null comment '审核者',
    `create_time`  datetime    not null default (utc_timestamp()) comment '创建时间',
    `inspect_time` datetime null comment '审核时间',
    `priority`     int         not null default 1 comment '优先级',
    `modify_time`  datetime             default (utc_timestamp()) comment '修改时间',
    `origin_id`    bigint null comment '源ID',
    `old`          int         not null default 0 comment '已修改',
    `deleted`      bigint      not null default 0,
    `version`      int         not null default 0,
    constraint t_affair_type_id_fk foreign key (type_id) references t_affair_type (id),
    constraint t_affair_applicant_id_fk foreign key (applicant_id) references t_user (id),
    constraint t_affair_inspector_id_fk foreign key (inspector_id) references t_user (id)
) comment '事务';

create table `t_attendance`
(
    `id`          bigint   not null primary key,
    `user_id`     bigint   not null comment '用户',
    `att_time`    datetime not null default (utc_timestamp()) comment '考勤时间',
    `status`      int      not null default 0 comment '考勤状态',
    `modify_id`   bigint   not null comment '修改人',
    `modify_time` datetime not null default (utc_timestamp()) comment '修改时间',
    `deleted`     bigint   not null default 0,
    `version`     int      not null default 0,
    constraint t_attendance_user_id_fk foreign key (user_id) references t_user (id),
    constraint t_attendance_modify_id_fk foreign key (modify_id) references t_user (id)
) comment '考勤';