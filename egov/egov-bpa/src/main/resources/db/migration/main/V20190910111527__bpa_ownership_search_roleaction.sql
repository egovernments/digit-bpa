
Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Search Ownership Transfer','/application/ownership/transfer/search',null,(select id from eg_module where name='BPA Transanctions'),17,'Search Ownership Transfer','true','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='SYSTEM'), (select id from eg_action where name='Search Ownership Transfer'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='Search Ownership Transfer'));


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'View ownership transfer application','/application/ownership/transfer/view/',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'View ownership transfer application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));


Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='View ownership transfer application'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'),(select id from eg_action where name='View ownership transfer application'));

