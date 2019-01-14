Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='Personal Register report'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),(select id from eg_action where name='Personal Register report'));

