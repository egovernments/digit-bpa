
Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Create Inspection','/inspection/citizen/create',null,(select id from eg_module where name='BPA Transanctions'),16,'Create Inspection','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='Create Inspection'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='Create Inspection'));


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Inspection Success','/inspection/citizen/success',null,(select id from eg_module where name='BPA Transanctions'),16,'Create Inspection','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='Inspection Success'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='Inspection Success'));


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Update Inspection','/inspection/update/',null,(select id from eg_module where name='BPA Transanctions'),16,'Create Inspection','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='Update Inspection'));

