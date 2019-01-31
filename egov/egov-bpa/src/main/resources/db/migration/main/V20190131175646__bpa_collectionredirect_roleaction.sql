
Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Redirect to collection fees for StakeHolder Registration application','/application/stakeholder/generate-bill',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Redirect to collection fees for StakeHolder Registration application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Collection Operator'), (select id from eg_action where name='Redirect to collection fees for StakeHolder Registration application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'), (select id from eg_action where name='Redirect to collection fees for StakeHolder Registration application'));

Insert into eg_roleaction values((select id from eg_role where name='CITIZEN'),(select id from eg_action where name='Redirect to collection fees for StakeHolder Registration application'));

Insert into eg_roleaction values((select id from eg_role where name='BUSINESS'),(select id from eg_action where name='Redirect to collection fees for StakeHolder Registration application'));