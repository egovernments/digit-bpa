
Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'generate renewal rejection notice','/application/permitrenewal/rejectionnotice',null,(select id from eg_module where name='BPA Transanctions'),2,
'generate renewal rejection notice','false','bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='generate renewal rejection notice'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='generate renewal rejection notice'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),
(select id from eg_action where name='generate renewal rejection notice'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),
(select id from eg_action where name='generate renewal rejection notice'));


insert into eg_wf_matrix(id, department, objecttype, currentstate, nextstate, currentstatus, pendingactions, 
currentdesignation, additionalrule, nextaction, nextdesignation, nextstatus, validactions, fromdate, todate, version)
 values (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'Rejected', 'generate rejection notice', '', '', '', 
 'High Risk', 'Application is rejected by approver', 'Assistant Engineer', 'Rejected', 'Generate Rejection Notice', '2019-01-01', '2099-04-01',0);


insert into eg_wf_matrix(id, department, objecttype, currentstate, nextstate, currentstatus, pendingactions, 
currentdesignation, additionalrule, nextaction, nextdesignation, nextstatus, validactions, fromdate, todate, version)
 values (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'Rejected', 'generate rejection notice', '', '', '', 
 'Low Risk', 'Application is rejected by approver', 'Assistant Engineer', 'Rejected', 'Generate Rejection Notice', '2019-01-01', '2099-04-01',0);

insert into eg_wf_matrix(id, department, objecttype, currentstate, nextstate, currentstatus, pendingactions, 
currentdesignation, additionalrule, nextaction, nextdesignation, nextstatus, validactions, fromdate, todate, version)
 values (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'Rejected', 'generate rejection notice', '', '', '', 
 'Medium Risk', 'Application is rejected by approver', 'Assistant Engineer', 'Rejected', 'Generate Rejection Notice', '2019-01-01', '2099-04-01',0);