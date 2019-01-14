Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Search BPA Application','/application/search',null,(select id from eg_module where name='BPA Transanctions'),9,'Search Application','true','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='Search BPA Application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Collection Operator'), (select id from eg_action where name='Search BPA Application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Search BPA Application'));


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'View BPA Application Details','/application/view',null,(select id from eg_module where name='BPA Transanctions'),10,'View BPA Application Details','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='View BPA Application Details'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Collection Operator'), (select id from eg_action where name='View BPA Application Details'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='View BPA Application Details'));