delete from eg_appconfig_values where KEY_ID=(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='APPLICATIONFEECOLLECTIONREQUIRED' AND MODULE =(select id from eg_module where name='BPA'));

delete from eg_appconfig where KEY_NAME='APPLICATIONFEECOLLECTIONREQUIRED' AND MODULE =(select id from eg_module where name='BPA');

INSERT INTO eg_appconfig ( ID, KEY_NAME, DESCRIPTION, VERSION, MODULE ) VALUES (nextval('SEQ_EG_APPCONFIG'), 'OCAPPLICATIONFEECOLLECTIONREQUIRED', 'value for oc application fee collection is required or not',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='OCAPPLICATIONFEECOLLECTIONREQUIRED' AND MODULE =(select id from eg_module where name='BPA')),current_date, 'NO',0);