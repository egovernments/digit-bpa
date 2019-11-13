
Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Renewal Application','/application/getrenewalapplication',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Renewal Application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),(select id from eg_action where name='Renewal Application'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),(select id from eg_action where name='Renewal Application'));




Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Ownership Renewal Application','/application/getownerrenewalapplication',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Ownership Renewal Application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),(select id from eg_action where name='Ownership Renewal Application'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),(select id from eg_action where name='Ownership Renewal Application'));





Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Active Ownership Application','/application/getactiveownershipapplication',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Active Ownership Application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),(select id from eg_action where name='Active Ownership Application'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),(select id from eg_action where name='Active Ownership Application'));