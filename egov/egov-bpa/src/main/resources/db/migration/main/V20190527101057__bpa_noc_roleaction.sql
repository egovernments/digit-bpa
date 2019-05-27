
Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'NocUser','/ajax/getNocUsersByCode',null,(select id from eg_module where name='BPA Transanctions'),18,'NocUser','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='NocUser'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='NocUser'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='NocUser'));




Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'viewNoc','/nocapplication/view',null,(select id from eg_module where name='BPA Transanctions'),
(select max(ordernumber)+1 from EG_ACTION),'viewNoc','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),
(select id from eg_action where name='viewNoc'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),
(select id from eg_action where name='viewNoc'));


Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'),
(select id from eg_action where name='viewNoc'));