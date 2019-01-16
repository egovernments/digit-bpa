Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Document Scrutiny for bpa applications','/application/bpadocumentscrutiny',null,(select id from eg_module where name='BPA Transanctions'),20,'Document Scrutiny','true','bpa',0,1,now(),1,now(), (select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Document Scrutiny for bpa applications'));

