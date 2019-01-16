-----------------------Enable/Disable app config for field inspection schedule/reschedue for regular permit application------------

INSERT INTO eg_appconfig ( ID, KEY_NAME, DESCRIPTION, VERSION, MODULE ) VALUES
(nextval('SEQ_EG_APPCONFIG'), 'REGULAR_PERMIT_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED', 'To enable/disable field inspection scheduling one day permit application integration',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION )
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='REGULAR_PERMIT_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED'
 AND MODULE =(select id from eg_module where name='BPA')),current_date, 'YES',0);

-----------------------Enable/Disable app config for field inspection schedule/reschedue for one day permit application------------

INSERT INTO eg_appconfig ( ID, KEY_NAME, DESCRIPTION, VERSION, MODULE ) VALUES
(nextval('SEQ_EG_APPCONFIG'), 'ONE_DAY_PERMIT_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED', 'To enable/disable field inspection scheduling one day permit application integration',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION )
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='ONE_DAY_PERMIT_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED'
 AND MODULE =(select id from eg_module where name='BPA')),current_date, 'NO',0);


------------Occupancy certificate changes------------------------------------------

-----------------------Enable/Disable app config for field inspection schedule/reschedue for occupancy certificate application------------

INSERT INTO eg_appconfig ( ID, KEY_NAME, DESCRIPTION, VERSION, MODULE ) VALUES
(nextval('SEQ_EG_APPCONFIG'), 'OC_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED', 'To enable/disable field inspection scheduling occupancy certificate application integration',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION )
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='OC_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED'
 AND MODULE =(select id from eg_module where name='BPA')),current_date, 'YES',0);