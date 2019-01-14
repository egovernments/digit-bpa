Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'get one day permit slot','/ajax/getOneDayPermitSlotByBoundary',null,(select id from eg_module where name='BPA Transanctions'),34,'get one day permit slot','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='get one day permit slot'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='get one day permit slot'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='get one day permit slot'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='get one day permit slot'));
