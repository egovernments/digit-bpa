delete from eg_roleaction where actionid in (select id from eg_action where name in ('updateBpaApplication','new appointment schedule','reschedule appointment','view scheduled appointment','capturedocumentscrutinydetails','createinspectiondetails','viewInspection')) and roleid in (select id from eg_role where name in ('Collection Operator','ULB Operator'));

insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='updateBpaApplication'));

insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),(select id from eg_action where name='capturedocumentscrutinydetails'));

insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),(select id from eg_action where name='viewInspection'));

insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),(select id from eg_action where name='createinspectiondetails'));

insert into eg_roleaction(roleid, actionid) values ((select id from eg_role where name = 'BPA Approver'), (select id from eg_action where name = 'AjaxDesignationsByDepartmentWithDesignation')); 

insert into eg_roleaction(roleid, actionid) values ((select id from eg_role where name = 'BPA Approver'), (select id from eg_action where name = 'AjaxApproverByDesignationAndDepartment'));