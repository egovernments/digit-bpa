

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'generate ownership transfer rejection notice','/application/ownership/transfer/rejectionnotice',null,(select id from eg_module where name='BPA Transanctions'),17,'generate ownership transfer rejection notice','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='generate ownership transfer rejection notice'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='SYSTEM'), (select id from eg_action where name='generate ownership transfer rejection notice'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='generate ownership transfer rejection notice'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='generate ownership transfer rejection notice'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Bpa Administrator'), (select id from eg_action where name='generate ownership transfer rejection notice'));


