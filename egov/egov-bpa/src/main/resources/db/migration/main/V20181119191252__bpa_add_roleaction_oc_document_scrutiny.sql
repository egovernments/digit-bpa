Insert into eg_module values(nextval('seq_eg_module'),'BPA Occupancy Certificate','t','bpa',(select id from eg_module where name = 'BPA Transanctions'),'Occupancy Certificate',1);

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Search OC for document scrutiny','/application/occupancy-certificate/search/document-scrutiny',null,(select id from eg_module where name='BPA Occupancy Certificate'),1,'Document Scrutiny','true','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='Document Scrutiniser'),(select id from eg_action where name = 'Search OC for document scrutiny' and contextroot = 'bpa'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='Reschedule appointment for document scrutiny of OC application'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Get appointment time for date for oc','/application/occupancy-certificate/scrutiny/slot-details',null,(select id from eg_module where name='BPA Occupancy Certificate'),1,'Get appointment time for date for oc','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='Document Scrutiniser'),(select id from eg_action where name = 'Get appointment time for date for oc' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='CITIZEN'),(select id from eg_action where name = 'Get appointment time for date for oc' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='BUSINESS'),(select id from eg_action where name = 'Get appointment time for date for oc' and contextroot = 'bpa'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'View Oc Scheduled Appointment Details','/application/occupancy-certificate/scrutiny/view',null,((select id from eg_module where name='BPA Transanctions')),1,'View Oc Scheduled Appointment Details','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='Document Scrutiniser'),(select id from eg_action where name = 'View Oc Scheduled Appointment Details' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='CITIZEN'),(select id from eg_action where name = 'View Oc Scheduled Appointment Details' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='BUSINESS'),(select id from eg_action where name = 'View Oc Scheduled Appointment Details' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='BPA Approver'),(select id from eg_action where name = 'View Oc Scheduled Appointment Details' and contextroot = 'bpa'));






