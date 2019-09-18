


INSERT INTO eg_appconfig ( ID, KEYNAME, DESCRIPTION, VERSION, MODULE ) VALUES (nextval('SEQ_EG_APPCONFIG'), 'OWNERSHIPAPPLNFEECOLLECTIONREQUIRED', 'ownership application fee collection required or not',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, CONFIG, EFFECTIVEFROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEYNAME='OWNERSHIPAPPLNFEECOLLECTIONREQUIRED' AND MODULE =(select id from eg_module where name='BPA')),current_date, 'NO',0);


INSERT INTO eg_appconfig ( ID, KEYNAME, DESCRIPTION, VERSION, MODULE ) VALUES (nextval('SEQ_EG_APPCONFIG'), 'OWNERSHIPFEECOLLECTIONREQUIRED', 'ownership fee collection required or not',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, CONFIG, EFFECTIVEFROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEYNAME='OWNERSHIPFEECOLLECTIONREQUIRED' AND MODULE =(select id from eg_module where name='BPA')),current_date, 'YES',0);

