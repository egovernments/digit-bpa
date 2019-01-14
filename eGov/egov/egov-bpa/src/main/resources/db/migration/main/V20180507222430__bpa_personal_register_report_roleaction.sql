
Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Personal Register report','/reports/personalregister',null,(select id from eg_module where name='BPA Reports'),1,'Personal Register report','true','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Personal Register report'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Report Viewer'), (select id from eg_action where name='Personal Register report'));

Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='Personal Register report'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='SYSTEM'), (select id from eg_action where name='Zone wise service type report'));
