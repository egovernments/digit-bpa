insert into eg_roleaction values((select id from eg_role where name='Bill Creator'),(select id from eg_action where name='AjaxDesignationsForActiveAssignmentsByDepartment'));
insert into eg_roleaction values((select id from eg_role where name='Bill Approver'),(select id from eg_action where name='AjaxDesignationsForActiveAssignmentsByDepartment'));

insert into eg_roleaction values((select id from eg_role where name='Bill Creator'),(select id from eg_action where name='AjaxDesignationsForActiveAssignmentsByObjectTypeAndDesignation'));
insert into eg_roleaction values((select id from eg_role where name='Bill Approver'),(select id from eg_action where name='AjaxDesignationsForActiveAssignmentsByObjectTypeAndDesignation'));