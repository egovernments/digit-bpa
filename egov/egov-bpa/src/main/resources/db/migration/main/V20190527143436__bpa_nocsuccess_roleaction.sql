
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'),
(select id from eg_action where name='Noc application success'));