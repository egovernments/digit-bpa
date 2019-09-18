INSERT INTO eg_appconfig ( ID, KEYNAME, DESCRIPTION, VERSION, MODULE ) VALUES
(nextval('SEQ_EG_APPCONFIG'), 'SLA_OWNERSHIP_TRANSFER', 'Sla Ownership Transfer',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, CONFIG, EFFECTIVEFROM, VALUE, VERSION ) 
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEYNAME='SLA_OWNERSHIP_TRANSFER'
 AND MODULE =(select id from eg_module where name='BPA')),current_date, '15',0);