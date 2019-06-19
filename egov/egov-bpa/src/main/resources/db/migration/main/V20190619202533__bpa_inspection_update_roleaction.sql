
Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'UpdateInspectionApp','/inspection/citizen/update/',null,(select id from eg_module where name='BPA Transanctions'),16,'UpdateInspectionApp','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));


Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='UpdateInspectionApp'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='UpdateInspectionApp'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'),
(select id from eg_action where name='Get application details by permit number'));