Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Building Plan Application register report','/reports/bparegister',null,(select id from eg_module where name='BPA Reports'),1,'Building Plan Application register report','true','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='Building Plan Application register report'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Building Plan Application register report'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Report Viewer'), (select id from eg_action where name='Building Plan Application register report'));

Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='Building Plan Application register report'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Building Plan Application register report'));
