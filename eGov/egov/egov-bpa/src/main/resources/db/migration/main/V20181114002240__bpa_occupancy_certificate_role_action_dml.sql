INSERT INTO eg_appconfig ( ID, KEY_NAME, DESCRIPTION, VERSION, MODULE ) VALUES
(nextval('SEQ_EG_APPCONFIG'), 'APPLICATIONFEECOLLECTIONREQUIRED', 'value for application fee collection is required or not',
0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION )
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='APPLICATIONFEECOLLECTIONREQUIRED'
 AND MODULE =(select id from eg_module where name='BPA')),current_date, 'NO',0);

INSERT INTO eg_wf_types (id, module, type, link, createdby, createddate, lastmodifiedby, lastmodifieddate, enabled, grouped, typefqn, displayname, version)
 VALUES (nextval('seq_eg_wf_types'), (select id from eg_module where name='BPA'), 'OccupancyCertificate', '/bpa/application/occupancy-certificate/update/:ID', 1, now(), 1, now(), 'Y', 'N', 'org.egov.bpa.transaction.entity.oc.OccupancyCertificate', 'Occupancy Certificate', 0);

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Get application details by permit number','/application/findby-permit-number',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Get application details by permit number','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),
(select id from eg_action where name='Get application details by permit number'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),
(select id from eg_action where name='Get application details by permit number'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Apply for occupancy certificate','/application/citizen/occupancy-certificate/apply',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Apply For Occupancy Certificate','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),
(select id from eg_action where name='Apply for occupancy certificate'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),
(select id from eg_action where name='Apply for occupancy certificate'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Citizen Submit occupancy certificate application','/application/citizen/occupancy-certificate/submit',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Citizen Submit occupancy certificate application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),
(select id from eg_action where name='Citizen Submit occupancy certificate application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),
(select id from eg_action where name='Citizen Submit occupancy certificate application'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Citizen Edit occupancy certificate application details','/application/citizen/occupancy-certificate/update',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Citizen Edit occupancy certificate application details','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),
(select id from eg_action where name='Citizen Edit occupancy certificate application details'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),
(select id from eg_action where name='Citizen Edit occupancy certificate application details'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Citizen Update occupancy certificate application details','/application/citizen/occupancy-certificate/update-submit',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Citizen Update occupancy certificate application details','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),
(select id from eg_action where name='Citizen Update occupancy certificate application details'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),
(select id from eg_action where name='Citizen Update occupancy certificate application details'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Employee Update occupancy certificate application details','/application/occupancy-certificate/update',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Employee Update occupancy certificate application details','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Employee Update occupancy certificate application details'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Employee submit occupancy certificate application details','/application/occupancy-certificate/update-submit',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Employee submit occupancy certificate application details','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Employee submit occupancy certificate application details'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Show document scrutiny form for occupancy certificate application','/application/occupancy-certificate/document-scrutiny',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Show document scrutiny form for occupancy certificate application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Show document scrutiny form for occupancy certificate application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='Show document scrutiny form for occupancy certificate application'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Submit occupancy certificate application document scrutiny','/application/occupancy-certificate/document-scrutiny/submit',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Submit occupancy certificate application document scrutiny','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Submit occupancy certificate application document scrutiny'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'),(select id from eg_action where name='Submit occupancy certificate application document scrutiny'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Redirect to collect fees for occupancy certificate application','/application/occupancy-certificate/generate-bill',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Redirect to collect fees for occupancy certificate application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Collection Operator'), (select id from eg_action where name='Redirect to collect fees for occupancy certificate application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'), (select id from eg_action where name='Redirect to collect fees for occupancy certificate application'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Schedule appointment for occupancy certificate application','/application/occupancy-certificate/schedule-appointment',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Schedule appointment for occupancy certificate application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Schedule appointment for occupancy certificate application'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Reschedule appointment for occupancy certificate application','/application/occupancy-certificate/reschedule-appointment',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Reschedule appointment for occupancy certificate application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Reschedule appointment for occupancy certificate application'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Schedule appointment for document scrutiny of occupancy certificate application','/application/occupancy-certificate/scrutiny/schedule',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Schedule appointment for document scrutiny of occupancy certificate application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Schedule appointment for document scrutiny of occupancy certificate application'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Reschedule appointment for document scrutiny of OC application','/application/occupancy-certificate/scrutiny/reschedule',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Reschedule appointment for document scrutiny of OC application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Reschedule appointment for document scrutiny of OC application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),(select id from eg_action where name='Reschedule appointment for document scrutiny of OC application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),(select id from eg_action where name='Reschedule appointment for document scrutiny of OC application'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'View scheduled appointment details of doc scrutiny for OC application','/application/occupancy-certificate/scrutiny/view-appointment',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'View scheduled appointment details of doc scrutiny for OC application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='View scheduled appointment details of doc scrutiny for OC application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),(select id from eg_action where name='View scheduled appointment details of doc scrutiny for OC application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),(select id from eg_action where name='View scheduled appointment details of doc scrutiny for OC application'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Occupancy certificate application success','/application/occupancy-certificate/success',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Occupancy certificate application success','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Occupancy certificate application success'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'View Bpa application details by permit number','/application/view-by-permit-number',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'View Bpa application details by permit number','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='View Bpa application details by permit number'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='SYSTEM'), (select id from eg_action where name='View Bpa application details by permit number'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),(select id from eg_action where name='View Bpa application details by permit number'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),(select id from eg_action where name='View Bpa application details by permit number'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'),(select id from eg_action where name='View Bpa application details by permit number'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='View Bpa application details by permit number'));
