Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'get slotdetails by date and zone','/application/scrutiny/slotdetails',null,(select id from eg_module where name='BPA Transanctions'),27,'get slotdetails by date and zone','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='get slotdetails by date and zone'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='get slotdetails by date and zone'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='get slotdetails by date and zone'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'view scheduled scrutiny details','/application/scrutiny/view',null,(select id from eg_module where name='BPA Transanctions'),28,'get slotdetails by date and zone','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='view scheduled scrutiny details'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='view scheduled scrutiny details'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='view scheduled scrutiny details'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='reschedule appointment for document scrutiny'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='reschedule appointment for document scrutiny'));

update egbpa_slotapplication set isRescheduledByEmployee =false where isRescheduledByEmployee is null;

update egbpa_slotapplication set isRescheduledByCitizen =false where isRescheduledByCitizen is null;

alter table egbpa_slotapplication ALTER COLUMN isRescheduledByCitizen SET default false;

alter table egbpa_slotapplication ALTER COLUMN isRescheduledByEmployee SET default false;
