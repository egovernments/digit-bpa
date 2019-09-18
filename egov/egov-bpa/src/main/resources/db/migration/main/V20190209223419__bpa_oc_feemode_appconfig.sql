----OC_FEEMODE_CONFIG
INSERT INTO eg_appconfig ( ID, KEYNAME, DESCRIPTION, VERSION, MODULE )
VALUES (nextval('SEQ_EG_APPCONFIG'), 'OC_FEE_CALCULATION', 'Auto and manual calculate oc fee',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, CONFIG, EFFECTIVEFROM, VALUE, VERSION )
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'), (SELECT id FROM EG_APPCONFIG WHERE KEYNAME='OC_FEE_CALCULATION' and module= (select id from eg_module where name='BPA')), current_date, 'AUTOFEECAL',0);



Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'captureOCFee','/occupancy-certificate/fee/calculateFee/',null,(select id from eg_module where name='BPA Transanctions'),1,'Capture OC Fee','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='captureOCFee'));