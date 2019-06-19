
Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'UpdateInspection','/inspection/update-submit/',null,(select id from eg_module where name='BPA Transanctions'),16,'UpdateInspection','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='UpdateInspection'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='UpdateInspection'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='UpdateInspection'));


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Create Inconstruction Inspection','/inspection/createinspection/',null,(select id from eg_module where name='BPA Transanctions'),16,'Create Inconstruction Inspection','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='Create Inconstruction Inspection'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='Create Inconstruction Inspection'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Create Inconstruction Inspection'));


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Update Inconstruction Inspection','/inspection/updateinspection/',null,(select id from eg_module where name='BPA Transanctions'),16,'Update Inconstruction Inspection','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='Update Inconstruction Inspection'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='Update Inconstruction Inspection'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Update Inconstruction Inspection'));




Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Success InconstructionInspection','/inspection/success/view-inspection-details/',null,(select id from eg_module where name='BPA Transanctions'),16,'Success InconstructionInspection','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='Success InconstructionInspection'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='Success InconstructionInspection'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Success InconstructionInspection'));



Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'View InconstructionInspection','/inspection/show-inspection-details/',null,(select id from eg_module where name='BPA Transanctions'),16,'View InconstructionInspection','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='View InconstructionInspection'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='View InconstructionInspection'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='View InconstructionInspection'));




Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Inspection schedule appointment','/inspection/scheduleappointment/',null,(select id from eg_module where name='BPA Transanctions'),16,'Inspection schedule appointment','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='Inspection schedule appointment'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='Inspection schedule appointment'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Inspection schedule appointment'));





Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Inspection reschedule appointment','/inspection/rescheduleappointment/',null,(select id from eg_module where name='BPA Transanctions'),16,'View InconstructionInspection','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='Inspection reschedule appointment'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='Inspection reschedule appointment'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Inspection reschedule appointment'));



Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'view inspection appointment','/inspection/appointment/view-details/',null,(select id from eg_module where name='BPA Transanctions'),16,'View InconstructionInspection','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='view inspection appointment'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='view inspection appointment'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='view inspection appointment'));



ALTER TABLE egbpa_inspection_schedule RENAME TO egbpa_inconstruction_inspection;

drop sequence seq_egbpa_inspection_schedule;

create sequence seq_egbpa_inconstruction_inspection;

alter table egbpa_inconstruction_inspection rename inspectioncommon to inspection;
