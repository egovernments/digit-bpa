insert into eg_wf_matrix(id, department, objecttype, currentstate, nextstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextaction, nextdesignation, nextstatus, validactions, fromdate, todate, version) values (nextval('seq_eg_wf_matrix'), 'ANY', 'InspectionApplication', 'LP Initiated', 'LP Created', '', '', '', 'INSPECTIONAPPLICATION', 'Letter To Party Reply Pending', 'Section Clerk', 'Letter To Party Created', 'Forward', '2019-01-01', '2099-04-01',0);
insert into eg_wf_matrix(id, department, objecttype, currentstate, nextstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextaction, nextdesignation, nextstatus, validactions, fromdate, todate, version) values (nextval('seq_eg_wf_matrix'), 'ANY', 'InspectionApplication', 'LP Created', 'LP Reply Received', '', '', '', 'INSPECTIONAPPLICATION', 'Forward to LP Initiator pending', 'LP Raised', 'Letter To Party Reply Received', 'Forward', '2019-01-01', '2099-04-01',0);



Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'CreateInspectionLetterToParty','/inspection/lettertoparty/create/',null,(select id from eg_module where name='BPA Transanctions'),2,
'CreateInspectionLetterToParty','false','bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='CreateInspectionLetterToParty'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='CreateInspectionLetterToParty'));


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'InspectionLetterToPartySuccess','/inspection/lettertoparty/result',null,(select id from eg_module where name='BPA Transanctions'),2,
'InspectionLetterToPartySuccess','false','bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='InspectionLetterToPartySuccess'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='InspectionLetterToPartySuccess'));




Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'UpdateInspectionLetterToParty','/inspection/lettertoparty/update/',null,(select id from eg_module where name='BPA Transanctions'),2,
'UpdateInspectionLetterToParty','false','bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='UpdateInspectionLetterToParty'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='UpdateInspectionLetterToParty'));




Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'ViewInspectionLetterToParty','/inspection/lettertoparty/viewdetails',null,(select id from eg_module where name='BPA Transanctions'),2,
'ViewInspectionLetterToParty','false','bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='ViewInspectionLetterToParty'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='ViewInspectionLetterToParty'));





Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'capturesentdateInsLTP','/inspection/lettertoparty/capturesentdate',null,(select id from eg_module where name='BPA Transanctions'),2,
'capturesentdateInsLTP','false','bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='capturesentdateInsLTP'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='capturesentdateInsLTP'));





Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'InspectionLetterToPartyReply','/inspection/lettertoparty/lettertopartyreply',null,(select id from eg_module where name='BPA Transanctions'),2,
'InspectionLetterToPartyReply','false','bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='InspectionLetterToPartyReply'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='InspectionLetterToPartyReply'));




Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'INSPECTION','Letter To Party Created',now(),'Letter To Party Created',true,0,1,now());

Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'INSPECTION','Letter To Party Reply Received',now(),'Letter To Party Reply Received',true,0,1,now());
 
