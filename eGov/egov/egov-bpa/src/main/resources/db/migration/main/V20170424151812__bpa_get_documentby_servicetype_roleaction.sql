Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'get documents by service type','/application/getdocumentlistbyservicetype',null,(select id from eg_module where name='BPA Transanctions'),16,'get documents by service typeon','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='get documents by service type'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='get documents by service type'));

delete from eg_roleaction where roleid=(select id from eg_role where name='ULB Operator') and actionid=(select id from eg_action where name='ModifyInspection');