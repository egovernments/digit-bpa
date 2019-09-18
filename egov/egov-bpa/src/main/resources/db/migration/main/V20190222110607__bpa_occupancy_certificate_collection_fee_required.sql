delete from eg_appconfig_values where CONFIG=(SELECT id FROM EG_APPCONFIG WHERE KEYNAME='APPLICATIONFEECOLLECTIONREQUIRED' AND MODULE =(select id from eg_module where name='BPA'));

delete from eg_appconfig where KEYNAME='APPLICATIONFEECOLLECTIONREQUIRED' AND MODULE =(select id from eg_module where name='BPA');

INSERT INTO eg_appconfig ( ID, KEYNAME, DESCRIPTION, VERSION, MODULE ) VALUES (nextval('SEQ_EG_APPCONFIG'), 'OCAPPLICATIONFEECOLLECTIONREQUIRED', 'value for oc application fee collection is required or not',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, CONFIG, EFFECTIVEFROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEYNAME='OCAPPLICATIONFEECOLLECTIONREQUIRED' AND MODULE =(select id from eg_module where name='BPA')),current_date, 'NO',0);