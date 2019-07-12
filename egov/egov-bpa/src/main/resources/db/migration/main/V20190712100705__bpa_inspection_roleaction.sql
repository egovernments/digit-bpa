
Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'SearchInspection','/inspection/search',null,(select id from eg_module where name='BPA Transanctions'),16,'Search Inspection','true','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='SearchInspection'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='SearchInspection'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='SearchInspection'));




Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'CaptureInspection','/inspection/captureinspection',null,(select id from eg_module where name='BPA Transanctions'),16,'CaptureInspection','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='CaptureInspection'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='CaptureInspection'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='CaptureInspection'));



Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Result InconstructionInspection','/inspection/result/view-inspection-details/',null,(select id from eg_module where name='BPA Transanctions'),16,'Result InconstructionInspection','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='Result InconstructionInspection'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='Result InconstructionInspection'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Result InconstructionInspection'));


Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='PermitApplicationWFStatus'));