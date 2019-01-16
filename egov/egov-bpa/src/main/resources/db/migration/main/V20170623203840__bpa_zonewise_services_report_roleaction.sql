Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Zone wise service type report','/reports/zonewisedetails',null,(select id from eg_module where name='BPA Reports'),1,'Zone wise service type report','true','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='Zone wise service type report'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Zone wise service type report'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Report Viewer'), (select id from eg_action where name='Zone wise service type report'));

Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='Zone wise service type report'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Collect fee for bpa applications','/application/bpacollectfee',null,(select id from eg_module where name='BPA Transanctions'),20,'Collect Fee','true','bpa',0,1,now(),1,now(), (select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Collection Operator'), (select id from eg_action where name='Collect fee for bpa applications'));