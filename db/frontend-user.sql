/* 前台用户所属的office/role，预先导入，永久存在, id=100 */
INSERT INTO sys_office (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `area_id`, `type`, `grade`, `USEABLE`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`)
  VALUES ('100', '0', '0,', '前台用户', '30', '20', '4', '1', '1', '1', '2015-04-04 00:00:00', '1', '2015-04-04 00:00:00', '前台用户所属的office，预先导入，永久存在', '0');
INSERT INTO sys_role (id, office_id, name, enname, role_type, data_scope, is_sys, useable, create_by, create_date, update_by, update_date, remarks, del_flag)
  VALUES ('100', '100', '前台用户', 'frontend_user', 'user', '9', '1', '1', '0', '2015-06-18 00:41:04', '0', '2015-06-18 00:41:04', '前台用户的角色，永久存在', '0');
INSERT INTO sys_role_office (role_id, office_id) VALUES ('100', '100');


/* 前台用户注册时, 添加记录类似如下 */
INSERT INTO sys_user (`id`, `company_id`, `office_id`, `login_name`, `password`, `name`, `mobile`, `login_date`, `create_date`, `update_date`, `remarks`, `del_flag`)
  VALUES ('?', '100', '100', '15889639895', 'cb0d7128f90361f38b2b20920167f7c1be90ca1e68e966ba90a7d08e', '15889639895', '15889639895', '2015-04-04 00:00:00', '2015-04-04 00:00:00', '2015-04-04 00:00:00', '前台用户', '0');
INSERT INTO sys_user_role (user_id, role_id) VALUES ("?", "100");
