Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Update bpa aplication details form submit','/application/update-submit',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Update bpa aplication details form submit','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='Update bpa aplication details form submit'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),
(select id from eg_action where name='Update bpa aplication details form submit'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='Update bpa aplication details form submit'));


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Update bpa aplication details form submit by citizen','/application/citizen/update-submit',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Update bpa aplication details form submit by citizen','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='Update bpa aplication details form submit by citizen'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),
(select id from eg_action where name='Update bpa aplication details form submit by citizen'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='Update bpa aplication details form submit by citizen'));