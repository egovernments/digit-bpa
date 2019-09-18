INSERT INTO eg_appconfig ( ID, KEYNAME, DESCRIPTION, VERSION, MODULE ) VALUES
(nextval('SEQ_EG_APPCONFIG'), 'PERMIT_EXTENSION_FEE_IN_PERCENTAGE', 'Permit extension fee percentage on permit fee',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, CONFIG, EFFECTIVEFROM, VALUE, VERSION ) 
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEYNAME='PERMIT_EXTENSION_FEE_IN_PERCENTAGE'
 AND MODULE =(select id from eg_module where name='BPA')),current_date, '10',0);

INSERT INTO eg_appconfig ( ID, KEYNAME, DESCRIPTION, VERSION, MODULE ) VALUES
(nextval('SEQ_EG_APPCONFIG'), 'PERMIT_RENEWAL_FEE_IN_PERCENTAGE', 'Permit renewal fee percentage on permit fee ',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, CONFIG, EFFECTIVEFROM, VALUE, VERSION ) 
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEYNAME='PERMIT_RENEWAL_FEE_IN_PERCENTAGE'
 AND MODULE =(select id from eg_module where name='BPA')),current_date, '50',0);