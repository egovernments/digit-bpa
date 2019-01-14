

 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, 
pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'), 
 'ANY', 'BpaApplication', 'Application Approval Pending', NULL, NULL, 'Assistant engineer', 
 'CREATEBPAAPPLICATION', 'Application Approval Pending', 'Approval Pending', 
 'Assistant executive engineer', 
 NULL, 'Approve,Forward', NULL, NULL, now(), '2099-04-01');


 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, 
pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'), 
 'ANY', 'BpaApplication', 'Application Approval Pending', NULL, NULL, 'Assistant executive engineer', 
 'CREATEBPAAPPLICATION', 'Application Approval Pending', 'Approval Pending', 
 'Executive engineer', 
 NULL, 'Approve,Forward', NULL, NULL, now(), '2099-04-01');


  INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, 
pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'), 
 'ANY', 'BpaApplication', 'Application Approval Pending', NULL, NULL, 'Executive engineer', 
 'CREATEBPAAPPLICATION', 'Application Approval Pending', 'Approval Pending', 
 'Commissioner', 
 NULL, 'Approve,Forward', NULL, NULL, now(), '2099-04-01');

   INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, 
pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'), 
 'ANY', 'BpaApplication', 'Application Approval Pending', NULL, NULL, 'Commissioner', 
 'CREATEBPAAPPLICATION', 'Application Approval Pending', 'Approval Pending', 
 'Secretary', 
 NULL, 'Approve,Forward', NULL, NULL, now(), '2099-04-01');


    INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, 
pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'), 
 'ANY', 'BpaApplication', 'Application Approval Pending', NULL, NULL, 'Secretary', 
 'CREATEBPAAPPLICATION', 'Record Approved', 'Digital Sign Pending', 
 'Superintendent of Approval', 
 NULL, 'Approve', NULL, NULL, now(), '2099-04-01');






update eg_wf_matrix set nextstate='Application Approval Pending'
where currentstate='NOC updation in progress'  and objecttype='BpaApplication'; 

update eg_wf_matrix set nextstate='Application Approval Pending'
where currentstate='Noc Details Updated'  and objecttype='BpaApplication'; 


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'getDesignationByDeptAndAreaInSqmtr','/bpaajaxWorkFlow-getDesignationsByObjectTypeAndDesignation',
null,(select id from eg_module where name='BPA Transanctions'),1,'getDesignationByDeptAndAreaInSqmtr','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));


Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='getDesignationByDeptAndAreaInSqmtr'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='getDesignationByDeptAndAreaInSqmtr'));
