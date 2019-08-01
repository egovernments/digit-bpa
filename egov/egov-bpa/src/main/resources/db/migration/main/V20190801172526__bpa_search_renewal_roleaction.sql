Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Search Permit Renewal','/application/permitrenewal/search',null,(select id from eg_module where name='BPA Transanctions'),17,'Search Permit Renewal','true','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='SYSTEM'), (select id from eg_action where name='Search Permit Renewal'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='Search Permit Renewal'));



Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'View Permit Renewal','/application/permitrenewal/view/',null,(select id from eg_module where name='BPA Transanctions'),17,'View Permit Renewal','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='SYSTEM'), (select id from eg_action where name='View Permit Renewal'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='View Permit Renewal'));

