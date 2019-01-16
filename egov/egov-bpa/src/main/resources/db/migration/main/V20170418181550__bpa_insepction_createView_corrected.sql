
alter table egbpa_docket add  column inspection bigint;

alter table egbpa_docket add  column locationOfPlot bigint;

alter table egbpa_docket add constraint fk_insp_docket_key FOREIGN KEY (inspection)
      REFERENCES egbpa_inspection (id);
      
alter table egbpa_inspection drop column docket;


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'viewInspection','/application/view-inspection/',null,
(select id from eg_module where name='BPA Transanctions'),1,'View Inspetion details','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));



Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='viewInspection'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'createinspectiondetails','/application/createinspectiondetails/',null,
(select id from eg_module where name='BPA Transanctions'),1,'Capture Inspetion details','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));



Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='createinspectiondetails'));